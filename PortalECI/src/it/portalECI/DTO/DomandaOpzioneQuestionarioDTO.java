package it.portalECI.DTO;

public class DomandaOpzioneQuestionarioDTO extends DomandaQuestionarioDTO {
	private OpzioneRispostaQuestionarioDTO opzione;

	public DomandaOpzioneQuestionarioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public OpzioneRispostaQuestionarioDTO getOpzione() {
		return opzione;
	}

	public void setOpzione(OpzioneRispostaQuestionarioDTO opzione) {
		this.opzione = opzione;
	}
}
