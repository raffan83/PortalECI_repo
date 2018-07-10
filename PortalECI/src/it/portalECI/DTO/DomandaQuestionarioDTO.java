package it.portalECI.DTO;

public class DomandaQuestionarioDTO {
	
	private int id;
	private String testo;
	private Boolean obbligatoria;
	private String placeholder;
	private RispostaQuestionario risposta;
	private QuestionarioDTO questionario;
	
	public DomandaQuestionarioDTO() {
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

	public Boolean getObbligatoria() {
		return obbligatoria;
	}

	public void setObbligatoria(Boolean obbligatoria) {
		this.obbligatoria = obbligatoria;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public RispostaQuestionario getRisposta() {
		return risposta;
	}

	public void setRisposta(RispostaQuestionario risposta) {
		this.risposta = risposta;
	}

	public QuestionarioDTO getQuestionario() {
		return questionario;
	}

	public void setQuestionario(QuestionarioDTO questionario) {
		this.questionario = questionario;
	}


}
