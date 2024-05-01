package com.rayzhou.springboottemplate.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

@Data
public class GenChartRequest implements Serializable {

    /**
     * analysis goal
     */
    private String goal;

    /**
     * chart type
     */
    private String chartType;

    /**
     * chart name
     */
    private String chartName;
}
