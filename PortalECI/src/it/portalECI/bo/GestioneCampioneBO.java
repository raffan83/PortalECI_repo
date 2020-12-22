package it.portalECI.bo;

import it.portalECI.DAO.DirectMySqlDAO;
import it.portalECI.DAO.GestioneCampioneDAO;
//import it.portalECI.DTO.AttivitaManutenzioneDTO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.DTO.CertificatoCampioneDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.Util.Costanti;
//import it.portalECI.DTO.TipoAttivitaManutenzioneDTO;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.fileupload.FileItem;
import org.hibernate.Session;

import com.google.gson.JsonArray;

public class GestioneCampioneBO {



	public static int saveCampione(CampioneDTO campione, String action, FileItem fileItem, String ente_certificatore, Session session)  throws Exception{
		
		int toRet=0;
		
		try{
		int idCampione=0;
		
		if(action.equals("modifica")){
			session.update(campione);
			idCampione=campione.getId();
		}
		else if(action.equals("nuovo")){
			idCampione=(Integer) session.save(campione);
			

			
		}
		
		if(fileItem!=null && fileItem.getName().length()>0)
		{
		
		 
			CertificatoCampioneDTO certificatoCampioneDTO = new CertificatoCampioneDTO();
			certificatoCampioneDTO.setId_campione(idCampione);
			certificatoCampioneDTO.setDataCreazione(new Date());
			certificatoCampioneDTO.setNumero_certificato(campione.getNumeroCertificato());
			certificatoCampioneDTO.setEnte_certificatore(ente_certificatore);
			
			int idCertificatoCampione=GestioneCampioneDAO.saveCertifiactoCampione(certificatoCampioneDTO,session);
			
			String filename =salvaCertificatoCampione(fileItem,campione,idCertificatoCampione);
			
			if(filename.equals("BADFILE"))
			{
				return 2;
			}
			certificatoCampioneDTO.setFilename(filename);
			campione.setFilenameCertificato(filename);
			
			GestioneCampioneDAO.updateCertificatoCampione(certificatoCampioneDTO,session);
			session.update(campione);
		}
			
		toRet=0;	
			
		}catch (Exception ex)
		{
			toRet=1;
			throw ex;
	 		
	 		
		}
		return toRet;
	}


	private static String salvaCertificatoCampione(FileItem fileItem,CampioneDTO campione, int idCertificatoCampione) throws Exception {
		
		File directory =new File(Costanti.PATH_ROOT+"//Campioni//"+campione.getId());
		
		if(directory.exists()==false)
		{
			directory.mkdir();
		}
		
		if(fileItem.getName().substring(fileItem.getName().length()-3, fileItem.getName().length()).equalsIgnoreCase("pdf"))
		{
		
			File file =new File(directory.getPath()+"//"+campione.getId()+"_"+idCertificatoCampione+".pdf");
		
		fileItem.write(file);
		
		
		return file.getName();
		}
		else
		{
			return "BADFILE";
		}
	}


	public static CampioneDTO controllaCodice(String codice) {
		
		return GestioneCampioneDAO.getCampioneFromCodice(codice);
	}






	public static ArrayList<DocumentoDTO> getListaDocumentiEsterni(int id_campione, Session session) {
		
		return GestioneCampioneDAO.getListaDocumentiEsterni(id_campione, session);
	}


	public static JsonArray getCampioniScadenzaDate(String data_start, String data_end, Integer id_company) throws Exception {
		// TODO Auto-generated method stub
		return GestioneCampioneDAO.getCampioniScadenzaDate(data_start, data_end, id_company);
	}




}
