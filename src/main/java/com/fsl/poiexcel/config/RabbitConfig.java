package com.fsl.poiexcel.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: chenrj
 * @Date: 2019/8/5 16:39
 * @Description:
 */
@Configuration
@Data
public class RabbitConfig {




    @Value("${spring.rabbitmq.host}")
    private  String host;

    @Value("${spring.rabbitmq.port}")
    private int port;


    @Value("${spring.rabbitmq.username}")
    private String username;


    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;




    /*@Bean
    public Queue opreateQuence() {
        return new Queue("rpc_queue");
    }

    //声明交互器
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(opreateQuence()).to(topicExchange()).with("key.1");
    }*/


}
