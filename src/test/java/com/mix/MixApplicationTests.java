package com.mix;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.mix.aop.EatMotionsImpl;
import com.mix.chain.pojo.Cat;
import com.mix.chain.pojo.Flur;
import com.mix.listener.CustomApply;
import com.mix.mq.test.AMQPDemo;
import com.mix.mq.test.ReceiverDemo;
import com.mix.strategy.StartStrategy;
import com.mix.strategy.StrategyContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@EnableRabbit
class MixApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    EatMotionsImpl eatMotions;

    @Autowired
    CustomApply customApply;

    @Autowired
    AMQPDemo amqpDemo;

//    @Autowired
//    ReceiverDemo receiverDemo;


    @BeforeEach
    public void contextLoads() {
        eatMotions= applicationContext.getBean(EatMotionsImpl.class);

    }

    @Test
    void motionTest(){
        eatMotions.eatMeal();
        System.out.println("============");
        eatMotions.eatVege();
    }

    @Test
    void listenerTest(){
        customApply.mainMotion();
    }

    @Test
    void strategyAndCommands(){
        StrategyContext strategyContext = new StrategyContext();
        strategyContext.setStrategy(new StartStrategy());
        strategyContext.execute();
    }

    @Test
    void testMapTransfer(){
        Map<String, Object> map = new HashMap<>();
        Cat cat = new Cat();

        Flur flur = new Flur();
        flur.setColor("red");
        flur.setLength("1000");
        map.put("hands","cat hands");
        map.put("eyes","cat eyes");
        map.put("flurs",flur);


//        System.out.println(map.get("flurs").toString());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String flurs = objectMapper.writeValueAsString(map.get("flurs"));
            System.out.println(flurs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

//        map.put()
    }

    @Test
    void mqTest(){
        amqpDemo.send();
    }



    @Test
    void algo(){
        int cursorVal = 0;
        int rateVal = 50;
        for(int i = cursorVal +1;i <= (rateVal+cursorVal);i++ ){
            System.out.println(i+";  cursorVal = "+cursorVal+" ; rateVal = "+rateVal);
        }

    }

}
