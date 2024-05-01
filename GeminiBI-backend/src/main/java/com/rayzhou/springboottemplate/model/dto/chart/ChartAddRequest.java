package com.rayzhou.springboottemplate.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChartAddRequest implements Serializable {
    /**
     * analysis goal
     */
    private String goal;

    /**
     * table analysis
     */
    private String chartData;

    /**
     * chart type
     */
    private String chartType;
    /**
     * chart name
     */
    private String chartName;


}
