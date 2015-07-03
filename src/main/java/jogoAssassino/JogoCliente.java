package jogoAssassino;

/*
 * ClienteAplicativo.java
 */

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import chat.ChatJMSSub;
import chat.ChatUtil;

public class JogoCliente {
	private JogoInterface jogo;
	private String nomeJogador;
	private InitialContext context;

	public JogoCliente() throws NamingException {
		setNomeJogador("ERRO");
		context = (InitialContext) ChatUtil.getInitialContext();
		setJogo((JogoInterface) context.lookup("JogoDados"));
	}

	// Getters e Setters
	/**
	 * @return the jogo
	 */
	public JogoInterface getJogo() {
		return jogo;
	}

	/**
	 * @param jogo
	 *            the jogo to set
	 */
	public void setJogo(JogoInterface jogo) {
		this.jogo = jogo;
	}

	/**
	 * @return the nomeJogador
	 */
	public String getNomeJogador() {
		return nomeJogador;
	}

	/**
	 * @param nomeJogador
	 *            the nomeJogador to set
	 */
	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public String registraNome(String nome) throws RemoteException {
		String retorno = getJogo().registraJogador(nome, "", "");
		if (!retorno.equals("ERRO")) {
			setNomeJogador(nome);
		}
		return retorno;
	}

	public String enviaMensagem(String mensagem) throws RemoteException, IllegalArgumentException, JMSException, NamingException{
		return getJogo().gerenciaCodigoDigitado(mensagem, "app", getNomeJogador());
	}
}