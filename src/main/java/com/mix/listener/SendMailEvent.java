package com.mix.listener;

import org.springframework.context.ApplicationEvent;


public class SendMailEvent extends ApplicationEvent {

    private String msg;

    public SendMailEvent(Object source,String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
