package com.mix.micro.sp.factory.support;

import com.mix.micro.sp.BeansException;
import com.mix.micro.sp.factory.factory.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * TODO
 * @author Ryan Z
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();


    @Override
    Object createBean(String beanName, BeanDefinition beanDefinition,Object[] args) throws BeansException {

        Object bean = null;

        //TODO refactor version 3 - - - - - - - - - - - - - - - -
        bean = createBeanInstance(beanDefinition, beanName, args);
        // bean = beanDefinition.getBeanClass().newInstance();
        addSingleton(beanName,bean);
        return bean;
    }

    /**
     * 选出对应的构造器
     * @param beanDefinition
     * @param beanName
     * @param args
     * @return
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) throws BeansException {

        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanDefinition,beanName,constructorToUse,args);
    }
}
