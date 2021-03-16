package it.arubapec.arubasignservice;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.activation.DataHandler;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;

import com.google.gson.JsonObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Auth;
import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.PdfProfile;
import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.PdfSignApparence;
import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.PdfsignatureV2;
import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.PdfsignatureV2E;
import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.PdfsignatureV2ResponseE;
import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Pkcs7SignV2;
import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Pkcs7SignV2E;
import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Pkcs7SignV2ResponseE;
import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.SignRequestV2;
import it.arubapec.arubasignservice.ArubaSignServiceServiceStub.TypeTransport;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.bo.GestioneUtenteBO;

public class ArubaSignService {

	public static JsonObject sign(String utente, DocumentoDTO documento, String controfirma) throws TypeOfTransportNotImplementedException, IOException {
	
		 
		ArubaSignServiceServiceStub stub = new ArubaSignServiceServiceStub();
		
		
		Pkcs7SignV2E request= new Pkcs7SignV2E();
		Pkcs7SignV2 pkcs = new Pkcs7SignV2();
		
		SignRequestV2  sign = new SignRequestV2();
		
		sign.setCertID("AS0");
		
		Auth identity = new Auth();
		identity.setDelegated_domain("faSTI");
		identity.setTypeOtpAuth("faSTI");
		identity.setOtpPwd("dsign");
		identity.setTypeOtpAuth("faSTI");
		
		identity.setUser(utente);
		
		identity.setDelegated_user("admin.firma");
		identity.setDelegated_password("uBFqc8YYslTG");
		
		sign.setIdentity(identity);
		//${certificato.nomeCertificato}&pack=${certificato.misura.intervento.nomePack}
		
		String nomeCert = "";
		
		if(controfirma!=null && controfirma.equals("1")) {
			nomeCert = Costanti.PATH_CERTIFICATI+documento.getFilePath()+".p7m";
		}else {
			nomeCert = Costanti.PATH_CERTIFICATI+documento.getFilePath();
		}
		
		File f = new File(nomeCert);

 		URI uri = f.toURI();
		
		javax.activation.DataHandler dh = new DataHandler(uri.toURL());
		
		sign.setBinaryinput(dh);
	//	sign.setSrcName("C:\\Users\\raffaele.fantini\\Desktop\\FirmDigitale\\Test\\test_firm.pdf");
		
	//	sign.setDstName("C:\\Users\\raffaele.fantini\\Desktop\\FirmDigitale\\Test\\TestFirm_firmato.pdf.p7m");
		
		
		sign.setTransport(TypeTransport.BYNARYNET);
		
		pkcs.setSignRequestV2(sign);
		
		request.setPkcs7SignV2(pkcs);
		
		Pkcs7SignV2ResponseE response= stub.pkcs7SignV2(request);
		JsonObject jsonObj = new JsonObject();
		

	
//	System.out.println("Status :"+response.getPkcs7SignV2Response().get_return().getStatus());
//	System.out.println("Status :"+response.getPkcs7SignV2Response().get_return().getDescription());
//	System.out.println("Status :"+response.getPkcs7SignV2Response().get_return().getReturn_code());
	
		if( response.getPkcs7SignV2Response().get_return().getStatus().equals("KO")) {
			jsonObj.addProperty("success", false);
			jsonObj.addProperty("messaggio", response.getPkcs7SignV2Response().get_return().getDescription());
			jsonObj.addProperty("errorType", "sign");
		}else {
			
			jsonObj.addProperty("success", true);
			
			DataHandler fileReturn=response.getPkcs7SignV2Response().get_return().getBinaryoutput();
			File targetFile = new File(nomeCert+".p7m");
			FileUtils.copyInputStreamToFile(fileReturn.getInputStream(), targetFile);

			jsonObj.addProperty("messaggio", "Verbale firmato con successo!");
		}
		return jsonObj;
		 
	}


	public static JsonObject signDocumentoPades(UtenteDTO utente, String filename,DocumentoDTO documento, String controfirma) throws TypeOfTransportNotImplementedException, IOException {
		

		ArubaSignServiceServiceStub stub = new ArubaSignServiceServiceStub();
		
		
		PdfsignatureV2E request= new PdfsignatureV2E();
		PdfsignatureV2 pkcs = new PdfsignatureV2();
		
		SignRequestV2  sign = new SignRequestV2();
		
		
		sign.setCertID("AS0");
		
		Auth identity = new Auth();
		identity.setDelegated_domain("faSTI");
		identity.setTypeOtpAuth("faSTI");
		identity.setOtpPwd("dsign");
		identity.setTypeOtpAuth("faSTI");
		
		identity.setUser(utente.getId_firma());
		
		identity.setDelegated_user("admin.firma");
		identity.setDelegated_password("uBFqc8YYslTG");
		
		sign.setIdentity(identity);
		
		String fileNoExt = documento.getFilePath().substring(0, documento.getFilePath().length()-4);
		
		String path = Costanti.PATH_CERTIFICATI+documento.getFilePath();
		
		String keyWord = "Verificatore";
		
		if(controfirma!=null && controfirma.equals("1")) {
			keyWord = "Riesaminato";
			path = Costanti.PATH_CERTIFICATI +fileNoExt+"_F.pdf";
		}
		
		File f = new File(path);

		URI uri = f.toURI();
		

        PdfReader reader = new PdfReader(path);

		javax.activation.DataHandler dh = new DataHandler(uri.toURL());
		
		sign.setBinaryinput(dh);

		sign.setTransport(TypeTransport.BYNARYNET);
		

		pkcs.setSignRequestV2(sign);
		pkcs.setPdfprofile(PdfProfile.BASIC);
		PdfSignApparence apparence = new PdfSignApparence();
		apparence.setPage( reader.getNumberOfPages());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		apparence.setTesto("Firmato digitalmente da: "+utente.getNominativo()+"\nData: "+sdf.format(new Date()));

		
		Integer[] fontPosition = getFontPosition(path, keyWord, reader.getNumberOfPages());
		
		if(fontPosition[0] == null && fontPosition[1] == null) {
			fontPosition = getFontPosition(path, keyWord, (reader.getNumberOfPages()-1));
			apparence.setPage((reader.getNumberOfPages()-1));
		}
		
        System.out.println(Arrays.toString(fontPosition));

        JsonObject jsonObj = new JsonObject();
        
        if(fontPosition[0]!= null && fontPosition[1]!=null) {

        	if(controfirma!=null && controfirma.equals("1")) {
            	apparence.setLeftx(fontPosition[0] - 65);        	
        		apparence.setLefty(fontPosition[1] - 60);
        		apparence.setRightx(fontPosition[0] + 145);
        		apparence.setRighty(fontPosition[1] -20);
            }else {
            	apparence.setLeftx(fontPosition[0] - 65);
        		apparence.setLefty(fontPosition[1] - 60);
        		apparence.setRightx(fontPosition[0] + 165);
        		apparence.setRighty(fontPosition[1] -20);
            }
    		
    		
        }else {
        	
        	keyWord = "Il verificatore";
        	
        	fontPosition =  getFontPosition(path, keyWord, reader.getNumberOfPages());
        	apparence.setPage(reader.getNumberOfPages());
        	
        	if(fontPosition[0] == null && fontPosition[1] == null) {
        		fontPosition = getFontPosition(path, keyWord, (reader.getNumberOfPages()-1));
        		apparence.setPage((reader.getNumberOfPages()-1));
    		}
            System.out.println(Arrays.toString(fontPosition));
        	
 
            if(controfirma!=null && controfirma.equals("1")) {
            	apparence.setLeftx(fontPosition[0] - 65);     
        		apparence.setLefty(fontPosition[1] - 60);
        		apparence.setRightx(fontPosition[0] + 145);
        		apparence.setRighty(fontPosition[1] -20);
            }else {
            	apparence.setLeftx(fontPosition[0] - 65);
        		apparence.setLefty(fontPosition[1] - 60);
        		apparence.setRightx(fontPosition[0] + 165);
        		apparence.setRighty(fontPosition[1] -20);
            }
        }
        
        if(apparence.getLeftx()!= 0 && apparence.getLefty() != 0) {
        	
        	 pkcs.setApparence(apparence);
         	request.setPdfsignatureV2(pkcs);
         		
         		
         		PdfsignatureV2ResponseE response= stub.pdfsignatureV2(request);
         		
         		if( response.getPdfsignatureV2Response().get_return().getStatus().equals("KO")) {
         			jsonObj.addProperty("success", false);
         			jsonObj.addProperty("messaggio", response.getPdfsignatureV2Response().get_return().getDescription());
         		}else {
         			
         			jsonObj.addProperty("success", true);
         			
         			DataHandler fileReturn=response.getPdfsignatureV2Response().get_return().getBinaryoutput();
         			//File targetFile = new File(Costanti.PATH_FIRMA_DIGITALE+ fileNoExt+".pdf");
         			File targetFile;
         			if(controfirma!=null && controfirma.equals("1")) {
         				targetFile=  new File(Costanti.PATH_CERTIFICATI+fileNoExt+"_CF.pdf");
         			}else {
         				targetFile=  new File(Costanti.PATH_CERTIFICATI+fileNoExt+"_F.pdf");	
         			}
         			
         			FileUtils.copyInputStreamToFile(fileReturn.getInputStream(), targetFile);

         			jsonObj.addProperty("messaggio", "Documento firmato");
         		}
        	
        }else {
        	jsonObj.addProperty("success", false);
        	jsonObj.addProperty("messaggio", "Impossibile trovare posizione firma!");
        }
       
        		
	//	f.delete();
		reader.close();
		return jsonObj;
		
		
		 
	}

	
	
	 private static Integer[] getFontPosition(String filePath, final String keyWord, Integer pageNum) throws IOException {
	        final Integer[] result = new Integer[2];
	        PdfReader pdfReader = new PdfReader(filePath);
	        if (pageNum == null) {
	            pageNum = pdfReader.getNumberOfPages();
	        }
	        new PdfReaderContentParser(pdfReader).processContent(pageNum, new RenderListener() {
	            public void beginTextBlock() {
	 
	            }
	 
	            public void renderText(TextRenderInfo textRenderInfo) {
	            	
	                String text = textRenderInfo.getText();
	              //  System.out.println("text is ：" + text);
	                if (text != null && text.contains(keyWord)) {
	                                         // The abscissa and ordinate of the text in the page
	                    com.itextpdf.awt.geom.Rectangle2D.Float textFloat = textRenderInfo.getBaseline().getBoundingRectange();
	                    float x = textFloat.x;
	                    float y = textFloat.y;
	                    result[0] = (int) x;
	                    result[1] = (int) y;
	                     //                    System.out.println(String.format("The signature text field absolute position is x:%s, y:%s", x, y));
	                }
	            }
	 
	            public void endTextBlock() {
	 
	            }
	 
	            public void renderImage(ImageRenderInfo renderInfo) {
	 
	            }
	        });
	        return result;
	    }

	 
//	 public static void main(String[] args) throws Exception {
//			Session session = SessionFacotryDAO.get().openSession();
//			session.beginTransaction();
//		 UtenteDTO utente = GestioneUtenteBO.getUtenteById("4", session);
//		 session.close();
//		 signDocumentoPadesTest(utente);
//	}

	 
	 
//	 public static JsonObject signDocumentoPadesTest(UtenteDTO utente) throws TypeOfTransportNotImplementedException, IOException {
//			
//
//			ArubaSignServiceServiceStub stub = new ArubaSignServiceServiceStub();
//			
//			
//			PdfsignatureV2E request= new PdfsignatureV2E();
//			PdfsignatureV2 pkcs = new PdfsignatureV2();
//			
//			SignRequestV2  sign = new SignRequestV2();
//			
//			
//			sign.setCertID("AS0");
//			
//			Auth identity = new Auth();
//			identity.setDelegated_domain("faSTI");
//			identity.setTypeOtpAuth("faSTI");
//			identity.setOtpPwd("dsign");
//			identity.setTypeOtpAuth("faSTI");
//			
//			identity.setUser(utente.getId_firma());
//			
//			identity.setDelegated_user("admin.firma");
//			identity.setDelegated_password("uBFqc8YYslTG");
//			
//			sign.setIdentity(identity);
//			
//			//String fileNoExt = ""
//			
//			String path = "C:\\Users\\antonio.dicivita\\Desktop\\Esempio firma.pdf";
//			
//			String keyWord = "Verificatore";
//
//			
//			File f = new File(path);
//
//			URI uri = f.toURI();
//			
//
//	        PdfReader reader = new PdfReader(path);
//
//			javax.activation.DataHandler dh = new DataHandler(uri.toURL());
//			
//			sign.setBinaryinput(dh);
//
//			sign.setTransport(TypeTransport.BYNARYNET);
//			
//
//			pkcs.setSignRequestV2(sign);
//			pkcs.setPdfprofile(PdfProfile.BASIC);
//			PdfSignApparence apparence = new PdfSignApparence();
//			apparence.setPage( reader.getNumberOfPages());
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			apparence.setTesto("Firmato digitalmente da: "+utente.getNominativo()+"\nData: "+sdf.format(new Date()));
//
//			
//			Integer[] fontPosition = getFontPosition(path, keyWord, null);
//	        System.out.println(Arrays.toString(fontPosition));
//	        apparence.setLeftx(300);
//	        apparence.setLefty(100);
//
//	        JsonObject jsonObj = new JsonObject();
//	        
//
//	        
//	        if(apparence.getLeftx()!= 0 && apparence.getLefty() != 0) {
//	        	
//	        	 pkcs.setApparence(apparence);
//	         	request.setPdfsignatureV2(pkcs);
//	         		
//	         		
//	         		PdfsignatureV2ResponseE response= stub.pdfsignatureV2(request);
//	         		
//	         		if( response.getPdfsignatureV2Response().get_return().getStatus().equals("KO")) {
//	         			jsonObj.addProperty("success", false);
//	         			jsonObj.addProperty("messaggio", response.getPdfsignatureV2Response().get_return().getDescription());
//	         		}else {
//	         			
//	         			jsonObj.addProperty("success", true);
//	         			
//	         			DataHandler fileReturn=response.getPdfsignatureV2Response().get_return().getBinaryoutput();
//	         			//File targetFile = new File(Costanti.PATH_FIRMA_DIGITALE+ fileNoExt+".pdf");
//	         			File targetFile = 
//	         				new File("C:\\Users\\antonio.dicivita\\Desktop\\"+"_F.pdf");	
//	         			
//	         			
//	         			FileUtils.copyInputStreamToFile(fileReturn.getInputStream(), targetFile);
//
//	         			jsonObj.addProperty("messaggio", "Documento firmato");
//	         		}
//	        	
//	        }else {
//	        	jsonObj.addProperty("success", false);
//	        	jsonObj.addProperty("messaggio", "Impossibile trovare posizione firma!");
//	        }
//	       
//	        		
//		//	f.delete();
//			reader.close();
//			return jsonObj;
//			
//			
//			 
//		}
}

