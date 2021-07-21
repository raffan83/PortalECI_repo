package it.portalECI.DTO;

import java.util.Date;

public class VersionePortaleDTO {

	private int id;
	private Date data_aggiornamento;
	private String versione;
	private String note_aggiornamento;
	private int disabilitato;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVersione() {
		return versione;
	}
	public void setVersione(String versione) {
		this.versione = versione;
	}
	public String getNote_aggiornamento() {
		return note_aggiornamento;
	}
	public void setNote_aggiornamento(String note_aggiornamento) {
		this.note_aggiornamento = note_aggiornamento;
	}
	public Date getData_aggiornamento() {
		return data_aggiornamento;
	}
	public void setData_aggiornamento(Date data_aggiornamento) {
		this.data_aggiornamento = data_aggiornamento;
	}
	public int getDisabilitato() {
		return disabilitato;
	}
	public void setDisabilitato(int disabilitato) {
		this.disabilitato = disabilitato;
	}
	
	
	
}
