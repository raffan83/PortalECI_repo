package it.portalECI.DTO;

public class DomandaSchedaTecnicaQuestionarioDTO extends DomandaQuestionarioDTO {
	
	private QuestionarioDTO questionario;

	public DomandaSchedaTecnicaQuestionarioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public QuestionarioDTO getQuestionario() {
		return questionario;
	}

	public void setQuestionario(QuestionarioDTO questionario) {
		this.questionario = questionario;
	}
}
