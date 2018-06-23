package it.portalECI.DTO;

import com.google.gson.JsonObject;

public class CompanyDTO  {

	private Integer id;
	private String denominazione;
	private String pIva;
	private String indirizzo;
	private String comune;
	private String cap;
	private String mail;
	private String telefono;
	private String codAffiliato;
	
	


	public CompanyDTO(Integer id, String denominazione, String pIva,
			String indirizzo, String comune, String cap, String mail,
			String telefono, String codAffiliato) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.pIva = pIva;
		this.indirizzo = indirizzo;
		this.comune = comune;
		this.cap = cap;
		this.mail = mail;
		this.telefono = telefono;
		this.codAffiliato = codAffiliato;
	}



	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public CompanyDTO() {
	}

	

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getpIva() {
		return pIva;
	}
	
	public void setpIva(String pIva) {
		this.pIva = pIva;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getComune() {
		return this.comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}


	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCodAffiliato() {
		return this.codAffiliato;
	}

	public void setCodAffiliato(String codAffiliato) {
		this.codAffiliato = codAffiliato;
	}
	
	public JsonObject getCompanyJsonObject() {
		
		JsonObject companyjobj = new JsonObject();
		companyjobj.addProperty("id", this.id);
		companyjobj.addProperty("denominazione", this.denominazione);
		companyjobj.addProperty("pIva", this.pIva);
		companyjobj.addProperty("indirizzo", this.indirizzo);
		companyjobj.addProperty("comune", this.comune);
		companyjobj.addProperty("cap", this.cap);
		companyjobj.addProperty("mail", this.mail);
		companyjobj.addProperty("telefono", this.telefono);
		companyjobj.addProperty("codAffiliato", this.codAffiliato);
		
		return companyjobj;
		
	}
}
