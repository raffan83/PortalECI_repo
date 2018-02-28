package it.portalECI.DTO;

import java.util.HashSet;
import java.util.Set;

public class TipoCampionamentoDTO {

	private int id = 0;
	private String codice = "";
	private String descrizione = "";
	private String nomeScheda = "";
	private String codiceScheda = "";
	private String revisioneScheda = "";
	
	private Set<DatasetCampionamentoDTO> listaDatasetCampionamentoDTO = new HashSet<DatasetCampionamentoDTO>(0);
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNomeScheda() {
		return nomeScheda;
	}

	public void setNomeScheda(String nomeScheda) {
		this.nomeScheda = nomeScheda;
	}

	public Set<DatasetCampionamentoDTO> getListaDatasetCampionamentoDTO() {
		return listaDatasetCampionamentoDTO;
	}

	public void setListaDatasetCampionamentoDTO(Set<DatasetCampionamentoDTO> listaDatasetCampionamentoDTO) {
		this.listaDatasetCampionamentoDTO = listaDatasetCampionamentoDTO;
	}

	public String getCodiceScheda() {
		return codiceScheda;
	}

	public void setCodiceScheda(String codiceScheda) {
		this.codiceScheda = codiceScheda;
	}

	public String getRevisioneScheda() {
		return revisioneScheda;
	}

	public void setRevisioneScheda(String revisioneScheda) {
		this.revisioneScheda = revisioneScheda;
	}


	
	
}
