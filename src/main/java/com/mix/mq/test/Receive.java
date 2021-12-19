package com.mix.mq.test;

import com.mix.mq.MessageConsumer;
import org.apache.pulsar.client.api.MessageListener;

public class Receive {

    public static void main(String[] args) {
        MessageConsumer messageConsumer = new MessageConsumer();
        messageConsumer.receive("mq-prod","test",(consumer, msg) -> {
            try {
                System.out.println("Message received: " + new String(msg.getData()));
                consumer.acknowledge(msg);
            } catch (Exception e) {
                consumer.negativeAcknowledge(msg);
            }
        });
    }
}
