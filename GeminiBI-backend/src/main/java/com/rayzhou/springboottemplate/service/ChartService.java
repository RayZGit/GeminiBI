package com.rayzhou.springboottemplate.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rayzhou.springboottemplate.model.dto.chart.ChartQueryRequest;
import com.rayzhou.springboottemplate.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Ray
* @createDate 2024-04-25 01:39:02
*/
public interface ChartService extends IService<Chart> {
    /**
     * getQueryWrapper
     *
     * @param postQueryRequest
     * @return
     */
    QueryWrapper<Chart> getQueryWrapper(ChartQueryRequest postQueryRequest);
}
