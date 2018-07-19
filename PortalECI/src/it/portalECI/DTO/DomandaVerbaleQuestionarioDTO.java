package it.portalECI.DTO;

public class DomandaVerbaleQuestionarioDTO extends DomandaQuestionarioDTO {
	
	private QuestionarioDTO questionario;
	
	public DomandaVerbaleQuestionarioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public QuestionarioDTO getQuestionario() {
		return questionario;
	}

	public void setQuestionario(QuestionarioDTO questionario) {
		this.questionario = questionario;
	}

}
