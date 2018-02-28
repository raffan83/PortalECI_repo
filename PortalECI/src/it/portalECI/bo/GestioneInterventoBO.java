package it.portalECI.bo;

import it.portalECI.DAO.GestioneInterventoDAO;
import it.portalECI.DAO.SQLLiteDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CertificatoDTO;
import it.portalECI.DTO.ClassificazioneDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.InterventoDatiDTO;
import it.portalECI.DTO.MisuraDTO;
import it.portalECI.DTO.ObjSavePackDTO;
import it.portalECI.DTO.ProceduraDTO;
import it.portalECI.DTO.PuntoMisuraDTO;
import it.portalECI.DTO.ScadenzaDTO;
import it.portalECI.DTO.StatoCertificatoDTO;
import it.portalECI.DTO.StatoPackDTO;
import it.portalECI.DTO.StrumentoDTO;
import it.portalECI.DTO.TipoRapportoDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Util.Costanti;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.Session;

public class GestioneInterventoBO {

	public static List<InterventoDTO> getListaInterventi(String idCommessa, Session session) throws Exception {


		return GestioneInterventoDAO.getListaInterventi(idCommessa,session);
	}

	public static void save(InterventoDTO intervento, Session session) throws Exception {

		session.save(intervento);

		InterventoDatiDTO intDati = new InterventoDatiDTO();
		intDati.setId_intervento(intervento.getId());
		intDati.setDataCreazione(intervento.getDataCreazione());
		intDati.setNomePack(intervento.getNomePack());
		intDati.setNumStrMis(0);
		intDati.setNumStrNuovi(0);
		intDati.setStato(new StatoPackDTO(1));
		intDati.setUtente(intervento.getUser());
		session.save(intDati);
		
	}

	public static void save(InterventoDatiDTO interventoDati,Session session)throws Exception {
		
		session.save(interventoDati);


	}

	public static InterventoDTO getIntervento(String idIntervento) {

		return GestioneInterventoDAO.getIntervento(idIntervento);

	}

	public static ObjSavePackDTO savePackUpload(FileItem item, String nomePack) {

		ObjSavePackDTO  objSave= new ObjSavePackDTO();

		/*check salvataggio nomeFile*/
		
		if(!(nomePack+".db").equals(item.getName()))
		{
		 objSave.setEsito(0);
		 objSave.setErrorMsg("Questo pacchetto non corrisponde a quest'intervento");
		 
		 return objSave;
		}
		
		
		String folder=item.getName().substring(0,item.getName().indexOf("."));
			
		int index=1;
			while(true)
			{
				File file = new File(Costanti.PATH_FOLDER+"//"+folder+"//"+folder+"_"+index+".db");

				
				
				if(file.exists()==false)
				{

					try {
						
						item.write(file);
						
						boolean  checkFile=GestioneInterventoBO.controllaFile(file);
						
						if(checkFile)
						{

						objSave.setPackNameAssigned(file);
						objSave.setEsito(1);
						break;
						}
						else
						{
							objSave.setEsito(2);
							break;
						}
					} catch (Exception e) {

						e.printStackTrace();
						objSave.setEsito(0);
						objSave.setErrorMsg("Errore Salvataggio Dati");

						return objSave; 
					}
				}
				else
				{
					index++;
				}
			}
		return objSave;
	}

	private static boolean controllaFile(File file) throws Exception {
		
		
		return SQLLiteDAO.checkFile(file.getPath());
	}

	public static ObjSavePackDTO saveDataDB(ObjSavePackDTO esito, InterventoDTO intervento,UtenteDTO utente, Session session) throws Exception {
		
		InterventoDatiDTO interventoDati = new InterventoDatiDTO();
		
		StrumentoDTO nuovoStrumento=null;
		try {
			System.out.println(intervento);
			
			String nomeDB=esito.getPackNameAssigned().getPath();
			
			Connection con =SQLLiteDAO.getConnection(nomeDB);
			
			ArrayList<MisuraDTO> listaMisure=SQLLiteDAO.getListaMisure(con,intervento);

			esito.setEsito(1);
			
			interventoDati.setId_intervento(intervento.getId());
			interventoDati.setNomePack(esito.getPackNameAssigned().getName().substring(0,esito.getPackNameAssigned().getName().length()-3));
			interventoDati.setDataCreazione(new Date());
			interventoDati.setStato(new StatoPackDTO(3));
			interventoDati.setNumStrMis(0);
			interventoDati.setNumStrNuovi(0);
			interventoDati.setUtente(utente);
			
			saveInterventoDati(interventoDati,session);
			
			esito.setInterventoDati(interventoDati);
			
			int strumentiDuplicati=0;
			
		    for (int i = 0; i < listaMisure.size(); i++) 
		    {
		    	MisuraDTO misura = listaMisure.get(i);
		    	
		   	if(misura.getStrumento().getCreato().equals("S") && misura.getStrumento().getImportato().equals("N"))
		   		
		    	{
		   		
		   		String listaProcedure = misura.getStrumento().getProcedureString();
		   		
		   		if(listaProcedure!=null && listaProcedure.length()>0) 
		   		{
		   			
		   			String[] listaProc = listaProcedure.split(";");
	   			
		   			misura.getStrumento().getListaProcedure().clear();
		   			
		   			for (String proc : listaProc) 
		   			{
		   				misura.getStrumento().getListaProcedure().add(new ProceduraDTO(proc));
					}
		   		}
		   			
		    		nuovoStrumento=GestioneStrumentoBO.createStrumeto(misura.getStrumento(),intervento,session);

//		    		int nuoviStrumenti =intervento.getnStrumentiNuovi()+1;
//		    		intervento.setnStrumentiNuovi(nuoviStrumenti);
		    		
		    		int nuoviStrumentiInterventoDati=interventoDati.getNumStrNuovi()+1;
		    		interventoDati.setNumStrNuovi(nuoviStrumentiInterventoDati);
		    		
		    	}
		    	
		   	if(misura.getStrumento().getStrumentoModificato()!=null && misura.getStrumento().getStrumentoModificato().equals("S")) {
		   		
		   		StrumentoDTO strumentoModificato=new StrumentoDTO();
		   		
		   		strumentoModificato = GestioneStrumentoBO.getStrumentoById(""+misura.getStrumento().get__id(),session);
		   		
		   		StrumentoDTO strumentoDaFile = misura.getStrumento();
		   		
		   		strumentoModificato.setUserModifica(utente);
		   		strumentoModificato.setDataModifica(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		   		
		   		TipoRapportoDTO tipoRapp = new TipoRapportoDTO(strumentoDaFile.getIdTipoRapporto(),"");
		   		strumentoModificato.getScadenzaDTO().setTipo_rapporto(tipoRapp);
		   		
		   		ClassificazioneDTO classificazione = new ClassificazioneDTO(strumentoDaFile.getIdClassificazione(),"");		   		
		   		strumentoModificato.setClassificazione(classificazione);
		   		strumentoModificato.getScadenzaDTO().setFreq_mesi(strumentoDaFile.getFrequenza());
		   		strumentoModificato.setDenominazione(strumentoDaFile.getDenominazione());   	
		   		strumentoModificato.setCodice_interno(strumentoDaFile.getCodice_interno());
		   		strumentoModificato.setCostruttore(strumentoDaFile.getCostruttore());
		   		strumentoModificato.setModello(strumentoDaFile.getModello());
		   		strumentoModificato.setReparto(strumentoDaFile.getReparto());
		   		strumentoModificato.setUtilizzatore(strumentoDaFile.getUtilizzatore());
		   		strumentoModificato.setMatricola(strumentoDaFile.getMatricola());
		   		strumentoModificato.setCampo_misura(strumentoDaFile.getCampo_misura());
		   		strumentoModificato.setRisoluzione(strumentoDaFile.getRisoluzione());
		   		strumentoModificato.setNote(strumentoDaFile.getNote());
		   		
		   		String listaProcedure = strumentoDaFile.getProcedureString();
		   		
		   		if(listaProcedure!=null && listaProcedure.length()>0) {
		   			String[] listaProc = listaProcedure.split(";");
	   			
		   			strumentoModificato.getListaProcedure().clear();
		   			
		   			for (String proc : listaProc) 
		   			{
		   				strumentoModificato.getListaProcedure().add(new ProceduraDTO(proc));
					}
		   		}
		   		
		   		GestioneStrumentoBO.update(strumentoModificato, session);
		   	}
		   	
		    	boolean isPresent=GestioneInterventoDAO.isPresentStrumento(intervento.getId(),misura.getStrumento(),session);
			
		    	if(isPresent==false)
		    	{
		    		misura.setInterventoDati(interventoDati);
		    		misura.setUser(utente);
		    		int idTemp=misura.getId();
		    		saveMisura(misura,session);

		    		/*
		    		 * Salvo scadenza 
		    		 */
		    		ScadenzaDTO scadenza =misura.getStrumento().getScadenzaDTO();
		    		scadenza.setIdStrumento(misura.getStrumento().get__id());
			    	scadenza.setDataUltimaVerifica(new java.sql.Date(misura.getDataMisura().getTime()));
		    		GestioneStrumentoBO.saveScadenza(scadenza,session);
		    		
		    		ArrayList<PuntoMisuraDTO> listaPuntiMisura = SQLLiteDAO.getListaPunti(con,idTemp,misura.getId());
		    		
		    		for (int j = 0; j < listaPuntiMisura .size(); j++) 
		    		{
		    			saveListaPunti(listaPuntiMisura.get(j),session);
					}
		    		
		    	
		    		//intervento.setnStrumentiMisurati(intervento.getnStrumentiMisurati()+1);
		    		interventoDati.setNumStrMis(interventoDati.getNumStrMis()+1);
		    		
		    	
		    		updateInterventoDati(interventoDati,session);
		    		update(intervento, session);
		    		
		    		
		    		CertificatoDTO certificato = new CertificatoDTO();
		    		certificato.setMisura(misura);
		    		certificato.setStato(new StatoCertificatoDTO(1));
		    		certificato.setUtente(misura.getUser());
		    		
		    		saveCertificato(certificato,session);
		    		GestioneInterventoDAO.update(intervento,session);

		    	}
		    		else
		    	{
		    		esito.getListaStrumentiDuplicati().add(misura.getStrumento());	
		    		strumentiDuplicati++;
		    		esito.setEsito(1);
		    	}
		    }
			
		    if(strumentiDuplicati!=0)
		    {
		    	esito.setDuplicati(true);
		    }		    
			
		} catch (Exception e) 
		{
		
			esito.setEsito(0);
			esito.setErrorMsg("Errore Connessione DB: "+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		return esito;
	}

	private static void saveCertificato(CertificatoDTO certificato,Session session)throws Exception {
		
		session.save(certificato);
	}

	public static void updateInterventoDati(InterventoDatiDTO interventoDati,Session session)throws Exception {
		
		session.update(interventoDati);
	}

	private static void saveListaPunti(PuntoMisuraDTO puntoMisuraDTO,Session session)throws Exception {
		
		session.save(puntoMisuraDTO);
		
	}


	public static void update(InterventoDTO intervento, Session session) {
		
		session.update(intervento);
	
	}

	private static void saveMisura(MisuraDTO misura, Session session) {
		
		session.save(misura);
		
	}

	private static void saveInterventoDati(InterventoDatiDTO interventoDati,Session session)throws Exception {
		
		session.save(interventoDati);
		
	}

	public static void removeInterventoDati(InterventoDatiDTO interventoDati, Session session)throws Exception {
	
		session.delete(interventoDati);
		
	}

	public static void updateMisura(String idStr, ObjSavePackDTO esito, InterventoDTO intervento, UtenteDTO utente, Session session) throws Exception {
	
		try{
		
			
			
			String nomeDB=esito.getPackNameAssigned().getPath();
			
			Connection con =SQLLiteDAO.getConnection(nomeDB);
			
			ArrayList<MisuraDTO> listaMisure=SQLLiteDAO.getListaMisure(con,intervento);
			
			
			for (int i = 0; i < listaMisure.size(); i++) 
			{
				MisuraDTO misura = listaMisure.get(i);
				
				if(listaMisure.get(i).getStrumento().get__id()==Integer.parseInt(idStr))
				{
					int idTemp=misura.getId();
					misura.setInterventoDati(esito.getInterventoDati());
		    		misura.setUser(utente);
					
					session.save(listaMisure.get(i));
					
					MisuraDTO misuraObsoleta = GestioneInterventoDAO.getMisuraObsoleta(intervento.getId(),idStr);
					GestioneInterventoDAO.misuraObsoleta(misuraObsoleta,session);
					
					ArrayList<PuntoMisuraDTO> listaPuntiMisura = SQLLiteDAO.getListaPunti(con,idTemp,misura.getId());
		    		for (int j = 0; j < listaPuntiMisura .size(); j++) 
		    		{
		    			session.save(listaPuntiMisura.get(j));
		    			GestioneInterventoDAO.puntoMisuraObsoleto(misuraObsoleta.getId());
					}
		    		
		    		CertificatoDTO certificato = new CertificatoDTO();
		    		certificato.setMisura(misura);
		    		certificato.setStato(new StatoCertificatoDTO(1));
		    		certificato.setUtente(misura.getUser());
		    		
		    		saveCertificato(certificato,session);
				}
				
			}
		
		}catch (Exception e) {
				e.printStackTrace();
				esito.setEsito(0);
				esito.setErrorMsg("Errore Connessione DB: "+e.getMessage());
				e.printStackTrace();
				throw e;
			}
		
	}
	
	public static ArrayList<MisuraDTO> getListaMirureByInterventoDati(int idIntervento)throws Exception
	{
		
			return GestioneInterventoDAO.getListaMirureByInterventoDati(idIntervento);
			
		
	}
	public static ArrayList<MisuraDTO> getListaMirureByIntervento(int idIntervento)throws Exception
	{
		
			return GestioneInterventoDAO.getListaMirureByIntervento(idIntervento);
			
		
	}

	public static ArrayList<InterventoDTO> getListaInterventiDaSede(String idCliente, String idSede, Integer idCompany,
			Session session) {
		// TODO Auto-generated method stub
		return GestioneInterventoDAO.getListaInterventiDaSede(idCliente,idSede,idCompany, session);
	}

	public static ArrayList<Integer> getListaClientiInterventi() {
		// TODO Auto-generated method stub
		return GestioneInterventoDAO.getListaClientiInterventi();
	}

	public static ArrayList<Integer> getListaSediInterventi() {
		// TODO Auto-generated method stub
		return GestioneInterventoDAO.getListaSediInterventi();
	}

}
