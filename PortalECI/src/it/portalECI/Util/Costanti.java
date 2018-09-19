package it.portalECI.Util;

import java.io.File;
import java.util.Properties;

public class Costanti {



	public static final String CON_STR_MYSQL = "jdbc:mysql://158.58.172.111:3306/gtv_db?user=root&password=5243FWF5344R";
	//public static final String CON_STR_MYSQL = "jdbc:mysql://158.58.172.111:3306/gtv_db?user=root&password=root";
	//IP MACROSOLUTION
//	public static final String CON_STR_MYSQL = "jdbc:mysql://212.237.23.231:3306/gtv_db?user=root&password=root";
	
	
	public static final String CON_STR_SQLSRV = "jdbc:sqlserver://158.58.172.111:1433;databaseName=BTOMEN_CRESCO_DATI";
	//IP MACROSOLUTION
//	public static final String CON_STR_SQLSRV = "jdbc:sqlserver://212.237.23.231:1433;databaseName=BTOMEN_CRESCO_DATI";
//	public static final String CON_STR_SQLSRV = "jdbc:sqlserver://localhost:1433;databaseName=BTOMEN_CRESCO_DATI";
	
    public static final String USR_SQL_SVR = "fantini";
	public static final String USR_PASS_SVR = "fantini";
	
	public static String PATH_ROOT = "/";
	public static String PATH_CERTIFICATI = "/documenti/interventi/";
	public static String PATH_FONT_IMAGE = "/verbaliImage/fonts/";
	public static String PATH_FONT_STYLE = "/stile/";
	public static String PATH_HEADER_IMAGE = "/verbaliImage/header/";
	public static String PATH_FOOTER_IMAGE = "/verbaliImage/footer/";
	
	static {
		Properties properties = System.getProperties();
		PATH_ROOT = properties.getProperty("PATH_ROOT", PATH_ROOT);
		PATH_CERTIFICATI = properties.getProperty("PATH_CERTIFICATI", PATH_CERTIFICATI);
		PATH_FONT_IMAGE = properties.getProperty("PATH_FONT_IMAGE", PATH_FONT_IMAGE);
		PATH_FONT_STYLE = properties.getProperty("PATH_FONT_STYLE", PATH_FONT_STYLE);
		PATH_HEADER_IMAGE = properties.getProperty("PATH_HEADER_IMAGE", PATH_HEADER_IMAGE);
		File headerFolder = new File(PATH_HEADER_IMAGE);
		if(!headerFolder.exists()) headerFolder.mkdirs();
		PATH_FOOTER_IMAGE = properties.getProperty("PATH_FOOTER_IMAGE", PATH_FOOTER_IMAGE);
		File footerFolder = new File(PATH_FOOTER_IMAGE);
		if (!footerFolder.exists()) footerFolder.mkdirs();
	}
}
