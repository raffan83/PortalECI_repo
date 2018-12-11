package it.portalECI.DTO;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RispostaSceltaVerbaleDTO extends RispostaVerbaleDTO {
	
	private RispostaSceltaQuestionarioDTO rispostaQuestionario;
	
	private Set<OpzioneRispostaVerbaleDTO> opzioni;
	
	public RispostaSceltaVerbaleDTO() {
		super();
		this.setTipo(TIPO_SCELTA);
	}

	public RispostaSceltaQuestionarioDTO getRispostaQuestionario() {
		return rispostaQuestionario;
	}

	public void setRispostaQuestionario(RispostaSceltaQuestionarioDTO rispostaQuestionario) {
		this.rispostaQuestionario = rispostaQuestionario;
	}

	public Set<OpzioneRispostaVerbaleDTO> getOpzioni() {
		return opzioni;
	}

	public void setOpzioni(Set<OpzioneRispostaVerbaleDTO> opzioni) {
		this.opzioni = opzioni;
	}

	public void addToOpzioni(OpzioneRispostaVerbaleDTO opzione) {
		if(this.opzioni==null)
			this.opzioni=new HashSet<>();
		this.opzioni.add(opzione);
	}
	
	@Override
	public JsonElement getJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.getId());
		jobj.addProperty("tipo", this.getTipo());
		jobj.addProperty("multipla", this.getRispostaQuestionario().getMultipla());
		
		if(this.opzioni!=null) {
			JsonArray opzioniobj = new JsonArray();
			
			for(OpzioneRispostaVerbaleDTO opzione: this.opzioni) {
				opzioniobj.add(opzione.getJsonObject());
			}
			jobj.add("opzioni", opzioniobj);
		}		
		
		return jobj;
	}
	
	@Override
	public String toString() {
		
		String result = "";
		for(OpzioneRispostaVerbaleDTO opzione: this.opzioni) {
			if (opzione.getChecked()) result = result + "<br/>"+ opzione.getOpzioneQuestionario().getTesto();
		}
		return result;
	}

	

}
