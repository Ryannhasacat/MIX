package com.mix.mq.test;
import com.google.common.collect.ImmutableMap;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import static com.google.common.collect.ImmutableMap.of;
import static com.rabbitmq.client.BuiltinExchangeType.TOPIC;

@Component
public class AMQPDemo implements ApplicationContextAware{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.topic.exchange.name: MIX_TOPIC_COMMON_EXCHANGE}")
    private String exchangeName;

    @Value("${rabbitmq.topic.demo.queue.name: MIX_TOPIC_DEMO_QUEUE}")
    private String queueName;

    ApplicationContext applicationContext;

    public void send(){
        rabbitTemplate.convertAndSend(exchangeName,"route.a","hello world!");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

@Slf4j
class RabbitMQSender {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("rabbitmq-user");
        factory.setPassword("rabbitmq-password");
        factory.setVirtualHost("/");
        factory.setPort(5672);

        try (Connection conn = factory.newConnection(); Channel channel = conn.createChannel();) {
            //设置死信交换,Topic类型，持久化
            channel.exchangeDeclare("dlx", TOPIC, true, false, null);

            //设置死信队列，持久化，lazy型
            channel.queueDeclare("dlq", true, false, false, of("x-queue-mode", "lazy"));

            //接收所有发给dlx的消息，另外可以定义其他queue接收指定类型的消息
            channel.queueBind("dlq", "dlx", "#");


            //定义与order相关的事件exchange，如果无法路由，则路由到死信交换dlx
            channel.exchangeDeclare("order", TOPIC, true, false, of("alternate-exchange", "dlx"));


            //定义用于异步更新order读模型的queue，设置死信交换为dlx，队列满(x-overflow)时将头部消息发到dlx
            //定义queue的最大消息数(x-max-length)为300，满后发到dlx，另外定义消息的存活时间(x-message-ttl)为1天，1天后发送到dlx
            ImmutableMap<String, Object> orderSummaryQueueArguments = of("x-dead-letter-exchange",
                    "dlx",
                    "x-overflow",
                    "drop-head",
                    "x-max-length",
                    300,
                    "x-message-ttl",
                    24 * 60 * 60 * 1000);
            channel.queueDeclare("order-summary-queue", true, false, false, orderSummaryQueueArguments);
            channel.queueBind("order-summary-queue", "order", "order.#");


            //定义用于order创建时向用户发出通知的queue，设置死信交换为dlx
            ImmutableMap<String, Object> orderNotificationQueueArguments = of("x-dead-letter-exchange",
                    "dlx",
                    "x-overflow",
                    "drop-head",
                    "x-max-length",
                    300,
                    "x-message-ttl",
                    24 * 60 * 60 * 1000);
            channel.queueDeclare("order-notification-queue", true, false, false, orderNotificationQueueArguments);
            channel.queueBind("order-notification-queue", "order", "order.created");


            //设置发送端确认
            channel.addConfirmListener(new ConfirmListener() {
                public void handleAck(long seqNo, boolean multiple) {
                    if (multiple) {
                        log.info(seqNo + "号及其以前的所有消息发送成功，当消息发送成功后执行相应逻辑，比如标记事件为已发送或者删除原来事件");
                    } else {
                        log.info(seqNo + "号发送成功，当消息发送成功后执行相应逻辑，比如标记事件为已发送或者删除原来事件");

                    }
                }

                public void handleNack(long seqNo, boolean multiple) {
                    if (multiple) {
                        log.info(seqNo + "号及其以前的所有消息发送失败，当消息发送失败后执行相应逻辑，比如重试或者标记事件发送失败");
                    } else {
                        log.info(seqNo + "号发送失败，当消息发送失败后执行相应逻辑，比如重试或者标记事件发送失败");

                    }
                }
            });

            //开启发送者确认
            channel.confirmSelect();

            //设置消息持久化
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .contentType("application/json")
                    .deliveryMode(2)
                    .priority(0)
                    .build();


            //发送时没有必要设置mandatory，因为无法路由的消息会记录在dlq中
            //达到queue的上限时，queue头部消息将被放入dlx中
            try {
                channel.basicPublish("order", "order.created", false, properties, "create order data".getBytes());
                channel.basicPublish("order", "order.updated", false, properties, "update order data".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(5000);
        }

    }
}

@Slf4j
class RabbitMQReceiver {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("rabbitmq-user");
        factory.setPassword("rabbitmq-password");
        factory.setVirtualHost("/");
        factory.setPort(5672);

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.basicQos(1, true);

        boolean autoAck = false;
        channel.basicConsume("order-summary-queue", autoAck,
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body)
                            throws IOException {
                        long deliveryTag = envelope.getDeliveryTag();

                        //用Random来模拟有时处理成功有时处理失败的场景
                        if (new Random().nextBoolean()) {
                            log.info("成功消费消息" + deliveryTag);
                            channel.basicAck(deliveryTag, false);
                        } else {
                            if (!envelope.isRedeliver()) {
                                log.warn("首次消费消息" + deliveryTag + "不成功，尝试重试");
                                channel.basicNack(deliveryTag, false, true);
                            } else {
                                log.warn("第二次消费消息" + deliveryTag + "不成功，扔到DLX");
                                channel.basicNack(deliveryTag, false, false);
                            }
                        }
                    }
                });
    }
}
