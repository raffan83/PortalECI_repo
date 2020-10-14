package it.portalECI.DTO;

import java.io.Serializable;

public class TipoCampioneDTO implements Serializable{
	private int id = 0;
	private String nome = "";
	
	public TipoCampioneDTO(){}
	public TipoCampioneDTO(int id, String nome){
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
	};
	
	

}
