package it.portalECI.DTO;

public class StatoCertificatoDTO {

	private int id = 0;
	private String descrizione = "";
	
	public StatoCertificatoDTO(){}
	
	public StatoCertificatoDTO(int id) {
		super();
		this.id = id;	
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
}
