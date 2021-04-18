package com.mix.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerListener implements ApplicationListener<SendMailEvent> {

    @Override
    public void onApplicationEvent(SendMailEvent sendMailEvent) {
        System.out.println(sendMailEvent.getSource()+"DO SUCCESS");
    }
}
