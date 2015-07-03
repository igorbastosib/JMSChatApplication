package jogoAssassino;

public class Jogador {
//Atributos
	private String nome;
	private String host;
	private String status;
	private String job;

//Construtor
	public Jogador(){
		this.nome = "";
		this.status = "";
		this.job = "";
	}
	
	public Jogador(String nome, String status, String jog){
		this();
		this.nome = nome;
		this.status = status;
		this.job = job;
	}

//Getters and Setters
	//Nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	//Status
	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	//Job
	public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
