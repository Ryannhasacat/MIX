package com.mix.micro.sp.factory.support;

import com.mix.micro.sp.factory.factory.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
