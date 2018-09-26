package it.portalECI.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.lang.IllegalStateException;

public class InterventoDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Date dataCreazione;
	private int idSede;
	private int id_cliente;
	private String nome_sede="";
	private String codiceProvincia;	
	private UtenteDTO user;
	private String idCommessa="";
	StatoInterventoDTO statoIntervento;
	private CompanyDTO company;
	
	private UtenteDTO tecnico_verificatore;
	
	private Collection<TipoVerificaDTO> tipo_verifica;
	
	private List<VerbaleDTO> verbali;
	
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	
	
	public String getNome_sede() {
		return nome_sede;
	}
	public void setNome_sede(String nome_sede) {
		this.nome_sede = nome_sede;
	}
	
	
	public String getCodiceProvincia() {
		return codiceProvincia;
	}
	public void setCodiceProvincia(String codiceProvincia) {
		this.codiceProvincia = codiceProvincia;
	}	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getIdSede() {
		return idSede;
	}
	public void setIdSede(int idSede) {
		this.idSede = idSede;
	}
	
	
	public String getIdCommessa() {
		return idCommessa;
	}
	public void setIdCommessa(String idCommessa) {
		this.idCommessa = idCommessa;
	}
	

	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}


	public StatoInterventoDTO getStatoIntervento() {
		return statoIntervento;
	}
	public void setStatoIntervento(StatoInterventoDTO statoIntervento) {
		this.statoIntervento = statoIntervento;
	}


	public UtenteDTO getUser() {
		return user;
	}
	public void setUser(UtenteDTO user) {
		this.user = user;
	}


	public CompanyDTO getCompany() {
		return company;
	}
	public void setCompany(CompanyDTO company) {
		this.company = company;
	}


	public UtenteDTO getTecnico_verificatore() {
		return tecnico_verificatore;
	}
	public void setTecnico_verificatore(UtenteDTO tecnico_verificatore) {
		this.tecnico_verificatore = tecnico_verificatore;
	}
	
	
	public Collection<TipoVerificaDTO> getTipo_verifica() {
		return tipo_verifica;
	}
	public void setTipo_verifica(Collection<TipoVerificaDTO> tipoverificalist) {
		this.tipo_verifica = tipoverificalist;
	}
	
	
	
	public List<VerbaleDTO> getVerbali() {
		return verbali;
	}
	public void setVerbali(List<VerbaleDTO> verbali) {
		this.verbali = verbali;
	}
	
	public void addToVerbali(VerbaleDTO verbale) {
		if(this.verbali==null) {
			this.verbali= new ArrayList<>();
		}
		this.verbali.add(verbale);
	}
	
	public JsonObject getInterventoJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("id", this.id);
		jobj.addProperty("dataCreazione", this.dataCreazione.getTime());
		jobj.addProperty("idSede", this.idSede);
		jobj.addProperty("id_cliente", this.id_cliente);
		jobj.addProperty("nome_sede", this.nome_sede);
		jobj.addProperty("idCommessa", this.idCommessa);
		
		if(this.user!=null)
			jobj.add("user", this.user.getUtenteJsonObject(false));
		
		if(this.getStatoIntervento()!=null)
			jobj.add("statoIntervento", this.statoIntervento.getStatoInterventoJsonObject());
		
		if(this.company!=null)
			jobj.add("company", this.company.getCompanyJsonObject());
		
		if(this.tecnico_verificatore!=null)
			jobj.add("tecnico_verificatore", this.tecnico_verificatore.getUtenteJsonObject(false));
			
		if(this.tipo_verifica!=null) {
			JsonArray tipo_verificajobj = new JsonArray();
			
			for(TipoVerificaDTO tipo_verifica : this.tipo_verifica) {
				tipo_verificajobj.add(tipo_verifica.getTipoVerificaJsonObject());
			}
			jobj.add("tipo_verifica", tipo_verificajobj);
		}		
		
		if(this.verbali!=null) {
			JsonArray verbaliobj = new JsonArray();
			
			for(VerbaleDTO verbale : this.verbali) {
				verbaliobj.add(verbale.getVerbaleJsonObject());
			}
			jobj.add("verbali", verbaliobj);
		}		
		
		return jobj;
	}
	
	public Boolean cambioStatoIntervento(StatoInterventoDTO newStato) throws IllegalStateException{
		

		//se nuovo
		if(this.statoIntervento==null && newStato.getId()== StatoInterventoDTO.CREATO) {	
			
		}else if(this.statoIntervento!=null && newStato.getId()==this.statoIntervento.id) {
			//se uguale allo stato esistente
			return true;
		}else if(this.statoIntervento!=null && this.statoIntervento.id== StatoInterventoDTO.CREATO){
			if(newStato.getId()== StatoInterventoDTO.SCARICATO || newStato.getId()==StatoInterventoDTO.ANNULLATO || newStato.getId()==StatoInterventoDTO.CHIUSO) {				
				
			}else {
				throw new IllegalStateException("Passaggio di Stato non consentito!");				
			}
		}else if(this.statoIntervento!=null && this.statoIntervento.id==StatoInterventoDTO.DA_VERIFICARE) {
			if(newStato.getId()== StatoInterventoDTO.ANNULLATO || newStato.getId()==StatoInterventoDTO.IN_VERIFICA || newStato.getId()==StatoInterventoDTO.CHIUSO) {				
								
			}else if(newStato.getId()!= StatoInterventoDTO.SCARICATO){
				throw new IllegalStateException("Passaggio di Stato non consentito!");	
			}
		}else if(this.statoIntervento!=null && this.statoIntervento.id==StatoInterventoDTO.IN_VERIFICA) {
			if(newStato.getId()== StatoInterventoDTO.VERIFICATO || newStato.getId()==StatoInterventoDTO.ANNULLATO || newStato.getId()==StatoInterventoDTO.CHIUSO) {				
				
			}else if(newStato.getId()!= StatoInterventoDTO.SCARICATO){
				throw new IllegalStateException("Passaggio di Stato non consentito!");	
			}
		}else if(this.statoIntervento!=null && this.statoIntervento.id==StatoInterventoDTO.SCARICATO) {
			if(newStato.getId()==StatoInterventoDTO.CHIUSO) {				
						
			}else if(newStato.getId()!= StatoInterventoDTO.SCARICATO){
				throw new IllegalStateException("Passaggio di Stato non consentito!");	
			}
		}else if(this.statoIntervento!=null && this.statoIntervento.id==StatoInterventoDTO.VERIFICATO) {
			if(newStato.getId()==StatoInterventoDTO.CHIUSO) {				
						
			}else if(newStato.getId()!= StatoInterventoDTO.SCARICATO){
				throw new IllegalStateException("Passaggio di Stato non consentito!");	
			}else {
				return true;
			}
		}else if(this.statoIntervento!=null && this.statoIntervento.id==StatoInterventoDTO.CHIUSO){
			if(newStato.getId()!=StatoInterventoDTO.SCARICATO) {
				throw new IllegalStateException("Passaggio di Stato non consentito!");	
			}else {
				return true;
			}
		}else {
			throw new IllegalStateException("Passaggio di Stato non consentito!");	
		}		
		
		this.setStatoIntervento(newStato);
		
		return true;
	}
}
