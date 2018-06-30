package it.portalECI.DTO;

import java.io.Serializable;

import com.google.gson.JsonObject;

public class QuestionarioDTO implements Serializable {

	private int id=0;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public JsonObject getQuestionariJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.id);
		
		return jobj;
	}
	
}