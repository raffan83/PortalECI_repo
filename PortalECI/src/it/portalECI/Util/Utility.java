package it.portalECI.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class Utility extends HttpServlet {

	public static String getNomeFile(String memo) {

		if(memo!=null){
			int indexStart=memo.indexOf("<name>");
			int indexEnd=memo.indexOf("</name>");

			memo=memo.substring(indexStart+6,indexEnd);
			return memo;
		}else{
			return "";
		}
	}

	public static ArrayList<Integer> getYearList() {
		
		ArrayList<Integer> yearList=new ArrayList<Integer>();
		
		yearList.add(2015);
		yearList.add(2016);
		yearList.add(2017);
		yearList.add(2018);
		yearList.add(2019);
		
		return yearList;
	}
	public static  void copiaFile(String origine, String destinazione)throws Exception{
		FileInputStream fis = new FileInputStream(origine);
		FileOutputStream fos = new FileOutputStream(destinazione);

		byte [] dati = new byte[fis.available()];
		fis.read(dati);
		fos.write(dati);

		fis.close();
		fos.close();
	}
	
	public static  void overwriteFile(String origine, String destinazione)throws Exception{
		FileInputStream fis = new FileInputStream(origine);
		FileOutputStream fos = new FileOutputStream(destinazione,false);

		byte [] dati = new byte[fis.available()];
		fis.read(dati);
		fos.write(dati);

		fis.close();
		fos.close();
	}
	
	
	public static boolean checkSession(HttpSession session, String att) {

		if(session.getAttribute(att)!=null){
			return true;
		}
		return false;
	}

	public static boolean validateSession(HttpServletRequest request,HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		
		if (request.getSession().getAttribute("userObj")==null ) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			
			String heardeName = request.getHeader("x-requested-with");
			if(null == heardeName){
				RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/page/sessionDown.jsp");
				dispatcher.forward(request,response);
			}else {
				ServletOutputStream out = response.getOutputStream();
				out.print("NON AUTORIZZATO");
				out.flush();
			}
			return true;
		}
		return false;
	}
	
	public static String checkStringNull(String value) {
		
		if (value==null) {
			
			return "-";
		}
		return value;
	}
	
	public static String checkFloatNull(Float value) {
		
		if (value==null) {			
			return "-";
		}
		return value.toString();
	}
	

	public static String checkIntegerNull(Integer value) {
		
		if (value==null) {			
			return "-";
		}
		return value.toString();
	}
	
	public static String checkDateNull(Date value) {		
		if (value==null) {			
			return "-";
		}
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		
		return sdf.format(value);
	}

	public static String getVarchar(String string) {
		
		if(string==null){
			return "";
		}else{
			string=string.replaceAll("\"", "");
			string=string.replaceAll("\'", "");
		}
		return string;
	}	
	
	public static Date getActualDateSQL(){
		java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
		
		return date;
	}
	
}
