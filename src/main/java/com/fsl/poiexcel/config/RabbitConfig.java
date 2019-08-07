package com.fsl.poiexcel.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: chenrj
 * @Date: 2019/8/5 16:39
 * @Description:
 */
@Configuration
public class RabbitConfig {

    @Bean
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
    }


}
