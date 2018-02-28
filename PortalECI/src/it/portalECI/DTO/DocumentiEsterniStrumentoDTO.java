package it.portalECI.DTO;

import java.util.Date;

public class DocumentiEsterniStrumentoDTO {
	
	private int id=0;
	private int id_strumento;
	private String nomeDocumento;
	private Date dataCaricamento;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_strumento() {
		return id_strumento;
	}
	public void setId_strumento(int id_strumento) {
		this.id_strumento = id_strumento;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}
	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}




}
