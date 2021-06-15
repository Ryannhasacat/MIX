package com.mix.pipeline.config;

import com.mix.pipeline.InputDataPreChecker;
import com.mix.pipeline.base.ContextHandler;
import com.mix.pipeline.base.PipelineContext;
import com.mix.pipeline.context.InstanceBuildContext;
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

    private static final Map<Class<? extends PipelineContext>,
                        List<Class<? extends ContextHandler<? extends PipelineContext>>>> PIPELINE_ROUTE_MAP = new HashMap<>(4);

    static {
        PIPELINE_ROUTE_MAP.put(InstanceBuildContext.class,
                Arrays.asList(
                        InputDataPreChecker.class
                ));
        // 将来其他 Context 的管道配置
    }

    @Bean("pipelineRouteConfig")
    public Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? extends PipelineContext>>> getHandlerPipelineMap(){
            return PIPELINE_ROUTE_MAP.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,this::toPipeline));
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
