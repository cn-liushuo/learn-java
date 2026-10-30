package com.example.controller;

import com.example.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Cookie, HttpSession 演示
 */
@Slf4j
@RestController
public class SessionController {

    // 设置 Cookie
    @GetMapping("/c1")
    public Result cookie1(HttpServletResponse response) {
        response.addCookie(new Cookie("login_username", "example")); // 设置 Cookie/响应Cookie
        return Result.success();
    }

    // 获取 Cookie
    @GetMapping("/c2")
    public Result cookie2(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies(); // 获取所有的 Cookie
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login_username")) { // 输入 name 为 login_username 的 cookie
                System.out.println("login_username:" + cookie.getValue());
            }
        }
        return Result.success();
    }



    // 往 HttpSession 中存储值
    @GetMapping("/s1")
    public Result session1(HttpSession session) {
        log.info("HttpSession-s1: {}", session.hashCode()); // 往 session 中存储数据

        session.setAttribute("LoginUser", "tom"); // 往 session 中存储数据
        return Result.success();
    }

    // 从 HttpSession 中取值
    @GetMapping("/s2")
    public Result session2(HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info("HttpSession-s2: {}", session.hashCode());

        Object loginUser = session.getAttribute("LoginUser"); // 从 session 中获取数据
        log.info("loginUser: {}", loginUser);
        return Result.success(loginUser);
    }
}
