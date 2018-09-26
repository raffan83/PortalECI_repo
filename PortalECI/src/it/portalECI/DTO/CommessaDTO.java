package it.portalECI.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CommessaDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ID_COMMESSA="";
	private Date DT_COMMESSA;
	private Date FIR_CHIUSURA_DT;
	private int ID_ANAGEN=0;
	private String ID_ANAGEN_NOME="";
	private String DESCR="";
	private String SYS_STATO="";
	private int ID_ANAGEN_COMM;
	private int K2_ANAGEN_INDR;
	private String ANAGEN_INDR_DESCR="";
	private String ANAGEN_INDR_INDIRIZZO="";
	private String INDIRIZZO_PRINCIPALE="";
	private String NOTE_GEN="";
	private String N_ORDINE="";
	private String COD_PROV="";
	
	private ArrayList<AttivitaMilestoneDTO> listaAttivita= new ArrayList<AttivitaMilestoneDTO>();
	
	public String getID_COMMESSA() {
		return ID_COMMESSA;
	}
	public void setID_COMMESSA(String iD_COMMESSA) {
		ID_COMMESSA = iD_COMMESSA;
	}
	public Date getDT_COMMESSA() {
		return DT_COMMESSA;
	}
	public void setDT_COMMESSA(Date dT_COMMESSA) {
		DT_COMMESSA = dT_COMMESSA;
	}
	public int getID_ANAGEN() {
		return ID_ANAGEN;
	}
	public void setID_ANAGEN(int iD_ANAGEN) {
		ID_ANAGEN = iD_ANAGEN;
	}
	public String getDESCR() {
		return DESCR;
	}
	public void setDESCR(String dESCR) {
		DESCR = dESCR;
	}
	public String getSYS_STATO() {
		return SYS_STATO;
	}
	public void setSYS_STATO(String sYS_STATO) {
		SYS_STATO = sYS_STATO;
	}
	public int getID_ANAGEN_COMM() {
		return ID_ANAGEN_COMM;
	}
	public void setID_ANAGEN_COMM(int iD_ANAGEN_COMM) {
		ID_ANAGEN_COMM = iD_ANAGEN_COMM;
	}
	public Date getFIR_CHIUSURA_DT() {
		return FIR_CHIUSURA_DT;
	}
	public void setFIR_CHIUSURA_DT(Date fIR_CHIUSURA_DT) {
		FIR_CHIUSURA_DT = fIR_CHIUSURA_DT;
	}
	public String getID_ANAGEN_NOME() {
		return ID_ANAGEN_NOME;
	}
	public void setID_ANAGEN_NOME(String iD_ANAGEN_NOME) {
		ID_ANAGEN_NOME = iD_ANAGEN_NOME;
	}
	
	public String getINDIRIZZO_PRINCIPALE() {
		return INDIRIZZO_PRINCIPALE;
	}
	public void setINDIRIZZO_PRINCIPALE(String iNDIRIZZO_PRINCIPALE) {
		INDIRIZZO_PRINCIPALE = iNDIRIZZO_PRINCIPALE;
	}
	public int getK2_ANAGEN_INDR() {
		return K2_ANAGEN_INDR;
	}
	public void setK2_ANAGEN_INDR(int k2_ANAGEN_INDR) {
		K2_ANAGEN_INDR = k2_ANAGEN_INDR;
	}
	public String getANAGEN_INDR_DESCR() {
		return ANAGEN_INDR_DESCR;
	}
	public void setANAGEN_INDR_DESCR(String aNAGEN_INDR_DESCR) {
		ANAGEN_INDR_DESCR = aNAGEN_INDR_DESCR;
	}
	public String getANAGEN_INDR_INDIRIZZO() {
		return ANAGEN_INDR_INDIRIZZO;
	}
	public void setANAGEN_INDR_INDIRIZZO(String aNAGEN_INDR_INDIRIZZO) {
		ANAGEN_INDR_INDIRIZZO = aNAGEN_INDR_INDIRIZZO;
	}
	public ArrayList<AttivitaMilestoneDTO> getListaAttivita() {
		return listaAttivita;
	}
	public void setListaAttivita(ArrayList<AttivitaMilestoneDTO> listaAttivita) {
		this.listaAttivita = listaAttivita;
	}
	public String getNOTE_GEN() {
		return NOTE_GEN;
	}
	public void setNOTE_GEN(String nOTE_GEN) {
		NOTE_GEN = nOTE_GEN;
	}
	public String getN_ORDINE() {
		return N_ORDINE;
	}
	public void setN_ORDINE(String n_ORDINE) {
		N_ORDINE = n_ORDINE;
	}
	
	public String getCOD_PROV() {
		return COD_PROV;
	}
	public void setCOD_PROV(String cOD_PROV) {
		COD_PROV = cOD_PROV;
	}
	public JsonObject getCommessaJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("ID_COMMESSA", this.ID_COMMESSA);
		jobj.addProperty("DT_COMMESSA", this.DT_COMMESSA.getTime());
		jobj.addProperty("FIR_CHIUSURA_DT", this.FIR_CHIUSURA_DT.getTime());
		jobj.addProperty("ID_ANAGEN", this.ID_ANAGEN);
		jobj.addProperty("ID_ANAGEN_NOME", this.ID_ANAGEN_NOME);
		jobj.addProperty("DESCR", this.DESCR);
		jobj.addProperty("SYS_STATO", this.SYS_STATO);
		jobj.addProperty("ID_ANAGEN_COMM", this.ID_ANAGEN_COMM);
		jobj.addProperty("K2_ANAGEN_INDR", this.K2_ANAGEN_INDR);
		jobj.addProperty("ANAGEN_INDR_DESCR", this.ANAGEN_INDR_DESCR);
		jobj.addProperty("ANAGEN_INDR_INDIRIZZO", this.ANAGEN_INDR_INDIRIZZO);
		jobj.addProperty("INDIRIZZO_PRINCIPALE", this.INDIRIZZO_PRINCIPALE);
		jobj.addProperty("NOTE_GEN", this.NOTE_GEN);
		jobj.addProperty("N_ORDINE", this.N_ORDINE);
		
		if(this.listaAttivita!=null) {
			JsonArray attivitajobj = new JsonArray();
			
			for(AttivitaMilestoneDTO attivita : this.listaAttivita) {
				attivitajobj.add( attivita.getListaAttivitaJsonObject());
			}
			jobj.add("listaAttivita", attivitajobj);
		}
	
		
		return jobj;
	}
	
	
}
