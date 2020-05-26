package it.portalECI.DTO;

import java.util.Date;

public class StrumentoVerificatoreDTO {
	
	private int id;
	private String marca;
	private String modello;
	private String matricola;
	private Date data_ultima_taratura;
	private Date scadenza;
	private Integer id_verificatore;
	private String nominativo_verificatore;
	
	
	public StrumentoVerificatoreDTO(int id) {
		this.id = id;		
	}
	
	public StrumentoVerificatoreDTO() {
		super();
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public String getMatricola() {
		return matricola;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	public Date getData_ultima_taratura() {
		return data_ultima_taratura;
	}
	public void setData_ultima_taratura(Date data_ultima_taratura) {
		this.data_ultima_taratura = data_ultima_taratura;
	}
	public Date getScadenza() {
		return scadenza;
	}
	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}
	public Integer getId_verificatore() {
		return id_verificatore;
	}
	public void setId_verificatore(Integer id_verificatore) {
		this.id_verificatore = id_verificatore;
	}
	public String getNominativo_verificatore() {
		return nominativo_verificatore;
	}
	public void setNominativo_verificatore(String nominativo_verificatore) {
		this.nominativo_verificatore = nominativo_verificatore;
	}
	

}
