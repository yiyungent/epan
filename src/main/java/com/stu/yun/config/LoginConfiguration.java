package com.stu.yun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

    /**
     * 一定要加上 @Bean 这样 LoginHandlerIntercptor 内才能注入 service
     * @return
     */
    @Bean
    public LoginHandlerIntercptor getLoginHandlerIntercptor(){
        return new LoginHandlerIntercptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        LoginHandlerIntercptor loginInterceptor = getLoginHandlerIntercptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);
        // 拦截路径
        loginRegistry.addPathPatterns("/admin/*");
//        loginRegistry.addPathPatterns("/*"); // 500
        loginRegistry.addPathPatterns("/");
        // 排除路径
//        loginRegistry.excludePathPatterns("/");
        loginRegistry.excludePathPatterns("/login");
        loginRegistry.excludePathPatterns("/api/rw/maps");
        loginRegistry.excludePathPatterns("/admin/login");
        // 排除资源请求
        loginRegistry.excludePathPatterns("/css/login/*.css");
        loginRegistry.excludePathPatterns("/js/login/**/*.js");
        loginRegistry.excludePathPatterns("/css/*");
        loginRegistry.excludePathPatterns("/js/*");
        loginRegistry.excludePathPatterns("/lib/*");
    }

}
