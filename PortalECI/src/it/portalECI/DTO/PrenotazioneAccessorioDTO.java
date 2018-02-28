package it.portalECI.DTO;

import java.util.Date;

public class PrenotazioneAccessorioDTO {
	
	private int id;
	private InterventoCampionamentoDTO intervento;
	private UtenteDTO user;
	private AccessorioDTO accessorio;
	private int quantita;
	private Date data_inizio_prenotazione;
	private Date data_fine_prenotazione;
	
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
	public AccessorioDTO getAccessorio() {
		return accessorio;
	}
	public void setAccessorio(AccessorioDTO accessorio) {
		this.accessorio = accessorio;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	public Date getData_inizio_prenotazione() {
		return data_inizio_prenotazione;
	}
	public void setData_inizio_prenotazione(Date data_inizio_prenotazione) {
		this.data_inizio_prenotazione = data_inizio_prenotazione;
	}
	public Date getData_fine_prenotazione() {
		return data_fine_prenotazione;
	}
	public void setData_fine_prenotazione(Date data_fine) {
		this.data_fine_prenotazione = data_fine;
	}
	public InterventoCampionamentoDTO getIntervento() {
		return intervento;
	}
	public void setIntervento(InterventoCampionamentoDTO intervento) {
		this.intervento = intervento;
	};
	
	
}
