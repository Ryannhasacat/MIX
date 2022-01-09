package com.mix.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.ByteBuffer;


@Slf4j
public class PulsarMessageMulticaster<T> {

    @Autowired
    private PulsarClient client;

    /**
     * receive String message via Pulsar Queue
     * @param topic
     * @param subscriptionName
     * @param listener
     */
    public void receive(String topic, String subscriptionName, MessageListener<T> listener, Class<T> type){

        ConsumerBuilder builder = null;

        try {

            if (type.getName().equals(String.class.getTypeName())) {
                builder = client.newConsumer(Schema.STRING);
            }
            if (type.getName().equals(ByteBuffer.class.getTypeName())){
                builder = client.newConsumer(Schema.BYTEBUFFER);
            }
            if (type.getName().equals(byte[].class.getTypeName())){
                builder = client.newConsumer(Schema.BYTES);
            }
            createSubscribe(builder,topic,subscriptionName,listener);

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

    public void createSubscribe(ConsumerBuilder builder,String topic,
                                String subscriptionName, MessageListener listener) throws PulsarClientException {
        builder.topic(topic).subscriptionName(subscriptionName)
                .messageListener(listener)
                .subscribe();
    }



}
