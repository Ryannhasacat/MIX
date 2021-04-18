package com.mix.aop;

import org.springframework.stereotype.Component;

@Component
public class EatMotionsImpl implements AopServiceMotions{
    @Override
    @AnnotationMealCut
    public void eatMeal() {
        System.out.println("im meal");
    }

    @Override
    @AnnotationMealCut
    public void eatVege() {
        System.out.println("im vegetable");
    }

    @Override
    public void eatMeat() {
        System.out.println("im meat");
    }
}
