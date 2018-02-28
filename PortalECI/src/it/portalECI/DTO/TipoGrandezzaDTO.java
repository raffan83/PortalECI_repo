package it.portalECI.DTO;

import java.util.HashSet;
import java.util.Set;

public class TipoGrandezzaDTO {
	private int id = 0;
	private String nome = "";
	
	private Set<UnitaMisuraDTO> listaUM = new HashSet<UnitaMisuraDTO>(0);
	
	

	public TipoGrandezzaDTO(){}

	public int getId() {
		return id;
	}

	public Set<UnitaMisuraDTO> getListaUM() {
		return listaUM;
	}

	public void setListaUM(Set<UnitaMisuraDTO> listaUM) {
		this.listaUM = listaUM;
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
