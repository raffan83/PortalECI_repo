package it.portalECI.DTO;

import java.util.Date;

public class PrenotazioniDotazioneDTO {
	
	private int id = 0;
	private int id_intervento_campionamento = 0;
	private DotazioneDTO dotazione;
	private UtenteDTO userRichiedente;
	private Date dataRichiesta;
	private Date prenotatoDal;
	private Date prenotatoAl;
	private String note = "";
	private InterventoCampionamentoDTO intervento;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

	public int getId_intervento_campionamento() {
		return id_intervento_campionamento;
	}
	public void setId_intervento_campionamento(int id_intervento_campionamento) {
		this.id_intervento_campionamento = id_intervento_campionamento;
	}
	public DotazioneDTO getDotazione() {
		return dotazione;
	}
	public void setDotazione(DotazioneDTO dotazione) {
		this.dotazione = dotazione;
	}
	public UtenteDTO getUserRichiedente() {
		return userRichiedente;
	}
	public void setUserRichiedente(UtenteDTO userRichiedente) {
		this.userRichiedente = userRichiedente;
	}
	public Date getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public Date getPrenotatoDal() {
		return prenotatoDal;
	}
	public void setPrenotatoDal(Date prenotatoDal) {
		this.prenotatoDal = prenotatoDal;
	}
	public Date getPrenotatoAl() {
		return prenotatoAl;
	}
	public void setPrenotatoAl(Date prenotatoAl) {
		this.prenotatoAl = prenotatoAl;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public InterventoCampionamentoDTO getIntervento() {
		return intervento;
	}
	public void setIntervento(InterventoCampionamentoDTO intervento) {
		this.intervento = intervento;
	}

 	
	
}
