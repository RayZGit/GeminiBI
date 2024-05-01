package com.rayzhou.springboottemplate.model.dto.chart;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChartUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

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

    private static final long serialVersionUID = 1L;
}