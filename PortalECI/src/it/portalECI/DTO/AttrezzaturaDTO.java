package it.portalECI.DTO;

import java.util.Date;

public class AttrezzaturaDTO {

	private int id;
	private String matricola_inail;
	private String numero_fabbrica;
	private String tipo_attivita;
	private String descrizione;
	private int id_cliente;
	private int id_sede;
	private String nome_cliente;
	private String nome_sede;
	private Date data_verifica_funzionamento;
	private Date data_prossima_verifica_funzionamento;
	private Date data_verifica_integrita;
	private Date data_prossima_verifica_integrita;
	private Date data_verifica_interna;
	private Date data_prossima_verifica_interna;
	private int anno_costruzione;
	private String fabbricante;
	private String modello;
	private String settore_impiego;
	private String note_tecniche;
	private String note_generiche;
	private int obsoleta;
	
	
	private String tipo_attrezzatura;
	private String tipo_attrezzatura_GVR;
	private String ID_specifica;
	private String sogg_messa_serv_GVR;
	private Integer n_panieri_idroestrattori;
	private String marcatura;
	private String n_id_on;
	private Date data_scadenza_ventennale;
	
		
	public int getAnno_costruzione() {
		return anno_costruzione;
	}
	public void setAnno_costruzione(int anno_costruzione) {
		this.anno_costruzione = anno_costruzione;
	}
	public String getFabbricante() {
		return fabbricante;
	}
	public void setFabbricante(String fabbricante) {
		this.fabbricante = fabbricante;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public String getSettore_impiego() {
		return settore_impiego;
	}
	public void setSettore_impiego(String settore_impiego) {
		this.settore_impiego = settore_impiego;
	}
	public String getNote_tecniche() {
		return note_tecniche;
	}
	public void setNote_tecniche(String note_tecniche) {
		this.note_tecniche = note_tecniche;
	}
	public String getNote_generiche() {
		return note_generiche;
	}
	public void setNote_generiche(String note_generiche) {
		this.note_generiche = note_generiche;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMatricola_inail() {
		return matricola_inail;
	}
	public void setMatricola_inail(String matricola_inail) {
		this.matricola_inail = matricola_inail;
	}
	public String getNumero_fabbrica() {
		return numero_fabbrica;
	}
	public void setNumero_fabbrica(String numero_fabbrica) {
		this.numero_fabbrica = numero_fabbrica;
	}
	public String getTipo_attivita() {
		return tipo_attivita;
	}
	public void setTipo_attivita(String tipo_attivita) {
		this.tipo_attivita = tipo_attivita;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId_sede() {
		return id_sede;
	}
	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}
	public String getNome_cliente() {
		return nome_cliente;
	}
	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}
	public String getNome_sede() {
		return nome_sede;
	}
	public void setNome_sede(String nome_sede) {
		this.nome_sede = nome_sede;
	}
	public Date getData_verifica_funzionamento() {
		return data_verifica_funzionamento;
	}
	public void setData_verifica_funzionamento(Date data_verifica_funzionamento) {
		this.data_verifica_funzionamento = data_verifica_funzionamento;
	}
	public Date getData_prossima_verifica_funzionamento() {
		return data_prossima_verifica_funzionamento;
	}
	public void setData_prossima_verifica_funzionamento(Date data_prossima_verifica_funzionamento) {
		this.data_prossima_verifica_funzionamento = data_prossima_verifica_funzionamento;
	}
	public Date getData_verifica_integrita() {
		return data_verifica_integrita;
	}
	public void setData_verifica_integrita(Date data_verifica_integrita) {
		this.data_verifica_integrita = data_verifica_integrita;
	}
	public Date getData_prossima_verifica_integrita() {
		return data_prossima_verifica_integrita;
	}
	public void setData_prossima_verifica_integrita(Date data_prossima_verifica_integrita) {
		this.data_prossima_verifica_integrita = data_prossima_verifica_integrita;
	}
	public Date getData_verifica_interna() {
		return data_verifica_interna;
	}
	public void setData_verifica_interna(Date data_verifica_interna) {
		this.data_verifica_interna = data_verifica_interna;
	}
	public Date getData_prossima_verifica_interna() {
		return data_prossima_verifica_interna;
	}
	public void setData_prossima_verifica_interna(Date data_prossima_verifica_interna) {
		this.data_prossima_verifica_interna = data_prossima_verifica_interna;
	}
	public int getObsoleta() {
		return obsoleta;
	}
	public void setObsoleta(int obsoleta) {
		this.obsoleta = obsoleta;
	}
	public String getTipo_attrezzatura() {
		return tipo_attrezzatura;
	}
	public void setTipo_attrezzatura(String tipo_attrezzatura) {
		this.tipo_attrezzatura = tipo_attrezzatura;
	}
	public String getTipo_attrezzatura_GVR() {
		return tipo_attrezzatura_GVR;
	}
	public void setTipo_attrezzatura_GVR(String tipo_attrezzatura_GVR) {
		this.tipo_attrezzatura_GVR = tipo_attrezzatura_GVR;
	}
	public String getID_specifica() {
		return ID_specifica;
	}
	public void setID_specifica(String iD_specifica) {
		ID_specifica = iD_specifica;
	}
	public String getSogg_messa_serv_GVR() {
		return sogg_messa_serv_GVR;
	}
	public void setSogg_messa_serv_GVR(String sogg_messa_serv_GVR) {
		this.sogg_messa_serv_GVR = sogg_messa_serv_GVR;
	}
	public Integer getN_panieri_idroestrattori() {
		return n_panieri_idroestrattori;
	}
	public void setN_panieri_idroestrattori(Integer n_panieri_idroestrattori) {
		this.n_panieri_idroestrattori = n_panieri_idroestrattori;
	}
	public String getMarcatura() {
		return marcatura;
	}
	public void setMarcatura(String marcatura) {
		this.marcatura = marcatura;
	}
	public String getN_id_on() {
		return n_id_on;
	}
	public void setN_id_on(String n_id_on) {
		this.n_id_on = n_id_on;
	}
	public Date getData_scadenza_ventennale() {
		return data_scadenza_ventennale;
	}
	public void setData_scadenza_ventennale(Date data_scadenza_ventennale) {
		this.data_scadenza_ventennale = data_scadenza_ventennale;
	}
	
	
}
