package it.portalECI.DTO;

import java.util.Date;

public class VerbaleStoricoDTO {
	
	private int id;
	private String codice_verificatore;
	private String verificatore;
	private String numero_verbale;
	private String cliente;
	private String indirizzo_cliente;
	private String localita_cliente;
	private String ubicazione_impianto;
	private String localita_impianto;
	private String provincia;
	private String esito;
	private String codice_commessa;
	private String strumento_utilizzato;
	private Date data_verifica;
	private int frequenza;
	private Date data_prossima_verifica;
	private String ore_uomo;
	private int tipologia_verifica;
	private String filename;
	private int id_verbale_storico;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodice_verificatore() {
		return codice_verificatore;
	}
	public void setCodice_verificatore(String codice_verificatore) {
		this.codice_verificatore = codice_verificatore;
	}
	public String getVerificatore() {
		return verificatore;
	}
	public void setVerificatore(String verificatore) {
		this.verificatore = verificatore;
	}
	public String getNumero_verbale() {
		return numero_verbale;
	}
	public void setNumero_verbale(String numero_verbale) {
		this.numero_verbale = numero_verbale;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getIndirizzo_cliente() {
		return indirizzo_cliente;
	}
	public void setIndirizzo_cliente(String indirizzo_cliente) {
		this.indirizzo_cliente = indirizzo_cliente;
	}
	public String getLocalita_cliente() {
		return localita_cliente;
	}
	public void setLocalita_cliente(String localita_cliente) {
		this.localita_cliente = localita_cliente;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getCodice_commessa() {
		return codice_commessa;
	}
	public void setCodice_commessa(String codice_commessa) {
		this.codice_commessa = codice_commessa;
	}

	public Date getData_verifica() {
		return data_verifica;
	}
	public void setData_verifica(Date data_verifica) {
		this.data_verifica = data_verifica;
	}
	public int getFrequenza() {
		return frequenza;
	}
	public void setFrequenza(int frequenza) {
		this.frequenza = frequenza;
	}
	public Date getData_prossima_verifica() {
		return data_prossima_verifica;
	}
	public void setData_prossima_verifica(Date data_prossima_verifica) {
		this.data_prossima_verifica = data_prossima_verifica;
	}
	public String getOre_uomo() {
		return ore_uomo;
	}
	public void setOre_uomo(String ore_uomo) {
		this.ore_uomo = ore_uomo;
	}
	public int getTipologia_verifica() {
		return tipologia_verifica;
	}
	public void setTipologia_verifica(int tipologia_verifica) {
		this.tipologia_verifica = tipologia_verifica;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUbicazione_impianto() {
		return ubicazione_impianto;
	}
	public void setUbicazione_impianto(String ubicazione_impianto) {
		this.ubicazione_impianto = ubicazione_impianto;
	}
	public String getLocalita_impianto() {
		return localita_impianto;
	}
	public void setLocalita_impianto(String localita_impianto) {
		this.localita_impianto = localita_impianto;
	}
	public String getStrumento_utilizzato() {
		return strumento_utilizzato;
	}
	public void setStrumento_utilizzato(String strumento_utilizzato) {
		this.strumento_utilizzato = strumento_utilizzato;
	}

	public int getId_verbale_storico() {
		return id_verbale_storico;
	}
	public void setId_verbale_storico(int id_verbale_storico) {
		this.id_verbale_storico = id_verbale_storico;
	}
	
	
	
	

}
