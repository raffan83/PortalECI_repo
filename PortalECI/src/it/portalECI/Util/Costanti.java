package it.portalECI.Util;

import java.io.File;
import java.util.Properties;

public class Costanti {



//public static final String CON_STR_MYSQL = "jdbc:mysql://158.58.172.111:3306/gtv_db?user=root&password=5243FWF5344R";
	public static final String CON_STR_MYSQL = "jdbc:mysql://89.46.227.105:3306/eci_db?user=root&password=5243FWF5344R";
//  IP MACROSOLUTION
//	public static final String CON_STR_MYSQL = "jdbc:mysql://212.237.23.231:3306/gtv_db?user=root&password=5243FWF5344R";
	
	
	//public static final String CON_STR_SQLSRV = "jdbc:sqlserver://158.58.172.111:1433;databaseName=BTOMEN_CRESCO_DATI";
	public static final String CON_STR_SQLSRV = "jdbc:sqlserver://10.10.42.11:1433;databaseName=BTOMEN_CRESCO_DATI";
	//IP MACROSOLUTION
//	public static final String CON_STR_SQLSRV = "jdbc:sqlserver://212.237.23.231:1433;databaseName=BTOMEN_CRESCO_DATI";
//	public static final String CON_STR_SQLSRV = "jdbc:sqlserver://localhost:1433;databaseName=BTOMEN_CRESCO_DATI";
	
    public static final String USR_SQL_SVR = "fantini";
	public static final String USR_PASS_SVR = "fantini";
	
	public static String PATH_ROOT = "C:/portalECI/";
	public static String PATH_CERTIFICATI ="C:/PortalECI/documenti/interventi/";
	public static String PATH_FONT_IMAGE ="C:/PortalECI/verbaliImage/fonts/";
	public static String PATH_FONT_STYLE ="C:/PortalECI/stile/";
	public static String PATH_FOOTER_IMAGE ="C:/PortalECI/footerImg/";
	public static String PATH_HEADER_IMAGE ="C:/PortalECI//headerImg/";
	
	//Usare il placehlder %s se si vuole inserire il numero del nuovo documento generato nella dicitura 
	public static String DOCUMENT_IS_INVALID_PHRASE = "Questo documento \u00E8 stato sostituito dal documento %s";
	
	//Usare il placehlder %s se si vuole inserire il numero del documento invalidato nella dicitura
	public static String DOCUMENT_INVALIDS_PHRASE = "Questo documento annulla e sostituisce il documento %s";
	
	public static String PASS_EMAIL;
	public static String PASS_PEC;
}
