package com.mix.micro.sp.factory.support;

import com.mix.micro.sp.BeansException;
import com.mix.micro.sp.factory.factory.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 *
 * @author Ryan Z
 */
public interface InstantiationStrategy {

    /**
     *  实例化策略
     * @param beanDefinition
     * @param beanName
     * @param constructor
     * @param args
     * @return
     * @throws BeansException
     */
    Object instantiate(BeanDefinition beanDefinition ,String beanName, Constructor constructor ,Object[] args) throws BeansException;
}
