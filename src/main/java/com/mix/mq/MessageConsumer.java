package com.mix.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;

@Slf4j
public class MessageConsumer {

    public Consumer<String> receive(String topic,String subscriptionName,MessageListener<String> listener){

        Consumer<String> subscribe = null;

        try {
            PulsarClient client = MessageClient.getMQClient();
            ConsumerBuilder<String> stringConsumerBuilder = client.newConsumer(Schema.STRING);
            subscribe = stringConsumerBuilder.topic(topic)
                    .subscriptionName(subscriptionName)
                    .messageListener(listener)
                    .subscribe();
        } catch (PulsarClientException e) {
            log.error("build consumer fail!");
        }

        return subscribe;

//        MessageListener<String> myMessageListener = (consumer, msg) -> {
//            try {
//                System.out.println("Message received: " + new String(msg.getData()));
//                consumer.acknowledge(msg);
//            } catch (Exception e) {
//                consumer.negativeAcknowledge(msg);
//            }
//        };
    }


}
