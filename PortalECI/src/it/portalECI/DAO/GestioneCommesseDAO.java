package it.portalECI.DAO;

import it.portalECI.DTO.AttivitaMilestoneDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.UtenteDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GestioneCommesseDAO {

	private static final String querySqlServerComTras = "SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE " +
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN " +
			"WHERE ID_ANAGEN_COMM<>1703";

	private static final String queryArticoli = "SELECT * FROM BWT_ANAART WHERE ID_ANAART=?";

	private static String querySqlServerCommon="SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE " +
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN " +
			"WHERE ID_ANAGEN_COMM=?";
	
	private static String querySqlServerComId="SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE, ID_ANAGEN_COMM " +
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN " +
			"WHERE ID_COMMESSA=?";

	
	private static String querySqlAttivitaCom="SELECT a.descr as DESC_ATT,a.note AS NOTE_ATT,b.DESCR as DESC_ART,a.QTA AS QUANTITA ,a.K2_RIGA AS RIGA , a.ID_ANAART as CODICEARTICOLO, a.NOTE_AGGREG_COD as CODAGG " +
										"from BWT_COMMESSA_AVANZ AS a " +
										"Left join BWT_ANAART AS b ON a.ID_ANAART =b.ID_ANAART " +
										"where ID_COMMESSA=? AND TB_TIPO_MILE='MILE'";

	public static ArrayList<CommessaDTO> getListaCommesse(CompanyDTO company, String categoria, UtenteDTO user) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		PreparedStatement pstA=null;
		ResultSet rs=null;
		ResultSet rsA=null;
		
		ArrayList<CommessaDTO> listaCommesse = new ArrayList<>();
		
		try
		{
		
		con =ManagerSQLServer.getConnectionSQL();
		
		String categ="";
		
		if(!categoria.equals("")){
			
			
			String[] listaCategorie=categoria.split(";");

			for (int i = 0; i < listaCategorie.length; i++) {
				
				categ=categ+" AND TB_CATEG_COM='"+listaCategorie[i]+"'";
			}
			
		}
		
		if(user.isTras())
		{
			if(!categ.equals(""))
			{
				String query=querySqlServerComTras.concat(" WHERE ").concat(categ.substring(5,categ.length()));
				pst=con.prepareStatement(query);
			}
			else
			{
				pst=con.prepareStatement(querySqlServerComTras);
			}
		}
		else
		{
			pst=con.prepareStatement(querySqlServerCommon+categ);
			pst.setInt(1, company.getId());
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

			listaCommesse.add(commessa);
			
		}
		
		}catch (Exception e) 
		{
		throw e;
		}
		return listaCommesse;
	}

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
		return commessa;
	}

	private static ArrayList<AttivitaMilestoneDTO> getListaAttivitaAggregate(Connection con, String codAggreg, AttivitaMilestoneDTO attivita) throws Exception {
		
		ArrayList<AttivitaMilestoneDTO> listaAttivita= new ArrayList<AttivitaMilestoneDTO>();
		
		try 
		{
			String[] listaArticoli=codAggreg.split(",");
		
			for (int i = 0; i < listaArticoli.length; i++) {
				
				PreparedStatement pst= con.prepareStatement(queryArticoli);
				pst.setString(1, listaArticoli[i]);
				
				ResultSet rs =pst.executeQuery();
				
				AttivitaMilestoneDTO att=null;
				
				while(rs.next())
				{
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
			
		} 
		catch (Exception e) {
			throw e;
		}
		return listaAttivita;
	}

}
