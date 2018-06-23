package it.portalECI.REST;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.portalECI.Util.Utility;

@WebServlet(name="VerbaleREST" , urlPatterns = { "/rest/verbale" })

public class VerbaleREST extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerbaleREST() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//READ		
		// TODO Auto-generated method stub
		//doPost(request,response);
		
		//if(Utility.validateSession(request,response,getServletContext()))return;
		
		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		myObj.addProperty("result", "dentro VerbaleREST doGet");
		out.println(myObj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//CREATE		
		//if(Utility.validateSession(request,response,getServletContext()))return;

		//Session session =SessionFacotryDAO.get().openSession();
		//session.beginTransaction();
		
		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		myObj.addProperty("result", "dentro VerbaleREST doPost");
		out.println(myObj);
	
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//DELETE
		// TODO Auto-generated method stub
		//doPost(request,response);
		
		//if(Utility.validateSession(request,response,getServletContext()))return;
		
		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		myObj.addProperty("result", "dentro VerbaleREST doDelete");
		out.println(myObj);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//UPDATE
		// TODO Auto-generated method stub
		//doPost(request,response);
		//if(Utility.validateSession(request,response,getServletContext()))return;
		
		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		myObj.addProperty("result", "dentro VerbaleREST doPut");
		out.println(myObj);
	}

}
