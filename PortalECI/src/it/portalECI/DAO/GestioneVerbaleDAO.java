package it.portalECI.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.AllegatoClienteDTO;
import it.portalECI.DTO.AllegatoMinisteroDTO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.ProgressivoVerbaleDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.StoricoEmailVerbaleDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneVerbaleDAO {
	
	public static List<VerbaleDTO> getListaVerbali(Session session, UtenteDTO user){
		Query query=null;
		
		boolean ck_AM=user.checkRuolo("AM");
		boolean ck_ST=user.checkRuolo("ST");
		boolean ck_RT=user.checkRuolo("RT");
		boolean ck_SRT=user.checkRuolo("SRT");
		boolean ck_CL=user.checkRuolo("CL");
		if(ck_ST==false && ck_AM==false && ck_RT==false && ck_SRT==false && ck_CL == false) 
		{
		 
		query  = session.createQuery( "from VerbaleDTO WHERE type = :_type AND intervento.tecnico_verificatore.id=:_idUser");
		query.setParameter("_type",VerbaleDTO.VERBALE);
		query.setParameter("_idUser",user.getId());
		}
		else if(ck_CL == true) {
			query  = session.createQuery( "from VerbaleDTO WHERE type = :_type AND stato.id = 5 and visibile_cliente = 1 and intervento.id_cliente = :_idCliente and intervento.idSede =:_idSede");
			query.setParameter("_type",VerbaleDTO.VERBALE);
			query.setParameter("_idCliente",user.getIdCliente());
			query.setParameter("_idSede",user.getIdSede());
		}
		else 
		{
			 query  = session.createQuery( "from VerbaleDTO WHERE type = :_type");
			query.setParameter("_type",VerbaleDTO.VERBALE);
		}
		List<VerbaleDTO> result = query.list();
		return result;
	}

	public static void save(VerbaleDTO verbale, Session session) {
		session.save(verbale);
	}

	public static VerbaleDTO  getVerbale(String idVerbale, Session session) {
		
		Query query=null;
		VerbaleDTO verbale=null;
		try {
		
			String s_query = "from VerbaleDTO WHERE id = :_id";
			query = session.createQuery(s_query);
			query.setParameter("_id",Integer.parseInt(idVerbale));			
			
			if(query.list().size()>0){	
				return (VerbaleDTO) query.list().get(0);
			}
			return null;
						

	    } catch(Exception e){
	    	e.printStackTrace();
	    }
		return verbale;		
	}
	
	public static VerbaleDTO getVerbaleFromSkTec(String idSchedaTecnica, Session session) {
		
		Query query=null;
		VerbaleDTO verbale=null;
		try {
		
			String s_query = "from VerbaleDTO WHERE schedaTecnica.id = :_id";
			query = session.createQuery(s_query);
			query.setParameter("_id",Integer.parseInt(idSchedaTecnica));			
			
			if(query.list().size()>0){	
				return (VerbaleDTO) query.list().get(0);
			}
			return null;
						

	    } catch(Exception e){
	    	e.printStackTrace();
	    }
		return verbale;		
	}
	
	public static List<VerbaleDTO> getVerbaliConQuestionarioAggiornato(int idQuestionarioOld, Session session) {
		
		Query query=session.createQuery("from VerbaleDTO WHERE stato.id = :_stato and questionarioID = :_questionarioOld and type = :_type");	
			query.setParameter("_stato",StatoVerbaleDTO.CREATO);	
			query.setParameter("_questionarioOld",idQuestionarioOld);
			query.setParameter("_type",VerbaleDTO.VERBALE);
			List<VerbaleDTO> result = query.list();
			return result;
	}

	public static synchronized ProgressivoVerbaleDTO getProgressivoVerbale(UtenteDTO utente, TipoVerificaDTO tipo, Session session) {
		Query query = session.createQuery("from ProgressivoVerbaleDTO where idUtente= :_idUtente and sigla= :_idTipo");
		query.setString("_idTipo", tipo.getSigla());
		query.setInteger("_idUtente", utente.getId());
		ProgressivoVerbaleDTO progressivo = (ProgressivoVerbaleDTO) query.uniqueResult();
		if(progressivo == null) progressivo= new ProgressivoVerbaleDTO(utente.getId(), tipo.getSigla());
		progressivo.incrementaProgressivo();
		session.saveOrUpdate(progressivo);
		return progressivo;
	}
	
	
	public static List<VerbaleDTO> getListaVerbaliDate(Session session, UtenteDTO user, String dateFrom, String dateTo) throws Exception{
		Query query=null;
		
		boolean ck_AM=user.checkRuolo("AM");
		boolean ck_ST=user.checkRuolo("ST");
		boolean ck_RT=user.checkRuolo("RT");
		boolean ck_SRT=user.checkRuolo("SRT");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(ck_ST==false && ck_AM==false && ck_RT==false && ck_SRT==false) 
		{
		 
		query  = session.createQuery( "from VerbaleDTO WHERE type = :_type AND intervento.tecnico_verificatore.id=:_idUser and (data_verifica between :_dateFrom and :_dateTo or data_verifica_integrita between :_dateFrom and :_dateTo or data_verifica_interna between :_dateFrom and :_dateTo)");
		query.setParameter("_type",VerbaleDTO.VERBALE);
		query.setParameter("_idUser",user.getId());
		query.setParameter("_dateFrom", sdf.parse(dateFrom));
		query.setParameter("_dateTo", sdf.parse(dateTo));
		}
		else 
		{
			 query  = session.createQuery( "from VerbaleDTO WHERE type = :_type and (data_verifica between :_dateFrom and :_dateTo or data_verifica_integrita between :_dateFrom and :_dateTo or data_verifica_interna between :_dateFrom and :_dateTo)");
			query.setParameter("_type",VerbaleDTO.VERBALE);
			query.setParameter("_dateFrom", sdf.parse(dateFrom));
			query.setParameter("_dateTo", sdf.parse(dateTo));
		}
		List<VerbaleDTO> result = query.list();
		return result;
	}

	public static ArrayList<VerbaleDTO> getListaVerbaliFromAttrezzatura(int id_attrezzatura, UtenteDTO user, Session session) {

		Query query=null;
		
		boolean ck_AM=user.checkRuolo("AM");
		boolean ck_ST=user.checkRuolo("ST");
		boolean ck_RT=user.checkRuolo("RT");
		boolean ck_SRT=user.checkRuolo("SRT");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(ck_ST==false && ck_AM==false && ck_RT==false && ck_SRT==false) 
		{
		 
		query  = session.createQuery( "from VerbaleDTO WHERE type = :_type AND intervento.tecnico_verificatore.id=:_idUser and attrezzatura.id = :_id_attrezzatura");
		query.setParameter("_type",VerbaleDTO.VERBALE);
		query.setParameter("_idUser",user.getId());
		query.setParameter("_id_attrezzatura",id_attrezzatura);
	
		}
		else 
		{
			 query  = session.createQuery( "from VerbaleDTO WHERE type = :_type and attrezzatura.id = :_id_attrezzatura");
			query.setParameter("_type",VerbaleDTO.VERBALE);
			query.setParameter("_id_attrezzatura",id_attrezzatura);
			
		}
		ArrayList<VerbaleDTO> result = (ArrayList<VerbaleDTO>) query.list();
		
		return result;
		
	}

	public static List<DocumentoDTO> getVerbaliPDFAll(Session session, String dateFrom, String dateTo) throws Exception, ParseException {
		
		List<DocumentoDTO> lista = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Query query  = null;
		
		if(dateFrom==null) {
			query = session.createQuery( "from DocumentoDTO WHERE type = 'CERTIFICATO'");
		
		}else {
			query = session.createQuery( "from DocumentoDTO WHERE type = 'CERTIFICATO' AND createDate between :_dateFrom and :_dateTo");
			query.setParameter("_dateFrom", sdf.parse(dateFrom));
			query.setParameter("_dateTo", sdf.parse(dateTo));
		}
		
		lista = query.list();		
		
		return lista;
	}

	public static List<VerbaleDTO> getListaVerbaliMinistero(Session session, String dateFrom, String dateTo) throws Exception, ParseException {
		
		List<VerbaleDTO> lista = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Query query  = null;
		
		if(dateFrom==null) {
			query = session.createQuery( "from VerbaleDTO WHERE type = 'VERBALE' and stato.id = 5 and codiceCategoria = 'VIE'");
		
		}else {
			query = session.createQuery( "from VerbaleDTO WHERE type = 'VERBALE' and stato.id = 5 and codiceCategoria = 'VIE' AND data_verifica between :_dateFrom and :_dateTo");
			query.setParameter("_dateFrom", sdf.parse(dateFrom));
			query.setParameter("_dateTo", sdf.parse(dateTo));
		}
		
		lista = query.list();		
		
		return lista;
		
	}

	public static ArrayList<AllegatoMinisteroDTO> getListaAllegatiMinistero(Session session) {
		
		ArrayList<AllegatoMinisteroDTO> lista = null;
		
		Query query  = session.createQuery( "from AllegatoMinisteroDTO where disabilitato = 0");

		lista = (ArrayList<AllegatoMinisteroDTO>) query.list();		
		
		return lista;
	}

	public static AllegatoMinisteroDTO getAllegatoMinistero(int id_allegato, Session session) {
		
		ArrayList<AllegatoMinisteroDTO> lista = null;
		AllegatoMinisteroDTO result = null;
		
		Query query  = session.createQuery( "from AllegatoMinisteroDTO where id=:_id_allegato");
		query.setParameter("_id_allegato", id_allegato);
		
		lista = (ArrayList<AllegatoMinisteroDTO>) query.list();		
		
		if(lista.size()>0) {
			result = lista.get(0);
		}
		
		return result;
	}

	public static ArrayList<StoricoEmailVerbaleDTO> getListaEmailVerbale(int id, Session session) {
		
		ArrayList<StoricoEmailVerbaleDTO> lista = null;		
		
		Query query  = session.createQuery( "from StoricoEmailVerbaleDTO where id_verbale=:_id");
		query.setParameter("_id", id);
		
		lista = (ArrayList<StoricoEmailVerbaleDTO>) query.list();		
		
				
		return lista;
	}

	public static ArrayList<AllegatoClienteDTO> getListaAllegatiCliente(int id_cliente, int id_sede, Session session) {
		
		ArrayList<AllegatoClienteDTO> lista = null;
		
		Query query  = null;
		
		if(id_cliente == 0 && id_sede == 0) {
			query = session.createQuery( "from AllegatoClienteDTO where disabilitato = 0");
		}else{
			query = session.createQuery( "from AllegatoClienteDTO where disabilitato = 0 and id_cliente = :_id_cliente and id_sede = :_id_sede");
			query.setParameter("_id_cliente", id_cliente);
			query.setParameter("_id_sede", id_sede);
		}
		

		lista = (ArrayList<AllegatoClienteDTO>) query.list();		
		
		return lista;
	}

	public static AllegatoClienteDTO getAllegatoCliente(int id_allegato, Session session) {
		
		ArrayList<AllegatoClienteDTO> lista = null;
		AllegatoClienteDTO result = null;
		
		Query query  = session.createQuery( "from AllegatoClienteDTO where id=:_id_allegato");
		query.setParameter("_id_allegato", id_allegato);
		
		lista = (ArrayList<AllegatoClienteDTO>) query.list();		
		
		if(lista.size()>0) {
			result = lista.get(0);
		}
		
		return result;
	}
	
}
