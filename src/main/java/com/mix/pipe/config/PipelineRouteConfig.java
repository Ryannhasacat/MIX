package com.mix.pipe.config;

import com.mix.pipe.context.InstanceBuildContext;
import com.mix.pipe.context.PipelineContext;
import com.mix.pipe.handler.ContextHandler;
import com.mix.pipe.handler.InputDataPreChecker;
import com.mix.pipe.handler.ModelInstanceCreator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class PipelineRouteConfig implements ApplicationContextAware {

    /**
     * 数据类型->管道中处理器类型列表 的路由
     */
    private static final
    Map<Class<? extends PipelineContext>,
            List<Class<? extends ContextHandler<? extends PipelineContext>>>> PIPELINE_ROUTE_MAP = new HashMap<>(4);

    /*
     * 在这里配置各种上下文类型对应的处理管道：键为上下文类型，值为处理器类型的列表
     */
    static {
        PIPELINE_ROUTE_MAP.put(InstanceBuildContext.class,
                Arrays.asList(
                        InputDataPreChecker.class,
                        ModelInstanceCreator.class
                ));

        // 将来其他 Context 的管道配置
    }

    /**
     * 在 Spring 启动时，根据路由表生成对应的管道映射关系
     */
    @Bean("pipelineRouteMap")
    public Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? extends PipelineContext>>> getHandlerPipelineMap() {
        return PIPELINE_ROUTE_MAP.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, this::toPipeline));
    }

    /**
     * 根据给定的管道中 ContextHandler 的类型的列表，构建管道
     */
    private List<? extends ContextHandler<? extends PipelineContext>> toPipeline(
            Map.Entry<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> entry) {
        return entry.getValue()
                .stream()
                .map(applicationContext::getBean)
                .collect(Collectors.toList());
    }


    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
