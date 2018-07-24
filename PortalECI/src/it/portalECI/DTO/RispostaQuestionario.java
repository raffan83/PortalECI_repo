package it.portalECI.DTO;

public abstract class RispostaQuestionario {
	
	public static final String TIPO_TESTO="RES_TEXT";
	public static final String TIPO_SCELTA="RES_CHOICE";
	public static final String TIPO_FORMULA="RES_FORMULA";

	
	private int id;
	private String tipo;
	private String placeholder;
	private DomandaQuestionarioDTO domanda;
	
	public DomandaQuestionarioDTO getDomanda() {
		return domanda;
	}

	public void setDomanda(DomandaQuestionarioDTO domanda) {
		this.domanda = domanda;
	}

	public RispostaQuestionario() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	
}
