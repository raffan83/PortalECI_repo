package it.portalECI.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.TemplateQuestionarioDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneQuestionarioBO;
import it.portalECI.bo.GestioneTemplateQuestionarioBO;


@WebServlet(name = "gestioneTemplateQuestionario", urlPatterns = { "/gestioneTemplateQuestionario.do" })
public class GestioneTemplateQuestionario extends HttpServlet {

	public GestioneTemplateQuestionario() {
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		
		Session session=SessionFacotryDAO.get().openSession();
		String idQuestionario = request.getParameter("idQuestionario");
		QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(idQuestionario, session);
		
		String idTemplate = request.getParameter("idTemplate");
		Integer idTemplateInt = 0;
		try {
			idTemplateInt = Integer.parseInt(idTemplate);
		}catch (NumberFormatException e) {
			System.out.println("formato errato per il parametro id questionario");
		}
		
		TemplateQuestionarioDTO template = null;
		if(idTemplateInt > 0) {
			template = GestioneTemplateQuestionarioBO.getQuestionarioById(idTemplateInt, session);
		}
				
		forwardResponse(request, response, questionario, template);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		if(request.getParameter("_method")!= null && request.getParameter("_method").equalsIgnoreCase("PUT")) {
			doPut(request,response);
			return;
		}
		

		Session session=SessionFacotryDAO.get().openSession();
		Transaction transaction = session.beginTransaction();
	
		String idQuestionario = request.getParameter("idQuestionario");
		QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(idQuestionario, session);
		
		TemplateQuestionarioDTO template = new  TemplateQuestionarioDTO();
		template.setTitolo(request.getParameter("titolo"));
		template.setTemplate(request.getParameter("template"));
		template.setHeader(request.getParameter("headerFileName"));
		template.setFooter(request.getParameter("footerFileName"));
		
		session.save(template);		
		if(request.getParameter("tipo").equals("Verbale")) {
			questionario.setTemplateVerbale(template);
		}else if(request.getParameter("tipo").equals("SchedaTecnica")) {
			questionario.setTemplateSchedaTecnica(template);
		}
		session.update(questionario);
		
		transaction.commit();
		forwardResponse(request, response, questionario, template);
	}
	
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(Utility.validateSession(request,response,getServletContext()))return;
		
		
		String idTemplate = request.getParameter("idTemplate");
		Integer idTemplateInt = 0;
		try {
			idTemplateInt = Integer.parseInt(idTemplate);
		}catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		Session session=SessionFacotryDAO.get().openSession();
		Transaction transaction = session.beginTransaction();
	
		String idQuestionario = request.getParameter("idQuestionario");
		QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(idQuestionario, session);
 
		TemplateQuestionarioDTO template = GestioneTemplateQuestionarioBO.getQuestionarioById(idTemplateInt, session);		
		template.setTitolo(request.getParameter("titolo"));
		
		//summernote aggiunge questa stringa a volte che rappresenta un ? e non potendo risolvere il problema del plugin abbiamo deciso di toglere questo carattere
		template.setTemplate(request.getParameter("template").replaceAll("&#65279;", ""));
		template.setHeader(request.getParameter("headerFileName"));
		template.setFooter(request.getParameter("footerFileName"));
		
		session.update(template);
		transaction.commit();
		session.close();
		forwardResponse(request, response, questionario, template);

	}
	
	private void forwardResponse(HttpServletRequest request, HttpServletResponse response,  QuestionarioDTO questionario, TemplateQuestionarioDTO template) throws ServletException, IOException {
		request.setAttribute("tipo", request.getParameter("tipo"));
		request.setAttribute("questionario", questionario);
		request.setAttribute("template", template);
		File header = new File(Costanti.PATH_HEADER_IMAGE);
		ArrayList<String> listaHeader = new ArrayList<String>(Arrays.asList(header.list()));
		request.setAttribute("listaHeader",listaHeader);
		
		File footer = new File(Costanti.PATH_FOOTER_IMAGE);
		ArrayList<String> listaFooter = new ArrayList<String>(Arrays.asList(footer.list()));
		request.setAttribute("listaFooter",listaFooter);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/template/formTemplate.jsp");
		dispatcher.forward(request,response);
	}
}
