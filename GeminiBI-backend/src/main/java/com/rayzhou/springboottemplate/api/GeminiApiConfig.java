package com.rayzhou.springboottemplate.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.rayzhou.springboottemplate.api.Constant.ACCESS_KEY;

@Configuration
@ConfigurationProperties("gemini.client")
@ComponentScan
public class GeminiApiConfig {
    private String apiKey;

    @Bean
    public GeminiApiClient getGeminiApiClient() {
        return new GeminiApiClient(ACCESS_KEY);
    }
}
