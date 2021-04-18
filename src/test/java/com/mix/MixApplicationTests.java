package com.mix;

import com.mix.aop.EatMotionsImpl;
import com.mix.listener.CustomApply;
import com.mix.strategy.StartStrategy;
import com.mix.strategy.StrategyContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class MixApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    EatMotionsImpl eatMotions;

    @Autowired
    CustomApply customApply;

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

}
