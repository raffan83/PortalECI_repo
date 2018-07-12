package it.portalECI.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class VerbaleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int questionarioID;
	private Date createDate;
	private Date updateDate;
	private String codiceCategoria;
	private String codiceVerifica;
	private String descrizioneVerifica;
	private StatoVerbaleDTO stato;
	private List<DomandaVerbaleDTO> domandeVerbale;
	//private List<DomandaQuestionarioDTO> domandeSchedaTecnica;
	
	//TODO: dovrò aggiungere la referenza a scheda tecnica e anche al template per il pdf
		
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	public StatoVerbaleDTO getStato() {
		return stato;
	}

	public void setStato(StatoVerbaleDTO stato) {
		this.stato = stato;
	}
	
	

	public String getCodiceCategoria() {
		return codiceCategoria;
	}

	public void setCodiceCategoria(String codiceCategoria) {
		this.codiceCategoria = codiceCategoria;
	}

	public List<DomandaVerbaleDTO> getDomandeVerbale() {
		return domandeVerbale;
	}

	public void setDomandeVerbale(List<DomandaVerbaleDTO> domandeVerbale) {
		this.domandeVerbale = domandeVerbale;
	}
	
	public void addToDomande(DomandaVerbaleDTO domanda) {
		if(this.domandeVerbale ==null)
			this.domandeVerbale =new ArrayList<>();
		
		this.domandeVerbale.add(domanda);
	}
//
//	public List<DomandaQuestionarioDTO> getDomandeSchedaTecnica() {
//		return domandeSchedaTecnica;
//	}
//
//	public void setDomandeSchedaTecnica(List<DomandaQuestionarioDTO> domandeSchedaTecnica) {
//		this.domandeSchedaTecnica = domandeSchedaTecnica;
//	}

	public int getQuestionarioID() {
		return questionarioID;
	}

	public void setQuestionarioID(int questionarioID) {
		this.questionarioID = questionarioID;
	}

	public String getCodiceVerifica() {
		return codiceVerifica;
	}

	public void setCodiceVerifica(String codiceVerifica) {
		this.codiceVerifica = codiceVerifica;
	}

	public String getDescrizioneVerifica() {
		return descrizioneVerifica;
	}

	public void setDescrizioneVerifica(String descrizioneVerifica) {
		this.descrizioneVerifica = descrizioneVerifica;
	}

	public JsonObject getVerbaleJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.id);
	
		jobj.addProperty("codiceVerifica", this.codiceVerifica);
		jobj.addProperty("codiceCategoria", this.codiceCategoria);
		jobj.addProperty("descrizioneVerifica", this.descrizioneVerifica);
		if(this.domandeVerbale!=null) {
			JsonArray domandeVerbaleobj = new JsonArray();
			
			for(DomandaVerbaleDTO domanda : this.domandeVerbale) {
				domandeVerbaleobj.add(domanda.getDomandaJsonObject());
			}
			jobj.add("domande", domandeVerbaleobj);
		}		
		
		
		return jobj;
	}

	
}