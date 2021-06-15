package com.mix.pipeline;

import com.mix.pipeline.base.ContextHandler;
import com.mix.pipeline.context.InstanceBuildContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ryan has a cat
 */
@Component
@Slf4j
public class InputDataPreChecker implements ContextHandler<InstanceBuildContext> {

    @Override
    public boolean handle(InstanceBuildContext context) {
        log.info("\"--输入数据校验--\"");

        Map<String, Object> formInput = context.getFormInput();

        if (formInput.isEmpty()) {
            context.setErrorMsg("表单输入数据不能为空");
            return false;
        }

        String instanceName = (String) formInput.get("instanceName");

        if (StringUtils.isBlank(instanceName)) {
            context.setErrorMsg("表单输入数据必须包含实例名称");
            return false;
        }


        return false;
    }
}
