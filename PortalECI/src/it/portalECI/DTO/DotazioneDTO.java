package it.portalECI.DTO;


import java.util.Set;

public class DotazioneDTO {
	private int id;
	private CompanyDTO company;
	private String marca="";
	private String modello="";
	private TipologiaDotazioniDTO tipologia;
	private String matricola="";
	private String targa="";
	private Set<PrenotazioniDotazioneDTO> listaPrenotazioni;
	private String schedaTecnica;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public TipologiaDotazioniDTO getTipologia() {
		return tipologia;
	}
	public void setTipologia(TipologiaDotazioniDTO tipologia) {
		this.tipologia = tipologia;
	}
	public String getMatricola() {
		return matricola;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	public Set<PrenotazioniDotazioneDTO> getListaPrenotazioni() {
		return listaPrenotazioni;
	}
	public void setListaPrenotazioni(Set<PrenotazioniDotazioneDTO> listaPrenotazioni) {
		this.listaPrenotazioni = listaPrenotazioni;
	}
	public CompanyDTO getCompany() {
		return company;
	}
	public void setCompany(CompanyDTO company) {
		this.company = company;
	}
	public String getSchedaTecnica() {
		return schedaTecnica;
	}
	public void setSchedaTecnica(String schedaTecnica) {
		this.schedaTecnica = schedaTecnica;
	}
	
}
