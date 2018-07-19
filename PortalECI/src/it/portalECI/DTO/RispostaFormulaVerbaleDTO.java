package it.portalECI.DTO;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RispostaFormulaVerbaleDTO extends RispostaVerbaleDTO {
		
	private RispostaFormulaQuestionarioDTO rispostaQuestionario;
	
	private String responseValue;
	
	public RispostaFormulaVerbaleDTO() {
		this.setTipo(TIPO_FORMULA);
	}

	

	public RispostaFormulaQuestionarioDTO getRispostaQuestionario() {
		return rispostaQuestionario;
	}



	public void setRispostaQuestionario(RispostaFormulaQuestionarioDTO rispostaQuestionario) {
		this.rispostaQuestionario = rispostaQuestionario;
	}



	public String getResponseValue() {
		return responseValue;
	}



	public void setResponseValue(String responseValue) {
		this.responseValue = responseValue;
	}



	@Override
	public JsonElement getJsonObject() {
		
		JsonObject jobj = new JsonObject();
		jobj.addProperty("id", this.getId());
		jobj.addProperty("tipo", this.getTipo());
		jobj.addProperty("label1", this.getRispostaQuestionario().getValore1());
		jobj.addProperty("label2", this.getRispostaQuestionario().getValore2());
		jobj.addProperty("operatore", this.getRispostaQuestionario().getOperatore());
		jobj.addProperty("label_risultato", this.getRispostaQuestionario().getRisultato());
		
		
		return jobj;
	}
	
}
