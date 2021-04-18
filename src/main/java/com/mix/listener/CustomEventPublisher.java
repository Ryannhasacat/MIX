package com.mix.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CustomEventPublisher implements ApplicationContextAware {

    ApplicationContext applicationContext;

    public void publishEvent(String msg){
        applicationContext.publishEvent(new SendMailEvent(this,msg));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
