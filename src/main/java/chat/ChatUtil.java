package chat;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.naming.Context;

public abstract class ChatUtil {
	public static Context getInitialContext() throws javax.naming.NamingException {

		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		p.put(Context.URL_PKG_PREFIXES, " org.jboss.naming:org.jnp.interfaces");
		p.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		return new javax.naming.InitialContext(p);
	}

	public static void criaTopico(ArrayList<String> sTopic) throws MalformedObjectNameException, InstanceNotFoundException, ReflectionException, MBeanException, IOException {
		String nomeArq = ".\\jboss-6.1.0.Final\\server\\default\\deploy\\hornetq\\hornetq-jms.xml";
		String texto = textoXML(sTopic);
		// tentando criar arquivo
		try {
			FileWriter arq = new FileWriter(nomeArq);
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.printf(texto);
			arq.close();
			Thread.sleep(5000);
		} catch (Exception erro) {
			erro.printStackTrace();
		}
	}

	private static String textoXML(ArrayList<String> sTopic) {
		String texto = "<configuration xmlns=\"urn:hornetq\"\n" 
				+ "            xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" 
				+ "            xsi:schemaLocation=\"urn:hornetq /schema/hornetq-jms.xsd\">\n" 
				+ "\n" 
				+ "	<connection-factory name=\"NettyConnectionFactory\">\n"
				+ "      <xa>true</xa>\n" 
				+ "      <connectors>\n" 
				+ "         <connector-ref connector-name=\"netty\"/>\n" 
				+ "      </connectors>\n" 
				+ "      <entries>\n" 
				+ "         <entry name=\"/ConnectionFactory\"/>\n"
				+ "         <entry name=\"/XAConnectionFactory\"/>\n" 
				+ "      </entries>" 
				+ "   </connection-factory>\n" 
				+ "\n" 
				+ "   <connection-factory name=\"NettyThroughputConnectionFactory\">\n" 
				+ "      <xa>true</xa>\n" 
				+ "      <connectors>\n"
				+ "         <connector-ref connector-name=\"netty-throughput\"/>\n" 
				+ "      </connectors>\n" 
				+ "      <entries>\n" 
				+ "         <entry name=\"/ThroughputConnectionFactory\"/>\n" 
				+ "         <entry name=\"/XAThroughputConnectionFactory\"/>\n"
				+ "      </entries>\n" 
				+ "   </connection-factory>\n" 
				+ "\n" 
				+ "   <connection-factory name=\"InVMConnectionFactory\">\n" 
				+ "      <xa>true</xa>\n" 
				+ "      <connectors>\n" 
				+ "         <connector-ref connector-name=\"in-vm\"/>\n"
				+ "      </connectors>\n" 
				+ "      <entries>\n" 
				+ "         <entry name=\"java:/ConnectionFactory\"/>\n" 
				+ "         <entry name=\"java:/XAConnectionFactory\"/>\n" 
				+ "      </entries>\n" 
				+ "   </connection-factory>\n" 
				+ "\n"
				+ "   <queue name=\"DLQ\">\n" 
				+ "      <entry name=\"/queue/DLQ\"/>\n" 
				+ "   </queue>" + "\n" 
				+ "   <queue name=\"ExpiryQueue\">\n" 
				+ "      <entry name=\"/queue/ExpiryQueue\"/>\n" 
				+ "   </queue>\n" 
				+ "\n" 
				+ "   <topic name=\"jogoAssassinoTopicMsgPublic\">\n"
				+ "      <entry name=\"/jogoAssassino/topicMsgPublic\"/>\n"
				+ "   </topic>" + "\n";
		for (int i = 0; i < sTopic.size(); i++) {
			texto = texto + "   <topic name=\"jogoAssassinoTopic"+sTopic.get(i)+"\">\n"
							+ "      <entry name=\"/jogoAssassino/topic"+sTopic.get(i)+"\"/>\n"
							+ "   </topic>" + "\n";
		}
				
				texto = texto+ "</configuration>";
		return texto;
	}
	
	public static void imprimeTextos(ArrayList<String> topicos) throws IOException{
		for (int i = 0; i < topicos.size(); i++) {  
		    System.out.println(topicos.get(i));   
		}  
	}
}
