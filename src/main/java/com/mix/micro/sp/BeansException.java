package com.mix.micro.sp;

public class BeansException extends Throwable{

    String errorMsg;

    public BeansException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BeansException(String errorMsg, Exception e) {
        this.errorMsg = errorMsg;
    }
}
