package chat;

import static chat.ChatUtil.getInitialContext;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.swing.JTextArea;

import org.apache.bsf.util.event.adapters.java_awt_event_TextAdapter;

public class ChatJMSSub implements MessageListener {

	private TopicSession subSession;
	private TopicConnectionFactory connectionFactory = null;
	private TopicConnection connection;
	private JTextArea areaTexto;

	public ChatJMSSub(String privateTopic, JTextArea areaTexto) throws Exception {
		System.out.println("*** Config JMS Sub ***");
		
		System.out.println("Iniciando Servidor JMS");
		Context ic = getInitialContext();

		// Look up a JMS connection factory
		connectionFactory = (TopicConnectionFactory) ic.lookup("/ConnectionFactory");
		System.out.println("Procurando fabrica de conexao JMS");

		// Create a JMS connection
		connection = connectionFactory.createTopicConnection();
		System.out.println("Criando conexao com o JMS");
		//connection.setClientID("Luma");

		// Create two JMS session objects
		subSession = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
		System.out.println("Criando canal Sub");
		System.out.println("*** END Config JMS Sub ***\n\n");

		Topic ChatJMSApplicationTopicPublic = (Topic) ic.lookup("/jogoAssassino/topicMsgPublic");
		Topic ChatJMSApplicationTopicPrivate = (Topic) ic.lookup("/jogoAssassino/topic"+privateTopic);
		System.out.println("Pesquisando por topicos MsgPublic e " + privateTopic);

		//subscriberTopic = subSession.createDurableSubscriber(ChatJMSApplicationTopic, "Luma");
		TopicSubscriber subscriberTopicMsgPublic = subSession.createSubscriber(ChatJMSApplicationTopicPublic);
		TopicSubscriber subscriberTopicMsgPrivate = subSession.createSubscriber(ChatJMSApplicationTopicPrivate);
		System.out.println("Criando Pub e Sub Topic");

		// Set a JMS message listener
		subscriberTopicMsgPublic.setMessageListener(this);
		subscriberTopicMsgPrivate.setMessageListener(this);
		System.out.println("Criando o Sub listener");

		// Initialize the ChatJMSApplication application
		System.out.println("Inicializando o Pub");

		// Start the JMS connection; allows messages to be delivered
		connection.start();
		System.out.println("Iniciando servico JMS\n\n");
		this.areaTexto = areaTexto;
	}

	/* Receive message from topic subscriber */
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			if(areaTexto != null){
				areaTexto.setText(areaTexto.getText()+"\n"+text);
			}
			System.out.println(text);
		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}
	}

	/* Close the JMS connection */
	public void close() throws JMSException {
		connection.close();
	}
}