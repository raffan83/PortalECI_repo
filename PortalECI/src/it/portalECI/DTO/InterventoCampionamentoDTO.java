package it.portalECI.DTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class InterventoCampionamentoDTO {

	private int id;
	private UtenteDTO user;
	private Date dataCreazione;
	private String ID_COMMESSA="";
	private String idAttivita="";
	private StatoInterventoDTO stato;
	private Date dataChiusura;
	private String nomePack="";
	private TipoCampionamentoDTO tipoCampionamento;
	private Set<PrenotazioneAccessorioDTO> listaPrenotazioniAccessori = new HashSet<PrenotazioneAccessorioDTO>(0);
	private Set<PrenotazioniDotazioneDTO> listaPrenotazioniDotazioni = new HashSet<PrenotazioniDotazioneDTO>(0);
	private Date dataUpload;
	private String statoUpload="";
	private Date dataInizio;
	private Date dataFine;
	private UtenteDTO userUpload;
	private TipologiaCampionamentoDTO tipologiaCampionamento;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UtenteDTO getUser() {
		return user;
	}
	public void setUser(UtenteDTO user) {
		this.user = user;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public String getID_COMMESSA() {
		return ID_COMMESSA;
	}
	public void setID_COMMESSA(String iD_COMMESSA) {
		ID_COMMESSA = iD_COMMESSA;
	}
	public StatoInterventoDTO getStato() {
		return stato;
	}
	public void setStato(StatoInterventoDTO stato) {
		this.stato = stato;
	}
	public Date getDataChiusura() {
		return dataChiusura;
	}
	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}
	public Set<PrenotazioneAccessorioDTO> getListaPrenotazioniAccessori() {
		return listaPrenotazioniAccessori;
	}
	public void setListaPrenotazioniAccessori(
			Set<PrenotazioneAccessorioDTO> listaPrenotazioniAccessori) {
		this.listaPrenotazioniAccessori = listaPrenotazioniAccessori;
	}
	public Set<PrenotazioniDotazioneDTO> getListaPrenotazioniDotazioni() {
		return listaPrenotazioniDotazioni;
	}
	public void setListaPrenotazioniDotazioni(
			Set<PrenotazioniDotazioneDTO> listaPrenotazioniDotazioni) {
		this.listaPrenotazioniDotazioni = listaPrenotazioniDotazioni;
	}
	public String getIdAttivita() {
		return idAttivita;
	}
	public void setIdAttivita(String idAttivita) {
		this.idAttivita = idAttivita;
	}
	public String getNomePack() {
		return nomePack;
	}
	public void setNomePack(String nomePack) {
		this.nomePack = nomePack;
	}
	public TipoCampionamentoDTO getTipoCampionamento() {
		return tipoCampionamento;
	}
	public void setTipoCampionamento(TipoCampionamentoDTO tipoCampionamento) {
		this.tipoCampionamento = tipoCampionamento;
	}
	public Date getDataUpload() {
		return dataUpload;
	}
	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}
	public String getStatoUpload() {
		return statoUpload;
	}
	public void setStatoUpload(String statoUpload) {
		this.statoUpload = statoUpload;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	public UtenteDTO getUserUpload() {
		return userUpload;
	}
	public void setUserUpload(UtenteDTO userUpload) {
		this.userUpload = userUpload;
	}
	public TipologiaCampionamentoDTO getTipologiaCampionamento() {
		return tipologiaCampionamento;
	}
	public void setTipologiaCampionamento(TipologiaCampionamentoDTO tipologiaCampionamento) {
		this.tipologiaCampionamento = tipologiaCampionamento;
	}
	
	
	
}
