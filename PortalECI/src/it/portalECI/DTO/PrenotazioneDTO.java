package it.portalECI.DTO;

import java.util.Date;

public class PrenotazioneDTO {
	private int id;
	private CampioneDTO campione;
	private CompanyDTO companyRichiedente;
	private UtenteDTO userRichiedente;
	private Date dataRichiesta;
	private Date dataGestione;
	private Date prenotatoDal;
	private Date prenotatoAl;
	private String note="";
	private String noteApprovazione="";
	private String matricolaCampione="";
	private Date dataApprovazione;
	private StatoPrenotazioneDTO stato;
	
	public PrenotazioneDTO() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public CampioneDTO getCampione() {
		return campione;
	}


	public void setCampione(CampioneDTO id_campione) {
		this.campione = id_campione;
	}


	public CompanyDTO getCompanyRichiedente() {
		return companyRichiedente;
	}


	public void setCompanyRichiedente(CompanyDTO companyRichiedente) {
		this.companyRichiedente = companyRichiedente;
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


	public Date getDataGestione() {
		return dataGestione;
	}


	public void setDataGestione(Date dataApprovazione) {
		this.dataGestione = dataApprovazione;
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


	public String getNoteApprovazione() {
		return noteApprovazione;
	}


	public void setNoteApprovazione(String noteApprovazione) {
		this.noteApprovazione = noteApprovazione;
	}



	public String getMatricolaCampione() {
		return matricolaCampione;
	}


	public void setMatricolaCampione(String matricolaCampione) {
		this.matricolaCampione = matricolaCampione;
	}


	public Date getDataApprovazione() {
		return dataApprovazione;
	}


	public void setDataApprovazione(Date dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}


	public StatoPrenotazioneDTO getStato() {
		return stato;
	}


	public void setStato(StatoPrenotazioneDTO stato) {
		this.stato = stato;
	}
}
