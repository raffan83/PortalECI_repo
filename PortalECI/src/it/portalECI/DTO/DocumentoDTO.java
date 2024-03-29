package it.portalECI.DTO;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class DocumentoDTO implements Serializable {

	public static String CERTIFIC="CERTIFICATO";
	public static String SK_TEC="SCHEDA_TECNICA";
	public static String ATTACHMENT = "ALLEGATO";
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String filePath;
	private String type;
	private Date createDate;
	private Date updateDate;
	private VerbaleDTO verbale;
	private Boolean invalid = false;
	private CampioneDTO campione;
	
	private int allegato_inviabile;
	private int allegato_visibile_cliente;
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public VerbaleDTO getVerbale() {
		return verbale;
	}
	public void setVerbale(VerbaleDTO verbale) {
		this.verbale = verbale;
	}
	public String getFileName() {
		File file = new File(filePath);
		return file.getName();
	}
	public Boolean getInvalid() {
		return invalid;
	}
	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}
	public CampioneDTO getCampione() {
		return campione;
	}
	public void setCampione(CampioneDTO campione) {
		this.campione = campione;
	}
	public int getAllegato_inviabile() {
		return allegato_inviabile;
	}
	public void setAllegato_inviabile(int allegato_inviabile) {
		this.allegato_inviabile = allegato_inviabile;
	}
	public int getAllegato_visibile_cliente() {
		return allegato_visibile_cliente;
	}
	public void setAllegato_visibile_cliente(int allegato_visibile_cliente) {
		this.allegato_visibile_cliente = allegato_visibile_cliente;
	}
	
}
