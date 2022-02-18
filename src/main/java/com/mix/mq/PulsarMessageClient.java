package com.mix.mq;

import lombok.Data;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "pulsar.server")
@Data
public class PulsarMessageClient implements MessageQueueClientGenerate<PulsarClient>{

    private String[] urls;

    @Override
    @Bean
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
        if (Optional.ofNullable(urls).isPresent() && urls.length > 0) {
            Iterator<String> iterator = Arrays.stream(urls).iterator();
            servers.append(iterator.next());
            if (iterator.hasNext()) {
                servers.append(",");
            }
        }

        return servers.toString();
    }


}
