package com.mix.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class AnnotationAopPointCut {

    @Pointcut("@annotation(com.mix.aop.AnnotationMealCut)")
    private void eatMeal(){ }
    @Around("eatMeal()")
    public void handleMealCutResult(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("try");
        joinPoint.proceed();
        System.out.println("catch");
    }
}
