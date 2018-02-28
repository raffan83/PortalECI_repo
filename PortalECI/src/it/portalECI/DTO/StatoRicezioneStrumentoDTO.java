package it.portalECI.DTO;

import java.io.Serializable;



public class StatoRicezioneStrumentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	private int id = 0; 

	private String nome = "";

	public StatoRicezioneStrumentoDTO(){}
    public StatoRicezioneStrumentoDTO(int i) 
    {
    	id=i;
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}