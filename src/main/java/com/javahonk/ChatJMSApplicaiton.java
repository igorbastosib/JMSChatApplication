package com.javahonk;

import javax.jms.*;
import javax.naming.*;
 
import java.io.*;
import java.util.Properties;
 
public class ChatJMSApplicaiton implements MessageListener {
 
	private TopicSession pubSession;
	private TopicSession subSession;
	private TopicConnectionFactory connectionFactory = null;
	private TopicPublisher publisher;
	private TopicSubscriber subscriber;
	private TopicConnection connection;
	
	public static Context getInitialContext() throws javax.naming.NamingException {
 
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		p.put(Context.URL_PKG_PREFIXES, " org.jboss.naming:org.jnp.interfaces");
		p.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		return new javax.naming.InitialContext(p);
	}
	
 
	public ChatJMSApplicaiton() throws Exception {
		System.out.println("Iniciando Servidor JMS");
		Context ic = getInitialContext();

		// Look up a JMS connection factory
		connectionFactory = (TopicConnectionFactory) ic.lookup("/ConnectionFactory");
		System.out.println("Procurando fábrica de conexão JMS");
 
		// Create a JMS connection
		connection = connectionFactory.createTopicConnection();
		System.out.println("Criando conexao com o JMS");
 
		// Create two JMS session objects
		pubSession = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
		subSession = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
		System.out.println("Criando canal Pub e Sub");
 
		// Look up a JMS topic
		String topic = "/javahonk/topic";
		Topic ChatJMSApplicationTopic = (Topic) ic.lookup(topic);
		System.out.println("Pesquisando por tópico "+topic);

		// Create a JMS publisher and subscriber
		publisher = pubSession.createPublisher(ChatJMSApplicationTopic);
		subscriber = subSession.createSubscriber(ChatJMSApplicationTopic);
		System.out.println("Criando Pub e Sub");
		
		// Set a JMS message listener
		subscriber.setMessageListener(this);
		System.out.println("Criando o Sub listener");
 
		// Initialize the ChatJMSApplication application
		set(connection, pubSession, subSession, publisher);
		System.out.println("Inicializando o Pub");
 
		// Start the JMS connection; allows messages to be delivered
		connection.start();
		System.out.println("Iniciando serviço");
	}
 
	/* Initialize the instance variables */
	public void set(TopicConnection con, TopicSession pubSess, TopicSession subSess, TopicPublisher pub) {
		this.connection = con;
		this.publisher = pub;		
	}
 
	/* Receive message from topic subscriber */
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println("a "+text);
		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}
	}
 
	/* Create and send message using topic publisher */
	protected void writeMessage(String text) throws JMSException {
		
		TextMessage message = pubSession.createTextMessage();
		message.setText("Message received: " + " : " + text);
		publisher.publish(message);
	}
 
	/* Close the JMS connection */
	public void close() throws JMSException {
		connection.close();
	}
 
	/* Run the ChatJMSApplication client */
	public static void main(String[] args) {
		try {
			
			ChatJMSApplicaiton ChatJMSApplication = new ChatJMSApplicaiton();
 
			// Read input from command line
			BufferedReader commandLine = new java.io.BufferedReader(new InputStreamReader(System.in));
 
			// Continue while loop until user type word "exit"
			while (true) {
				String s = commandLine.readLine();
				if (s.equalsIgnoreCase("exit")) {
					ChatJMSApplication.close(); // close the connection
					System.exit(0);// exit from program
				} else
					ChatJMSApplication.writeMessage(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
}