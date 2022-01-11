package com.mix.mq.configuration;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(1)
public class LogMessagePostProcessor implements MessagePostProcessor, Ordered {

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
