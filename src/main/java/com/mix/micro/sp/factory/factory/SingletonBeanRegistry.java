package com.mix.micro.sp.factory.factory;

/**
 * 单例Bean注册接口
 * @author Ryan Z
 */
public interface SingletonBeanRegistry {

    /**
     * 根据beanName 获取对应的单例Bean
     * @param beanName
     * @return Object
     */
    Object getSingleton(String beanName);
}
