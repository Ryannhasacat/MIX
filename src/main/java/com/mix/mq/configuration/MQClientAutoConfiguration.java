package com.mix.mq.configuration;

import com.mix.mq.MessageQueueClientGenerate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

@Configuration
@ConditionalOnClass(MessageQueueClientGenerate.class)
public class MQClientAutoConfiguration implements EnvironmentAware,
        ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

    private Environment environment;

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 我这里是从spring.factories加载了MessageQueueClientGenerate的所有实现,
        List<MessageQueueClientGenerate> MessageQueueClientGenerates = SpringFactoriesLoader.loadFactories(MessageQueueClientGenerate.class, this.getClass().getClassLoader());
        // 然后用BeanDefinitionRegistry 注册到BeanDefinitions
        MessageQueueClientGenerates.forEach(MessageQueueClientGenerate->{
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(MessageQueueClientGenerate.getClass());
            beanDefinition.setLazyInit(false);
            beanDefinition.setAbstract(false);
            beanDefinition.setAutowireCandidate(true);
            beanDefinition.setScope("singleton");
            registry.registerBeanDefinition(MessageQueueClientGenerate.getClass().getSimpleName(), beanDefinition);
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
