package it.portalECI.DTO;

import java.util.List;

public class RispostaSceltaVerbaleDTO extends RispostaVerbaleDTO {
	
	private Boolean multipla;
	private List<OpzioneRispostaVerbaleDTO> opzioni;
	
	public RispostaSceltaVerbaleDTO() {
		super();

	}

	public Boolean getMultipla() {
		return multipla;
	}

	public void setMultipla(Boolean multipla) {
		this.multipla = multipla;
	}

	public List<OpzioneRispostaVerbaleDTO> getOpzioni() {
		return opzioni;
	}

	public void setOpzioni(List<OpzioneRispostaVerbaleDTO> opzioni) {
		this.opzioni = opzioni;
	}
	

}
