package it.portalECI.DTO;

public class OpzioneRispostaVerbaleDTO {
	private int id;
	private OpzioneRispostaQuestionarioDTO opzioneQuestionario;
	private RispostaSceltaVerbaleDTO risposta;
	
	public OpzioneRispostaVerbaleDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OpzioneRispostaQuestionarioDTO getOpzioneQuestionario() {
		return opzioneQuestionario;
	}

	public void setOpzioneQuestionario(OpzioneRispostaQuestionarioDTO opzioneQuestionario) {
		this.opzioneQuestionario = opzioneQuestionario;
	}

	public RispostaSceltaVerbaleDTO getRisposta() {
		return risposta;
	}

	public void setRisposta(RispostaSceltaVerbaleDTO risposta) {
		this.risposta = risposta;
	}
	
}
