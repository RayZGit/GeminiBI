package com.rayzhou.springboottemplate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rayzhou.springboottemplate.mapper.ChartMapper;
import com.rayzhou.springboottemplate.model.dto.chart.ChartQueryRequest;
import com.rayzhou.springboottemplate.model.entity.Chart;
import com.rayzhou.springboottemplate.service.ChartService;
import com.rayzhou.springboottemplate.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author Ray
* @createDate 2024-04-25 01:39:02
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{
    private static final String SORT_ORDER_ASC = "ascend";
    @Override
    public QueryWrapper<Chart> getQueryWrapper(ChartQueryRequest postQueryRequest) {
        QueryWrapper<Chart> queryWrapper = new QueryWrapper<>();
        if (postQueryRequest == null) {
            return queryWrapper;
        }
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        Long id = postQueryRequest.getId();
        String chartType = postQueryRequest.getChartType();
        String goal = postQueryRequest.getGoal();
        Long userId = postQueryRequest.getUserId();

        queryWrapper.like(StringUtils.isNotBlank(chartType), "chartType", chartType);
        queryWrapper.like(StringUtils.isNotBlank(goal), "goal", goal);


        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

}




