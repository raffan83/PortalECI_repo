package it.portalECI.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonObject;

public class InterventoDTO {

	
	private int id;
	private Date dataCreazione;
	private int idSede;
	private int id_cliente;
	private String nome_sede="";
	private UtenteDTO user;
	private String idCommessa="";
	StatoInterventoDTO statoIntervento;
	private CompanyDTO company;
	
	private UtenteDTO tecnico_verificatore;
	//private TipoVerificaDTO tipo_verifica;
	//private CategoriaVerificaDTO cat_verifica;
	
	private Set<TipoVerificaDTO> tipo_verifica= new HashSet<TipoVerificaDTO>();
	private Set<CategoriaVerificaDTO> cat_verifica= new HashSet<CategoriaVerificaDTO>();
	
	public Date getDataCreazione() {
		return dataCreazione;
	}
	
	
	public String getNome_sede() {
		return nome_sede;
	}


	public void setNome_sede(String nome_sede) {
		this.nome_sede = nome_sede;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
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
	
	public Set<TipoVerificaDTO> getTipo_verifica() {
		return tipo_verifica;
	}
	public void setTipo_verifica(Set<TipoVerificaDTO> tipo_verifica) {
		this.tipo_verifica = tipo_verifica;
	}
	
	public Set<CategoriaVerificaDTO> getCat_verifica() {
		return cat_verifica;
	}
	public void setCat_verifica(Set<CategoriaVerificaDTO> cat_verifica) {
		this.cat_verifica = cat_verifica;
	}

	/*public TipoVerificaDTO getTipo_verifica() {
		return tipo_verifica;
	}


	public void setTipo_verifica(TipoVerificaDTO tipo_verifica) {
		this.tipo_verifica = tipo_verifica;
	}


	public CategoriaVerificaDTO getCat_verifica() {
		return cat_verifica;
	}


	public void setCat_verifica(CategoriaVerificaDTO cat_verifica) {
		this.cat_verifica = cat_verifica;
	}*/
	
	public JsonObject getInterventoJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("id", this.id);
		jobj.addProperty("dataCreazione", this.dataCreazione.getTime());
		jobj.addProperty("idSede", this.idSede);
		jobj.addProperty("id_cliente", this.id_cliente);
		jobj.addProperty("nome_sede", this.nome_sede);
		jobj.addProperty("idCommessa", this.idCommessa);
		
		if(this.user!=null)
			jobj.add("user", this.user.getUtenteJsonObject());
		
		if(this.statoIntervento!=null)
			jobj.add("statoIntervento", this.statoIntervento.getStatoInterventoJsonObject());
		if(this.company!=null)
			jobj.add("company", this.company.getCompanyJsonObject());
		if(this.tecnico_verificatore!=null)
			jobj.add("tecnico_verificatore", this.tecnico_verificatore.getUtenteJsonObject());
		
		/*if(this.tipo_verifica!=null)
			jobj.add("tipo_verifica", this.tipo_verifica.getTipoVerificaJsonObject());*/
		if(this.tipo_verifica!=null) {
			JsonObject tipo_verificajobj = new JsonObject();
			
			for(TipoVerificaDTO tipo_verifica : this.tipo_verifica) {
				tipo_verificajobj.add(Integer.toString(tipo_verifica.getId()), tipo_verifica.getTipoVerificaJsonObject());
			}
			jobj.add("cat_verifica", tipo_verificajobj);
		}
		
		/*if(this.cat_verifica!=null)
			jobj.add("cat_verifica", this.cat_verifica.getCategoriaVerificaJsonObject());*/		
		
		if(this.cat_verifica!=null) {
			JsonObject cat_verificajobj = new JsonObject();
			
			for(CategoriaVerificaDTO cat_verifica : this.cat_verifica) {
				cat_verificajobj.add(Integer.toString(cat_verifica.getId()), cat_verifica.getCategoriaVerificaJsonObject());
			}
			jobj.add("cat_verifica", cat_verificajobj);
		}
		
		return jobj;
	}
}
