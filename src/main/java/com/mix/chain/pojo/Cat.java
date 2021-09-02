package com.mix.chain.pojo;

import lombok.Data;

@Data
public class Cat extends Pet {

    private String hands;

    private String eyes;

    private Flur flurs ;


    @Override
    public void feelLove() {
        System.out.println("is cat");
    }

    @Override
    void cute() {
        System.out.println("miao");
    }

    @Override
    Love touch() {
        return this;
    }
}
