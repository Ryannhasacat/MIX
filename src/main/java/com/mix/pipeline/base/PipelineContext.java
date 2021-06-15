package com.mix.pipeline.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PipelineContext {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    /**
     *   获取数据名称
     *
     * @return
     */
    public String getName(){
        return  this.getClass().getSimpleName();
    }
}
