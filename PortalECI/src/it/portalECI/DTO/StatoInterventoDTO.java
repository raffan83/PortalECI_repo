package it.portalECI.DTO;

import java.io.Serializable;
import java.util.HashMap;

import com.google.gson.JsonObject;

public class StatoInterventoDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int CREATO 		= 1;
	public static int SCARICATO 	= 2;
	public static int DA_VERIFICARE = 3;
	public static int IN_VERIFICA 	= 4;
	public static int VERIFICATO 	= 5;
	public static int ANNULLATO		= 6;
	public static int CHIUSO		= 7;
		    
	
	private final static HashMap StatoColore = new HashMap();
	static{
		StatoColore.put(1, "#E4E5E0");//grigio
		StatoColore.put(2, "#99CBFF");//celeste
		StatoColore.put(3, "#FFA500");//arancione
		StatoColore.put(4, "#ffff00");//giallo
		StatoColore.put(5, "#77DD77");//verde
		StatoColore.put(6, "#CE3018");//rosso
		StatoColore.put(7, "#D2D2D2");// grigio chiaro
	}
	
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
	
	public String getColore(int id) {
		return (String) StatoColore.get(id);
	}
	
	public JsonObject getStatoInterventoJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("id", this.id);
		jobj.addProperty("descrizione", this.descrizione);
		
		return jobj;
	}
	
}
