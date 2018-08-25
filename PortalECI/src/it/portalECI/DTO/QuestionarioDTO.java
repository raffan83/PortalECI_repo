package it.portalECI.DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;

public class QuestionarioDTO implements Serializable {

	private int id;
	private Date createDate;
	private Date updateDate;
	private String titolo;
	private TipoVerificaDTO tipo;
	private List<DomandaVerbaleQuestionarioDTO> domandeVerbale;
	private List<DomandaSchedaTecnicaQuestionarioDTO> domandeSchedaTecnica;
	
	private TemplateQuestionarioDTO templateVerbale;
	private TemplateQuestionarioDTO templateSchedaTecnica;
	
	private Boolean isObsoleto;
	
	//TODO: dovrò aggiungere i template per schedatecnica e verbale
		
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

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public TipoVerificaDTO getTipo() {
		return tipo;
	}

	public void setTipo(TipoVerificaDTO tipo) {
		this.tipo = tipo;
	}

	public List<DomandaVerbaleQuestionarioDTO> getDomandeVerbale() {
		return domandeVerbale;
	}

	public void setDomandeVerbale(List<DomandaVerbaleQuestionarioDTO> domandeVerbale) {
		this.domandeVerbale = domandeVerbale;
	}

	public List<DomandaSchedaTecnicaQuestionarioDTO> getDomandeSchedaTecnica() {
		return domandeSchedaTecnica;
	}

	public void setDomandeSchedaTecnica(List<DomandaSchedaTecnicaQuestionarioDTO> domandeSchedaTecnica) {
		this.domandeSchedaTecnica = domandeSchedaTecnica;
	}
	
	public TemplateQuestionarioDTO getTemplateVerbale() {
		return templateVerbale;
	}

	public void setTemplateVerbale(TemplateQuestionarioDTO templateVerbale) {
		this.templateVerbale = templateVerbale;
	}

	public TemplateQuestionarioDTO getTemplateSchedaTecnica() {
		return templateSchedaTecnica;
	}

	public void setTemplateSchedaTecnica(TemplateQuestionarioDTO templateSchedaTecnica) {
		this.templateSchedaTecnica = templateSchedaTecnica;
	}

	public Boolean getIsObsoleto() {
		return isObsoleto;
	}

	public void setIsObsoleto(Boolean isObsoleto) {
		this.isObsoleto = isObsoleto;
	}

	public JsonObject getQuestionariJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.id);
		
		return jobj;
	}
	
}