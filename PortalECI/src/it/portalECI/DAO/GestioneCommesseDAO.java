package it.portalECI.DAO;

import it.portalECI.DTO.AttivitaMilestoneDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.UtenteDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GestioneCommesseDAO {

	private static final String querySqlServerComTras = "SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE " +
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN " +
			"WHERE ID_ANAGEN_COMM=1703  AND (TB_CATEG_COM='VIE' OR TB_CATEG_COM='VAL')";
	
	private static final String querySqlServerComTrasWhitYear = "SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE," +
			"a.ID_ANAGEN_UTILIZ AS ID_UTIL ,a.K2_ANAGEN_INDIR_UTILIZ AS ID_IND_UTIL, e.nome as NOME_CLIENTE_UTIL, e.INDIR as IND_PRINC_UTIL,e.CITTA AS CITTAPRINCIPALE,e.CODPROV AS COD_PROV_PRINCIPALE,d.DESCR AS DESC_SEDE_UTIL,d.INDIR AS IND_SEDE_UTIL ,d.CITTA AS CITTA_SEDE_UTIL,d.CODPROV AS PROV_SEDE_UTIL "+
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN " +
			"LEFT JOIN [BTOMEN_CRESCO_DATI].[dbo].[BWT_ANAGEN_INDIR] AS d on a.K2_ANAGEN_INDIR_UTILIZ=d.K2_ANAGEN_INDIR AND a.ID_ANAGEN_UTILIZ=d.ID_ANAGEN "+
			"LEFT JOIN [BTOMEN_CRESCO_DATI].[dbo].[BWT_ANAGEN] AS e on a.ID_ANAGEN_UTILIZ=e.ID_ANAGEN "+			
			"WHERE ID_ANAGEN_COMM=1703 AND  year([DT_COMMESSA])=?  AND (TB_CATEG_COM='VIE' OR TB_CATEG_COM='VAL')";

	
	private static final String queryClienti = "SELECT ID_ANAGEN, NOME FROM BWT_ANAGEN";
	
	private static final String queryArticoli = "SELECT * FROM BWT_ANAART WHERE ID_ANAART=?";

	private static String querySqlServerCommon="SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE " +
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN " +
			"WHERE ID_ANAGEN_COMM=?  AND (TB_CATEG_COM='VIE' OR TB_CATEG_COM='VAL')";
	
	private static String querySqlServerCommonWhitYear="SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE " +
			",a.ID_ANAGEN_UTILIZ AS ID_UTIL ,a.K2_ANAGEN_INDIR_UTILIZ AS ID_IND_UTIL, e.nome as NOME_CLIENTE_UTIL, e.INDIR as IND_PRINC_UTIL,e.CITTA AS CITTAPRINCIPALE,e.CODPROV AS COD_PROV_PRINCIPALE,d.DESCR AS DESC_SEDE_UTIL,d.INDIR AS IND_SEDE_UTIL ,d.CITTA AS CITTA_SEDE_UTIL,d.CODPROV AS PROV_SEDE_UTIL "+
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN " +
			"LEFT JOIN [BTOMEN_CRESCO_DATI].[dbo].[BWT_ANAGEN_INDIR] AS d on a.K2_ANAGEN_INDIR_UTILIZ=d.K2_ANAGEN_INDIR AND a.ID_ANAGEN_UTILIZ=d.ID_ANAGEN "+
			"LEFT JOIN [BTOMEN_CRESCO_DATI].[dbo].[BWT_ANAGEN] AS e on a.ID_ANAGEN_UTILIZ=e.ID_ANAGEN "+
			"WHERE ID_ANAGEN_COMM=? AND  year([DT_COMMESSA])=?  AND (TB_CATEG_COM='VIE' OR TB_CATEG_COM='VAL')";
	
	private static String querySqlServerComId="SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE, ID_ANAGEN_COMM " +
			",a.ID_ANAGEN_UTILIZ AS ID_UTIL ,a.K2_ANAGEN_INDIR_UTILIZ AS ID_IND_UTIL, e.nome as NOME_CLIENTE_UTIL, e.INDIR as IND_PRINC_UTIL,e.CITTA AS CITTAPRINCIPALE,e.CODPROV AS COD_PROV_PRINCIPALE,d.DESCR AS DESC_SEDE_UTIL,d.INDIR AS IND_SEDE_UTIL ,d.CITTA AS CITTA_SEDE_UTIL,d.CODPROV AS PROV_SEDE_UTIL, b.CAP, c.CAP, d.CAP, e.CAP "+
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN " +
			"LEFT JOIN [BTOMEN_CRESCO_DATI].[dbo].[BWT_ANAGEN_INDIR] AS d on a.K2_ANAGEN_INDIR_UTILIZ=d.K2_ANAGEN_INDIR AND a.ID_ANAGEN_UTILIZ=d.ID_ANAGEN "+
			"LEFT JOIN [BTOMEN_CRESCO_DATI].[dbo].[BWT_ANAGEN] AS e on a.ID_ANAGEN_UTILIZ=e.ID_ANAGEN "+
			"WHERE ID_COMMESSA=?";

	
	
	private static String querySqlAttivitaCom="SELECT a.descr as DESC_ATT,a.note AS NOTE_ATT,b.DESCR as DESC_ART,a.QTA AS QUANTITA ,a.K2_RIGA AS RIGA , a.ID_ANAART as CODICEARTICOLO, a.NOTE_AGGREG_COD as CODAGG, b.H_LAVORATIVE as ORE_UOMO " +
										"from BWT_COMMESSA_AVANZ AS a " +
										"Left join BWT_ANAART AS b ON a.ID_ANAART =b.ID_ANAART " +
										"where ID_COMMESSA=? AND TB_TIPO_MILE='MILE'";
	

	private static final String querySqlServerComTrasDate = "SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE," +
			"a.ID_ANAGEN_UTILIZ AS ID_UTIL ,a.K2_ANAGEN_INDIR_UTILIZ AS ID_IND_UTIL, e.nome as NOME_CLIENTE_UTIL, e.INDIR as IND_PRINC_UTIL,e.CITTA AS CITTAPRINCIPALE,e.CODPROV AS COD_PROV_PRINCIPALE,d.DESCR AS DESC_SEDE_UTIL,d.INDIR AS IND_SEDE_UTIL ,d.CITTA AS CITTA_SEDE_UTIL,d.CODPROV AS PROV_SEDE_UTIL "+
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN " +
			"LEFT JOIN [BTOMEN_CRESCO_DATI].[dbo].[BWT_ANAGEN_INDIR] AS d on a.K2_ANAGEN_INDIR_UTILIZ=d.K2_ANAGEN_INDIR AND a.ID_ANAGEN_UTILIZ=d.ID_ANAGEN "+
			"LEFT JOIN [BTOMEN_CRESCO_DATI].[dbo].[BWT_ANAGEN] AS e on a.ID_ANAGEN_UTILIZ=e.ID_ANAGEN "+			
			"WHERE ID_ANAGEN_COMM=1703 AND  (TB_CATEG_COM='VIE' OR TB_CATEG_COM='VAL') AND DT_COMMESSA BETWEEN ? AND ?";
	
	
	private static String querySqlServerCommonDate= "SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE " +
			",a.ID_ANAGEN_UTILIZ AS ID_UTIL ,a.K2_ANAGEN_INDIR_UTILIZ AS ID_IND_UTIL, e.nome as NOME_CLIENTE_UTIL, e.INDIR as IND_PRINC_UTIL,e.CITTA AS CITTAPRINCIPALE,e.CODPROV AS COD_PROV_PRINCIPALE,d.DESCR AS DESC_SEDE_UTIL,d.INDIR AS IND_SEDE_UTIL ,d.CITTA AS CITTA_SEDE_UTIL,d.CODPROV AS PROV_SEDE_UTIL "+
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN " +
			"LEFT JOIN [BTOMEN_CRESCO_DATI].[dbo].[BWT_ANAGEN_INDIR] AS d on a.K2_ANAGEN_INDIR_UTILIZ=d.K2_ANAGEN_INDIR AND a.ID_ANAGEN_UTILIZ=d.ID_ANAGEN "+
			"LEFT JOIN [BTOMEN_CRESCO_DATI].[dbo].[BWT_ANAGEN] AS e on a.ID_ANAGEN_UTILIZ=e.ID_ANAGEN "+
			"WHERE ID_ANAGEN_COMM=? AND (TB_CATEG_COM='VIE' OR TB_CATEG_COM='VAL')  AND DT_COMMESSA BETWEEN ? AND ?";
	
	private static String queryTariffe = "SELECT a.[IMPORTO_UNIT],b.PREZZO " + 
			"  FROM [BTOMEN_CRESCO_DATI].[dbo].[BWT_COMMESSA_AVANZ] a JOIN " + 
			"  (SELECT ID_ANAART, [PREZZO]" + 
			"      ,DT_INIZIO_VAL" + 
			"  FROM [BTOMEN_CRESCO_DATI].[dbo].[PJT_LISTINO_CLIFOR] " + 
			"  where DT_INIZIO_VAL=( select max(DT_INIZIO_VAL) from [BTOMEN_CRESCO_DATI].[dbo].[PJT_LISTINO_CLIFOR] where ID_ANAART=?)" + 
			"  AND ID_ANAART=?) b on a.ID_ANAART=b.ID_ANAART" + 
			"  where tb_tipo_mile='MILE' AND ID_COMMESSA= ? and a.ID_ANAART = ? and a.[IMPORTO_UNIT] > 2";

	/*public static ArrayList<CommessaDTO> getListaCommesse(CompanyDTO company, String categoria, UtenteDTO user) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		PreparedStatement pstA=null;
		ResultSet rs=null;
		ResultSet rsA=null;
		
		ArrayList<CommessaDTO> listaCommesse = new ArrayList<>();
		
		try{
			con =ManagerSQLServer.getConnectionSQL();
			String categ="";
		
			if(!categoria.equals("")){
				String[] listaCategorie=categoria.split(";");

				for (int i = 0; i < listaCategorie.length; i++) {
					categ=categ+" AND TB_CATEG_COM='"+listaCategorie[i]+"'";
				}
			}
		
			if(user.isTras()){
				if(!categ.equals("")){
					String query=querySqlServerComTras.concat(" WHERE ").concat(categ.substring(5,categ.length()));
					pst=con.prepareStatement(query);
				}else{
					pst=con.prepareStatement(querySqlServerComTras);
				}
			}else{
				pst=con.prepareStatement(querySqlServerCommon+categ);
				pst.setInt(1, company.getId());
			}

			rs=pst.executeQuery();
		
			CommessaDTO commessa=null;
			while(rs.next()){
				commessa= new CommessaDTO();
				String idCommessa=rs.getString(1);
				commessa.setID_COMMESSA(idCommessa);
				commessa.setDT_COMMESSA(rs.getDate(2));
				commessa.setFIR_CHIUSURA_DT(rs.getDate(3));
				commessa.setID_ANAGEN(rs.getInt(4));
				commessa.setID_ANAGEN_NOME(rs.getString(5));
				commessa.setDESCR(rs.getString(6));
				commessa.setID_ANAGEN_COMM(company.getId());
				commessa.setSYS_STATO(rs.getString(7));
				commessa.setK2_ANAGEN_INDR(rs.getInt(8));
				commessa.setANAGEN_INDR_DESCR(null);
				String indirizzoSede=rs.getString(10);
				if (indirizzoSede!=null){
					commessa.setANAGEN_INDR_INDIRIZZO(indirizzoSede+" - "+rs.getString(11)+" ("+rs.getString(12)+")");
					commessa.setCOD_PROV(rs.getString(12));
				}else{
					commessa.setANAGEN_INDR_INDIRIZZO("");
					commessa.setCOD_PROV(rs.getString(15));					
				}
		
				commessa.setINDIRIZZO_PRINCIPALE(rs.getString(13)+" - "+rs.getString(14)+" ("+rs.getString(15)+")");
				commessa.setNOTE_GEN(rs.getString(16));
				commessa.setN_ORDINE(rs.getString(17));

				listaCommesse.add(commessa);
			
			}
		
		}catch (Exception e) {
			throw e;
		}
		
		return listaCommesse;
	}*/
	
	public static ArrayList<CommessaDTO> getListaCommesse(CompanyDTO company, UtenteDTO user, int year) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		PreparedStatement pstA=null;
		ResultSet rs=null;
		ResultSet rsA=null;
		
		ArrayList<CommessaDTO> listaCommesse = new ArrayList<>();
		
		try
		{
		
		con =ManagerSQLServer.getConnectionSQL();

		
		if(user.isTras())
		{
			
				if(year!=0) 
				{
					pst=con.prepareStatement(querySqlServerComTrasWhitYear);
					pst.setInt(1, year);
				}else 
				{
					pst=con.prepareStatement(querySqlServerComTras);
				}
			}
		
		else
		{
			if(year!=0) 
			{
				pst=con.prepareStatement(querySqlServerCommonWhitYear);
				pst.setInt(1, company.getId());
				pst.setInt(2, year);
			}
			else 
			{
				pst=con.prepareStatement(querySqlServerCommon);
				pst.setInt(1, company.getId());
			}
		}

		rs=pst.executeQuery();
		
		CommessaDTO commessa=null;
		while(rs.next())
		{
			commessa= new CommessaDTO();
			String idCommessa=rs.getString(1);
			commessa.setID_COMMESSA(idCommessa);
			commessa.setDT_COMMESSA(rs.getDate(2));
			commessa.setFIR_CHIUSURA_DT(rs.getDate(3));
			commessa.setID_ANAGEN(rs.getInt(4));
			commessa.setID_ANAGEN_NOME(rs.getString(5));
			commessa.setDESCR(rs.getString(6));
			commessa.setID_ANAGEN_COMM(company.getId());
			commessa.setSYS_STATO(rs.getString(7));
			commessa.setK2_ANAGEN_INDR(rs.getInt(8));
			commessa.setANAGEN_INDR_DESCR(null);
			String indirizzoSede=rs.getString(10);
			
			if (indirizzoSede!=null)
			{
				commessa.setANAGEN_INDR_INDIRIZZO(indirizzoSede+" - "+rs.getString(11)+" ("+rs.getString(12)+")");
			}
			else
			{
				commessa.setANAGEN_INDR_INDIRIZZO("");
			}
		
			commessa.setINDIRIZZO_PRINCIPALE(rs.getString(13)+" - "+rs.getString(14)+" ("+rs.getString(15)+")");
			commessa.setNOTE_GEN(rs.getString(16));
			commessa.setN_ORDINE(rs.getString(17));

			commessa.setID_ANAGEN_UTIL(rs.getInt(18));
			commessa.setK2_ANAGEN_INDR_UTIL(rs.getInt(19));
			commessa.setNOME_UTILIZZATORE(rs.getString(20));
		
			String sede_util=rs.getString(25);
			String prov="";
			if (sede_util!=null)
			{
				prov=rs.getString(27);
				commessa.setINDIRIZZO_UTILIZZATORE(sede_util+" - "+rs.getString(26)+" ("+prov+")");
				
			}
			else
			{
				prov=rs.getString(23);
				commessa.setINDIRIZZO_UTILIZZATORE(rs.getString(21)+" - "+rs.getString(22)+" ("+prov+")");
			}
			commessa.setCOD_PROV(prov);
			
			listaCommesse.add(commessa);
			
		}
		
		}catch (Exception e) 
		{
		throw e;
		}
		if(con!=null) {
			con.close();
		}
		return listaCommesse;
	}

//	public static CommessaDTO getCommessaById(String idCommessa) throws Exception {
//		Connection con=null;
//		PreparedStatement pst=null;
//		PreparedStatement pstA=null;
//		ResultSet rs=null;
//		ResultSet rsA=null;
//		
//		CommessaDTO commessa=null;
//		try{
//			con =ManagerSQLServer.getConnectionSQL();
//			pst=con.prepareStatement(querySqlServerComId);
//			pst.setString(1, idCommessa);
//			
//			rs=pst.executeQuery();
//		
//			while(rs.next()){
//				commessa= new CommessaDTO();
//
//				commessa.setID_COMMESSA(idCommessa);
//				commessa.setDT_COMMESSA(rs.getDate(2));
//				commessa.setFIR_CHIUSURA_DT(rs.getDate(3));
//				commessa.setID_ANAGEN(rs.getInt(4));
//				commessa.setID_ANAGEN_NOME(rs.getString(5));
//				commessa.setDESCR(rs.getString(6));
//				commessa.setSYS_STATO(rs.getString(7));
//				commessa.setK2_ANAGEN_INDR(rs.getInt(8));
//				commessa.setANAGEN_INDR_DESCR("");
//				String indirizzoSede=rs.getString(10);
//				if (indirizzoSede!=null){
//					commessa.setANAGEN_INDR_INDIRIZZO(indirizzoSede+" - "+rs.getString(11)+" ("+rs.getString(12)+")");
//					commessa.setCOD_PROV(rs.getString(12));					
//				}else{
//					commessa.setANAGEN_INDR_INDIRIZZO("");
//					commessa.setCOD_PROV(rs.getString(15));					
//				}
//			
//				commessa.setINDIRIZZO_PRINCIPALE(rs.getString(13)+" - "+rs.getString(14)+" ("+rs.getString(15)+")");
//				commessa.setNOTE_GEN(rs.getString(16));
//				commessa.setN_ORDINE(rs.getString(17));
//				commessa.setID_ANAGEN_COMM(rs.getInt(18));
//			
//				pstA=con.prepareStatement(querySqlAttivitaCom);
//				pstA.setString(1,idCommessa);
//				rsA=pstA.executeQuery();
//
//				while(rsA.next()){
//					AttivitaMilestoneDTO attivita = new AttivitaMilestoneDTO();
//					attivita.setId_riga(rsA.getInt("RIGA"));
//					attivita.setDescrizioneAttivita(rsA.getString("DESC_ATT"));
//					attivita.setNoteAttivita(rsA.getString("NOTE_ATT"));
//					attivita.setDescrizioneArticolo(rsA.getString("DESC_ART"));
//					attivita.setQuantita(rsA.getString("QUANTITA"));
//					attivita.setCodiceArticolo(rsA.getString("CODICEARTICOLO"));
//				
//					String codAggreg=rsA.getString("CODAGG");
//				
//					if(codAggreg!=null){
//						ArrayList<AttivitaMilestoneDTO> listaAttivitaAggregate=getListaAttivitaAggregate(con,codAggreg,attivita);
//					
//						for(AttivitaMilestoneDTO attivitaAggragata: listaAttivitaAggregate){
//							commessa.getListaAttivita().add(attivitaAggragata);
//						}
//					}else{
//						attivita.setCodiceAggregatore("CAMPIONAMENTO_"+attivita.getId_riga());
//						commessa.getListaAttivita().add(attivita);
//					}
//				}
//				rsA.close();
//				pstA.close();			
//			}
//		
//		}catch (Exception e) {
//			throw e;
//		}
//		return commessa;
//	}
	
	public static CommessaDTO getCommessaById(String idCommessa) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		PreparedStatement pstA=null;
		ResultSet rs=null;
		ResultSet rsA=null;
		
		CommessaDTO commessa=null;
		try
		{
		con =ManagerSQLServer.getConnectionSQL();
		
		
			pst=con.prepareStatement(querySqlServerComId);
	
		
		pst.setString(1, idCommessa);
		
		
	
		
		rs=pst.executeQuery();
		
		
		while(rs.next())
		{
			commessa= new CommessaDTO();

			commessa.setID_COMMESSA(idCommessa);
			commessa.setDT_COMMESSA(rs.getDate(2));
			commessa.setFIR_CHIUSURA_DT(rs.getDate(3));
			commessa.setID_ANAGEN(rs.getInt(4));
			commessa.setID_ANAGEN_NOME(rs.getString(5));
			commessa.setDESCR(rs.getString(6));
			commessa.setSYS_STATO(rs.getString(7));
			commessa.setK2_ANAGEN_INDR(rs.getInt(8));
			commessa.setANAGEN_INDR_DESCR("");
			String indirizzoSede=rs.getString(10);
			
			String cap_cliente = rs.getString(29);
			String cap_sede = rs.getString(30);
			String cap_cliente_util = rs.getString(32);
			String cap_sede_util = rs.getString(31);
			
			if (indirizzoSede!=null)
			{
				
				commessa.setANAGEN_INDR_INDIRIZZO(indirizzoSede + " - "+ cap_sede + " - " + rs.getString(11)+" ("+rs.getString(12)+")");
			}
			else
			{
				commessa.setANAGEN_INDR_INDIRIZZO("");
			}
			
			commessa.setINDIRIZZO_PRINCIPALE(rs.getString(13) + " - "+ cap_cliente +" - "+rs.getString(14)+" ("+rs.getString(15)+")");
			
			commessa.setNOTE_GEN(rs.getString(16));
			commessa.setN_ORDINE(rs.getString(17));
			
			commessa.setID_ANAGEN_UTIL(rs.getInt(19));
			commessa.setK2_ANAGEN_INDR_UTIL(rs.getInt(20));
			commessa.setNOME_UTILIZZATORE(rs.getString(21));
			
			String sede_util=rs.getString(26);
			String prov="";
			if (sede_util!=null)
			{
				prov=rs.getString(28);
				commessa.setINDIRIZZO_UTILIZZATORE(sede_util+ " - "+ cap_sede_util + " - "+rs.getString(27)+" ("+prov+")");
				
			}
			else
			{
				prov=rs.getString(24);
				commessa.setINDIRIZZO_UTILIZZATORE(rs.getString(22)+  " - "+ cap_cliente_util + " - "+rs.getString(23)+" ("+prov+")");

			}
			commessa.setCOD_PROV(prov);
			
			commessa.setID_ANAGEN_COMM(rs.getInt(18));
			

						
			pstA=con.prepareStatement(querySqlAttivitaCom);
			pstA.setString(1,idCommessa);
			rsA=pstA.executeQuery();

			while(rsA.next())
			{
				AttivitaMilestoneDTO attivita = new AttivitaMilestoneDTO();
				attivita.setId_riga(rsA.getInt("RIGA"));
				attivita.setDescrizioneAttivita(rsA.getString("DESC_ATT"));
				attivita.setNoteAttivita(rsA.getString("NOTE_ATT"));
				attivita.setDescrizioneArticolo(rsA.getString("DESC_ART"));
				attivita.setQuantita(rsA.getString("QUANTITA"));
				attivita.setCodiceArticolo(rsA.getString("CODICEARTICOLO"));
				attivita.setOreUomo(rsA.getDouble("ORE_UOMO"));
				String codAggreg=rsA.getString("CODAGG");
				
				if(codAggreg!=null)
				{
					ArrayList<AttivitaMilestoneDTO> listaAttivitaAggregate=getListaAttivitaAggregate(con,codAggreg,attivita);
					
					for(AttivitaMilestoneDTO attivitaAggragata: listaAttivitaAggregate)
					{
						commessa.getListaAttivita().add(attivitaAggragata);
					}
				}
				else
				{
					attivita.setCodiceAggregatore("CAMPIONAMENTO_"+attivita.getId_riga());
					commessa.getListaAttivita().add(attivita);
				}
		
				
				
				
			}
			rsA.close();
			pstA.close();
			
			
		}
		
		}catch (Exception e) 
		{
		throw e;
		}
		if(con!=null) {
			con.close();
		}
		return commessa;
	}

	private static ArrayList<AttivitaMilestoneDTO> getListaAttivitaAggregate(Connection con, String codAggreg, AttivitaMilestoneDTO attivita) throws Exception {
		
		ArrayList<AttivitaMilestoneDTO> listaAttivita= new ArrayList<AttivitaMilestoneDTO>();
		
		try {
			String[] listaArticoli=codAggreg.split(",");
		
			for (int i = 0; i < listaArticoli.length; i++) {
				
				PreparedStatement pst= con.prepareStatement(queryArticoli);
				pst.setString(1, listaArticoli[i]);
				
				ResultSet rs =pst.executeQuery();
				
				AttivitaMilestoneDTO att=null;
				
				while(rs.next()){
					att= new AttivitaMilestoneDTO();
					att.setId_riga(attivita.getId_riga());
					att.setDescrizioneAttivita(attivita.getDescrizioneAttivita());
					att.setNoteAttivita("");
					att.setDescrizioneArticolo(rs.getString("DESCR"));
					att.setQuantita("1");
					att.setCodiceArticolo(rs.getString("ID_ANAART"));
					att.setCodiceAggregatore("CAMPIONAMENTO_"+attivita.getId_riga());
				    
				    listaAttivita.add(att);
				}				
			}
		} catch (Exception e) {
			throw e;
		}
		return listaAttivita;
	}
	
	public static Map<String,String> getMappaClienti() throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		PreparedStatement pstA=null;
		ResultSet rs=null;
		ResultSet rsA=null;
		
		Map<String,String> mappaClienti =  new HashMap<String,String>();
		
		try{
			con =ManagerSQLServer.getConnectionSQL();
		
			pst=con.prepareStatement(queryClienti);
						
			rs=pst.executeQuery();
					
			while(rs.next()){
				mappaClienti.put(rs.getString(1), rs.getString(2));			
			}
		
		}catch (Exception e) {
			throw e;
		}
		if(con!=null) {
			con.close();
		}
		return mappaClienti;
	}

	public static ArrayList<CommessaDTO> getListaCommessePerData(CompanyDTO company, UtenteDTO user, String dateFrom, String dateTo) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		PreparedStatement pstA=null;
		ResultSet rs=null;
		ResultSet rsA=null;
		
		ArrayList<CommessaDTO> listaCommesse = new ArrayList<>();
		
		try
		{
		
		con =ManagerSQLServer.getConnectionSQL();

				
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
		java.sql.Date from = new java.sql.Date(df.parse(dateFrom).getTime());
		java.sql.Date to = new java.sql.Date(df.parse(dateTo).getTime());
		
		if(user.isTras())
		{
			
		
			pst=con.prepareStatement(querySqlServerComTrasDate);
			pst.setDate(1, from);
			pst.setDate(2, to);
				
		}
		
		else
		{
			
			pst=con.prepareStatement(querySqlServerCommonDate);
			pst.setInt(1, company.getId());
			pst.setDate(2, from);
			pst.setDate(3, to);
			
		}

		rs=pst.executeQuery();
		
		CommessaDTO commessa=null;
		while(rs.next())
		{
			commessa= new CommessaDTO();
			String idCommessa=rs.getString(1);
			commessa.setID_COMMESSA(idCommessa);
			commessa.setDT_COMMESSA(rs.getDate(2));
			commessa.setFIR_CHIUSURA_DT(rs.getDate(3));
			commessa.setID_ANAGEN(rs.getInt(4));
			commessa.setID_ANAGEN_NOME(rs.getString(5));
			commessa.setDESCR(rs.getString(6));
			commessa.setID_ANAGEN_COMM(company.getId());
			commessa.setSYS_STATO(rs.getString(7));
			commessa.setK2_ANAGEN_INDR(rs.getInt(8));
			commessa.setANAGEN_INDR_DESCR(null);
			String indirizzoSede=rs.getString(10);
			
			if (indirizzoSede!=null)
			{
				commessa.setANAGEN_INDR_INDIRIZZO(indirizzoSede+" - "+rs.getString(11)+" ("+rs.getString(12)+")");
			}
			else
			{
				commessa.setANAGEN_INDR_INDIRIZZO("");
			}
		
			commessa.setINDIRIZZO_PRINCIPALE(rs.getString(13)+" - "+rs.getString(14)+" ("+rs.getString(15)+")");
			commessa.setNOTE_GEN(rs.getString(16));
			commessa.setN_ORDINE(rs.getString(17));

			commessa.setID_ANAGEN_UTIL(rs.getInt(18));
			commessa.setK2_ANAGEN_INDR_UTIL(rs.getInt(19));
			commessa.setNOME_UTILIZZATORE(rs.getString(20));
		
			String sede_util=rs.getString(25);
			String prov="";
			if (sede_util!=null)
			{
				prov=rs.getString(27);
				commessa.setINDIRIZZO_UTILIZZATORE(sede_util+" - "+rs.getString(26)+" ("+prov+")");
				
			}
			else
			{
				prov=rs.getString(23);
				commessa.setINDIRIZZO_UTILIZZATORE(rs.getString(21)+" - "+rs.getString(22)+" ("+prov+")");
			}
			commessa.setCOD_PROV(prov);
			
			listaCommesse.add(commessa);
			
		}
		
		}catch (Exception e) 
		{
		throw e;
		}
		if(con!=null) {
			con.close();
		}
		return listaCommesse;
	}
	
	
	
	public static String getTariffeFromArticolo(String articolo, String commessa) throws Exception {
		
		Connection con=null;
		
		String result="";
		try {
			
			con =ManagerSQLServer.getConnectionSQL();
				
				PreparedStatement pst= con.prepareStatement(queryTariffe);
				pst.setString(1, articolo);
				pst.setString(2, articolo);
				pst.setString(3, commessa);
				pst.setString(4, articolo);
				
				ResultSet rs =pst.executeQuery();
								
				while(rs.next()){
					result = rs.getString(1)+";"+rs.getString(2);
					
				}				
			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
		
}