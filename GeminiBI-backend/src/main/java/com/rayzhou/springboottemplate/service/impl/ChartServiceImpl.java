package com.rayzhou.springboottemplate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rayzhou.springboottemplate.model.entity.Chart;
import com.rayzhou.springboottemplate.service.ChartService;
import com.rayzhou.springboottemplate.mapper.ChartMapper;
import org.springframework.stereotype.Service;

/**
* @author Ray
* @description 针对表【chart(chart)】的数据库操作Service实现
* @createDate 2024-04-25 01:39:02
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}




