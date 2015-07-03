package jogoAssassino;


/*

 * CreditoHome.java
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.jms.JMSException;
import javax.naming.NamingException;

public interface JogoInterface extends Remote {
	public Boolean isJogoIniciado() throws RemoteException;
	public String registraJogador(String nome, String status, String job) throws RemoteException;
	public String gerenciaCodigoDigitado(String codigo, String tipoSistema, String nomeJgadorEnvio) throws RemoteException, IllegalArgumentException, JMSException, NamingException;
	public String listaCodigo(String tipoSistema, String job) throws RemoteException;
	public String localizaJob(String nomeJogador) throws RemoteException;
}
