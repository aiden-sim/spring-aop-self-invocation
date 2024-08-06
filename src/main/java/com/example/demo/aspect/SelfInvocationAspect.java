package com.example.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SelfInvocationAspect {
    @Pointcut("execution(void com.example.demo.service..*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void apply() {
        System.out.println("advice apply");
    }
}
