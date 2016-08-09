/**
 * Отправка сообщений в очередь
 * Created by OAKutsenko on 09.08.2016.
 */

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Sender {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws java.io.IOException,
            java.util.concurrent.TimeoutException{

            ConnectionFactory factory = new ConnectionFactory();

            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");
            factory.setHost("10.199.49.224");
            factory.setPort(5672);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();
        }
}
