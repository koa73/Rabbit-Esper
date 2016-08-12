package Rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.concurrent.TimeoutException;
import java.io.IOException;

/**
 * Set connections to Rabbit server
 * Created by OAKutsenko on 09.08.2016.
 */
public class ConnectionQueue {


    private final ConnectionFactory factory = new ConnectionFactory();
    private Connection connection;
    private Channel channel;

    public  ConnectionQueue() throws IOException, TimeoutException{

        setParameters("guest", "guest", "/", "10.199.49.224");

    }

    public  ConnectionQueue(String userName, String password, String vHost, String hostName)
            throws IOException, TimeoutException {

        setParameters(userName, password, vHost, hostName);
    }

    private void setParameters(String userName, String password, String vHost, String hostName)
            throws IOException, TimeoutException{

        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setVirtualHost(vHost);
        factory.setHost(hostName);
        factory.setPort(5672);
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public Connection getConnection() {
        return connection;
    }

    public Channel getChannel() {

        return channel;
    }

    public void closeAll() throws IOException, TimeoutException{

        channel.close();
        connection.close();
    }
}
