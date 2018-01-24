package org.teel.mdbT1;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

/**
 * Hello world!
 */
public class App {

    private String MESSAGE = "Hello, World!";
    private String CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private String DESTINATION = "MdbT1Queue";

    public void run() throws Exception {
        Context namingContext = null;
        JMSContext context = null;

        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.naming.remote.client.InitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        //env.put(Context.SECURITY_PRINCIPAL, "jmsuser");
        //env.put(Context.SECURITY_CREDENTIALS, "Password1!");
        namingContext = new InitialContext(env);

        ConnectionFactory connectionFactory = (ConnectionFactory) namingContext
                .lookup(CONNECTION_FACTORY);
        System.out.println("Got ConnectionFactory " + CONNECTION_FACTORY);

        Destination destination = (Destination) namingContext
                .lookup(DESTINATION);
        System.out.println("Got JMS Endpoint " + DESTINATION);

        // Create the JMS context
        context = connectionFactory.createContext("thomas", "bambo99");
        //context = connectionFactory.createContext();

        context.createProducer().send(destination, MESSAGE);
        System.out.println("Sent message " + MESSAGE);
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            new App().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
