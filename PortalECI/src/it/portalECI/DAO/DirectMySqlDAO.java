package it.portalECI.DAO;



import it.portalECI.Util.Costanti;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Properties;

import org.hibernate.Session;

public class DirectMySqlDAO {
	
	private static  final String getPassword="SELECT PASSWORD(?)";  
	
	private static final String query1 = "DELETE FROM domanda_verbale WHERE verbale_id=?";
	private static final String query2 = "DELETE FROM storico_modifiche WHERE  id_verbale=?";
	private static final String query3 = "DELETE FROM documento WHERE verbale_id=?;";	
	private static final String query4 = "DELETE FROM verbale WHERE id=?";
	private static final String query5 = "DELETE FROM intervento__categoriaverifica_tipoverifica WHERE intervento_id=?";
	private static final String query6 = "DELETE FROM intervento WHERE id=?";
	
	



	

	

	
		
	public static Connection getConnection()throws Exception {
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(Costanti.CON_STR_MYSQL);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return con;
	}

	public static Connection getConnectionLocal(String path)throws Exception {
		Connection con = null;
		
		Properties prop = System.getProperties();

		prop.put("jdbc.drivers","sun.jdbc.odbc.JdbcOdbcDriver");
		
		System.setProperties(prop); 
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:;DRIVER=Microsoft Access Driver (*.mdb);DBQ="+path);
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return con;
	}


	public static String getPassword(String pwd) throws Exception{
		String toReturn="";
		PreparedStatement pst=null;
		ResultSet rs= null;
		Connection con=null;
		try{
			con = getConnection();	
			pst=con.prepareStatement(getPassword);
			pst.setString(1,pwd);
			rs=pst.executeQuery();
			rs.next();
			toReturn=rs.getString(1);
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
			
		}finally{
			pst.close();
			con.close();
		}

		return toReturn;
	}


	private static String replace(String string) {
	
		if(string!=null){
			string=string.replace(";"," ");
		}
		return string;
	}

	public static void deleteVerbaliInterventi(int id_verbale, int id_intervento, boolean intervento) throws Exception {		
		
		
		PreparedStatement pst=null;
		PreparedStatement pst2=null;
		PreparedStatement pst3=null;
		PreparedStatement pst4=null;
		PreparedStatement pst5=null;
		PreparedStatement pst6=null;
	
		Connection con=null;
		try{
			con = getConnection();	
			pst=con.prepareStatement(query1);
			pst.setInt(1,id_verbale);
			pst.executeUpdate();
			
			pst2=con.prepareStatement(query2);
			pst2.setInt(1,id_verbale);
			pst2.executeUpdate();
			
			pst3=con.prepareStatement(query3);
			pst3.setInt(1,id_verbale);
			pst3.executeUpdate();
			
			pst4=con.prepareStatement(query4);
			pst4.setInt(1,id_verbale);
			pst4.executeUpdate();
			
			if(intervento) {
			pst5=con.prepareStatement(query5);
			pst5.setInt(1,id_intervento);
			pst5.executeUpdate();
			
			pst6=con.prepareStatement(query6);
			pst6.setInt(1,id_intervento);
			pst6.executeUpdate();
			
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
			
		}finally{
			pst.close();
			pst2.close();
			pst3.close();
			pst4.close();
			if(intervento) {
			pst5.close();
			pst6.close();
			}
			con.close();
		}

		
	}
	
	
	public static void aggiornaCampioniScadenza() throws Exception {

		String query = "update campione set stato_campione='F' where (campione.data_Scadenza<now() or campione.data_scadenza is null)";

		Connection con=null;
		PreparedStatement pst=null;

		con=getConnection();
		pst=con.prepareStatement(query);

		pst.executeUpdate();

		pst.close();
		con.close();
	}
		
	
}