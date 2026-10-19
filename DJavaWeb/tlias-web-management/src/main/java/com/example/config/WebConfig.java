package com.example.config;

import com.example.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置类：注册登录校验拦截器。
 *
 * <p>实现 {@link WebMvcConfigurer} 接口并重写 {@link #addInterceptors(InterceptorRegistry)}，
 * 把 {@link LoginCheckInterceptor} 加入 Spring MVC 的拦截器链。</p>
 */
@Configuration // 配置类，启动时由 Spring 容器加载
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    /**
     * 注册拦截器，并配置拦截 / 放行的路径。
     *
     * <p>通配符说明：{@code /*} 拦截一级路径，{@code /**} 拦截所有路径。</p>
     * <p>当前规则：拦截所有请求，但放行 {@code /login}；登录接口不需要校验令牌，
     * 否则登录请求会被拦截，无法获取 JWT。</p>
     *
     * @param registry 拦截器注册表，由 Spring MVC 注入
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor) // 登录校验拦截器
                .addPathPatterns("/**")                // 拦截所有路径
                .excludePathPatterns("/login");        // 放行登录接口
    }
}
