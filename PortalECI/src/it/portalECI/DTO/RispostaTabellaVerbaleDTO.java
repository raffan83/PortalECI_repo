package it.portalECI.DTO;

import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RispostaTabellaVerbaleDTO extends RispostaVerbaleDTO {
	
	private RispostaTabellaQuestionarioDTO rispostaQuestionario;
	
	private Set<ColonnaTabellaVerbaleDTO> colonne;

	public RispostaTabellaVerbaleDTO() {
		this.setTipo(TIPO_TABELLA);
	}

	public RispostaTabellaQuestionarioDTO getRispostaQuestionario() {
		return rispostaQuestionario;
	}

	public void setRispostaQuestionario(RispostaTabellaQuestionarioDTO rispostaQuestionario) {
		this.rispostaQuestionario = rispostaQuestionario;
	}

	public Set<ColonnaTabellaVerbaleDTO> getColonne() {
		return colonne;
	}

	public void setColonne(Set<ColonnaTabellaVerbaleDTO> colonne) {
		this.colonne = colonne;
	}

	@Override
	public JsonElement getJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.getId());
		jobj.addProperty("tipo", this.getTipo());
		
		if(this.colonne!=null) {
			JsonArray colonnejson = new JsonArray();
			
			for(ColonnaTabellaVerbaleDTO colonna: this.colonne) {
				colonnejson.add(colonna.getJsonObject());
			}
			jobj.add("colonne", colonnejson);
		}		
		
		return jobj;
	}

}
