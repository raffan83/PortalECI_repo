package it.portalECI.DTO;

public class ColonnaTabellaQuestionarioDTO {
	
	private Long id;
	private Long posizione;

	private RispostaTabellaQuestionarioDTO risposta;
	private DomandaQuestionarioDTO domanda;
	private Long larghezza;

	
	public Long getId() {
		return id;
	}
	
	public Long getPosizione() {
		return posizione;
	}

	public void setPosizione(Long posizione) {
		this.posizione = posizione;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ColonnaTabellaQuestionarioDTO() {
	}

	public RispostaTabellaQuestionarioDTO getRisposta() {
		return risposta;
	}

	public void setRisposta(RispostaTabellaQuestionarioDTO risposta) {
		this.risposta = risposta;
	}

	public DomandaQuestionarioDTO getDomanda() {
		return domanda;
	}

	public void setDomanda(DomandaQuestionarioDTO domanda) {
		this.domanda = domanda;
	}

	public Long getLarghezza() {
		return larghezza;
	}

	public void setLarghezza(Long larghezza) {
		this.larghezza = larghezza;
	}
	

}
