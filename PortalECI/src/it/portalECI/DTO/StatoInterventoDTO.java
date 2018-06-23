package it.portalECI.DTO;

import com.google.gson.JsonObject;

public class StatoInterventoDTO 
{
	int id=1;
	String descrizione = "";

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
	
	public JsonObject getStatoInterventoJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("id", this.id);
		jobj.addProperty("descrizione", this.descrizione);
		
		return jobj;
	}
	
}
