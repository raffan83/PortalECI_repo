package it.portalECI.DTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

// default package
// Generated 15-feb-2017 9.30.19 by Hibernate Tools 3.4.0.CR1

/**
 * Utentedto generated by hbm2java
 */
public class UtenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1323732746316082695L;
	private int id = 0;
	private String user = "";
	private String codice = "";
	private String passw = "";
	private String nominativo = "";
	private String nome = "";
	private String cognome = "";
	private String indirizzo = "";
	private String comune = "";
	private String cap = "";
	private String EMail = "";
	private String telefono = "";
	private CompanyDTO company;
	private String tipoutente = "";
	private int idCliente;
	private int idSede;
	private int trasversale = 0;
	private String resetToken = "";
	private String qualifica = "";
	private String cf="";
	private String file_firma = "";
	private String pin_firma = "";
	private String id_firma = "";
	
	private Set<RuoloDTO> listaRuoli = new HashSet<RuoloDTO>(0);
	private Set<CategoriaVerificaDTO> listaCategorieVerifica = new HashSet<CategoriaVerificaDTO>(0);
	private Set<StrumentoVerificatoreDTO> listaStrumentiVerificatore = new HashSet<StrumentoVerificatoreDTO>(0);
	private Set<TipoComunicazioneUtenteDTO> listaComunicazioni = new HashSet<TipoComunicazioneUtenteDTO>(0);
	private Set<CampioneDTO> listaCampioni = new HashSet<CampioneDTO>(0);
	
	
	public UtenteDTO() {
	}

	public UtenteDTO(int id, String user, String passw, String nominativo,
			String nome, String cognome, String indirizzo, String comune,
			String cap, String EMail, String telefono, CompanyDTO _company,
			String tipoutente) {
		this.id = id;
		this.user = user;
		this.passw = passw;
		this.nominativo = nominativo;
		this.nome = nome;
		this.cognome = cognome;
		this.indirizzo = indirizzo;
		this.comune = comune;
		this.cap = cap;
		this.EMail = EMail;
		this.telefono = telefono;
		this.company = _company;
		this.tipoutente = tipoutente;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getPassw() {
		return this.passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public String getNominativo() {
		return this.nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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

	public String getEMail() {
		return this.EMail;
	}

	public void setEMail(String EMail) {
		this.EMail = EMail;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoutente() {
		return this.tipoutente;
	}

	public void setTipoutente(String tipoutente) {
		this.tipoutente = tipoutente;
	}


	public Set<RuoloDTO> getListaRuoli() {
		return listaRuoli;
	}


	public void setListaRuoli(Set<RuoloDTO> listaRuoli) {
		this.listaRuoli = listaRuoli;
	}
	
	public int getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}


	public int getIdSede() {
		return idSede;
	}


	public void setIdSede(int idSede) {
		this.idSede = idSede;
	}

	
	public boolean checkPermesso(String chiavePermesso){
		//Iterator<RuoloDTO> iterator = listaRuoli.iterator(); 
		Set<RuoloDTO> listaR=this.getListaRuoli();
		
		for(RuoloDTO ruolo : listaR) {
		//while (iterator.hasNext()){
			//RuoloDTO ruolo= (RuoloDTO) iterator.next(); 
			  			
			Iterator<PermessoDTO> iteratorPermessi=(Iterator<PermessoDTO>) ruolo.getListaPermessi().iterator();
			
			while(iteratorPermessi.hasNext()){
				PermessoDTO permesso=iteratorPermessi.next();
				
				if(permesso.getChiave_permesso().equals(chiavePermesso) && permesso.getStatoPermesso()){
					return true;
				}
			}		   
		}
		   
		return false;
	}

	public int getTrasversale(){
		return trasversale;
	}
	
	
	public boolean isTras(){
		if(trasversale==0){
			return false;
		}else{
			return true;
		}
	}
	
	public void setTrasversale(int i){
		trasversale=i;
	}
	
	public boolean checkRuolo(String siglaRuolo){
		Iterator<RuoloDTO> iterator = listaRuoli.iterator(); 
	      
		while (iterator.hasNext()){			  
			RuoloDTO ruolo= (RuoloDTO) iterator.next(); 
			
			if(ruolo.getSigla().equals(siglaRuolo)){
				return true;
			}
		}
		   
		return false;
	}
	
	public boolean checkCategoria(String siglaCategoria){
		Iterator<CategoriaVerificaDTO> iterator = listaCategorieVerifica.iterator(); 
	      
		while (iterator.hasNext()){			  
			CategoriaVerificaDTO categoria= (CategoriaVerificaDTO) iterator.next(); 
			
			if(categoria.getSigla().equals(siglaCategoria)){
				return true;
			}
		}
		   
		return false;
	}

	public boolean checkComunicazione(int id_comunicazione){
		Iterator<TipoComunicazioneUtenteDTO> iterator = listaComunicazioni.iterator(); 
	      
		while (iterator.hasNext()){			  
			TipoComunicazioneUtenteDTO comunicazone= (TipoComunicazioneUtenteDTO) iterator.next(); 
			
			if(comunicazone.getId()==id_comunicazione){
				return true;
			}
		}
		   
		return false;
	}
	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	
	public JsonObject getUtenteJsonObject(Boolean withPassword) {
		JsonObject userjobj = new JsonObject();
		userjobj.addProperty("id", this.id);
		userjobj.addProperty("user", this.user);
		
		if(withPassword) {
			userjobj.addProperty("passw", this.passw);
		}
		userjobj.addProperty("nominativo", this.nominativo);
		userjobj.addProperty("nome", this.nome);
		userjobj.addProperty("cognome", this.cognome);
		userjobj.addProperty("indirizzo", this.indirizzo);
		userjobj.addProperty("comune", this.comune);
		userjobj.addProperty("cap", this.cap);
		userjobj.addProperty("EMail", this.EMail);
		userjobj.addProperty("telefono", this.telefono);
		if(this.company!=null) {
			userjobj.add("company", this.company.getCompanyJsonObject());
		}
		userjobj.addProperty("tipoutente", this.tipoutente);
		
		if(!this.getListaRuoli().isEmpty()) {
			
				JsonArray ruoliObj = new JsonArray();
				
				for(RuoloDTO ruolo: this.getListaRuoli()) {
					ruoliObj.add(ruolo.getJsonObject());
				}
				userjobj.add("ruoli", ruoliObj);
				
		}
		
		if(!this.getListaCategorieVerifica().isEmpty()) {
			
			JsonArray categorieObj = new JsonArray();
			
			for(CategoriaVerificaDTO categoria: this.getListaCategorieVerifica()) {
				categorieObj.add(categoria.getCategoriaVerificaJsonObject());
			}
			userjobj.add("categorie", categorieObj);
			
	}
		
		return userjobj;
	}

	public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	public Set<CategoriaVerificaDTO> getListaCategorieVerifica() {
		return listaCategorieVerifica;
	}

	public void setListaCategorieVerifica(Set<CategoriaVerificaDTO> listaCategorieVerifica) {
		this.listaCategorieVerifica = listaCategorieVerifica;
	}

	public Set<StrumentoVerificatoreDTO> getListaStrumentiVerificatore() {
		return listaStrumentiVerificatore;
	}

	public void setListaStrumentiVerificatore(Set<StrumentoVerificatoreDTO> listaStrumentiVerificatore) {
		this.listaStrumentiVerificatore = listaStrumentiVerificatore;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Set<TipoComunicazioneUtenteDTO> getListaComunicazioni() {
		return listaComunicazioni;
	}

	public void setListaComunicazioni(Set<TipoComunicazioneUtenteDTO> listaComunicazioni) {
		this.listaComunicazioni = listaComunicazioni;
	}

	public String getFile_firma() {
		return file_firma;
	}

	public void setFile_firma(String file_firma) {
		this.file_firma = file_firma;
	}

	public String getPin_firma() {
		return pin_firma;
	}

	public void setPin_firma(String pin_firma) {
		this.pin_firma = pin_firma;
	}

	public String getId_firma() {
		return id_firma;
	}

	public void setId_firma(String id_firma) {
		this.id_firma = id_firma;
	}

	public Set<CampioneDTO> getListaCampioni() {
		return listaCampioni;
	}

	public void setListaCampioni(Set<CampioneDTO> listaCampioni) {
		this.listaCampioni = listaCampioni;
	}
}
