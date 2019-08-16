package com.fsl.poiexcel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoiexcelApplicationTests {


    private static final Logger log= LoggerFactory.getLogger(PoiexcelApplicationTests.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public  void testArr(){

        List<String> list = new ArrayList<>();
        log.info("4444444444444");

        log.info(CollectionUtils.isEmpty(list)  +"");

    }

    @Test
    public  void testMq(){
/*
        String aa ="hello word";

        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());

        Object response = rabbitTemplate.convertSendAndReceive("topicExchange", "key.1", aa, correlationId).toString();


        log.info("结束发送消息:msg({})",aa);
        log.info("消费者响应 :response({})",response+"   ");*/
    }

    @Test
    public void contextLoads() {
        System.out.println(f(3));
    }


    @Test
    public void testSplit(){


        String[] aa = "aaa*bbb*ccc".split("\\*");
        for (int i = 0 ; i <aa.length ; i++ ) {
            System.out.println("--"+aa[i]);
        }

    }


   public int f(int n){
            //f(0) = 0,f(1) = 1，等价于 n<=1时，f(n) = n。
            if(n <= 1){
                   return n;
            }

            if(n == 2){
              return 2;
             }

                return  f(n-1) + f(n-2);
        }

}
