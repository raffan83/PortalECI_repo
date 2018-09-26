package it.portalECI.DTO;

import java.io.Serializable;

public class ProgressivoVerbaleDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idUtente;
	private int idCategoria;
	private int progressivo = 0;
	
	public ProgressivoVerbaleDTO() {
		
	}
	public ProgressivoVerbaleDTO(int idUtente, int idCategoria) {
		this.idUtente = idUtente;
		this.idCategoria = idCategoria;
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
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int categoria) {
		this.idCategoria = categoria;
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
