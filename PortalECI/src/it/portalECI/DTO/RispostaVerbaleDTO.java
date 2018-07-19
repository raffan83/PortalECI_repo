package it.portalECI.DTO;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class RispostaVerbaleDTO {
	
	public static final String TIPO_TESTO="RES_TEXT";
	public static final String TIPO_SCELTA="RES_CHOICE";
	public static final String TIPO_FORMULA="RES_FORMULA";

	
	private int id;
	
	private String tipo;
	
	
	public RispostaVerbaleDTO() {
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public abstract JsonElement getJsonObject();
	
}
