package com.mix.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;

@Slf4j
public class MessageProducer<T> {

    public void send(String topic,String message,Class<T> type){
        try {
            PulsarClient client = MessageClient.getMQClient();
            if (type.getName().equals("java.lang.String")) {
                ProducerBuilder<String> stringProducerBuilder = client.newProducer(Schema.STRING);
                Producer<String> stringProducer = stringProducerBuilder.topic(topic)
                        .create();
                stringProducer.send(message);
            }
        } catch (PulsarClientException e) {
            log.error("send message fail!");
        }
    }

}
