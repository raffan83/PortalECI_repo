package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.itextpdf.text.DocumentException;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.TemplateQuestionarioDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneQuestionarioBO;
import it.portalECI.bo.GestioneVerbaleBO;

@WebServlet(name = "gestioneCertificato", urlPatterns = { "/gestioneCertificato.do" })
public class GestioneCertificato extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		
		Session session=SessionFacotryDAO.get().openSession();

		VerbaleDTO verbale = GestioneVerbaleBO.getVerbale(request.getParameter("idVerbale"),session);
		
		QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(verbale.getQuestionarioID(), session);
		
		TemplateQuestionarioDTO templateVerbale = questionario.getTemplateVerbale();
		
		File fileCertificato = null;
		try {
			fileCertificato = GestioneVerbaleBO.getPDFVerbale(verbale, questionario, session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileInputStream in = new FileInputStream(fileCertificato);
		byte[] buffer = new byte[4096];
		int length;
		OutputStream out = response.getOutputStream();
		while ((length = in.read(buffer)) > 0){
		    out.write(buffer, 0, length);
		}
		in.close();
		out.flush();
	}


}
