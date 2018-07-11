package it.portalECI.DAO;


import it.portalECI.DTO.InterventoDTO;

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
	
		try{
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
	
		}catch (Exception e) {
			throw e;
		}
	}

	public static void cerateDBCampionamento(Connection con) throws Exception {
	
		try{
	
			PreparedStatement pstGen =con.prepareStatement(sqlCreateGeneralCampionamento);
			pstGen.execute();
		
			PreparedStatement pstDataSet =con.prepareStatement(sqlCreateDataSetCampionamento);
			pstDataSet.execute();
		
			PreparedStatement pstPlayLoad =con.prepareStatement(sqlCreatePlayLoadCampionamento);
			pstPlayLoad.execute();
		
			PreparedStatement punto =con.prepareStatement(sqlCreatePuntoCampionamento);
			punto.execute();	
		}catch (Exception e) {
			throw e;
		}	
	}

	public static Date getDataChiusura(Connection con) throws Exception {
	
		PreparedStatement pst=null;
		ResultSet rs=null;
		Date dateReturn=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			pst=con.prepareStatement("SELECT data_prelievo FROM tbl_general");
			rs=pst.executeQuery();
			while (rs.next()) {
			
				dateReturn=sdf.parse(rs.getString(1));
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dateReturn;	
	}
}
