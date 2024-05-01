package com.rayzhou.springboottemplate.model.dto.chart;

import com.rayzhou.springboottemplate.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChartQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

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

    private Long userId;
}
