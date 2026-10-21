package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// 切面类
@Slf4j
@Aspect
@Component
public class MyAspect6 {

    /**
     * execution(public void com.itheima.service.impl.DeptServiceImpl.delete(java.lang.Integer))
     *          访问修饰符?  返回值   包名.类名.?方法名(方法参数类型)
     */
    // @Pointcut("execution(public void com.itheima.service.impl.DeptServiceImpl.delete(java.lang.Integer))")
    // @Pointcut("execution(void com.itheima.service.impl.DeptServiceImpl.delete(java.lang.Integer))")
    // @Pointcut("execution(void delete(java.lang.Integer))") // 包名.类名不建议省略
    // @Pointcut("execution(void com.itheima.service.DeptService.delete(java.lang.Integer))")

    // @Pointcut("execution(void com.itheima.service.DeptService.*(java.lang.Integer))")
    // @Pointcut("execution(* com.*.service.DeptService.*(*))")
    // @Pointcut("execution(* com.itheima.service.*Service.delete*(*))")

    // @Pointcut("execution(* com.itheima.service.DeptService.*(..))")
    // @Pointcut("execution(* com..DeptService.*(..))")
    // @Pointcut("execution(* com..*.*(..))")
    // @Pointcut("execution(* *(..))") // 慎用

    @Pointcut("execution(* com.itheima.service.DeptService.list()) || execution(* com.itheima.service.DeptService.delete(java.lang.Integer))")
    public void pt(){}

    @Before("pt()")
    private void before() {
        log.info("MyAspect6 ... before ...");
    }

}
