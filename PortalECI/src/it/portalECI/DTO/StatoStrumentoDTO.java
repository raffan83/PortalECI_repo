package it.portalECI.DTO;

public class StatoStrumentoDTO {
	int id = 0;
	String nome = "";
	
	public StatoStrumentoDTO(){}
	
	
	public StatoStrumentoDTO(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
}
