package it.portalECI.DTO;

import java.util.Date;

public class StoricoEmailVerbaleDTO {

	private int id;
	private int id_verbale;
	private String destinatario;
	private UtenteDTO utente;
	private Date data;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_verbale() {
		return id_verbale;
	}
	public void setId_verbale(int id_verbale) {
		this.id_verbale = id_verbale;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public UtenteDTO getUtente() {
		return utente;
	}
	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
