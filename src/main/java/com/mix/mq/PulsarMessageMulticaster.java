package com.mix.mq;

import com.mix.mq.listener.MarsMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;

import javax.annotation.Resource;

@Slf4j
public class PulsarMessageMulticaster<T> {

    @Resource
    private MessageQueueClientGenerate<PulsarClient> mqclient;

    /**
     * receive String message via Pulsar Queue
     * @param topic
     * @param subscriptionName
     * @param listener
     */
    public void receive(String topic, String subscriptionName, MessageListener<T> listener, Class<T> type){
        try {
            PulsarClient client = mqclient.generateClient();
            if (type.getName().equals(String.class.getTypeName())) {
                ConsumerBuilder<String> stringConsumerBuilder = client.newConsumer(Schema.STRING);
                stringConsumerBuilder.topic(topic)
                        .subscriptionName(subscriptionName)
                        .messageListener((MessageListener<String>) listener)
                        .subscribe();
            }
        } catch (PulsarClientException e) {
            log.error("build consumer fail!");
        }

    }

    /**
     * Send message to Pulsar mq.
     * @param topic
     * @param message
     * @param type
     */
    public void send(String topic,T message,Class<T> type){
        try {
            PulsarClient client = mqclient.generateClient();
            if (type.getName().equals(String.class.getTypeName())) {
                sendString(client,topic,message);
            }
        } catch (PulsarClientException e) {
            log.error("send message fail!");
        }
    }

    /**
     * Send String message implement.
     * @param client
     * @param topic
     * @param message
     * @throws PulsarClientException
     */
    private void sendString(PulsarClient client,String topic, T message) throws PulsarClientException {
        ProducerBuilder<String> stringProducerBuilder = client.newProducer(Schema.STRING);
        Producer<String> stringProducer = stringProducerBuilder.topic(topic)
                .create();
        stringProducer.send((String)message);
    }


}
