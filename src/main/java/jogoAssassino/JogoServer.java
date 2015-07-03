package jogoAssassino;

/*
 * CreditoServer.java
 */

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import chat.ChatJMSPub;
import chat.ChatUtil;

public class JogoServer extends UnicastRemoteObject implements JogoInterface {
	// Atributos
	private ArrayList<Jogador> jogadores;
	private ArrayList<String> papeis;
	private Boolean assassino;
	private Boolean detetive;
	private Boolean jogoIniciado;
	private ChatJMSPub pubChat;

	// Construtor
	public JogoServer() throws RemoteException {
		try {
			pubChat = new ChatJMSPub();
			System.out.println("ChatPub configurado");
		} catch (Exception e) {
			System.out.println("Erro ao conectar com ServidorChat");
			// e.printStackTrace();
		}

		this.jogadores = new ArrayList<Jogador>();
		this.papeis = new ArrayList<String>();
		this.papeis.add("ASSASSINO");
		this.papeis.add("DETETIVE");
		this.papeis.add("VITIMA");
		this.setJogoIniciado(false);
	}

	// Getters and Setters

	/**
	 * @return the jogoIniciado
	 */
	public Boolean isJogoIniciado() {
		return jogoIniciado;
	}

	/**
	 * @param jogoIniciado the jogoIniciado to set
	 */
	private void setJogoIniciado(Boolean jogoIniciado) {
		this.jogoIniciado = jogoIniciado;
	}

	// Outros Metodos
	// Remoto
	public String registraJogador(String nome, String status, String job) {
		try {
			if (!nome.isEmpty() && localizaJogador(nome) == null) {
				Jogador jogador = new Jogador(nome, status, job);
				jogadores.add(jogador);
				pubChat.criaTopicosJMS(listaJogadores());
				return nome;
			} else {
				return "ERRO";
			}
		} catch (Exception e) {
			jogadores.remove(jogadores.size()-1);
			return "ERRO";
		}
	}

	// Remoto
	public String listaCodigo(String tipoSistema, String job) {
		String codigos = "**Codigos disponíveis**\n" + "/listCode: lista codigos disponivies\n" + "/listPlayers: lista os dados de todos jogadores\n";
		if (tipoSistema.equals("servidor")) {
			codigos = codigos + "/play: inicia jogo\n";
		} else if (job.equals("ASSASSINO")) {
			codigos = codigos + "/kill <NOME JOGADOR>: tenta matar jogador informado\n";
		} else if (job.equals("DETETIVE")) {
			codigos = codigos + "/capture <NOME JOGADOR>: tenta prender jogador informado\n";
		}
		return codigos;
	}

	// Remoto
	public String gerenciaCodigoDigitado(String codigo, String tipoSistema, String nomeJogadorEnvio) throws JMSException, NamingException {
		Jogador jogadorEnvio = localizaJogador(nomeJogadorEnvio);
		String job = "";
		if (!tipoSistema.equals("servidor") && nomeJogadorEnvio.isEmpty() && jogadorEnvio == null) {
			throw new IllegalArgumentException("E necessario ser um Jogador Registrado para realizar esse procedimento");
		} else if (jogadorEnvio != null) {
			job = jogadorEnvio.getJob();
		}

		if (codigo.substring(0, 1).contains("/")) {
			if (codigo.equals("/play")) {
				if (jogadores.size() > 2) {
					System.out.println("**Iniciando Jogo**");
					iniciaJogo();
				} else {
					throw new IllegalArgumentException("Quantidade de jogadores insuficiente. " + jogadores.size());
				}
			} else if (codigo.equals("/listCode")) {
				return listaCodigo(tipoSistema, job);
			} else if (codigo.equals("/listPlayers")) {
				return listaJogadores(tipoSistema);
			} else if (codigo.substring(0, 6).contains("/kill ") || codigo.substring(0, 9).contains("/capture ")) {
				if (isJogoIniciado()) {
					int tamanho = 6;
					if (codigo.substring(0, 9).contains("/capture ")) {
						tamanho = 9;
					}
					String nomeJogador = codigo.substring(tamanho); // Pego o
																	// nome
																	// do
																	// jogador a
																	// ser
																	// morto/capturado
					Jogador jogador = localizaJogador(nomeJogador);

					if (jogador != null) {
						if (codigo.substring(0, 6).contains("/kill ")) { // Verifico
																			// se
																			// os
																			// 6
																			// primeiros
																			// caracteres
																			// sao
																			// /kill
							if (!jogadorEnvio.getJob().equals("ASSASSINO")) {
								throw new IllegalArgumentException("**Voce nao ter permissao para executar este comando**");
							}
							if (jogador.getJob().equals("VITIMA") && jogador.getStatus().equals("VIVO") && !jogador.getNome().equals(nomeJogadorEnvio)) {
								matarJogador(jogador);
								return "**O jogador " + nomeJogador + " foi assassinado**";
							} else {
								throw new IllegalArgumentException("**Impossivel assassinar o jogador <" + nomeJogador + ">**");
							}
						} else if (codigo.substring(0, 9).contains("/capture ")) { // Verifico
																					// se
																					// os
																					// 9
																					// primeiros
																					// caracteres
																					// sao
																					// /capture
							if (!jogadorEnvio.getJob().equals("DETETIVE")) {
								throw new IllegalArgumentException("**Voce nao ter permissao para executar este comando**");
							}
							if (jogador.getJob().equals("ASSASSINO") && jogador.getStatus().equals("VIVO") && !jogador.getNome().equals(nomeJogadorEnvio)) {
								matarJogador(jogador);
								return "**O jogador " + nomeJogador + " foi capturado**";
							} else {
								throw new IllegalArgumentException("**Impossivel capturar o jogador <" + nomeJogador + ">**");
							}
						}
					} else {
						throw new IllegalArgumentException("**Jogador <" + nomeJogador + "> nao encontrado**");
					}
				} else {
					throw new IllegalArgumentException("**O jogo nao foi iniciado**");
				}
			} else {
				throw new IllegalArgumentException("**Codigo nao reconhecido**");
			}
		} else {
			enviarMensagem(nomeJogadorEnvio, codigo);
		}
		return "";
	}

	private void enviarMensagem(String nomeJogadorEnvio, String mensagem) {
		// TODO Auto-generated method stub
		String jogadorMsgPrivada = "";

		if(mensagem.substring(0,1).equals("<")){
			jogadorMsgPrivada = mensagem.substring(1, mensagem.lastIndexOf('>'));
		}
		if(jogadorMsgPrivada.isEmpty()){
			pubChat.writeMessage("MsgPublic", nomeJogadorEnvio+": "+mensagem);
		}else{
			pubChat.writeMessage(jogadorMsgPrivada, "PM "+nomeJogadorEnvio+": "+mensagem.substring(mensagem.lastIndexOf('>')+1, mensagem.length()));
		}
	}

	public String listaJogadores(String tipoSistema) {
		String lista = "";
		for (int i = 0; i < jogadores.size(); i++) {
			lista = lista + "\n" + String.format("%-15s", jogadores.get(i).getNome())+ String.format("%-10s", jogadores.get(i).getStatus());
			if (tipoSistema.equals("servidor")) {
				lista = lista + String.format("%-15s", jogadores.get(i).getJob());
			}
		}
		return lista;
	}
	
	public ArrayList<String> listaJogadores() {
		ArrayList<String> lista = new ArrayList<String>();
		for (int i = 0; i < jogadores.size(); i++) {
			lista.add(jogadores.get(i).getNome());
		}
		return lista;
	}

	private void distribuirFuncoes() {
		System.out.println("**Alocando papeis**");
		this.assassino = false;
		this.detetive = false;
		int countJogradoresFunc = 0;
		for (int i = 0; i < this.jogadores.size(); i++) {
			controlFunc(this.jogadores.get(i), this.assassino, this.detetive, countJogradoresFunc);
			this.jogadores.get(i).setStatus("VIVO");
			enviarMensagem("JOGO", "<"+this.jogadores.get(i).getNome()+"> Você é "+this.jogadores.get(i).getJob());
			countJogradoresFunc++;
		}
		System.out.println(listaJogadores("servidor"));
	}

	private void controlFunc(Jogador jogador, Boolean assassino, Boolean detetive, int countJogadoresFunc) {
		int totalJogadores = jogadores.size() - countJogadoresFunc;
		Random gerador = new Random();
		int aux = gerador.nextInt(totalJogadores);

		// System.out.println(aux+" "+(jogadores.size()-countJogadoresFunc)+" "+this.assassino+" "+this.detetive);
		if (this.assassino == false && (aux == 0 || totalJogadores == 1)) {
			this.assassino = true;
			jogador.setJob(this.papeis.get(0));
		} else if (this.detetive == false && (aux == 1 || totalJogadores == 1)) {
			this.detetive = true;
			jogador.setJob(this.papeis.get(1));
		} else {
			jogador.setJob(this.papeis.get(2));
		}
	}

	private Jogador localizaJogador(String nome) {
		for (int i = 0; i < this.jogadores.size(); i++) {
			if (this.jogadores.get(i).getNome().equals(nome)) {
				return jogadores.get(i);
			}
		}
		return null;
	}
	
	public String localizaJob(String nomeJogador){
		Jogador jogador = localizaJogador(nomeJogador);
		if(jogador != null){
			return jogador.getJob();
		}
		return "";
	}

	private void matarJogador(Jogador jogador) {
		int qtdVitimas = 0;
		int qtdAssassino = 0;
		jogador.setStatus("MORTO");
		enviarMensagem("JOGO", "<"+jogador.getNome()+"> Você foi MORTO(a)");
		enviarMensagem("JOGO", "Jogador(a) "+jogador.getNome()+" foi MORTO(a)");

		for (int i = 0; i < this.jogadores.size(); i++) {
			if (this.jogadores.get(i).getStatus().equals("VIVO")) {
				if (this.jogadores.get(i).getJob().equals("VITIMA")) {
					qtdVitimas++;
				} else if (this.jogadores.get(i).getJob().equals("ASSASSINO")) {
					qtdAssassino++;
				}
			}
		}
		if (qtdVitimas == 0) {
			finalizaJogo("ASSASSINO");
		} else if (qtdAssassino == 0) {
			finalizaJogo("DETETIVE");
		}
	}

	private void iniciaJogo() {
		distribuirFuncoes();
		this.setJogoIniciado(true);
		enviarMensagem("JOGO", "** Jogo iniciado **");
	}

	private void finalizaJogo(String vencedor) {
		for (int i = 0; i < this.jogadores.size(); i++) {
			this.jogadores.get(i).setJob("");
			this.jogadores.get(i).setStatus("");
		}
		if (vencedor.equals("DETETIVE")) {
			System.out.println("**O detetive venceu**");
		} else {
			System.out.println("**O assassino venceu**");
		}
		enviarMensagem("JOGO", "O "+vencedor+" venceu");
		enviarMensagem("JOGO", "** Fim de Jogo **");
		System.out.println("**Fim de Jogo**");
		this.setJogoIniciado(false);
	}

	private static JogoInterface jogo;

	public static class LeCodigo implements Runnable {
		public void run() {
			System.out.println("Digite /listCode para ver os comandos disponiveis");
			while (true) {
				try {
					Scanner entrada = new Scanner(System.in);
					String codigo = entrada.nextLine();
					System.out.println(jogo.gerenciaCodigoDigitado(codigo, "servidor", ""));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public static void main(String args[]) {
		try {
			jogo = new JogoServer();
			InitialContext context = (InitialContext) ChatUtil.getInitialContext();
			context.rebind("JogoDados", jogo);
			System.out.println("JogoDados, objeto registado no rmiregistry");
		} catch (Exception e) {
			System.out.println("JogoDados err: " + e.getMessage());
			// e.printStackTrace();
		}
		System.out.println("**Pronto**");
		LeCodigo leCodigo = new LeCodigo();
		Thread threadLeCodigo = new Thread(leCodigo);
		threadLeCodigo.start();
	}
}
