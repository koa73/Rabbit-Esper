package Rabbit.tests; /**
 * Параметры подключения к очереди
 * Created by OAKutsenko on 09.08.2016.
 */

import Rabbit.ConnectionQueue;
import com.rabbitmq.client.*;

import java.io.IOException;


public class Sender_1 {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException,
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

/*
exchange - the exchange to publish the message to
routingKey - the routing key
props - other properties for the message - routing headers etc
body - the message body
*/
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();

           (new Thread(new Consumer())).start();
        }


    private static int msgCount = 1;

    static class Consumer implements Runnable {
        public void run() {
            try {
                // Setup
                Connection conn = new ConnectionQueue().getConnection();
                Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, true, null);

                // Consume
                QueueingConsumer qc = new QueueingConsumer(ch){
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String message = new String(body, "UTF-8");
                        try{

                            System.out.println(" [x] Received '" + message + "'");

                        } finally {
                            System.out.println(" [x] Done ");
                        }
                    }
                };
                ch.basicConsume(QUEUE_NAME, true, qc);
                for (int i = 0; i < msgCount; ++i) {
                    qc.nextDelivery();
                }

                // Cleanup
                ch.close();
                conn.close();
            } catch (Throwable e) {
                System.out.println("Whoosh!");
                System.out.print(e);
            }
        }
    }
}
