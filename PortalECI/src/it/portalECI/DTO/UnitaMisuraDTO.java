package it.portalECI.DTO;

public class UnitaMisuraDTO {
	
	private int id = 0;
	private String nome = "";
	private String simbolo = "";
	private String simbolo_normalizzato = "";
	
	public UnitaMisuraDTO(){}

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

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getSimbolo_normalizzato() {
		return simbolo_normalizzato;
	}

	public void setSimbolo_normalizzato(String simbolo_normalizzato) {
		this.simbolo_normalizzato = simbolo_normalizzato;
	}
	
	

}
