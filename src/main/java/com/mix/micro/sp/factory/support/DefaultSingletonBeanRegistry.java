package com.mix.micro.sp.factory.support;

import com.mix.micro.sp.factory.factory.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认单例 Bean注册
 * @author Ryan Z
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName , Object singletonObject){
        singletonObjects.put(beanName,singletonObject);
    }
}
