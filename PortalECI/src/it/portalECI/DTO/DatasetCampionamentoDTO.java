package it.portalECI.DTO;




public class DatasetCampionamentoDTO {
	
	private int id=0;
	private int idTipoCampionamento=0;
	private String nomeCampo="";
	private String tipoCampo="";
	private String codiceCampo="";
	
public DatasetCampionamentoDTO() {}
	public DatasetCampionamentoDTO(int int1) {
		
		id=int1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdTipoCampionamento() {
		return idTipoCampionamento;
	}
	public void setIdTipoCampionamento(int idTipoCampionamento) {
		this.idTipoCampionamento = idTipoCampionamento;
	}
	public String getNomeCampo() {
		return nomeCampo;
	}
	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}
	public String getTipoCampo() {
		return tipoCampo;
	}
	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}
	public String getCodiceCampo() {
		return codiceCampo;
	}
	public void setCodiceCampo(String codiceCampo) {
		this.codiceCampo = codiceCampo;
	}


	
}
