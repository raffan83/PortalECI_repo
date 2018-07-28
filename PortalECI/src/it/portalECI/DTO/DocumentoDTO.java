package it.portalECI.DTO;

import java.io.Serializable;

public class DocumentoDTO implements Serializable {

	public final static String CERTIFIC="CERTIFICATO";
	public final static String SK_TEC="SCHEDA_TECNICA";
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String filePath;
	private String type;
	
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
	
}
