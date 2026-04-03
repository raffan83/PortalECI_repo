package it.portalECI.Util;

import java.io.File;
import java.util.Properties;

public class Costanti {

	public static String CON_STR_MYSQL;
	public static String CON_STR_MYSQL_PASS;
	public static String CON_STR_MYSQL_USR;
	public static String CON_STR_SQLSRV ;
	public static String USR_SQL_SVR ;
    public static String USR_PASS_SVR;
	public static String PATH_ROOT ;
	public static String PATH_CERTIFICATI ;
	public static String PATH_FONT_IMAGE ;
	public static String PATH_FONT_STYLE ;
	public static String PATH_FOOTER_IMAGE;
	public static String PATH_HEADER_IMAGE;
	public static String PASS_EMAIL;
	public static String PASS_PEC;
	
	
	//Usare il placehlder %s se si vuole inserire il numero del nuovo documento generato nella dicitura 
	public static String DOCUMENT_IS_INVALID_PHRASE = "Questo documento \u00E8 stato sostituito dal documento %s";
	
	//Usare il placehlder %s se si vuole inserire il numero del documento invalidato nella dicitura
	public static String DOCUMENT_INVALIDS_PHRASE = "Questo documento annulla e sostituisce il documento %s";
	
	
}
