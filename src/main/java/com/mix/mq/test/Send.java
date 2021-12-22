package com.mix.mq.test;

import com.mix.mq.PulsarMessageMulticaster;

public class Send {

    public static void main(String[] args) {
        PulsarMessageMulticaster<String> producer = new PulsarMessageMulticaster<>();
        producer.send("mq-prod","test msg",String.class);
    }
}
