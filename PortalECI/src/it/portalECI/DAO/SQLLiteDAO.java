package it.portalECI.DAO;

import it.portalECI.DTO.ClassificazioneDTO;
import it.portalECI.DTO.DatasetCampionamentoDTO;
import it.portalECI.DTO.InterventoCampionamentoDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.LuogoVerificaDTO;
import it.portalECI.DTO.MisuraDTO;
import it.portalECI.DTO.PlayloadCampionamentoDTO;
import it.portalECI.DTO.PuntoMisuraDTO;
import it.portalECI.DTO.ScadenzaDTO;
import it.portalECI.DTO.StatoRicezioneStrumentoDTO;
import it.portalECI.DTO.StatoStrumentoDTO;
import it.portalECI.DTO.StrumentoDTO;
import it.portalECI.DTO.TipoRapportoDTO;
import it.portalECI.DTO.TipoStrumentoDTO;
import it.portalECI.Util.Costanti;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class SQLLiteDAO {

private static String sqlCreateStrumentTable="CREATE TABLE tblStrumenti(id Integer primary key autoincrement , " +
																		"indirizzo varchar(255),"+
																		"denominazione varchar(255),"+
																		"codice_interno varchar(255),"+
																		"costruttore varchar(255),"+
																		"modello varchar(255),"+
																		"classificazione varchar(255),"+
																		"matricola varchar(255),"+
																		"risoluzione varchar(255),"+
																		"campo_misura varchar(255),"+
																		"freq_verifica_mesi varchar(255),"+
																		"tipoRapporto varchar(255),"+
																		"StatoStrumento varchar(255),"+
																		"reparto varchar(255),"+
																		"utilizzatore varchar(255),"+
																		"procedura varchar(255),"+
																		"id_tipo_strumento Integer," +
																		"note varchar(255)," +
																		"creato varcar(1)," +
																		"importato varchar(1)," +
																		"dataUltimaVerifica Date," +
																		"dataProssimaVerifica Date," +
																		"nCertificato varchar(255)," +
																		"strumentoModificato varchar(1)," +
																		"luogo_verifica varchar(255));";

private static String sqlCreateCMPTable="CREATE TABLE tblCampioni(id_camp Integer," +
																  "codice varchar(255) ,"+
		    													  "matricola varchar(255),"+
		    													  "modello varchar(255),"+
		    													  "num_certificato varchar(255),"+
		    													  "dataVerifica Date,"+
		    													  "data_scadenza Date,"+
		    													  "freq_taratura_mesi Integer,"+
		    													  "parametri_taratura varchar(255),"+
		    													  "UM varchar(255),"+
		    													  "UM_FOND varchar(255),"+
		    													  "valore_taratura decimal(30,15),"+
		    													  "valore_nominale decimal(30,15),"+
		    													  "divisione_unita_misura decimal(30,15),"+
		    													  "incertezza_assoluta decimal(30,15),"+
		    													  "incertezza_relativa decimal(30,15),"+
		    													  "id_tipo_grandezza Integer,"+
		    													  "interpolazione_permessa Integer,"+
		    													  "tipoGrandezza varchar(255)," +
		    													  "abilitato varchar(1));";

private static String sqlCreateMISTab="CREATE TABLE tblMisure(id Integer primary key autoincrement , id_str Integer, dataMisura Date, temperatura decimal(30,15) , umidita decimal(30,15),tipoFirma Integer ,statoRicezione Intgeger,statoMisura Integer);";

private static String sqlCreateMisOpt="CREATE TABLE tblTabelleMisura(id Integer primary key autoincrement,id_misura Integer," +
																	 "id_tabella Integer," +
																	 "id_ripetizione Integer," +
																	 "ordine Integer," +
																	 "tipoProva char(1)," +
																	 "label varchar(255)," +
																	 "tipoVerifica varchar(255)," +
																	 "um varchar(50)," +
																	 "valoreCampione decimal(30,15)," +
																	 "valoreMedioCampione decimal(30,15)," +
																	 "valoreStrumento decimal(30,15)," +
																	 "valoreMedioStrumento decimal(30,15)," +
																	 "scostamento decimal(30,15)," +
																	 "accettabilita decimal(30,15)," +
																	 "incertezza decimal(30,15)," +
																	 "esito varchar(10)," +
																	 "desc_campione varchar(255)," +
																	 "desc_parametro varchar(255)," +
																	 "misura decimal(30,15)," +
																	 "um_calc varchar(50)," +
																	 "risoluzione_misura decimal(30,15)," +
																	 "risoluzione_campione decimal(30,15)," +
																	 "fondo_scala decimal(30,15)," +
																	 "interpolazione Integer," +
																	 "fm varchar(255)," +
																	 "selConversione Integer," +
																	 "selTolleranza Integer," +
																	 "letturaCampione decimal(30,15) , " +
																	 "calibrazione varchar(50) ," +
																	 "perc_util decimal(30,15)," +
																	 "val_misura_prec decimal(30,15)," +
																	 "val_campione_prec decimal(30,15)," +
																	 "applicabile varchar(1)," +
																	 "dgt varchar(255));";

private static String sqlCreateTipoStr_tipoGra="CREATE TABLE tbl_ts_tg(id_tipo_grandezza Integer ," +
																	 "id_tipo_strumento Integer);";

private static String sqlCreateClassificazione="CREATE TABLE tbl_classificazione(id Integer ," +
		 															   "descrizione Varchar(255));";

private static String sqlCreateLuogoVerifica="CREATE TABLE tbl_luogoVerifica(id Integer ," +
		   									 "descrizione Varchar(255));";

private static String sqlCreateTipoRapporto="CREATE TABLE tbl_tipoRapporto(id Integer ," +
		   															"descrizione Varchar(255));";

private static String sqlCreateStatoStumento="CREATE TABLE tbl_statoStrumento(id Integer ," +
		   															"descrizione Varchar(255));";

private static String sqlCreateTipoStumento="CREATE TABLE tbl_tipoStrumento(id Integer ," +
																			"descrizione Varchar(255));";
			
private static String sqlCreateGeneral="CREATE TABLE tbl_general(id Integer ," +
																	"sede varchar(255),upload varchar(1));";

private static String sqlCreateFattoriMoltiplicativi="CREATE TABLE tbl_fattori_moltiplicativi (descrizione varchar(20)," +
																							   "sigla varchar(2)," +
																							   "potenza double(2,0)," +
																							   "fm decimal(60,30))";


private static String sqlCreateTableConversione="CREATE TABLE tbl_conversione (id int(11) ,um_da varchar(100) ,um_a varchar(100) , " +
											"fattoreConversione decimal(60,30) ,um varchar(100) ,tipo_misura varchar(100) ," +
											"validita varchar(20) ,potenza Integer(5));"; 

private static String sqlCreateTableCampioniUtilizzati="CREATE TABLE tblCampioniUtilizzati(id Integer primary key autoincrement,id_misura Integer," +
																	 "id_tabellaMisura Integer,"+
																	  "desc_parametro varchar(100)," +
																	  "desc_campione varchar(100));"; 


private static String sqlCreateGeneralCampionamento="CREATE TABLE tbl_general(commessa varchar(255),cliente varchar(255)," +
																			 "temp_tras decimal(5,2)," +
																			 "data_prelievo date," +
																			 "id_tipoCampionamento Integer,id_tipologiaCampionamento Integer,upload varchar(1))";

private static String sqlCreateDataSetCampionamento="CREATE TABLE tbl_dataset_campionamento (id int(11),id_tipo_campionamento int(11) ,nome_campo varchar(100)," +
																						"tipo_campo varchar(100)," +
																						"codice_campo varchar(100),composite varcaher(255))";

private static String sqlCreatePlayLoadCampionamento="CREATE TABLE tbl_playload_campionamento (id Integer primary key autoincrement," +
																							"id_dataset_campionamento int(11),id_punto Integer," +
																							"valore_misurato varchar(50))";

private static String sqlCreatePuntoCampionamento="CREATE TABLE tbl_punto_campionamento (id Integer primary key autoincrement ," +
																	"nome_punto varchar(50)," +
																	"data date, ora varchar(5))";

public static Connection getConnection(String path, String nomeFile) throws ClassNotFoundException, SQLException {
		
		Class.forName("org.sqlite.JDBC");
		
		Connection con=DriverManager.getConnection("jdbc:sqlite:"+path+"/"+nomeFile+".db");
		
		return con;
	}

public static Connection getConnection(String nameFile) throws ClassNotFoundException, SQLException {
	
	Class.forName("org.sqlite.JDBC");
	
	Connection con=DriverManager.getConnection("jdbc:sqlite:"+nameFile);
	
	return con;
}

public static void createDB(Connection con) throws SQLException {
	
	try
	{
	PreparedStatement pst =con.prepareStatement(sqlCreateStrumentTable);
	pst.execute();
	
	PreparedStatement pstCM =con.prepareStatement(sqlCreateCMPTable);
	pstCM.execute();
	
	PreparedStatement pstMisure=con.prepareStatement(sqlCreateMISTab);
	pstMisure.execute();
	
	
	PreparedStatement pstMis =con.prepareStatement(sqlCreateMisOpt);
	pstMis.execute();
	
	PreparedStatement pstCampAss =con.prepareStatement(sqlCreateTipoStr_tipoGra);
	pstCampAss.execute();
	
	PreparedStatement pstFatMolt =con.prepareStatement(sqlCreateFattoriMoltiplicativi);
	pstFatMolt.execute();
	
	PreparedStatement pstConversione =con.prepareStatement(sqlCreateTableConversione);
	pstConversione.execute();
	
	PreparedStatement pstCampioniUtilizzati =con.prepareStatement(sqlCreateTableCampioniUtilizzati);
	pstCampioniUtilizzati.execute();
	
	PreparedStatement pstClass =con.prepareStatement(sqlCreateClassificazione);
	pstClass.execute();
	
	PreparedStatement psttipoRapporto =con.prepareStatement(sqlCreateTipoRapporto);
	psttipoRapporto.execute();
	
	PreparedStatement pstStatoStrumento =con.prepareStatement(sqlCreateStatoStumento);
	pstStatoStrumento.execute();
	
	PreparedStatement psttipoStrumento=con.prepareStatement(sqlCreateTipoStumento);
	psttipoStrumento.execute();
	
	PreparedStatement pstgeneral=con.prepareStatement(sqlCreateGeneral);
	pstgeneral.execute();
	
	PreparedStatement pstLuogoVerifica=con.prepareStatement(sqlCreateLuogoVerifica);
	pstLuogoVerifica.execute();
	
	}
	
	catch 
	(Exception e) 
	{
		throw e;
	}
}

public static void cerateDBCampionamento(Connection con) throws Exception {
	
	try
	{
	
		PreparedStatement pstGen =con.prepareStatement(sqlCreateGeneralCampionamento);
		pstGen.execute();
		
		PreparedStatement pstDataSet =con.prepareStatement(sqlCreateDataSetCampionamento);
		pstDataSet.execute();
		
		PreparedStatement pstPlayLoad =con.prepareStatement(sqlCreatePlayLoadCampionamento);
		pstPlayLoad.execute();
		
		PreparedStatement punto =con.prepareStatement(sqlCreatePuntoCampionamento);
		punto.execute();
	
	}
	
	catch (Exception e) 
	
	{
		throw e;
	}
	
}

public static ArrayList<MisuraDTO> getListaMisure(Connection con, InterventoDTO intervento) throws Exception {
	
	ArrayList<MisuraDTO> listaMisure = new ArrayList<MisuraDTO>();
	PreparedStatement pst=null;
	ResultSet rs= null;
	MisuraDTO misura = new MisuraDTO(); 
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	try
	{
	
	pst=con.prepareStatement("SELECT a.id as idMisura, a.id_Str as idStr,dataMisura,temperatura,umidita,tipoFirma,statoRicezione, b.* FROM tblMisure a " +
							 "join tblStrumenti b on a.id_str=b.id " +
							 "WHERE a.statoMisura=1");
	
	rs=pst.executeQuery();
	
	while(rs.next())
	{
		misura= new MisuraDTO();
		misura.setId(rs.getInt("idMisura"));
		misura.setIntervento(intervento);
		StrumentoDTO strumento = new StrumentoDTO();
		strumento.set__id(rs.getInt("idStr"));
		strumento.setDenominazione(rs.getString("denominazione"));
		strumento.setCodice_interno(rs.getString("codice_interno"));
		strumento.setCostruttore(rs.getString("costruttore"));
		strumento.setModello(rs.getString("modello"));
		strumento.setClassificazione(new ClassificazioneDTO(rs.getInt("classificazione"),""));
		strumento.setMatricola(rs.getString("matricola"));
		strumento.setRisoluzione(rs.getString("risoluzione"));
		strumento.setCampo_misura(rs.getString("campo_misura"));
		strumento.setLuogo(new LuogoVerificaDTO(rs.getInt("luogo_verifica"),""));
		ScadenzaDTO scadenza = new ScadenzaDTO();
		
		scadenza.setFreq_mesi(rs.getInt("freq_verifica_mesi"));
		scadenza.setTipo_rapporto(new TipoRapportoDTO(rs.getInt("tipoRapporto"), ""));
		
		if(scadenza.getTipo_rapporto().getId()==Costanti.ID_TIPO_RAPPORTO_SVT)
		{
			Date date = new Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			
			scadenza.setDataUltimaVerifica(sqlDate);
			
			Calendar data = Calendar.getInstance();
			
			data.setTime(date);
			data.add(Calendar.MONTH,scadenza.getFreq_mesi());
			
			java.sql.Date sqlDateProssimaVerifica = new java.sql.Date(data.getTime().getTime());
				
			scadenza.setDataProssimaVerifica(sqlDateProssimaVerifica);
			
		}
		
		Set<ScadenzaDTO> listaScadenze =new HashSet<ScadenzaDTO>();
		listaScadenze.add(scadenza);
		strumento.setListaScadenzeDTO(listaScadenze);
		strumento.setStato_strumento(new StatoStrumentoDTO(Costanti.STATO_STRUMENTO_IN_SERVIZIO, ""));
		strumento.setReparto(rs.getString("reparto"));
		strumento.setUtilizzatore(rs.getString("utilizzatore"));
		strumento.setTipo_strumento(new TipoStrumentoDTO(rs.getInt("id_tipo_strumento"),""));
		strumento.setNote(rs.getString("note"));
		strumento.setCreato(rs.getString("creato"));
		strumento.setImportato(rs.getString("importato"));
		strumento.setStrumentoModificato(rs.getString("strumentoModificato"));
		strumento.setIdTipoRapporto(rs.getInt("tipoRapporto"));
		strumento.setIdClassificazione(rs.getInt("classificazione"));
		strumento.setFrequenza(rs.getInt("freq_verifica_mesi"));
		strumento.setProcedureString(rs.getString("procedura"));
		
		misura.setStrumento(strumento);
		misura.setDataMisura(sdf.parse(rs.getString("dataMisura")));
		misura.setTemperatura(rs.getBigDecimal("temperatura"));
		misura.setUmidita(rs.getBigDecimal("umidita"));
		misura.setTipoFirma(rs.getInt("tipoFirma"));
		
		
		misura.setStatoRicezione(new StatoRicezioneStrumentoDTO(rs.getInt("statoRicezione")));
		misura.setObsoleto("N");
		misura.setnCertificato(rs.getString("nCertificato"));
	
		listaMisure.add(misura);
	}
	 
	
	return listaMisure;
	
	}catch (Exception e) {
		throw e;
	}
}

public static ArrayList<PuntoMisuraDTO> getListaPunti(Connection con, int idTemp, int idMisura) throws SQLException {
	
	ArrayList<PuntoMisuraDTO> listaPuntoMisura = new ArrayList<PuntoMisuraDTO>();
	
	PreparedStatement pst=null;
	ResultSet rs= null;
	PuntoMisuraDTO punto ; 
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	
	
	pst=con.prepareStatement("SELECT * FROM tblTabelleMisura WHERE id_misura=?");
	pst.setInt(1, idTemp);
	
	rs=pst.executeQuery();
	
	while(rs.next())
	{
		punto= new PuntoMisuraDTO();
		
		punto.setId_misura(idMisura);
		punto.setId_tabella(rs.getInt("id_tabella"));
		punto.setId_ripetizione(rs.getInt("id_ripetizione"));
		punto.setOrdine(rs.getInt("ordine"));
		punto.setTipoProva(rs.getString("tipoProva"));
		punto.setTipoVerifica(rs.getString("tipoVerifica"));
		punto.setUm(rs.getString("um"));
		punto.setValoreCampione(rs.getBigDecimal("valoreCampione"));
		punto.setValoreMedioCampione(rs.getBigDecimal("valoreMedioCampione"));
		punto.setValoreStrumento(rs.getBigDecimal("valoreStrumento"));
		punto.setValoreMedioStrumento(rs.getBigDecimal("valoreMedioStrumento"));
		punto.setScostamento(rs.getBigDecimal("scostamento"));
		punto.setAccettabilita(rs.getBigDecimal("accettabilita"));
		punto.setIncertezza(rs.getBigDecimal("incertezza"));
		punto.setEsito(rs.getString("esito"));
		punto.setDesc_Campione(rs.getString("desc_campione"));
		punto.setDesc_parametro(rs.getString("desc_parametro"));
		punto.setMisura(rs.getBigDecimal("misura"));
		punto.setUm_calc(rs.getString("um_calc"));
		punto.setRisoluzione_misura(rs.getBigDecimal("risoluzione_misura"));
		punto.setDgt(rs.getBigDecimal("dgt"));
		

		String fs=rs.getString("fondo_scala");
		
		if(fs!=null && fs.length()>0)
		{
			punto.setFondoScala(new BigDecimal(fs));
		}else
		{
			punto.setFondoScala(null);
		}
		
		String perc=rs.getString("perc_util");
		
		if(perc!=null && perc.length()>0)
		{
			punto.setPer_util(new BigDecimal(perc).doubleValue());
		}else
		{
			punto.setPer_util(0);
		}
		
		punto.setRisoluzione_campione(rs.getBigDecimal("risoluzione_campione"));
		
		punto.setInterpolazione(rs.getInt("interpolazione"));
		punto.setFm(rs.getString("fm"));
		punto.setSelConversione(rs.getInt("selConversione"));
		punto.setSelTolleranza(rs.getInt("selTolleranza"));
		punto.setLetturaCampione(rs.getBigDecimal("letturaCampione"));
		punto.setObsoleto("N");
		punto.setApplicabile(rs.getString("applicabile"));
		listaPuntoMisura.add(punto);
		
	}
	 
	
	return listaPuntoMisura;
}

public static void updateNuovoStrumento(Connection con,StrumentoDTO nuovoStrumento, int idMisura, int vecchioId) throws Exception {
	
	PreparedStatement pstUpdateStrumento=null;
	PreparedStatement pstUpdateMisura=null;
	
	try 
	{
		pstUpdateStrumento=con.prepareStatement("UPDATE tblStrumenti SET id=?,creato=?,importato=? WHERE id=?");
		pstUpdateStrumento.setInt(1,nuovoStrumento.get__id());
		pstUpdateStrumento.setString(2, "N");
		pstUpdateStrumento.setString(3,"S");
		pstUpdateStrumento.setInt(4, vecchioId);
		
		pstUpdateStrumento.execute();
		
		pstUpdateMisura=con.prepareStatement("UPDATE tblMisure SET id_Str=? WHERE id=?");
		pstUpdateMisura.setInt(1, nuovoStrumento.get__id());
		pstUpdateMisura.setInt(2, idMisura);
		
		pstUpdateMisura.execute();
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

public static boolean checkFile(String path) throws Exception {
	
	boolean toReturn =true;
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs = null;
	
	try
	{
		con=getConnection(path);
		pst=con.prepareStatement("select * FROM tbl_general WHERE upload='N'");
		
		rs=pst.executeQuery();
		while(rs.next())
		{
			toReturn=false;
		}
		
	} catch 
	(Exception e) 
	{
		e.printStackTrace();
		throw e;
	}
	
	
	return toReturn;
}

public static ArrayList<PlayloadCampionamentoDTO> getListaPlayLoad(Connection con, InterventoCampionamentoDTO intervento) throws Exception {
	
	PreparedStatement pst=null;
	ResultSet rs=null;
	ArrayList<PlayloadCampionamentoDTO> listaPlay= new ArrayList<>();
	try 
	{
		pst=con.prepareStatement("SELECT * FROM tbl_playload_campionamento");
		rs=pst.executeQuery();
		PlayloadCampionamentoDTO play = null;
		while (rs.next()) {
			play=new PlayloadCampionamentoDTO();
			play.setIntervento(intervento);
			play.setIdInterventoCampionamento(intervento.getId());
			play.setDataset(new DatasetCampionamentoDTO(rs.getInt("id_dataset_campionamento")));
			play.setId_punto(rs.getInt("id_punto"));
			play.setValore_misurato(rs.getString("valore_misurato"));
			
			listaPlay.add(play);
			
		}
	} 
	
	catch (Exception e) 
	{
		e.printStackTrace();
		throw e;
	}
	return listaPlay;
}

public static Date getDataChiusura(Connection con) throws Exception {
	
	
	PreparedStatement pst=null;
	ResultSet rs=null;
	Date dateReturn=null;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	try 
	{
		pst=con.prepareStatement("SELECT data_prelievo FROM tbl_general");
		rs=pst.executeQuery();
		while (rs.next()) {
			
			dateReturn=sdf.parse(rs.getString(1));
			
		}
	} 
	
	catch (Exception e) 
	{
		e.printStackTrace();
		throw e;
	}
	return dateReturn;
	
}


}
