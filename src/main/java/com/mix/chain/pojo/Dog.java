package com.mix.chain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dog extends Pet{
    private Boolean isBig;

    public static Dog create(final Supplier<Dog> supplier) {
        return supplier.get();
    }


    @Override
    public void feelLove() {
        System.out.println("is dog");
    }

    @Override
    void cute() {
        System.out.println("wang");
    }

    @Override
    Love touch() {
        return this;
    }

    public static void shout(){
        System.out.println("shout");
    }
}
