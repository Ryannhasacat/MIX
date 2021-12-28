package com.mix.provider;

import com.mix.provider.container.FilterMap;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class FilterConfiguration {

    @Bean
    FilterMap getFilters(ObjectProvider<PublishFilter> filters){

        Map<String, PublishFilter> filterMap = new HashMap<>();
        List<PublishFilter> filterList = filters.orderedStream().collect(Collectors.toList());
        filterList.forEach(filter-> filterMap.put(filter.getClass().getName(),filter));
        FilterMap filterMapdo = new FilterMap();
        filterMapdo.setFilterMap(filterMap);
        return filterMapdo;

    }

}
