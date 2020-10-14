package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.TransformerUtils;
import org.hibernate.Session;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.CreateScadenzarioVAL;
import it.portalECI.bo.CreateScadenzarioVIE;
import it.portalECI.bo.GestioneInterventoBO;
import it.portalECI.bo.GestioneVerbaleBO;
import it.portalECI.Util.Costanti;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * Servlet implementation class GestioneUtenti
 */
@WebServlet(name = "gestioneListaVerbali", urlPatterns = { "/gestioneListaVerbali.do" })
public class GestioneListaVerbali extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneListaVerbali() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Utility.validateSession(request,response,getServletContext())) return;
		
		response.setContentType("text/html");
		 
		try {
			Session session=SessionFacotryDAO.get().openSession();
			session.beginTransaction();
				
			UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
			
			String action = request.getParameter("action");
			if(action == null || action.equals("") ) {
				List<VerbaleDTO> listaVerbali =GestioneVerbaleBO.getListaVerbali(session,user) ;
				
				request.getSession().setAttribute("listaVerbali", listaVerbali);
				request.getSession().setAttribute("dateFrom", null);
				request.getSession().setAttribute("dateTo", null);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneListaVerbali.jsp");
		     	dispatcher.forward(request,response);
		     	
		     	session.getTransaction().commit();
				session.close();
			}
			
			else if(action.equals("filtra_date")){				
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");		
				
				List<VerbaleDTO> listaVerbali =GestioneVerbaleBO.getListaVerbaliDate(session,user, dateFrom, dateTo);
				
				request.getSession().setAttribute("listaVerbali", listaVerbali);
				request.getSession().setAttribute("dateFrom", dateFrom);
				request.getSession().setAttribute("dateTo", dateTo);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneListaVerbali.jsp");
		     	dispatcher.forward(request,response);
		     	
		     	session.getTransaction().commit();
				session.close();
			}
			
			else if(action.equals("lista_file")) {
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");		
			
				
				List<DocumentoDTO> listaVerbali = GestioneVerbaleBO.getVerbaliPDFAll(session,dateFrom, dateTo);
				
				request.getSession().setAttribute("listaVerbali", listaVerbali);
				request.getSession().setAttribute("dateFrom", dateFrom);
				request.getSession().setAttribute("dateTo", dateTo);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneListaVerbaliPDF.jsp");
		     	dispatcher.forward(request,response);
				
			}
			
			else if(action.equals("scadenzario_val")) {
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");	
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				Date from = df.parse(dateFrom);
				Date to = df.parse(dateTo);
				
				List<VerbaleDTO> listaVerbali = (List<VerbaleDTO>) request.getSession().getAttribute("listaVerbali");

				List<VerbaleDTO> listaVerbaliValidi = new ArrayList<VerbaleDTO>();
				
				
				for (VerbaleDTO verbaleDTO : listaVerbali) {
					
					if((verbaleDTO.getStato().getId()==5 || verbaleDTO.getStato().getId()==9) && verbaleDTO.getCodiceCategoria().equals("VAL")) {
						listaVerbaliValidi.add(verbaleDTO);
					}
					
				}
				
				
				new CreateScadenzarioVAL(listaVerbaliValidi, dateFrom, dateTo, session);
				
				DateFormat sdf = new SimpleDateFormat("ddMMyyyy");
				

				String path = Costanti.PATH_ROOT + "ScadenzarioVAL\\SCADVAL"+sdf.format(from)+ sdf.format(to)+".xlsx";
				
				 File file = new File(path);
					
					FileInputStream fileIn = new FileInputStream(file);

					ServletOutputStream outp = response.getOutputStream();
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition","attachment;filename=SCADVAL"+ sdf.format(from)+sdf.format(to)+".xlsx");
			
					    byte[] outputByte = new byte[1];
					    
					    while(fileIn.read(outputByte, 0, 1) != -1)
					    {
					    	outp.write(outputByte, 0, 1);
					    }
					    				    
					 
					    fileIn.close();
					    outp.flush();
					    outp.close();
				
				session.close();
				
			}
			else if(action.equals("scadenzario_vie")) {
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");	
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				Date from = df.parse(dateFrom);
				Date to = df.parse(dateTo);
				
				List<VerbaleDTO> listaVerbali = (List<VerbaleDTO>) request.getSession().getAttribute("listaVerbali");

				List<VerbaleDTO> listaVerbaliValidi = new ArrayList<VerbaleDTO>();
				
				
				for (VerbaleDTO verbaleDTO : listaVerbali) {
					
					if(verbaleDTO.getStato().getId()==5 && verbaleDTO.getCodiceCategoria().equals("VIE")) {
						listaVerbaliValidi.add(verbaleDTO);
					}
					
				}
				
				
				new CreateScadenzarioVIE(listaVerbaliValidi, dateFrom, dateTo, session);
				
				DateFormat sdf = new SimpleDateFormat("ddMMyyyy");
				

				String path = Costanti.PATH_ROOT + "ScadenzarioVIE\\SCADVIE"+sdf.format(from)+ sdf.format(to)+".xlsx";
				
				 File file = new File(path);
					
					FileInputStream fileIn = new FileInputStream(file);

					ServletOutputStream outp = response.getOutputStream();
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition","attachment;filename=SCADVIE"+ sdf.format(from)+sdf.format(to)+".xlsx");
			
					    byte[] outputByte = new byte[1];
					    
					    while(fileIn.read(outputByte, 0, 1) != -1)
					    {
					    	outp.write(outputByte, 0, 1);
					    }
					    				    
					 
					    fileIn.close();
					    outp.flush();
					    outp.close();
				
				session.close();
				
			}
			
				
		}catch(Exception ex){
   		 	ex.printStackTrace();
   		 	request.setAttribute("error",ECIException.callException(ex));
   		 	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
   		 	dispatcher.forward(request,response);	
		}  
	}
}
