package com.rayzhou.springboottemplate.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rayzhou.springboottemplate.api.GeminiApi;
import com.rayzhou.springboottemplate.common.BaseResponse;
import com.rayzhou.springboottemplate.common.DeleteRequest;
import com.rayzhou.springboottemplate.common.ErrorCode;
import com.rayzhou.springboottemplate.common.ResultUtils;
import com.rayzhou.springboottemplate.exception.BusinessException;
import com.rayzhou.springboottemplate.exception.ThrowUtils;
import com.rayzhou.springboottemplate.model.dto.chart.*;
import com.rayzhou.springboottemplate.model.entity.Chart;
import com.rayzhou.springboottemplate.model.entity.User;
import com.rayzhou.springboottemplate.model.vo.BIResponse;
import com.rayzhou.springboottemplate.service.ChartService;
import com.rayzhou.springboottemplate.service.UserService;
import com.rayzhou.springboottemplate.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

import static com.rayzhou.springboottemplate.api.GeminiApi.SPLIT;

@RestController
@RequestMapping("/chart")
@Slf4j
public class ChartController {

    @Resource
    private ChartService chartService;

    @Resource
    private GeminiApi geminiApi;

    @Resource
    private UserService userService;



    @PostMapping("/add")
    public BaseResponse<Long> addChart(@RequestBody ChartAddRequest chartAddRequest, HttpServletRequest request) {
        if (chartAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Chart chart = new Chart();
        BeanUtils.copyProperties(chartAddRequest, chart);

        User loginUser = userService.getLoginUser(request);
        chart.setUserId(loginUser.getId());

        boolean result = chartService.save(chart);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newChartId = chart.getId();
        return ResultUtils.success(newChartId);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteChart(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();

        Chart oldChart = chartService.getById(id);
        ThrowUtils.throwIf(oldChart == null, ErrorCode.NOT_FOUND_ERROR);

        if (!oldChart.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = chartService.removeById(id);
        return ResultUtils.success(b);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateChart(@RequestBody ChartUpdateRequest chartUpdateRequest) {
        if (chartUpdateRequest == null || chartUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Chart chart = new Chart();
        BeanUtils.copyProperties(chartUpdateRequest, chart);

        long id = chartUpdateRequest.getId();

        Chart oldChart = chartService.getById(id);
        ThrowUtils.throwIf(oldChart == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = chartService.updateById(chart);
        return ResultUtils.success(result);
    }

    @PostMapping("/generate")
    public BaseResponse<BIResponse> genChart(@RequestPart("file") MultipartFile multipartFile,
                                         GenChartRequest genChartRequest, HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        String goal = genChartRequest.getGoal();
        String chartType = genChartRequest.getChartType();
        String chartName = genChartRequest.getChartName();
        ThrowUtils.throwIf(StringUtils.isBlank(goal), ErrorCode.PARAMS_ERROR, "Goal is Empty");
        ThrowUtils.throwIf(StringUtils.isBlank(chartName)||chartName.length() > 100, ErrorCode.PARAMS_ERROR, "Parameter error in chart name");

        String result = ExcelUtils.excelToCsv(multipartFile);

        String aiResponse = geminiApi.generate(goal, result, chartType);
        String[] splits = aiResponse.split(SPLIT);

        ThrowUtils.throwIf(splits.length < 3, ErrorCode.SYSTEM_ERROR, "AI Generation Error");


        Chart chart = new Chart();
        chart.setUserId(user.getId());
        chart.setGoal(goal);
        chart.setChartData(result);
        chart.setChartType(chartType);
        chart.setGenChart(splits[2].trim());
        chart.setGenResult(splits[1].trim());

        boolean saveResult = chartService.save(chart);
        ThrowUtils.throwIf(!saveResult, ErrorCode.SYSTEM_ERROR, "Fail to save chart");

        BIResponse response = BIResponse.builder()
                .chartId(chart.getId())
                .chartData(chart.getGenChart())
                .analysisData(chart.getGenResult())
                .build();

        return ResultUtils.success(response);
    }

    @GetMapping("/get")
    public BaseResponse<Chart> getChartById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Chart chart = chartService.getById(id);
        if (chart == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(chart);
    }

    @PostMapping("/list/page")
    public BaseResponse<Page<Chart>> listChartByPage(@RequestBody ChartQueryRequest chartQueryRequest) {
        long current = chartQueryRequest.getCurrent();
        long size = chartQueryRequest.getPageSize();
        Page<Chart> chartPage = chartService.page(new Page<>(current, size),
                chartService.getQueryWrapper(chartQueryRequest));
        return ResultUtils.success(chartPage);
    }


    @PostMapping("/my/list/page/")
    public BaseResponse<Page<Chart>> listMyChartByPage(@RequestBody ChartQueryRequest chartQueryRequest,
                                                         HttpServletRequest request) {
        if (chartQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        chartQueryRequest.setUserId(loginUser.getId());
        long current = chartQueryRequest.getCurrent();
        long size = chartQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Chart> chartPage = chartService.page(new Page<>(current, size),
                chartService.getQueryWrapper(chartQueryRequest));
        return ResultUtils.success(chartPage);
    }


    @PostMapping("/edit")
    public BaseResponse<Boolean> editChart(@RequestBody ChartEditRequest chartEditRequest, HttpServletRequest request) {
        if (chartEditRequest == null || chartEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Chart chart = new Chart();
        BeanUtils.copyProperties(chartEditRequest, chart);

        User loginUser = userService.getLoginUser(request);
        long id = chartEditRequest.getId();

        Chart oldChart = chartService.getById(id);
        ThrowUtils.throwIf(oldChart == null, ErrorCode.NOT_FOUND_ERROR);

        if (!oldChart.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = chartService.updateById(chart);
        return ResultUtils.success(result);
    }
}
