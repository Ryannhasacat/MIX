package com.mix.micro.sp.factory.support;

import com.mix.micro.sp.BeansException;
import com.mix.micro.sp.factory.BeanFactory;
import com.mix.micro.sp.factory.factory.BeanDefinition;

/**
 * 继承{@link DefaultSingletonBeanRegistry} 并实现{@link BeanFactory}
 * 使得此类同时具有{@literal 获取Bean 和 注册单例 Bean的功能。}
 * @author Ryan Z
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {


    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null){
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name,beanDefinition);
    }

//    @Override
//    public void registerBeanDefinition(String name,BeanDefinition beanDefinition){
//        beanDefinitionMap.put(name,beanDefinition);
//    }

    /**
     * 获取容器map中保存的BeanDefinition
     * @param beanName
     * @return
     * @throws BeansException
     */
    abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     *  创建Bean对象
     * @param beanName
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
}
