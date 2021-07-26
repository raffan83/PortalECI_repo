package it.portalECI.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class VerbaleDTO implements Serializable {
	
	public static String VERBALE="VERBALE";
	public static String SK_TEC="SCHEDA_TECNICA";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int questionarioID;
	private Date createDate;
	private Date updateDate;
	private String codiceCategoria;
	private String codiceVerifica;
	private String descrizioneVerifica;
	private StatoVerbaleDTO stato;
	private InterventoDTO intervento;
	private Set<DomandaVerbaleDTO> domandeVerbale;
	private VerbaleDTO schedaTecnica;
	private String type;
	private Set<DocumentoDTO> documentiVerbale;
	private Date dataScaricamento;
	private Date dataTrasferimento;
	private String note;
	private AttrezzaturaDTO attrezzatura;
	private String sedeUtilizzatore;
		
	private String numeroVerbale;	
	private String esercente;
	private StrumentoVerificatoreDTO strumento_verificatore;
	
	private Date data_verifica;
	
	private Date data_prossima_verifica;
	private Date data_verifica_integrita;
	private Date data_prossima_verifica_integrita;
	private Date data_verifica_interna;
	private Date data_prossima_verifica_interna;
	private int effettuazione_verifica;
	private String esito;	
	private String descrizione_sospensione;
	private int tipo_verifica;
	private int tipo_verifica_gvr;	
	
	private int frequenza;
	private int motivo_verifica;
	private int tipologia_verifica;
	private String ore_uomo;
	private String matricola_vie;
	private Date data_fine_verifica;
	private String descrizione_sede_utilizzatore;
	private Date data_conferma;
	private Date data_approvazione;
	private UtenteDTO responsabile_approvatore;
	private int firmato;
	private int controfirmato;
	private CampioneDTO campione;
	private int inviato;
	private int visibile_cliente;
	
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
	
	
	public StatoVerbaleDTO getStato() {
		return stato;
	}

	public void setStato(StatoVerbaleDTO stato) {
		this.stato = stato;
	}
	
	public InterventoDTO getIntervento() {
		return intervento;
	}
	
	public void setIntervento(InterventoDTO intervento) {
		this.intervento = intervento;
	}

	public String getCodiceCategoria() {
		return codiceCategoria;
	}

	public void setCodiceCategoria(String codiceCategoria) {
		this.codiceCategoria = codiceCategoria;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public VerbaleDTO getSchedaTecnica() {
		return schedaTecnica;
	}

	public void setSchedaTecnica(VerbaleDTO schedaTecnica) {
		this.schedaTecnica = schedaTecnica;
	}

	public Set<DomandaVerbaleDTO> getDomandeVerbale() {
		return domandeVerbale;
	}

	public void setDomandeVerbale(Set<DomandaVerbaleDTO> domandeVerbale) {
		this.domandeVerbale = domandeVerbale;
	}
	
	public void addToDomande(DomandaVerbaleDTO domanda) {
		if (domanda != null) {
			if(this.domandeVerbale ==null) this.domandeVerbale = new HashSet<DomandaVerbaleDTO>();
			this.domandeVerbale.add(domanda);
		}
	}
	public void addToDocumentiVerbale(DocumentoDTO documento) {
		if (documento != null) {
			if(this.documentiVerbale ==null) this.documentiVerbale = new HashSet<DocumentoDTO>();
			this.documentiVerbale.add(documento);
		}
	}
//
//	public List<DomandaQuestionarioDTO> getDomandeSchedaTecnica() {
//		return domandeSchedaTecnica;
//	}
//
//	public void setDomandeSchedaTecnica(List<DomandaQuestionarioDTO> domandeSchedaTecnica) {
//		this.domandeSchedaTecnica = domandeSchedaTecnica;
//	}

	public int getQuestionarioID() {
		return questionarioID;
	}

	public void setQuestionarioID(int questionarioID) {
		this.questionarioID = questionarioID;
	}

	public String getCodiceVerifica() {
		return codiceVerifica;
	}

	public void setCodiceVerifica(String codiceVerifica) {
		this.codiceVerifica = codiceVerifica;
	}

	public String getDescrizioneVerifica() {
		return descrizioneVerifica;
	}

	public void setDescrizioneVerifica(String descrizioneVerifica) {
		this.descrizioneVerifica = descrizioneVerifica;
	}

	public Set<DocumentoDTO> getDocumentiVerbale() {
		return documentiVerbale;
	}

	public void setDocumentiVerbale(Set<DocumentoDTO> documentiVerbale) {
		this.documentiVerbale = documentiVerbale;
	}
	
	public String getNumeroVerbale() {
		return numeroVerbale;
	}

	public void setNumeroVerbale(String numeroVerbale) {
		this.numeroVerbale = numeroVerbale;
	}
	
	public Date getDataScaricamento() {
		return dataScaricamento;
	}

	public void setDataScaricamento(Date dataScaricamento) {
		this.dataScaricamento = dataScaricamento;
	}
	
	
	public Date getDataTrasferimento() {
		return dataTrasferimento;
	}

	public void setDataTrasferimento(Date dataTrasferimento) {
		this.dataTrasferimento = dataTrasferimento;
	}

	public JsonObject getVerbaleJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.id);
	
		jobj.addProperty("codiceVerifica", this.codiceVerifica);
		jobj.addProperty("codiceCategoria", this.codiceCategoria);
		jobj.addProperty("descrizioneVerifica", this.descrizioneVerifica);
		jobj.addProperty("note", this.note);
		
		if(this.domandeVerbale!=null) {
			JsonArray domandeVerbaleobj = new JsonArray();
			
			for(DomandaVerbaleDTO domanda : this.domandeVerbale) {
				domandeVerbaleobj.add(domanda.getDomandaJsonObject());
			}
			jobj.add("domande", domandeVerbaleobj);
		}	
		
		if(this.type.equals(VerbaleDTO.VERBALE) && this.schedaTecnica!=null) {
			jobj.add("schedaTecnica", this.schedaTecnica.getVerbaleJsonObject());
		}
		
		
		return jobj;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public AttrezzaturaDTO getAttrezzatura() {
		return attrezzatura;
	}

	public void setAttrezzatura(AttrezzaturaDTO attrezzatura) {
		this.attrezzatura = attrezzatura;
	}

	public String getSedeUtilizzatore() {
		return sedeUtilizzatore;
	}

	public void setSedeUtilizzatore(String sedeUtilizzatore) {
		this.sedeUtilizzatore = sedeUtilizzatore;
	}

	public String getEsercente() {
		return esercente;
	}

	public void setEsercente(String esercente) {
		this.esercente = esercente;
	}

	public StrumentoVerificatoreDTO getStrumento_verificatore() {
		return strumento_verificatore;
	}

	public void setStrumento_verificatore(StrumentoVerificatoreDTO strumento_verificatore) {
		this.strumento_verificatore = strumento_verificatore;
	}

	public Date getData_verifica() {
		return data_verifica;
	}

	public void setData_verifica(Date data_verifica) {
		this.data_verifica = data_verifica;
	}

	public Date getData_prossima_verifica() {
		return data_prossima_verifica;
	}

	public void setData_prossima_verifica(Date data_prossima_verifica) {
		this.data_prossima_verifica = data_prossima_verifica;
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

	public int getEffettuazione_verifica() {
		return effettuazione_verifica;
	}

	public void setEffettuazione_verifica(int effettuazione_verifica) {
		this.effettuazione_verifica = effettuazione_verifica;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public int getTipo_verifica() {
		return tipo_verifica;
	}

	public void setTipo_verifica(int tipo_verifica) {
		this.tipo_verifica = tipo_verifica;
	}

	public String getDescrizione_sospensione() {
		return descrizione_sospensione;
	}

	public void setDescrizione_sospensione(String descrizione_sospensione) {
		this.descrizione_sospensione = descrizione_sospensione;
	}

	public int getTipo_verifica_gvr() {
		return tipo_verifica_gvr;
	}

	public void setTipo_verifica_gvr(int tipo_verifica_gvr) {
		this.tipo_verifica_gvr = tipo_verifica_gvr;
	}

	public int getFrequenza() {
		return frequenza;
	}

	public void setFrequenza(int frequenza) {
		this.frequenza = frequenza;
	}

	public int getMotivo_verifica() {
		return motivo_verifica;
	}

	public void setMotivo_verifica(int motivo_verifica) {
		this.motivo_verifica = motivo_verifica;
	}

	public int getTipologia_verifica() {
		return tipologia_verifica;
	}

	public void setTipologia_verifica(int tipologia_verifica) {
		this.tipologia_verifica = tipologia_verifica;
	}

	public String getOre_uomo() {
		return ore_uomo;
	}

	public void setOre_uomo(String ore_uomo) {
		this.ore_uomo = ore_uomo;
	}


	public Date getData_fine_verifica() {
		return data_fine_verifica;
	}

	public void setData_fine_verifica(Date data_fine_verifica) {
		this.data_fine_verifica = data_fine_verifica;
	}

	public String getMatricola_vie() {
		return matricola_vie;
	}

	public void setMatricola_vie(String matricola_vie) {
		this.matricola_vie = matricola_vie;
	}

	public String getDescrizione_sede_utilizzatore() {
		return descrizione_sede_utilizzatore;
	}

	public void setDescrizione_sede_utilizzatore(String descrizione_sede_utilizzatore) {
		this.descrizione_sede_utilizzatore = descrizione_sede_utilizzatore;
	}

	public Date getData_conferma() {
		return data_conferma;
	}

	public void setData_conferma(Date data_conferma) {
		this.data_conferma = data_conferma;
	}

	public Date getData_approvazione() {
		return data_approvazione;
	}

	public void setData_approvazione(Date data_approvazione) {
		this.data_approvazione = data_approvazione;
	}

	public UtenteDTO getResponsabile_approvatore() {
		return responsabile_approvatore;
	}

	public void setResponsabile_approvatore(UtenteDTO responsabile_approvatore) {
		this.responsabile_approvatore = responsabile_approvatore;
	}

	public int getFirmato() {
		return firmato;
	}

	public void setFirmato(int firmato) {
		this.firmato = firmato;
	}

	public int getControfirmato() {
		return controfirmato;
	}

	public void setControfirmato(int controfirmato) {
		this.controfirmato = controfirmato;
	}

	public CampioneDTO getCampione() {
		return campione;
	}

	public void setCampione(CampioneDTO campione) {
		this.campione = campione;
	}

	public int getInviato() {
		return inviato;
	}

	public void setInviato(int inviato) {
		this.inviato = inviato;
	}

	public int getVisibile_cliente() {
		return visibile_cliente;
	}

	public void setVisibile_cliente(int visibile_cliente) {
		this.visibile_cliente = visibile_cliente;
	}

	
}