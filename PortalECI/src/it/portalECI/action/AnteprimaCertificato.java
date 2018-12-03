package it.portalECI.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.bo.GestioneQuestionarioBO;
import it.portalECI.bo.GestioneVerbaleBO;

@WebServlet(name = "anteprimaCertificato", urlPatterns = { "/anteprimaCertificato.do" })
public class AnteprimaCertificato extends HttpServlet {
	
    public AnteprimaCertificato() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		Session session=SessionFacotryDAO.get().openSession();
		
		String idVerbale=request.getParameter("idVerbale");
		VerbaleDTO verbale = null;
		if(request.getParameter("idVerbale") != null && (String)request.getParameter("idVerbale")!="" ) {
			verbale = GestioneVerbaleBO.getVerbale(idVerbale, session);
		}
		
		QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(verbale.getQuestionarioID(),session);
		File certificatoAnt;
		try {
			certificatoAnt = GestioneVerbaleBO.getPDFVerbaleAnteprima(verbale, questionario, session);
				
		} catch (Exception e) {
			ServletContext sc = getServletContext();
			request.setAttribute("issue", "Si &egrave; generato un errore durante la generazione del documento!");
			sc.getRequestDispatcher("/page/downloadError.jsp").forward(request, response);
			e.printStackTrace();
			return;	
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename=\""+certificatoAnt.getName()+"\"");
	    OutputStream output = response.getOutputStream();
	    output.write(Files.readAllBytes(certificatoAnt.toPath()));
	    output.close();
		
	}
}