package Rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by OAKutsenko on 09.08.2016.
 */
public class ConnectionQueue {


    private final ConnectionFactory factory = new ConnectionFactory();

    public  ConnectionQueue(){

        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("10.199.49.224");
        factory.setPort(5672);
    }

    public  ConnectionQueue(String userName, String password, String vHost, String hostName){

        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setVirtualHost(vHost);
        factory.setHost(hostName);
        factory.setPort(5672);
    }

    public Connection getConnection() throws java.io.IOException{
        return factory.newConnection();
    }
}
