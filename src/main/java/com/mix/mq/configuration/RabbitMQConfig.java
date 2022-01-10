package com.mix.mq.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:rabbitmq.properties")
public class RabbitMQConfig {

    @Value("${rabbitmq.topic.exchange.name: MIX_TOPIC_COMMON_EXCHANGE}")
    private String exchangeName;

    @Value("${rabbitmq.topic.demo.queue.name: MIX_TOPIC_DEMO_QUEUE}")
    private String queueName;

    private static final String MIX_PUBLISH_DLX = "MIX_PUBLISH_DLX";

    private static final String MIX_PUBLISH_DLQ = "MIX_PUBLISH_DLQ";

    //"发送方Exchange"
    @Bean("demoExchange")
    public TopicExchange demoPublishExchange() {
        return new TopicExchange(exchangeName, true, false, null);
    }


    @Bean("demoQueue")
    public Queue demoPublish() {
        return new Queue(queueName, true, false, false, null);
    }


    //"发送方DLX"，消息发送失败时传到该DLX @Description 死信交换机
    @Bean
    public TopicExchange orderPublishDlx() {
        return new TopicExchange(MIX_PUBLISH_DLX, true, false, null);
    }

    //"发送方DLQ"，所有发到"发送DLX"的消息都将路由到该DLQ @description 死信队列
    @Bean
    public Queue orderPublishDlq() {
        return new Queue(MIX_PUBLISH_DLQ, true, false, false, ImmutableMap.of("x-queue-mode", "lazy"));
    }


    //"发送方DLQ"绑定到"发送方DLX"
    @Bean
    public Binding orderPublishDlqBinding() {
        return BindingBuilder.bind(orderPublishDlq()).to(orderPublishDlx()).with("#");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(objectMapper);
        messageConverter.setClassMapper(classMapper());
        return messageConverter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        return classMapper;
    }

    @Bean
    public Binding binding(@Qualifier("demoExchange") TopicExchange topicExchange, @Qualifier("demoQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(topicExchange).with("route.#");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
