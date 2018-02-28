package it.portalECI.DTO;

public class TipoRapportoDTO {
	int id = 0;
	String noneRapporto = "";
	public TipoRapportoDTO() {}
	public TipoRapportoDTO(int id, String noneRapporto) {
		super();
		this.id = id;
		this.noneRapporto = noneRapporto;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNoneRapporto() {
		return noneRapporto;
	}
	public void setNoneRapporto(String noneRapporto) {
		this.noneRapporto = noneRapporto;
	}

}
