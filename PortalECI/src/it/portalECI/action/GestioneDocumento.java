package it.portalECI.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Session;

import it.portalECI.DAO.GestioneDocumentoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.Util.Costanti;

@WebServlet(name = "gestioneDocumento", urlPatterns = { "/gestioneDocumento.do" })
public class GestioneDocumento extends HttpServlet {
	
    public GestioneDocumento() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session session=SessionFacotryDAO.get().openSession();
		
		String idDocumento=request.getParameter("idDocumento");
System.out.println(idDocumento);
		DocumentoDTO documento = GestioneDocumentoDAO.getDocumento(idDocumento, session);
		File fileDocument = null;
		
		if(documento != null) {
System.out.println("Il documento non è nullo");

			fileDocument =  new File(Costanti.PATH_CERTIFICATI+documento.getFilePath());
System.out.println(Costanti.PATH_CERTIFICATI+documento.getFilePath());		
		}
System.out.println("fileDocument esiste: "+ fileDocument.exists());


		if(fileDocument == null || !fileDocument.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			session.close();
			return;
		}
	    
		response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename=\""+fileDocument.getName()+"\"");

	    OutputStream output = response.getOutputStream();
	    output.write(Files.readAllBytes(fileDocument.toPath()));
	    output.close();
	}
}
