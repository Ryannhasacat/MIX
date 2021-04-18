package com.mix.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomApply {

    @Autowired
    CustomEventPublisher customEventPublisher;

    public void mainMotion(){
        apply();
        customEventPublisher.publishEvent("execute");
    }

    public static void apply(){
        System.out.println("user  register success!");
    }
}
