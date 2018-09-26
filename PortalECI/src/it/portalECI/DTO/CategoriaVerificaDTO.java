package it.portalECI.DTO;

import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;

import com.google.gson.JsonObject;

public class CategoriaVerificaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String descrizione;
	private String codice;
	private String sigla;
	
	//private Set<InterventoDTO> intervento= new HashSet<InterventoDTO>();
	
	public CategoriaVerificaDTO() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	/*public Set<InterventoDTO> getIntervento() {
		return intervento;
	}
	public void setIntervento(Set<InterventoDTO> intervento) {
		this.intervento = intervento;
	}*/
	
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public JsonObject getCategoriaVerificaJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("id", this.id);
		jobj.addProperty("descrizione", this.descrizione);
		jobj.addProperty("codice", this.codice);
		
		return jobj;
	}

}
