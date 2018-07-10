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
	private List<DomandaQuestionarioDTO> domandeVerbale;
	private List<DomandaQuestionarioDTO> domandeSchedaTecnica;
	
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

	public List<DomandaQuestionarioDTO> getDomandeVerbale() {
		return domandeVerbale;
	}

	public void setDomandeVerbale(List<DomandaQuestionarioDTO> domandeVerbale) {
		this.domandeVerbale = domandeVerbale;
	}

	public List<DomandaQuestionarioDTO> getDomandeSchedaTecnica() {
		return domandeSchedaTecnica;
	}

	public void setDomandeSchedaTecnica(List<DomandaQuestionarioDTO> domandeSchedaTecnica) {
		this.domandeSchedaTecnica = domandeSchedaTecnica;
	}

	public JsonObject getQuestionariJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.id);
		
		return jobj;
	}
	
}