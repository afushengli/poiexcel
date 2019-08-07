package com.fsl.poiexcel.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: chenrj
 * @Date: 2019/8/5 16:51
 * @Description:
 */
@Component
public class OpreateReceiver {

    private static final Logger log= LoggerFactory.getLogger(OpreateReceiver.class);

/*
    @RabbitListener(queues = "opreate")
    @RabbitHandler
    public String process(String msg) {
       log.info(Thread.currentThread().getName() + " 接收到来自opreate队列的消息：" + msg);
       return msg.toUpperCase();
    }*/



}
