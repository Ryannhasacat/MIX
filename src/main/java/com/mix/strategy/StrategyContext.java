package com.mix.strategy;

import lombok.Data;

@Data
public class StrategyContext {

    Strategy strategy;

    public void execute(){
        strategy.executeStrategy();
    }

}
