package com.fsl.poiexcel;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: chenrj
 * @Date: 2019/8/13 10:14
 * @Description:
 */
public class RPCClient11 {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";

    public RPCClient11() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] argv) {

/*        for(int i=0;i<2;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RPCClient11 fibonacciRpc = null;
                    String response = null;
                    try {
                        fibonacciRpc = new RPCClient11();

                        String i_str = Integer.toString(10);
                        System.out.println(" [x] Requesting fib(" + i_str + ")");
                        response = fibonacciRpc.call(i_str);
                        System.out.println(" [.] Got '" + response + "'");
                        //fibonacciRpc.close();

                    } catch (IOException | TimeoutException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/

        for(int i=0;i<2;i++){


            RPCClient11 fibonacciRpc = null;
            String response = null;
            try {
                fibonacciRpc = new RPCClient11();

                String i_str = Integer.toString(10);
                System.out.println(" [x] Requesting fib(" + i_str + ")");
                response = fibonacciRpc.call(i_str);
                System.out.println(" [.] Got '" + response + "'");
                //fibonacciRpc.close();

            } catch (IOException | TimeoutException | InterruptedException e) {
                e.printStackTrace();
            }



        }

    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
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
        System.out.println(ctag);
        //channel.basicCancel("ssss"); //consumerTag
        channel.basicCancel(ctag);
        return result;
    }

    public void close() throws IOException {
        connection.close();
    }

}
