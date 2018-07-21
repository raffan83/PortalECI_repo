package it.portalECI.DTO;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RispostaFormulaVerbaleDTO extends RispostaVerbaleDTO {
		
	private RispostaFormulaQuestionarioDTO rispostaQuestionario;
	
	private String value1;
	private String value2;
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


	public String getValue1() {
		return value1;
	}



	public void setValue1(String value1) {
		this.value1 = value1;
	}



	public String getValue2() {
		return value2;
	}



	public void setValue2(String value2) {
		this.value2 = value2;
	}



	public String getResponseValue() {
		return responseValue;
	}



	public void setResponseValue(String resultValue) {
		this.responseValue = resultValue;
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
