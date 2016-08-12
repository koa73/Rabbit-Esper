import Rabbit.ConnectionQueue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/**
 *  Эмулятор отправки сообщений
 * Created by OAKutsenko on 11.08.2016.
 */
public class MainProducer {

    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String QUEUE_NAME = "UserPostBox";
    private static final String uid = "12asd123";

    public static void main(String[] args) throws java.io.IOException,
            java.util.concurrent.TimeoutException {


        ConnectionQueue connectionQueue = new ConnectionQueue();
        Channel channel = connectionQueue.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, uid);

        String message = "Hello World!";

        channel.basicPublish(EXCHANGE_NAME, uid, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");


        connectionQueue.closeAll();
    }
}
