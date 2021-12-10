package it.portalECI.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.bo.GestioneVerbaleBO;

@WebServlet(name = "gestioneDocumento", urlPatterns = { "/gestioneDocumento.do" })
public class GestioneDocumento extends HttpServlet {
	
    public GestioneDocumento() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
    
    	String action = request.getParameter("action");
		
		request.setCharacterEncoding("UTF-8");
		
		Session session=SessionFacotryDAO.get().openSession();
		
    	
		if(action!= null && action.equals("scarica_tutti")) {
			
			String id_verbale = request.getParameter("id_verbale");
			String type = request.getParameter("type");
			
			VerbaleDTO verbale = GestioneVerbaleBO.getVerbale(id_verbale, session);
			
			
			 FileOutputStream fos = null;
		     ZipOutputStream zipOut = null;
		     FileInputStream fis = null;
		     
		     String zip_path = "";
		     
		     if(type.equals("ALLEGATO")) {
		    	 zip_path = Costanti.PATH_CERTIFICATI+"/Allegati ID verbale "+verbale.getId()+".zip";
		     }else {
		    	 zip_path = Costanti.PATH_CERTIFICATI+"/Certificati ID verbale "+verbale.getId()+".zip";
		     }
		     
		     fos = new FileOutputStream(zip_path);
	         zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
	          ArrayList<String> inseriti = new ArrayList<String>();  
	      
			for (DocumentoDTO doc : verbale.getDocumentiVerbale()) {
				
				String filenoext = doc.getFilePath().substring(0, doc.getFilePath().length()-4);
				String path = "";
				
				if(!type.equals("ALLEGATO") && !doc.getType().equals("ALLEGATO") && verbale.getCodiceCategoria().equals("VAL")&& !doc.getInvalid()) {
					path = Costanti.PATH_CERTIFICATI+filenoext+"_F.pdf";
				}else if(!type.equals("ALLEGATO") && !doc.getType().equals("ALLEGATO") &&  verbale.getCodiceCategoria().equals("VIE") && !doc.getInvalid()){
					path = Costanti.PATH_CERTIFICATI+filenoext+"_CF.pdf";
				}else if(type.equals("ALLEGATO") &&  doc.getType().equals("ALLEGATO") &&  !doc.getInvalid() && doc.getAllegato_visibile_cliente()==1){
					path = Costanti.PATH_CERTIFICATI+doc.getFilePath();
				}
				if(!path.equals("")) {
					File input = new File(path);
					
					ZipEntry ze = null;
				
					fis = new FileInputStream(input);
		            ze = new ZipEntry(input.getName());
		            System.out.println("Zipping the file: "+input.getName());
		            inseriti.add(input.getName());
		            
	                zipOut.putNextEntry(ze);
	                byte[] tmp = new byte[4*1024];
	                int size = 0;
	                while((size = fis.read(tmp)) != -1){
	                    zipOut.write(tmp, 0, size);
	                }
	                zipOut.flush();
	                fis.close();
				}
            }
			
			if(verbale.getSchedaTecnica()!=null) {
				for (DocumentoDTO doc : verbale.getSchedaTecnica().getDocumentiVerbale()) {
					
					String filenoext = doc.getFilePath().substring(0, doc.getFilePath().length()-4);
					String path = "";
					
					if(!type.equals("ALLEGATO") && !doc.getType().equals("ALLEGATO") && verbale.getCodiceCategoria().equals("VAL")&& !doc.getInvalid()) {
						path = Costanti.PATH_CERTIFICATI+filenoext+"_F.pdf";
					}else if(!type.equals("ALLEGATO") && !doc.getType().equals("ALLEGATO") &&  verbale.getCodiceCategoria().equals("VIE") && !doc.getInvalid()){
						path = Costanti.PATH_CERTIFICATI+filenoext+"_CF.pdf";
					}else if(type.equals("ALLEGATO") &&  doc.getType().equals("ALLEGATO") &&  !doc.getInvalid() && doc.getAllegato_visibile_cliente()==1){
						path = Costanti.PATH_CERTIFICATI+doc.getFilePath();
					}
					if(!path.equals("")) {
						File input = new File(path);
						
						ZipEntry ze = null;
					
						fis = new FileInputStream(input);
			            ze = new ZipEntry(input.getName());
			            System.out.println("Zipping the file: "+input.getName());
			            inseriti.add(input.getName());
			            
		                zipOut.putNextEntry(ze);
		                byte[] tmp = new byte[4*1024];
		                int size = 0;
		                while((size = fis.read(tmp)) != -1){
		                    zipOut.write(tmp, 0, size);
		                }
		                zipOut.flush();
		                fis.close();
					}
	            }
			}
			
			
            zipOut.close();
            System.out.println("Done... Zipped the files...");
            
            File f = new File(zip_path);
			
			session.close();
			
			response.setContentType("application/octet-stream");	
		
				
			    response.setHeader("Content-disposition", "attachment; filename=\""+f.getName()+"\"");

			    OutputStream output = response.getOutputStream();
			    output.write(Files.readAllBytes(f.toPath()));
			    output.close();
			
		}
		
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		request.setCharacterEncoding("UTF-8");
		
		Session session=SessionFacotryDAO.get().openSession();
		
			String firmato = request.getParameter("firmato");
			String controfirmato = request.getParameter("controfirmato");
				
				String idDocumento=request.getParameter("idDocumento");		
				
				DocumentoDTO documento = GestioneDocumentoDAO.getDocumento(idDocumento, session);
				File fileDocument = null;
				String filenoext = documento.getFilePath().substring(0, documento.getFilePath().length()-4);
				if(documento != null) {
					
					if(controfirmato!=null && controfirmato.equals("1") ) {
						fileDocument =  new File(Costanti.PATH_CERTIFICATI+filenoext+"_CF.pdf");
					}
					else if(firmato!=null && firmato.equals("1")) {
						fileDocument =  new File(Costanti.PATH_CERTIFICATI+filenoext+"_F.pdf");
					}
					else {
						fileDocument =  new File(Costanti.PATH_CERTIFICATI+documento.getFilePath());
					}
				}

				if(fileDocument == null || !fileDocument.exists()) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					session.close();
					return;
				}
				session.close();
//				if(p7m!=null && p7m.equals("1")) {
//					response.setContentType("application/octet-stream");
//				}else {
					response.setContentType("application/octet-stream");	
			//	}
				
			    response.setHeader("Content-disposition", "attachment; filename=\""+fileDocument.getName()+"\"");

			    OutputStream output = response.getOutputStream();
			    output.write(Files.readAllBytes(fileDocument.toPath()));
			    output.close();
		
		
	}
}
