package it.portalECI.DTO;

import java.io.Serializable;

import com.google.gson.JsonObject;

public class StatoVerbaleDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int CREATO 			= 1;
	public static int IN_COMPILAZIONE 	= 2;
	public static int COMPILATO 		= 3;
	public static int DA_VERIFICARE 	= 4;
	public static int ACCETTATO 		= 5;
	public static int RIFIUTATO			= 6;
	public static int CHIUSO			= 7;
		    
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
	
	public JsonObject getStatoVErbaleJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("id", this.id);
		jobj.addProperty("descrizione", this.descrizione);
		
		return jobj;
	}
	
}
