package it.portalECI.action;

import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCommesseBO;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GestioneCommessa
 */
@WebServlet(name = "gestioneCommessa", urlPatterns = { "/gestioneCommessa.do" })

public class GestioneCommessa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneCommessa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext())) return;
		
		response.setContentType("text/html");
		 
		try {
			CompanyDTO company =(CompanyDTO)request.getSession().getAttribute("usrCompany");
			
			UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
			
			String action = request.getParameter("action");
			
			if(action == null) {
			
				String anno=request.getParameter("year");
				
				int year=0;
				
				if(anno!=null) {
					year=Integer.parseInt(anno);
				}
				
				ArrayList<CommessaDTO> listaCommesse =GestioneCommesseBO.getListaCommesse(company,user,year);
				
				request.getSession().setAttribute("listaCommesse", listaCommesse);
				request.getSession().setAttribute("current_year", year);
				request.getSession().setAttribute("yearList", Utility.getYearList());
				
				request.getSession().setAttribute("startDate", null);
				request.getSession().setAttribute("endDate", null);
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneCommessa.jsp");
		     	dispatcher.forward(request,response);
		     	
			}else if(action.equals("date")) {
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");				
				
				ArrayList<CommessaDTO> listaCommesse = GestioneCommesseBO.getListaCommessePerData(company, user, dateFrom, dateTo);
				
				request.getSession().setAttribute("listaCommesse", listaCommesse);
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
				Date from = df.parse(dateFrom);
				Date to = df.parse(dateTo);
				
				request.getSession().setAttribute("startDate", df2.format(from));
				request.getSession().setAttribute("endDate", df2.format(to));
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneCommessa.jsp");
		     	dispatcher.forward(request,response);
				
			}
			
		}catch(Exception ex){
   		 	ex.printStackTrace();
   		 	request.setAttribute("error",ECIException.callException(ex));
   		 	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
   		 	dispatcher.forward(request,response);	
		}  
	}

}
