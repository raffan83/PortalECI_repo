package it.portalECI.DTO;

import java.io.Serializable;

public class PermessoDTO implements Serializable {
	
	int idPermesso;
	String descrizione;
	String chiave_permesso;
	Boolean statoPermesso;
	
	
	public int getIdPermesso() {
		return idPermesso;
	}
	public void setIdPermesso(int idPermesso) {
		this.idPermesso = idPermesso;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getChiave_permesso() {
		return chiave_permesso;
	}
	public void setChiave_permesso(String chiave_permesso) {
		this.chiave_permesso = chiave_permesso;
	}
	public Boolean getStatoPermesso() {
		return statoPermesso;
	}
	public void setStatoPermesso(Boolean statoPermesso) {
		this.statoPermesso = statoPermesso;
	}
	
	

}
