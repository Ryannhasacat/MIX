package com.mix.lambda;

import com.mix.chain.pojo.Cat;
import com.mix.chain.pojo.Dog;
import com.mix.chain.pojo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class LambdaTest {

    User user1 = new User.Builder()
            .setUserid(1001)
            .setUserName("lab 1")
            .setAge(18)
            .setHasSession(Boolean.TRUE)
            .setPwd("1001")
            .setPet(new Cat())
            .build();
    User user2 = new User.Builder()
            .setUserid(1002)
            .setUserName("lab 2")
            .setAge(90)
            .setHasSession(Boolean.FALSE)
            .setPwd("1002")
            .setPet(new Dog())
            .build();
    User user3 = new User.Builder()
            .setUserid(1003)
            .setUserName("lab 3")
            .setAge(18)
            .setHasSession(Boolean.TRUE)
            .setPwd("1003")
            .setPet(new Dog())
            .build();
    User user4 = new User.Builder()
            .setUserid(1004)
            .setUserName("lab 4")
            .setAge(25)
            .setHasSession(Boolean.FALSE)
            .setPwd("1004")
            .setPet(new Cat())
            .build();
    User user5 = new User.Builder()
            .setUserid(1005)
            .setUserName("lab 5")
            .setAge(18)
            .setHasSession(Boolean.TRUE)
            .setPwd("1005")
            .setPet(new Dog())
            .build();
    User user6 =new User.Builder()
            .setUserid(1006)
            .setUserName("lab 6")
            .setAge(18)
            .setHasSession(Boolean.FALSE)
            .setPwd("1006")
            .setPet(new Cat())
            .build();
    User user7 = new User.Builder()
            .setUserid(1007)
            .setUserName("lab 7")
            .setAge(55)
            .setHasSession(Boolean.TRUE)
            .setPwd("1007")
            .setPet(new Dog())
            .build();
    User user8 = new User.Builder()
            .setUserid(1008)
            .setUserName("lab 8")
            .setAge(30)
            .setHasSession(Boolean.FALSE)
            .setPwd("1008")
            .setPet(new Cat())
            .build();
    User user9 = new User.Builder()
            .setUserid(1009)
            .setUserName("lab 9")
            .setAge(23)
            .setHasSession(Boolean.TRUE)
            .setPwd("1009")
            .setPet(new Dog())
            .build();
    User user10 = new User.Builder()
            .setUserid(1010)
            .setUserName("lab 10")
            .setAge(67)
            .setHasSession(Boolean.FALSE)
            .setPwd("1010")
            .setPet(new Cat())
            .build();
    List<User> users = new ArrayList<>();

    @BeforeEach
    private void setValue(){
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
        users.add(user9);
        users.add(user10);
    }

    @Test
    public void opt(){
        //循环遍历输出名字
//        users.stream().peek(user -> user.setUserName(user.getUserName()+"  map")).forEach(System.out::println);
//        users.forEach(user -> System.out.println(user.getUserName()));

//        long count = users.stream()
//                .filter(User::getHasSession)
//                .count();
//        System.out.println(count);

         users.stream()
                .filter((user) -> user.getPet() instanceof Dog)
                 .peek(user -> user.getPet().feelLove())
                .forEach(System.out::println);
//        System.out.println(count1);

        Dog dog1 = Dog.create(Dog::new);
    }
    @Test
    public void temp(){
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
    }
    @Test
    public void statistic(){
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
    }
    @Test
    public void skipOpt(){
        //In this stream, we're picking up the even numbers of the stream but we skip the first two
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(i -> i % 2 == 0)
                .skip(2)
                .forEach(i -> System.out.print(i + " "));
    }

    @Test
    public void mapAndReduce(){
        List<String> props = List.of("profile=native", "debug=true", "logging=warn", "interval=500");
        Map<String, String> map = props.stream()
                // 把k=v转换为Map[k]=v:
                .map(kv -> {
                    String[] ss = kv.split("\\=",2);
                    return Map.of(ss[0], ss[1]);//return Collections.singletonMap(ss[0], ss[1]); jdk  <=8
                })
                // 把所有Map聚合到一个Map:
                .reduce(new HashMap<String, String>(), (m, kv) -> {
                    m.putAll(kv);
                    return m;
                });
        // 打印结果:
        map.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
    }



}
