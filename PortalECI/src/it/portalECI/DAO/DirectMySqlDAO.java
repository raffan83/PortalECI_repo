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
	
	
	
	
	
	
	public static Connection getConnection()throws Exception {
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(Costanti.CON_STR_MYSQL);
		}
		catch(Exception e)
		{
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
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:;DRIVER=Microsoft Access Driver (*.mdb);DBQ="+path);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return con;
	}


	public static String getPassword(String pwd) throws Exception
	{
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
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
			
		}finally
		{
			pst.close();
			con.close();
		}

		return toReturn;
	}

	
	
	

private static String replace(String string) {
	
	if(string!=null)
	{
		string=string.replace(";"," ");
	}
	return string;
}




	
}
