package com.example.Filter;

import javax.servlet.*;
import java.io.IOException;

// @WebFilter(urlPatterns = "/*") // 拦截所有路径请求
// @WebFilter(urlPatterns = "/login") // 拦截登录请求
// @WebFilter(urlPatterns = "/depts/*") // 拦截/depts路径下所有的请求
public class DemoFilter implements Filter {
    /**
     * 初始化方法，只调用一次
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Demo init 初始化方法执行了");
    }

    /**
     * 连接到请求后都会调用，调用多次
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Demo 拦截到了请求...放行前的逻辑");
        // 放行
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("Demo 拦截到了请求...放行后的逻辑");
    }

    /**
     *  销毁方法，只调用一次
     */
    @Override
    public void destroy() {
        System.out.println("Demo destroy 销毁方法执行了");
    }
}
