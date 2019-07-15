package com.fsl.poiexcel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @Auther: chenrj
 * @Date: 2019/6/20 15:14
 * @Description:
 */
@Configuration
public class TomcatConfig {

    @Value("${spring.server.MaxFileSize}")
    private String MaxFileSize;
    @Value("${spring.server.MaxRequestSize}")
    private String MaxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(MaxFileSize); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize(MaxRequestSize);
        return factory.createMultipartConfig();
    }
}