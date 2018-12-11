package it.portalECI.DTO;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class RispostaTestoVerbaleDTO extends RispostaVerbaleDTO {
	
	
	private String responseValue;
	
	private RispostaTestoQuestionarioDTO rispostaQuestionario;
	
	
	public String getResponseValue() {
		return responseValue;
	}

	public void setResponseValue(String response) {
		this.responseValue = response;
	}

	public RispostaTestoQuestionarioDTO getRispostaQuestionario() {
		return rispostaQuestionario;
	}

	public void setRispostaQuestionario(RispostaTestoQuestionarioDTO rispostaQuestionario) {
		this.rispostaQuestionario = rispostaQuestionario;
	}

	public RispostaTestoVerbaleDTO() {
		this.setTipo(TIPO_TESTO);
	}

	@Override
	public JsonElement getJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.getId());
		jobj.addProperty("tipo", this.getTipo());
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

	@Override
	public String toString() {
		if(this.responseValue==null) return "";
		return this.responseValue;
	}
}
