package it.portalECI.DTO;

import java.io.Serializable;
import java.util.Date;

public class CertificatoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 983409836066964857L;
	private int id=0;
	private MisuraDTO misura;
	private StatoCertificatoDTO stato;
	private String nomeCertificato="";
	private UtenteDTO utente;
	private Date dataCreazione;
	
	public CertificatoDTO(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MisuraDTO getMisura() {
		return misura;
	}

	public void setMisura(MisuraDTO misura) {
		this.misura = misura;
	}

	public StatoCertificatoDTO getStato() {
		return stato;
	}

	public void setStato(StatoCertificatoDTO stato) {
		this.stato = stato;
	}

	public String getNomeCertificato() {
		return nomeCertificato;
	}

	public void setNomeCertificato(String nomeCertificato) {
		this.nomeCertificato = nomeCertificato;
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	
	

}
