package it.portalECI.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itextpdf.text.DocumentException;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.TemplateQuestionarioDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneQuestionarioBO;
import it.portalECI.bo.GestioneTemplateQuestionarioBO;


@WebServlet(name = "gestioneTemplateQuestionario", urlPatterns = { "/gestioneTemplateQuestionario.do" })
public class GestioneTemplateQuestionario extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public GestioneTemplateQuestionario() {
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
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
		template.setRevisione(request.getParameter("revisione"));
		template.setSubheader(request.getParameter("subheader"));
		template.setTemplate(request.getParameter("template"));
		template.setHeader(request.getParameter("headerFileName"));
		template.setFooter(request.getParameter("footerFileName"));
		
		String action=request.getParameter("action");
		if(action !=null && action.equals("generaDocumentoProva")) {
			
			File fileDocument;
			try {
				fileDocument = GestioneTemplateQuestionarioBO.getAnteprimaQuestionario(template, questionario, session);
			} catch (DocumentException e) {
				ServletContext sc = getServletContext();
				request.setAttribute("issue", "Si &egrave; generato un errore durante la generazione del documento!");
				sc.getRequestDispatcher("/page/downloadError.jsp").forward(request, response);
				e.printStackTrace();
				return;	
			}
		
			response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "attachment; filename=\""+fileDocument.getName()+"\"");

		    OutputStream output = response.getOutputStream();
		    output.write(Files.readAllBytes(fileDocument.toPath()));
		    output.close();
		}else {
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
		session.close();
		
	}
	
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(Utility.validateSession(request,response,getServletContext()))return;
		
		Session session=SessionFacotryDAO.get().openSession();
		Transaction transaction = session.beginTransaction();

		String idQuestionario = request.getParameter("idQuestionario");
		QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(idQuestionario, session);

		String action=request.getParameter("action");
		
		if(action !=null && action.equals("generaDocumentoProva")) {
			
			try {
				TemplateQuestionarioDTO template = new  TemplateQuestionarioDTO();
				template.setTitolo(request.getParameter("titolo"));
				template.setRevisione(request.getParameter("revisione"));
				template.setSubheader(request.getParameter("subheader"));
				template.setTemplate(request.getParameter("template"));
				template.setHeader(request.getParameter("headerFileName"));
				template.setFooter(request.getParameter("footerFileName"));
				
				File fileDocument;
				try {
					fileDocument = GestioneTemplateQuestionarioBO.getAnteprimaQuestionario(template, questionario, session);
				} catch (DocumentException e) {
					ServletContext sc = getServletContext();
					request.setAttribute("issue", "Si &egrave; generato un errore durante la generazione del documento!");
					sc.getRequestDispatcher("/page/downloadError.jsp").forward(request, response);
					e.printStackTrace();
					return;	
				}
				
				response.setContentType("application/pdf");
			    response.setHeader("Content-disposition", "attachment; filename=\""+fileDocument.getName()+"\"");

			    OutputStream output = response.getOutputStream();
			    output.write(Files.readAllBytes(fileDocument.toPath()));
			    output.close();

			} catch (Exception e) {
				e.printStackTrace();
				ServletContext sc = getServletContext();
				request.setAttribute("issue", "Non &egrave; stato possibile recuperare il Certificato. Problema di connessione.");
				sc.getRequestDispatcher("/page/downloadError.jsp").forward(request, response);
				return;				
			}
		    
		}else {
			String idTemplate = request.getParameter("idTemplate");
			Integer idTemplateInt = 0;
			try {
				idTemplateInt = Integer.parseInt(idTemplate);
			}catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			TemplateQuestionarioDTO template = GestioneTemplateQuestionarioBO.getQuestionarioById(idTemplateInt, session);		
			template.setTitolo(request.getParameter("titolo"));
			template.setRevisione(request.getParameter("revisione"));
			template.setSubheader(request.getParameter("subheader"));
			template.setTemplate(request.getParameter("template"));
			template.setHeader(request.getParameter("headerFileName"));
			template.setFooter(request.getParameter("footerFileName"));
		
			session.update(template);
			transaction.commit();
			forwardResponse(request, response, questionario, template);
		}
		session.close();
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
