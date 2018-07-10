package it.portalECI.DTO;

public class OpzioneRispostaQuestionarioDTO {
	private int id;
	private String testo;
	private RispostaSceltaQuestionarioDTO risposta;
	
	public OpzioneRispostaQuestionarioDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public RispostaSceltaQuestionarioDTO getRisposta() {
		return risposta;
	}

	public void setRisposta(RispostaSceltaQuestionarioDTO risposta) {
		this.risposta = risposta;
	}
	
}
