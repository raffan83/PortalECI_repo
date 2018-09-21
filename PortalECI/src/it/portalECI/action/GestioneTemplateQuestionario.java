package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
		
		String action=request.getParameter("action");
		if(action !=null && action.equals("generaDocumentoProva")) {
			
			try {
				TemplateQuestionarioDTO template = new  TemplateQuestionarioDTO();
				template.setTitolo(request.getParameter("titolo"));
				template.setTemplate(request.getParameter("template"));
				template.setHeader(request.getParameter("headerFileName"));
				template.setFooter(request.getParameter("footerFileName"));
				
				File fileDocument = GestioneTemplateQuestionarioBO.QuestionarioTest(template);
				if(fileDocument != null) {
					byte[] pdfArray = loadFileForBase64(fileDocument);
					if(pdfArray.length == 0) {
												
						ServletContext sc = getServletContext();
						request.getSession().setAttribute("issue", "Certificato troppo grande per essere generato!");
						sc.getRequestDispatcher("/page/downloadError.jsp").forward(request, response);
						return;	
					}
					
					response.setContentType("application/pdf");
				    response.setHeader("Content-disposition", "attachment; filename=\""+fileDocument.getName()+"\"");

				    OutputStream output = response.getOutputStream();
				    output.write(Files.readAllBytes(fileDocument.toPath()));
				    output.close();
					
				} else {
					ServletContext sc = getServletContext();
					request.getSession().setAttribute("issue", "Errore nel recupero del file header o footer da inserire nel documento!");
					sc.getRequestDispatcher("/page/downloadError.jsp").forward(request, response);
					return;	
				}
			} catch (Exception e) {
				e.printStackTrace();
				ServletContext sc = getServletContext();
				request.getSession().setAttribute("issue", "Non &egrave; stato possibile recuperare il Certificato. Problema di connessione.");
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
	
	private static byte[] loadFileForBase64(File file) throws IOException {
	    InputStream is = new FileInputStream(file);
	    long length = file.length();
	    byte[] bytes = new byte[(int)length];
	    if (length > Integer.MAX_VALUE) {
		    bytes = new byte[0];       
	    } else {    
		    int offset = 0;
		    int numRead = 0;
		    while (offset < bytes.length
		           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
		        offset += numRead;
		    }	
		    if (offset < bytes.length) {
		        throw new IOException("Could not completely read file "+file.getName());
		    }
	    }
		is.close();
		return bytes;
	}
}
