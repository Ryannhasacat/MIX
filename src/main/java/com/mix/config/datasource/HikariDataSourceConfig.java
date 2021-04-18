package com.mix.config.datasource;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HikariDataSourceConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);


    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource() {
        // 配置数据源（注意，我使用的是 HikariCP 连接池），以上注解是指定数据源，否则会有冲突
        return DataSourceBuilder.create().build();
    }
//    @Bean(name = "masterDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource masterDataSource(DataSourceProperties properties) {
//        LOGGER.info("init master data source：{}", properties);
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "slaveDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.slave")
//    public DataSource slaveDataSource(DataSourceProperties properties) {
//        LOGGER.info("init slave data source：{}", properties);
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @Primary
//    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
//        Map<String, DataSource> targetDataSources = new HashMap<>();
//        targetDataSources.put(DataSourceEnum.MASTER.getName(), masterDataSource);
//        targetDataSources.put(DataSourceEnum.SLAVE.getName(), slaveDataSource);
//
//        return new DynamicDataSource(masterDataSource, targetDataSources);
//    }

}
