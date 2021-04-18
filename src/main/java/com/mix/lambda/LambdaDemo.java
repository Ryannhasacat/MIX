package com.mix.lambda;

import com.mix.chain.pojo.Dog;
import com.mix.chain.pojo.User;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaDemo {
    /**
     * 四种
     * @Functional 注释的函数式接口
     * @see  Consumer  消费型  有进不出
     * @see  Function  有进有出
     * @see  Predicate  判断型 有入参 boolean返参
     * @see  Supplier   供给型 只有返参
     */
    public void filterTest(){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str)->str.startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str)->str.endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str)->true);

        System.out.println("Print no language : ");
        filter(languages, (str)->false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str)->str.length() > 4);

    }


    public void filter(List<String> names, Predicate<String> condition) {
        names.stream().filter(condition).forEach((name) -> System.out.println(name + " "));
    }

    User user = new User();

    Dog dog = new Dog();




}
