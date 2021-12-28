package com.mix.provider.container;

import com.mix.provider.PublishFilter;
import lombok.Data;

import java.util.Map;

@Data
public class FilterMap {

    private Map<String, PublishFilter> filterMap;
}
