package it.portalECI.DTO;

public class SchedaConsegnaDTO {
	
	private int id;
	private int id_intervento;
	private String nome_file;
	private String data_caricamento;
	private int abilitato;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome_file() {
		return nome_file;
	}
	public void setNome_file(String nome_file) {
		this.nome_file = nome_file;
	}
	public int getId_intervento() {
		return id_intervento;
	}
	public void setId_intervento(int id_intervento) {
		this.id_intervento = id_intervento;
	}
	public String getData_caricamento() {
		return data_caricamento;
	}
	public void setData_caricamento(String data_caricamento) {
		this.data_caricamento = data_caricamento;
	}
	public int getAbilitato() {
		return abilitato;
	}
	public void setAbilitato(int abilitato) {
		this.abilitato = abilitato;
	}
	
	
	
	

}
