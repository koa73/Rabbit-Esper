package Rabbit.tests; /**
 * Параметры подключения к очереди
 * Created by OAKutsenko on 09.08.2016.
 */

import Rabbit.ConnectionQueue;
import com.rabbitmq.client.*;


public class Sender {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws java.io.IOException,
            java.util.concurrent.TimeoutException {

        ConnectionQueue connectionQueue = new ConnectionQueue();
        Channel channel = connectionQueue.getChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, true, null);
        String message = "Hello World!";

        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        connectionQueue.closeAll();
    }
}
