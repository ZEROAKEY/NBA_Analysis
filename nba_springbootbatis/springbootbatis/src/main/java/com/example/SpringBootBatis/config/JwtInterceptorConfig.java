package com.example.SpringBootBatis.config;

import com.example.SpringBootBatis.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**") //拦截所有路径
//                .excludePathPatterns("/login","/login/register"); //排除登陆请求
                .excludePathPatterns("/**"); //排除登陆请求
    }
}
