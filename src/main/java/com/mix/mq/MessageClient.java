package com.mix.mq;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class MessageClient {

    public static PulsarClient getMQClient() throws PulsarClientException {
       return PulsarClient.builder()
                .serviceUrl("pulsar://34.92.132.97:6650")
                .build();
    }
}
