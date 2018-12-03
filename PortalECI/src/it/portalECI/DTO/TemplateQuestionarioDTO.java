package it.portalECI.DTO;

import java.util.Date;

public class TemplateQuestionarioDTO {

	private int id;
	private Date createDate;
	private Date updateDate;
	private String titolo;
	private String template;
	private String header;
	private String footer;
	private String revisione;
	private String subheader;
	
	public TemplateQuestionarioDTO() {
		// TODO Auto-generated constructor stub
	}

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

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getRevisione() {
		return revisione;
	}

	public void setRevisione(String revisione) {
		this.revisione = revisione;
	}
	
	public String getSubheader() {
		return subheader;
	}

	public void setSubheader(String subheader) {
		this.subheader = subheader;
	}
	
	

}
