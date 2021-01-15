package it.portalECI.DTO;

public class VerbaleStoricoAllegatoDTO {
	
	private int id;
	private int id_verbale_storico;
	private String filename;
	private int disabilitato;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_verbale_storico() {
		return id_verbale_storico;
	}
	public void setId_verbale_storico(int id_verbale_storico) {
		this.id_verbale_storico = id_verbale_storico;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getDisabilitato() {
		return disabilitato;
	}
	public void setDisabilitato(int disabilitato) {
		this.disabilitato = disabilitato;
	}

}
