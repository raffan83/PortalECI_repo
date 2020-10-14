package it.portalECI.DTO;

public class ComunicazioneUtenteDTO {
	
	private int id;
	private UtenteDTO utente;
	private TipoComunicazioneUtenteDTO tipo_comunicazione;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UtenteDTO getUtente() {
		return utente;
	}
	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
	public TipoComunicazioneUtenteDTO getTipo_comunicazione() {
		return tipo_comunicazione;
	}
	public void setTipo_comunicazione(TipoComunicazioneUtenteDTO tipo_comunicazione) {
		this.tipo_comunicazione = tipo_comunicazione;
	}

	

}
