package com.mix.chain.pojo;

public class Cat extends Pet {
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
