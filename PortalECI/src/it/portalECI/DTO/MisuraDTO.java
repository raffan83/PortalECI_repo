package it.portalECI.DTO;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class MisuraDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private InterventoDTO intervento;
	
	private InterventoDatiDTO interventoDati;
 
	private Date dataMisura;

	private StatoRicezioneStrumentoDTO statoRicezione;
	
	private StrumentoDTO strumento;

	
	private UtenteDTO user;

	private BigDecimal temperatura=BigDecimal.ZERO;

	private BigDecimal umidita=BigDecimal.ZERO;
	
	private int tipoFirma;
	
	private String obsoleto="";
	
	private String nCertificato="";
	
	private Set<PuntoMisuraDTO> listaPunti = new HashSet<PuntoMisuraDTO>(0);

	
    public String getObsoleto() {
		return obsoleto;
	}

	public void setObsoleto(String obsoleto) {
		this.obsoleto = obsoleto;
	}

	public int getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(int tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public MisuraDTO() {
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataMisura() {
		return dataMisura;
	}

	public void setDataMisura(Date dataMisura) {
		this.dataMisura = dataMisura;
	}

	public StatoRicezioneStrumentoDTO getStatoRicezione() {
		return statoRicezione;
	}

	public void setStatoRicezione(StatoRicezioneStrumentoDTO statoRicezione) {
		this.statoRicezione = statoRicezione;
	}

	public StrumentoDTO getStrumento() {
		return strumento;
	}

	public void setStrumento(StrumentoDTO strumento) {
		this.strumento = strumento;
	}

	public UtenteDTO getUser() {
		return user;
	}

	public void setUser(UtenteDTO user) {
		this.user = user;
	}

	
	public BigDecimal getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(BigDecimal temperatura) {
		this.temperatura = temperatura;
	}

	public BigDecimal getUmidita() {
		return umidita;
	}

	public void setUmidita(BigDecimal umidita) {
		this.umidita = umidita;
	}

	public InterventoDTO getIntervento() {
		return intervento;
	}

	public void setIntervento(InterventoDTO intervento) {
		this.intervento = intervento;
	}

	public InterventoDatiDTO getInterventoDati() {
		return interventoDati;
	}

	public Set<PuntoMisuraDTO> getListaPunti() {
		return listaPunti;
	}

	public void setListaPunti(Set<PuntoMisuraDTO> listaPunti) {
		this.listaPunti = listaPunti;
	}

	public void setInterventoDati(InterventoDatiDTO interventoDati) {
		this.interventoDati = interventoDati;
	}

	public String getnCertificato() {
		return nCertificato;
	}

	public void setnCertificato(String nCertificato) {
		this.nCertificato = nCertificato;
	}

}