package it.portalECI.Util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import it.portalECI.DTO.VerbaleDTO;



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
		

		yearList.add(2017);
		yearList.add(2018);
		yearList.add(2019);
		yearList.add(2020);
		yearList.add(2021);
		yearList.add(2022);
		
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
	
	public static Date getMostRecentDate(ArrayList<Date> lista_date) {
		
		Date today = new Date();
		Date ret = null;
		long min = 0;
		if(lista_date.size()>0) {
		 min = Math.abs(today.getTime()-lista_date.get(0).getTime());
		 ret = lista_date.get(0);
		}
		for (int i = 1; i<lista_date.size();i++) {
			long diff =  Math.abs(today.getTime()-lista_date.get(i).getTime());
			
			if(diff<min) {
				min = diff;
				ret = lista_date.get(i);
			}
		}

		
		return ret;
		
		
	}
	
    public static String escapeHTML(String value) {
        return StringEscapeUtils.escapeHtml4(value);
    }
    
    public static String escapeJS(String value) {
        return StringEscapeUtils.escapeEcmaScript(StringEscapeUtils.escapeHtml4(value));
    }
    
    
    
    public static void createQR(int id_verbale, String path) throws Exception
	{
		byte[] bytesEncoded = Base64.encodeBase64((""+id_verbale).getBytes());
		System.out.println("encoded value is " + new String(bytesEncoded));		

		String myCodeText = "http://portale.ecisrl.it/gestioneInfoVerbale.do?id="+new String(bytesEncoded);

		String filePath =path+"\\qrcode.png";
		int size = 120;
		String fileType = "png";
		File myFile = new File(filePath);
		try {
			
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			
			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 0); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
 
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
					size, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
 
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);
 
			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, myFile);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
    
    
    
	public static Date getDataPvP(VerbaleDTO verbale) {
		
		Date data_pvp = null;
		
			if(verbale.getStato().getId()!=9 && verbale.getTipo_verifica_gvr()==0) {
				data_pvp = verbale.getData_prossima_verifica();
			}else {
				
				 if((verbale.getTipo_verifica() == 1 || verbale.getTipo_verifica() == 2) || verbale.getTipo_verifica_gvr()==1 || verbale.getTipo_verifica_gvr()==8 || verbale.getTipo_verifica_gvr()==9|| verbale.getTipo_verifica_gvr()==10 ) {
					 data_pvp = verbale.getData_prossima_verifica();
	    			 
	    		 }else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==2 ) {
	    			 data_pvp = verbale.getData_prossima_verifica_integrita();
	    			 
	    		 }else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==3 ) {
	    			 data_pvp = verbale.getData_prossima_verifica_interna();	    			 
	    			 
	    		 }else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==7){
	    			 data_pvp = getMinDate(verbale.getData_prossima_verifica(), verbale.getData_prossima_verifica_interna());
	    		 }
	    		 else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && (verbale.getTipo_verifica_gvr()==4 || verbale.getTipo_verifica_gvr()==11 || verbale.getTipo_verifica_gvr()==12 || verbale.getTipo_verifica_gvr()==13)){
	    			 data_pvp = getMinDate(verbale.getData_prossima_verifica(), verbale.getData_prossima_verifica_integrita());
	    		 }
	    		 else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==5){
	    			 data_pvp = getMinDate(getMinDate(verbale.getData_prossima_verifica(), verbale.getData_prossima_verifica_integrita()), verbale.getData_prossima_verifica_interna());
	    		 }
	    		 else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==6){
	    			 data_pvp = getMinDate(verbale.getData_prossima_verifica_integrita(), verbale.getData_prossima_verifica_interna());
	    		 }
			}
			
				
		return data_pvp;
	}
	
	
	
	
public static Date getMinDate(Date data1, Date data2) {
		
		Date min = null;
		
		long time1 = data1.getTime();
		long time2 = data2.getTime();
		
		if(time1<=time2) {
			min = data1;
		}else {
			min = data2;
		}
		
		return min;
	}
	
}
