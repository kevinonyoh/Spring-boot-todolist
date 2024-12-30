package com.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.todolist.interceptors.LoggingInterceptor;
import com.todolist.interceptors.PublicEndpointInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;

    private PublicEndpointInterceptor publicEndpointInterceptor;


    public WebConfig(LoggingInterceptor loggingInterceptor, PublicEndpointInterceptor publicEndpointInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
        this.publicEndpointInterceptor = publicEndpointInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");
        registry.addInterceptor(publicEndpointInterceptor);
    }

}
