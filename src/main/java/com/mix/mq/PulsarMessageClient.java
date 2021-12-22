package com.mix.mq;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

@Configuration
public class PulsarMessageClient implements MessageQueueClientGenerate<PulsarClient>{

    @Value("${pulsar.server.serverUrls}")
    private static String[] serverUrls;

    @Override
    public PulsarClient generateClient() {
        try {
            return PulsarClient.builder()
                    .serviceUrl(servers())
                    .build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String servers(){
        StringBuilder servers = new StringBuilder();
        if (Optional.ofNullable(serverUrls).isPresent() && serverUrls.length > 0) {
            Iterator<String> iterator = Arrays.stream(serverUrls).iterator();
            servers.append(iterator.next());
            if (iterator.hasNext()) {
                servers.append(",");
            }
        }

        return servers.toString();
    }


}
