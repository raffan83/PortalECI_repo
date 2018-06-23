package it.portalECI.DTO;

import com.google.gson.JsonObject;

public class TipoVerificaDTO {
	
	private int id;
	private int id_categoria;
	private String descrizione;
	private String codice;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_categoria() {
		return id_categoria;
	}
	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
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
	
	public JsonObject getTipoVerificaJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("id", this.id);
		jobj.addProperty("id_categoria", this.id_categoria);
		jobj.addProperty("descrizione", this.descrizione);
		jobj.addProperty("codice", this.codice);
		
		return jobj;
	}

}
