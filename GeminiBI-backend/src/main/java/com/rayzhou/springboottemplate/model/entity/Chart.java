package com.rayzhou.springboottemplate.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * chart
 * @TableName chart
 */
@TableName(value ="chart")
@Data
public class Chart implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * user Id
     */
    private Long userId;

    /**
     * analysis goal
     */
    private String goal;
    /**
     * chart name
     */
    private String chartName;

    /**
     * table analysis
     */
    private String chartData;

    /**
     * chart type
     */
    private String chartType;

    /**
     * generative chart
     */
    private String genChart;

    /**
     * generative description
     */
    private String genResult;

    /**
     * create time
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;

    /**
     * deleted
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}