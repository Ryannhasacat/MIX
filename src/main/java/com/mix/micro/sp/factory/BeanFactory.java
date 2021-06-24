package com.mix.micro.sp.factory;

import com.mix.micro.sp.BeansException;
import com.mix.micro.sp.factory.factory.BeanDefinition;

/**
 *  Spring Bean 的实现，
 *  核心就是要实现 Bean的定义 -> 注册 -> 获取
 *  Bean工厂得设计思路：
 *  通过加载写好的XML配置文件 将XML中的Bean元素转化成BeanDefinition，
 *  然后通过{@see com.mix.micro.sp.factory.support.BeanDefinitionRegistry} 将这些Bean注册到一个 map容器中 然后通过key 获取到想要的Bean信息。
 * @author Ryan z
 */
public interface BeanFactory {

    /***
     *  根据name获取Bean
     * @param name
     * @return Object
     * @throws BeansException
     */
    Object getBean(String name) throws BeansException;

    /**
     *  version3
     *
     * @param name
     * @param args
     * @return
     * @throws BeansException
     */
    Object getBean(String name, Object... args) throws BeansException;

}
