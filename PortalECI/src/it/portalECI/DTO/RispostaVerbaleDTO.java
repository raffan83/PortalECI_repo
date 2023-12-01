package it.portalECI.DTO;

import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public  class RispostaVerbaleDTO {
	
	public static final String TIPO_TESTO="RES_TEXT";
	public static final String TIPO_SCELTA="RES_CHOICE";
	public static final String TIPO_FORMULA="RES_FORMULA";
	public static final String TIPO_TABELLA="RES_TABLE";

	
	private int id;
	
	private String tipo;
	
	private Date createDate;
	private Date updateDate;
	
	
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
	
	

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	//public abstract JsonElement getJsonObject();
	
}
