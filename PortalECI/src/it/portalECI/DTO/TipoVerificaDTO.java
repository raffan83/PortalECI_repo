package it.portalECI.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;

public class TipoVerificaDTO implements Serializable{
	
	private int id;
	private CategoriaVerificaDTO categoria;
	private String descrizione;
	private String codice;
	
	private Collection<InterventoDTO> intervento;
	
	public TipoVerificaDTO() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CategoriaVerificaDTO getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaVerificaDTO categoria) {
		this.categoria = categoria;
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
	
	public Collection<InterventoDTO> getIntervento() {
		return intervento;
	}
	public void setIntervento(Collection<InterventoDTO> intervento) {
		this.intervento = intervento;
	}
	
	public JsonObject getTipoVerificaJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("id", this.id);
		
		if(this.categoria!=null)
			jobj.add("categoria", this.categoria.getCategoriaVerificaJsonObject());
		
		jobj.addProperty("descrizione", this.descrizione);
		jobj.addProperty("codice", this.codice);
		
		return jobj;
	}

}
