package org.example.vecinomerchantserver.component;

import lombok.RequiredArgsConstructor;
import org.example.vecinomerchantserver.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/register")
                .excludePathPatterns("/api/resetPassword")
                .excludePathPatterns("/api/upload")
                .excludePathPatterns("/pictures/**");

    }
    @Value("${upload.local-path}")
    private String localPath;
    @Value("${upload.visit-prefix}")
    private String visitPrefix;
    /**
     * 配置静态资源映射
     * 浏览器通过：ip:端口/pictures/xxx.jpg 即可访问本地E盘的图片
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(visitPrefix + "**")
                .addResourceLocations("file:" + localPath);
    }
}
