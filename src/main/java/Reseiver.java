/**
 * Just receive messages from
 * Created by OAKutsenko on 10.08.2016.
 */

import Rabbit.ConnectionQueue;
import com.rabbitmq.client.*;
import java.io.IOException;

public class Reseiver {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException {

        Connection connection = new ConnectionQueue().getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, true, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // maximum number of messages that the server will deliver, 0 if unlimited
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                try{

                    System.out.println(" [x] Received '" + message + "'");
                    doWork();

                } finally {
                    System.out.println(" [x] Done ");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    private static void doWork() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
