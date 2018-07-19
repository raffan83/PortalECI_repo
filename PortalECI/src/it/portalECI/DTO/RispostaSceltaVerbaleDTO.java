package it.portalECI.DTO;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RispostaSceltaVerbaleDTO extends RispostaVerbaleDTO {
	
	private RispostaSceltaQuestionarioDTO rispostaQuestionario;
	
	private List<OpzioneRispostaVerbaleDTO> opzioni;
	
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

	public List<OpzioneRispostaVerbaleDTO> getOpzioni() {
		return opzioni;
	}

	public void setOpzioni(List<OpzioneRispostaVerbaleDTO> opzioni) {
		this.opzioni = opzioni;
	}

	public void addToOpzioni(OpzioneRispostaVerbaleDTO opzione) {
		if(this.opzioni==null)
			this.opzioni=new ArrayList<>();
		this.opzioni.add(opzione);
	}
	
	@Override
	public JsonElement getJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.getId());
		jobj.addProperty("tipo", this.getTipo());
		jobj.addProperty("multipla", this.getRispostaQuestionario().getMultipla());
		
		
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
