package it.portalECI.DTO;

import java.io.Serializable;

public class ProgressivoVerbaleDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idUtente;
	private int idTipo;
	private int progressivo = 0;
	
	public ProgressivoVerbaleDTO() {
		
	}
	public ProgressivoVerbaleDTO(int idUtente, int idTipo) {
		this.idUtente = idUtente;
		this.idTipo = idTipo;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int utente) {
		this.idUtente = utente;
	}
	public int getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(int tipo) {
		this.idTipo = tipo;
	}
	public int getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	} 
	public void incrementaProgressivo() {
		this.progressivo++;
	} 

}
