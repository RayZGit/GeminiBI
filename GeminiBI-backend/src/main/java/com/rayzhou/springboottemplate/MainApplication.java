package com.rayzhou.springboottemplate;

import org.mapstruct.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

/**
 *  Application Initialization
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@MapperScan("com.rayzhou.springboottemplate.mapper")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}