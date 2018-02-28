package it.portalECI.DTO;

import java.io.Serializable;
import java.util.Date;

public class CertificatoCampioneDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1874346293974061524L;

	private int id=0;
	
	private int id_campione=0;
	
	private String numero_certificato="";
	
	private String filename="";
	
	private Date dataCreazione;
	
	private String obsoleto="N";

	public String getObsoleto() {
		return obsoleto;
	}

	public void setObsoleto(String obsoleto) {
		this.obsoleto = obsoleto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public int getId_campione() {
		return id_campione;
	}

	public void setId_campione(int id_campione) {
		this.id_campione = id_campione;
	}

	public String getNumero_certificato() {
		return numero_certificato;
	}

	public void setNumero_certificato(String numero_certificato) {
		this.numero_certificato = numero_certificato;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	
	
}
