package it.portalECI.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;

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

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.bo.GestioneVerbaleBO;

@WebServlet(name = "headerFooterUpload", urlPatterns = { "/headerFooterUpload.do" })
@MultipartConfig
public class UploadHeaderFooter extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JsonObject jsonResponse = new JsonObject();
		PrintWriter  out = response.getWriter();
		jsonResponse.addProperty("success", true);
		
		String type = request.getParameter("type");
	    Part filePart = request.getPart("file");
	    String fileName = getUploadedFileName(filePart);
	    InputStream fileContent = filePart.getInputStream();
	    
	    try {
		    File fileTarget = getFileTarget(type, fileName);
		    FileUtils.copyInputStreamToFile(fileContent, fileTarget);
	    }catch (Exception e) {
	    	jsonResponse.addProperty("success", false);
	    	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    	return;
		}
		
		jsonResponse.addProperty("fileName", fileName);
		out.print(jsonResponse);
		return;
	}
	
	private File getFileTarget(String type, String fileName) {
		switch (type) {
		case "header":
			return new File(Costanti.PATH_HEADER_IMAGE,fileName);
		case "footer":
			return new File(Costanti.PATH_FOOTER_IMAGE,fileName);
		default:
			return null;
		}
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
