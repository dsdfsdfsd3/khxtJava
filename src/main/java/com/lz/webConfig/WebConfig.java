package com.lz.webConfig;

import com.lz.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 注意：类名建议改为 WebConfig（大驼峰，符合Java规范）
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，排除公开接口
        // 注意：具体的排除路径逻辑主要在 LoginInterceptor 中处理
        // 这里只需要注册拦截器即可
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**");
    }
}