package it.portalECI.DTO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StoricoModificheDTO implements Serializable {

	public static final String INSERT= "INSERT" ;
	public static final String UPDATE= "UPDATE" ;
	public static final String DELETE= "DELETE" ;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id=0;
	private String nominativo="";
	private String username="";
	private String nomeCampo="";
	private String vecchioValore="";
	private VerbaleDTO verbale;
	private int idRisposta;
	private String azione="";
	private Date createDate;
	
	public int getId() {
		return id;
	}	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNominativo() {
		return nominativo;
	}	
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getNomeCampo() {
		return nomeCampo;
	}
	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}
	
	public String getVecchioValore() {
		return vecchioValore;
	}	
	public void setVecchioValore(String vecchioValore) {
		this.vecchioValore = vecchioValore;
	}
	
	public VerbaleDTO getVerbale() {
		return verbale;
	}	
	public void setVerbale(VerbaleDTO verbale) {
		this.verbale = verbale;
	}
	
	public int getIdRisposta() {
		return idRisposta;
	}	
	public void setIdRisposta(int idRisposta) {
		this.idRisposta = idRisposta;
	}
	
	public String getAzione() {
		return azione;
	}	
	public void setAzione(String azione) {
		this.azione = azione;
	}
	
	public Date getCreateDate() {
		return createDate;
	}	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateDateFormat() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		if (createDate != null)
			return format.format(createDate);
		else return null;
		
	}
	
	
}
