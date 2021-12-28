package com.mix.chain.pojo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Flur {

    private String color;

    private String length;

    public static void main(String[] args) {
        Flur flur = new Flur();

        flur.setColor("RED");
        flur.setLength("100");

        Flur flur1 = new Flur();
        flur1.setColor("BLUE");
        flur1.setLength("1000");

        ArrayList<Flur> flurs = Lists.newArrayList(flur, flur1);

        String s = JSON.toJSONString(flurs);

        System.out.println(s);

    }
}
