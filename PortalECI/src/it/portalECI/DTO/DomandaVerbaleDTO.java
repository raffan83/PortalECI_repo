package it.portalECI.DTO;

public class DomandaVerbaleDTO {
	
	private int id;
	private String testo;
	private Boolean obbligatoria;
	private String placeholder;
	private RispostaVerbaleDTO risposta;
	private VerbaleDTO verbale;
	
	public DomandaVerbaleDTO() {
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

	public RispostaVerbaleDTO getRisposta() {
		return risposta;
	}

	public void setRisposta(RispostaVerbaleDTO risposta) {
		this.risposta = risposta;
	}

	public VerbaleDTO getVerbale() {
		return verbale;
	}

	public void setVerbale(VerbaleDTO verbale) {
		this.verbale= verbale;
	}


}
