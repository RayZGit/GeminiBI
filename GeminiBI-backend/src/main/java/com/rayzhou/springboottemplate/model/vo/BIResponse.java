package com.rayzhou.springboottemplate.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class BIResponse implements Serializable {

    private String chartData;

    private String analysisData;
}
