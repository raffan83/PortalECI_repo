package it.portalECI.DAO;


import java.sql.Connection;
import java.sql.DriverManager;

import it.portalECI.Util.Costanti;

public class ManagerSQLServer {

	public static Connection getConnectionSQL()throws Exception
	{
		Connection con=null;
		
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(Costanti.CON_STR_SQLSRV,Costanti.USR_SQL_SVR,Costanti.USR_PASS_SVR);
			
			
		}
		catch(Exception e)
		{
			throw e;
		}
		return con;
		
		
	}
}
