package com.mix.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class BeforeEatAopPointCut {

    @Pointcut("execution(* com.mix.aop.EatMotionsImpl.eatMeal())")
    private void eatMeal(){}
    @Around("eatMeal()")
    public void excuteAroundMeal(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("wash");
        joinPoint.proceed();
        System.out.println("wash2");
    }

}
