package com.mix.mq.test;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

@RabbitListener(queues = "MIX_TOPIC_DEMO_QUEUE")
public class ReceiverDemo {

    @RabbitHandler
    public void onMessage(@Payload Message message, Channel channel) throws Exception {
        System.out.println("Message content : " + message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        System.out.println("消息已确认");
    }

}
