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
	
	
}
