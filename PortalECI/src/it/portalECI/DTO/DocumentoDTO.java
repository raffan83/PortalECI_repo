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
	
	public String getFileName(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}
	
}
