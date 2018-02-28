package it.portalECI.DTO;

import java.util.Date;

public class InterventoDatiDTO {
	
	private int id;
	private int id_intervento;
	private Date dataCreazione;
	private String nomePack="";
	private StatoPackDTO stato;
	private int numStrNuovi;
	private int numStrMis;
	private UtenteDTO utente;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_intervento() {
		return id_intervento;
	}
	public void setId_intervento(int id_intervento) {
		this.id_intervento = id_intervento;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public String getNomePack() {
		return nomePack;
	}
	public void setNomePack(String nomePack) {
		this.nomePack = nomePack;
	}
	public StatoPackDTO getStato() {
		return stato;
	}
	public void setStato(StatoPackDTO stato) {
		this.stato = stato;
	}
	public int getNumStrNuovi() {
		return numStrNuovi;
	}
	public void setNumStrNuovi(int numStrNuovi) {
		this.numStrNuovi = numStrNuovi;
	}
	public int getNumStrMis() {
		return numStrMis;
	}
	public void setNumStrMis(int numStrMis) {
		this.numStrMis = numStrMis;
	}
	public UtenteDTO getUtente() {
		return utente;
	}
	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
	

	
}
