package it.portalECI.DTO;

public class OpzioneRispostaQuestionarioDTO {
	private int id;
	private String testo;
	private RispostaSceltaQuestionarioDTO risposta;
	private int posizione;
	
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

	public int getPosizione() {
		return posizione;
	}

	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}
	
}
