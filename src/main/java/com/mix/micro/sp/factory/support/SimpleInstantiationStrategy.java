package com.mix.micro.sp.factory.support;

import com.mix.micro.sp.BeansException;
import com.mix.micro.sp.factory.factory.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * jdk 实例化Bean
 * @author Ryan Z
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args) throws BeansException {

        Class clazz = beanDefinition.getBeanClass();

        try {
            if (constructor != null) {
                return clazz.getDeclaredConstructor(constructor.getParameterTypes()).newInstance(args);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        }
       catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
            }
    }
}
