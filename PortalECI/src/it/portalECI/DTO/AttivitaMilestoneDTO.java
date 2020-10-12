package it.portalECI.DTO;

import java.io.Serializable;

import com.google.gson.JsonObject;

public class AttivitaMilestoneDTO implements Serializable {

	private int id_riga=0;
	private String descrizioneAttivita="";
	private String noteAttivita="";
	private String descrizioneArticolo="";
	private String quantita="";
	private String codiceArticolo="";
	private String codiceAggregatore="";
	private double oreUomo;
	
	public double getOreUomo() {
		return oreUomo;
	}

	public void setOreUomo(double oreUomo) {
		this.oreUomo = oreUomo;
	}

	public int getId_riga() {
		return id_riga;
	}
	
	public void setId_riga(int id_riga) {
		this.id_riga = id_riga;
	}
	
	public String getCodiceArticolo() {
		return codiceArticolo;
	}
	
	public void setCodiceArticolo(String codiceArticolo) {
		this.codiceArticolo = codiceArticolo;
	}
	
	public String getDescrizioneAttivita() {
		return descrizioneAttivita;
	}
	
	public void setDescrizioneAttivita(String descrizioneAttivita) {
		this.descrizioneAttivita = descrizioneAttivita;
	}
	
	public String getNoteAttivita() {
		return noteAttivita;
	}
	
	public void setNoteAttivita(String noteAttivita) {
		this.noteAttivita = noteAttivita;
	}
	
	public String getDescrizioneArticolo() {
		return descrizioneArticolo;
	}
	
	public void setDescrizioneArticolo(String descrizioneArticolo) {
		this.descrizioneArticolo = descrizioneArticolo;
	}
	
	public String getQuantita() {
		return quantita;
	}
	
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	
	public String getCodiceAggregatore() {
		return codiceAggregatore;
	}
	
	public void setCodiceAggregatore(String codiceAggregatore) {
		this.codiceAggregatore = codiceAggregatore;
	}
	
	public JsonObject getListaAttivitaJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id_riga", this.id_riga);
		jobj.addProperty("descrizioneAttivita", this.descrizioneAttivita);
		jobj.addProperty("noteAttivita", this.noteAttivita);
		jobj.addProperty("descrizioneArticolo", this.descrizioneArticolo);
		jobj.addProperty("quantita", this.quantita);
		jobj.addProperty("codiceArticolo", this.codiceArticolo);
		jobj.addProperty("codiceAggregatore", this.codiceAggregatore);	
		
		return jobj;
	}
	
}
