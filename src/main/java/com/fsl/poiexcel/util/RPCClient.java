package com.fsl.poiexcel.util;

/**
 * @Auther: chenrj
 * @Date: 2019/8/7 14:11
 * @Description:
 */

import com.fsl.poiexcel.common.Constant;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClient implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(RPCClient.class);

    private static  Connection connection;
    private static Channel channel;
    private String requestQueueName = "rpc_queue";



    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constant.RBBIT_MQ_HOST);
        factory.setPort(Constant.RBBIT_MQ_POSRT);
        factory.setUsername(Constant.RBBIT_MQ_USERNAME);
        factory.setPassword(Constant.RBBIT_MQ_PASSWORD);


        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] argv) {

        RPCClient fibonacciRpc = null;
        String response = null;
        try {
                fibonacciRpc = new RPCClient();

                String i_str = Integer.toString(0);
                System.out.println(" [x] Requesting fib(" + i_str + ")");
                response = fibonacciRpc.call(i_str);
                System.out.println(" [.] Got '" + response + "'");
                fibonacciRpc.close();

        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (fibonacciRpc!= null) {
                try {
                    fibonacciRpc.close();
                }
                catch (IOException _ignore) {}
            }
        }
    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        //返回的临时队列
        String replyQueueName = channel.queueDeclare().getQueue();

        //System.out.println("replyQueueName:"+replyQueueName);
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });

        String result = response.take();
        log.info("消费的临时队列名称:"+ctag );
        channel.basicCancel(ctag);

    }

    public  void close() throws IOException {
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        connection.close();
    }
}


