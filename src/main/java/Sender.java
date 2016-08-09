/**
 * Параметры подключения к очереди
 * Created by OAKutsenko on 09.08.2016.
 */

import Rabbit.ConnectionQueue;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Sender {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws java.io.IOException,
            java.util.concurrent.TimeoutException {

            Connection connection = new ConnectionQueue().getConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();
        }
}
