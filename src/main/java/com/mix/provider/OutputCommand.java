package com.mix.provider;

import com.mix.provider.container.FilterMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OutputCommand implements CommandLineRunner {

    @Autowired
    private FilterMap filterMap;


    @Override
    public void run(String... args) throws Exception {
        filterMap.getFilterMap().forEach((key,value) -> value.filter());
    }

}
