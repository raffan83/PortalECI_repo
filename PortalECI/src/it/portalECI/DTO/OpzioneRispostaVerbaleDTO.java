package it.portalECI.DTO;

public class OpzioneRispostaVerbaleDTO {
	private int id;
	private String testo;
	private RispostaSceltaVerbaleDTO risposta;
	
	public OpzioneRispostaVerbaleDTO() {
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

	public RispostaSceltaVerbaleDTO getRisposta() {
		return risposta;
	}

	public void setRisposta(RispostaSceltaVerbaleDTO risposta) {
		this.risposta = risposta;
	}
	
}
