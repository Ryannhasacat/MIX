package com.mix.mq.test;

import com.mix.mq.MessageProducer;

public class Send {

    public static void main(String[] args) {
        MessageProducer<String> producer = new MessageProducer<>();
        producer.send("mq-prod","test msg",String.class);
    }
}
