package it.portalECI.DTO;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RispostaVerbaleDTO {
	
	public static final String TIPO_TESTO="RES_TEXT";
	public static final String TIPO_SCELTA="RES_CHOICE";
	public static final String TIPO_FORMULA="RES_FORMULA";

	
	private int id;
	private String tipo;
	private String placeholder;
	
	public RispostaVerbaleDTO() {
	}

	public RispostaVerbaleDTO(int id, String placeholder) {
		this.id = id;
		this.placeholder = placeholder;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public JsonElement getJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.id);
	
		jobj.addProperty("testo", this.tipo);
			
//		if(this.risposta!=null) {
//			JsonArray domandeVerbaleobj = new JsonArray();
//			
//			for(RispostaVerbaleDTO domanda : this.risposta) {
//				domandeVerbaleobj.add(domanda.getDomandaJsonObject());
//			}
//			jobj.add("domande", domandeVerbaleobj);
//		}
		
		
		
		return jobj;
	}
	
}
