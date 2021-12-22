package com.mix.mq.test;

import com.mix.mq.PulsarMessageMulticaster;

public class Receive {

    public static void main(String[] args) {
        PulsarMessageMulticaster<String> messageConsumer = new PulsarMessageMulticaster<>();
        messageConsumer.receive("mq-prod","test",(consumer, msg) -> {
            try {
                System.out.println("Message received: " + new String(msg.getData()));
                consumer.acknowledge(msg);
            } catch (Exception e) {
                consumer.negativeAcknowledge(msg);
            }
        },String.class);
    }
}
