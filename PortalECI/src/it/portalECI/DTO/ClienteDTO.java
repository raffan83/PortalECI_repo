package it.portalECI.DTO;

import java.io.Serializable;

public class ClienteDTO implements Serializable{
	
	 private int __id=0 ;
	 private String codice=""  ;
	 private String nome="" ;
	 private Integer committente_attivo=0;
	 private Integer destinatario_attivo=0;
	 private String telefono="";
	 private String fax="";
	 private String partita_iva="";
	 private String cf;
	 private String sito="";
	 private String email="";
	 private String cellulare="";
	 private String indirizzo;
	 private String cap;
	 private String citta;
	 private String provincia;
	 private String pec;

	  public ClienteDTO(){}

	public ClienteDTO(int __id, String codice, String nome,
			Integer committente_attivo, Integer destinatario_attivo,
			String telefono, String fax, String partita_iva, String sito,
			String email, String cellulare,String cf) {
		super();
		this.__id = __id;
		this.codice = codice;
		this.nome = nome;
		this.committente_attivo = committente_attivo;
		this.destinatario_attivo = destinatario_attivo;
		this.telefono = telefono;
		this.fax = fax;
		this.partita_iva = partita_iva;
		this.sito = sito;
		this.email = email;
		this.cellulare = cellulare;
		this.cf=cf;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public int get__id() {
		return __id;
	}

	public void set__id(int __id) {
		this.__id = __id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCommittente_attivo() {
		return committente_attivo;
	}

	public void setCommittente_attivo(Integer committente_attivo) {
		this.committente_attivo = committente_attivo;
	}

	public Integer getDestinatario_attivo() {
		return destinatario_attivo;
	}

	public void setDestinatario_attivo(Integer destinatario_attivo) {
		this.destinatario_attivo = destinatario_attivo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPartita_iva() {
		return partita_iva;
	}

	public void setPartita_iva(String partita_iva) {
		this.partita_iva = partita_iva;
	}

	public String getSito() {
		return sito;
	}

	public void setSito(String sito) {
		this.sito = sito;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	};
	  
	  
}

