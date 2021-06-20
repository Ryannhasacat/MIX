package com.mix.micro.sp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  Spring Bean 的实现，
 *  核心就是要实现 Bean的定义 -> 注册 -> 获取
 *  Bean工厂得设计思路：
 *  通过加载写好的XML配置文件 将XML中的Bean元素转化成BeanDefinition，
 *  然后通过BeanDefinitionRegistry 将这些Bean注册到一个 map容器中 然后通过key 获取到想要的Bean信息。
 * @author Ryan z
 */
public class BeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public Object getBean(String name){
        return  beanDefinitionMap.get(name);
    }

    public void registerBeanDefinition(String name,BeanDefinition beanDefinition){
        beanDefinitionMap.put(name,beanDefinition);
    }


}
