package com.fsl.poiexcel.config;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;
import tk.mybatis.mapper.autoconfigure.MybatisProperties;


/**
 * @Auther: chenrj
 * @Date: 2019/8/16 10:05
 * @Description:
 */
@Configuration
@EnableConfigurationProperties({MybatisProperties.class})
class MapperNoWarn extends MapperAutoConfiguration {
    public MapperNoWarn(MybatisProperties properties, ObjectProvider interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider databaseIdProvider, ObjectProvider configurationCustomizersProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
    }
}

