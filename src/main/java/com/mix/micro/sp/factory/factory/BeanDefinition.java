package com.mix.micro.sp.factory.factory;

/**
 * Bean 定义
 * 定义 Bean 元素的一些元信息
 * 主要用作 将XML文件中或者Annotation的Bean标签或元素 中包含的信息 解析到此类中，以便加载时使用。
 * @author Ryan z
 */
public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass(){
        return beanClass;
    }
}
