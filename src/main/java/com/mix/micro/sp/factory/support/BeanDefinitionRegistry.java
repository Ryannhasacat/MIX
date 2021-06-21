package com.mix.micro.sp.factory.support;

import com.mix.micro.sp.factory.factory.BeanDefinition;

/**
 * BeanDefinition 注册接口
 * @author Ryan Z
 */
public interface BeanDefinitionRegistry {

    /**
     * 将BeanDefinition 注册到容器中的map中
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
