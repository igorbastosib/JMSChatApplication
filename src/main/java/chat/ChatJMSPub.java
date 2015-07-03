package chat;

import static chat.ChatUtil.criaTopico;
import static chat.ChatUtil.getInitialContext;

import java.io.IOException;
import java.util.ArrayList;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.naming.Context;

public class ChatJMSPub {

	private TopicSession pubSession;
	private TopicConnectionFactory connectionFactory = null;
	private TopicConnection connection;
	private Context ic;

	public ChatJMSPub() throws Exception {
		System.out.println("*** Config JMS Pub ***");

		System.out.println("JMS Iniciando Servidor JMS");
		ic = getInitialContext();

		connectionFactory = (TopicConnectionFactory) ic.lookup("/ConnectionFactory");
		System.out.println("JMS Procurando fabrica de conexao JMS");

		connection = connectionFactory.createTopicConnection();
		System.out.println("JMS Criando conexao com o JMS");

		pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		System.out.println("JMS Criando canal Pub");
		System.out.println("*** END Config JMS Pub ***\n\n");
	}

	/* Create and send message using topic publisher */
	public void writeMessage(String topic, String text) {
		try {
			Topic ChatJMSApplicationTopic = (Topic) ic.lookup("/jogoAssassino/topic" + topic);
			//System.out.println("JMS Pesquisando por topico " + topic);

			TopicPublisher publisherTopic = pubSession.createPublisher(ChatJMSApplicationTopic);
			//System.out.println("JMS Criando Pub e Sub Topic");

			connection.start();
			//System.out.println("JMS Iniciando servico");

			TextMessage message = pubSession.createTextMessage();
			message.setText(text);
			publisherTopic.publish(message);
			System.out.println("JMS Mensagem enviada "+topic+": " + text);
		} catch (Exception e) {
			System.out.println("ERRO: ChatJMSPub writeMessage -> " + e.getMessage());
		}
	}

	/* Close the JMS connection */
	public void close() throws JMSException {
		connection.close();
	}

	public void criaTopicosJMS(ArrayList<String> topicos) throws MalformedObjectNameException, InstanceNotFoundException, ReflectionException, MBeanException, IOException{
		criaTopico(topicos);
	}
}