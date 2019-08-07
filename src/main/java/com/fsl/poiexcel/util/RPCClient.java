package com.fsl.poiexcel.util;

/**
 * @Auther: chenrj
 * @Date: 2019/8/7 14:11
 * @Description:
 */
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClient implements AutoCloseable {

    private static  Connection connection;
    private static Channel channel;
    private String requestQueueName = "rpc_queue";



    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");


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
        channel.basicCancel(ctag);
        return result;
    }

    public  void close() throws IOException {
        connection.close();
    }
}


