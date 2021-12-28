package com.mix.provider.filter;

import com.mix.provider.PublishFilter;
import org.springframework.stereotype.Component;

@Component
public class BlackListFilter implements PublishFilter {

    @Override
    public void filter() {
        System.out.println("this is black list filter");
    }
}
