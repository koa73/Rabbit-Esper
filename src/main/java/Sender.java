/**
 * Параметры подключения к очереди
 * Created by OAKutsenko on 09.08.2016.
 */

import Rabbit.ConnectionQueue;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;


public class Sender {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws java.io.IOException,
            java.util.concurrent.TimeoutException {

            Connection connection = new ConnectionQueue().getConnection();
            Channel channel = connection.createChannel();
/*
queue - the name of the queue
durable - true if we are declaring a durable queue (the queue will survive a server restart)
exclusive - true if we are declaring an exclusive queue (restricted to this connection)
autoDelete - true if we are declaring an autodelete queue (server will delete it when no longer in use)
arguments - other properties (construction arguments) for the queue
 */
            channel.queueDeclare(QUEUE_NAME, true, false, true, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();
        }
}
