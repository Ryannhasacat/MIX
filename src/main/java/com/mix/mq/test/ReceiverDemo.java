package com.mix.mq.test;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ReceiverDemo {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "MIX_TOPIC_DEMO_QUEUE")
    public void onMessage(@Payload Message message, Channel channel) throws Exception {
        System.out.println("Message content : " + message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        System.out.println("消息已确认");
    }

//    public void receiveEvent() {
//
//        String queue = (String)rabbitTemplate.receiveAndConvert("MIX_TOPIC_DEMO_QUEUE");
//        assert queue != null;
//        System.out.println(queue);
//
//    }

}
