package com.mix;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MixApplication {

    public static void main(String[] args) {
        SpringApplication.run(MixApplication.class, args);
    }

}
