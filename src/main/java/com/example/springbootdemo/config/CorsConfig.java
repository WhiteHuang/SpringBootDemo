package com.example.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
public class CorsConfig {
    private static final long MAX_TIME=24*60*60;

    private CorsConfiguration buildConfig(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");//设置访问源请求地址 解决跨域问题
        configuration.addAllowedHeader("*");//设置访问源请求头
        configuration.addAllowedMethod("*");//设置访问源请求方法
        configuration.setMaxAge(MAX_TIME);
        return configuration;
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",buildConfig());//设置跨域配置
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
