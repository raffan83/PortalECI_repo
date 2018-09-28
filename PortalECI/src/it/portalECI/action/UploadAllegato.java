package it.portalECI.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneDocumentoDAO;
import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.bo.GestioneVerbaleBO;

@WebServlet(name = "allegatoUpload", urlPatterns = { "/allegatoUpload.do" })
@MultipartConfig
public class UploadAllegato extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JsonObject myObj = new JsonObject();
		PrintWriter  out = response.getWriter();
		myObj.addProperty("success", true);
		
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		try {
			VerbaleDTO verbale = GestioneVerbaleBO.getVerbale(request.getParameter("verbaleID"),session);
		
		    Part filePart = request.getPart("file");
		    String fileName = getUploadedFileName(filePart);
		    InputStream fileContent = filePart.getInputStream();
		    
			//File fileTarget = getFileTarget(fileName, verbale, session);
		    String path = getFileTarget(fileName, verbale, session);
			
			new File(Costanti.PATH_CERTIFICATI+path).mkdirs();
			File fileTarget = new File(Costanti.PATH_CERTIFICATI+path, fileName);
			
			if (!fileTarget.exists()) {
				
				DocumentoDTO allegato = new DocumentoDTO();
				allegato.setFilePath(path+fileName);
				allegato.setType(DocumentoDTO.ATTACHMENT);
				allegato.setVerbale(verbale);
		
		        GestioneDocumentoDAO.save(allegato, session);
		        verbale.getDocumentiVerbale().add(allegato);
		        GestioneVerbaleDAO.save(verbale, session);
		        
		        myObj.addProperty("idDocumento", allegato.getId());
			} else {
				myObj.addProperty("success", false);
				return;
			}

			FileUtils.copyInputStreamToFile(fileContent, fileTarget);
		
		    myObj.addProperty("fileName", fileName);
	
		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", false);
	    	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    	return;
			
		}finally {
			session.getTransaction().commit();
			session.close();
		}
		
		out.print(myObj);
		return;
		

	}
	
	private String getFileTarget(String fileName, VerbaleDTO verbale, Session session) {
		int idIntervento = verbale.getIntervento().getId();	
		String path = "Intervento_"+idIntervento+File.separator+verbale.getType()+"_"+verbale.getCodiceCategoria()+"_"+verbale.getId()+File.separator+"Allegati"+File.separator;

		
		return path;
	}
	
	private String getUploadedFileName(Part p) {
		String file = "", header = "Content-Disposition";
		String[] strArray = p.getHeader(header).split(";");
		for(String split : strArray) {
			if(split.trim().startsWith("filename")) {
				file = split.substring(split.indexOf('=') + 1);
				file = file.trim().replace("\"", "");
			}
		}
		return file;
	}

}