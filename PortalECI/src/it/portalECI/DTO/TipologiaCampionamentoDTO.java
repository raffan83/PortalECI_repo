package it.portalECI.DTO;

import java.util.HashSet;
import java.util.Set;

public class TipologiaCampionamentoDTO {

	private int id = 0;
 	private String descrizione = "";
	private TipoCampionamentoDTO tipoCampionamento;

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

	public TipoCampionamentoDTO getTipoCampionamento() {
		return tipoCampionamento;
	}

	public void setTipoCampionamento(TipoCampionamentoDTO tipoCampionamento) {
		this.tipoCampionamento = tipoCampionamento;
	}

	
	
}
