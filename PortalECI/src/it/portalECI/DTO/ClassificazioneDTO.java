package it.portalECI.DTO;

public class ClassificazioneDTO 
{
	int id=0;
	String descrizione="";
	
	public ClassificazioneDTO(int id, String descrizione) {
		super();
		this.id = id;
		this.descrizione = descrizione;
	}
	public ClassificazioneDTO(){}

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
