package it.portalECI.DTO;

import java.util.List;

public class RispostaSceltaQuestionarioDTO extends RispostaQuestionario {
	
	private Boolean multipla;
	private List<OpzioneRispostaQuestionarioDTO> opzioni;
	
	public RispostaSceltaQuestionarioDTO() {
		super();

	}

	public Boolean getMultipla() {
		return multipla;
	}

	public void setMultipla(Boolean multipla) {
		this.multipla = multipla;
	}

	public List<OpzioneRispostaQuestionarioDTO> getOpzioni() {
		return opzioni;
	}

	public void setOpzioni(List<OpzioneRispostaQuestionarioDTO> opzioni) {
		this.opzioni = opzioni;
	}
	

}
