package it.portalECI.DTO;

public class StatoPackDTO {

	private int id = 0;
	private String descrizione = "";
	
	public StatoPackDTO() {
	}
	
	public StatoPackDTO(int i) {
		id=i;
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
