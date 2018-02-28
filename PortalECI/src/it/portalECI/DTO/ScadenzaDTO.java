package it.portalECI.DTO;

import java.sql.Date;

public class ScadenzaDTO {
	
	private int id = 0;
	private int freq_mesi = 0;
	private Date dataUltimaVerifica;
	private Date dataProssimaVerifica;
	private Date dataEmissione;
	private TipoRapportoDTO tipo_rapporto;
	private  int idStrumento = 0 ;
	
	public ScadenzaDTO(){}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFreq_mesi() {
		return freq_mesi;
	}
	public void setFreq_mesi(int freq_mesi) {
		this.freq_mesi = freq_mesi;
	}
	public Date getDataUltimaVerifica() {
		return dataUltimaVerifica;
	}
	public void setDataUltimaVerifica(Date dataUltimaVerifica) {
		this.dataUltimaVerifica = dataUltimaVerifica;
	}
	public Date getDataProssimaVerifica() {
		return dataProssimaVerifica;
	}
	public void setDataProssimaVerifica(Date dataProssimaVerifica) {
		this.dataProssimaVerifica = dataProssimaVerifica;
	}

	public Date getDataEmissione() {
		return dataEmissione;
	}


	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}


	public TipoRapportoDTO getTipo_rapporto() {
		return tipo_rapporto;
	}

	public void setTipo_rapporto(TipoRapportoDTO tipo_rapporto) {
		this.tipo_rapporto = tipo_rapporto;
	}


	public int getIdStrumento() {
		return idStrumento;
	}


	public void setIdStrumento(int idStrumento) {
		this.idStrumento = idStrumento;
	}

	
}
