package it.portalECI.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneGraficiDAO {

	public static ArrayList<ArrayList<String>> getGraficoTipoVerifica(UtenteDTO user) {
		
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		

	
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> lista_codice_verifica = new ArrayList<String>();
		ArrayList<String> lista_codice_categoria = new ArrayList<String>();
		ArrayList<Object[]> lista = null;
		Query query  = null;
		
		boolean ck_AM=user.checkRuolo("AM");
		boolean ck_ST=user.checkRuolo("ST");
		boolean ck_RT=user.checkRuolo("RT");
		boolean ck_SRT=user.checkRuolo("SRT");
		if(ck_ST==false && ck_AM==false && ck_RT==false && ck_SRT == false) 
		{
		 
		query  = session.createQuery( "select a.codiceVerifica, a.codiceCategoria from VerbaleDTO a WHERE type = :_type AND a.intervento.tecnico_verificatore.id=:_idUser");
		query.setParameter("_type",VerbaleDTO.VERBALE);
		query.setParameter("_idUser",user.getId());
		}
		else 
		{
			 query  = session.createQuery( "select codiceVerifica, codiceCategoria from VerbaleDTO WHERE type = :_type");
			query.setParameter("_type",VerbaleDTO.VERBALE);
		}

		lista = (ArrayList<Object[]>) query.list();		

		for (Object[] obj : lista) {
			lista_codice_verifica.add(((String) obj[0]).split("_")[0]);
			lista_codice_categoria.add((String) obj[1]);
		}
		
		if(lista_codice_categoria!=null) {
			result.add(lista_codice_verifica);
			result.add(lista_codice_categoria);
		}
		
		session.close();
		
		return result;
	}
	
	

	public static HashMap<String, Integer> getGraficoVerbaliVerificatore(UtenteDTO user) {

		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();	
		ArrayList<String> lista = null;
		Query query  = null;
		
		boolean ck_AM=user.checkRuolo("AM");
		boolean ck_ST=user.checkRuolo("ST");
		boolean ck_RT=user.checkRuolo("RT");
		boolean ck_SRT=user.checkRuolo("SRT");
		if(ck_ST==false && ck_AM==false && ck_RT==false && ck_SRT==false) 
		{
		 
		query  = session.createQuery( "select a.intervento.tecnico_verificatore.nominativo from VerbaleDTO a WHERE a.type = :_type AND a.intervento.tecnico_verificatore.id=:_idUser");
		query.setParameter("_type",VerbaleDTO.VERBALE);
		query.setParameter("_idUser",user.getId());
		}
		else 
		{
			 query  = session.createQuery( "select a.intervento.tecnico_verificatore.nominativo from VerbaleDTO a WHERE a.type = :_type");
			query.setParameter("_type",VerbaleDTO.VERBALE);
		}
		
		lista = (ArrayList<String>) query.list();		
		
		int count = 1;
		for (String tecnico : lista) {
			
			if(map.containsKey(tecnico.replace(" ", "_").replace("\'","&prime"))) {
				count++;
			}else {
				count=1;
			}

			map.put(tecnico.replace(" ", "_").replace("\'","&prime"), count);
		}
		
		session.close();
		
		return map;
	}

	public static ArrayList<HashMap<String, String>>  getGraficoStatiVerbali(UtenteDTO user) {
				
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		ArrayList<HashMap<String, String>> list_map = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();	
		HashMap<String, String> color_map = new HashMap<String, String>();
		ArrayList<StatoVerbaleDTO> lista = null;		
		Query query  = null;
		
		boolean ck_AM=user.checkRuolo("AM");
		boolean ck_ST=user.checkRuolo("ST");
		boolean ck_RT=user.checkRuolo("RT");
		boolean ck_SRT=user.checkRuolo("SRT");
		if(ck_ST==false && ck_AM==false && ck_RT==false && ck_SRT==false) 
		{
		 
		query  = session.createQuery( "select a.stato from VerbaleDTO a WHERE a.type = :_type AND a.intervento.tecnico_verificatore.id=:_idUser");
		query.setParameter("_type",VerbaleDTO.VERBALE);
		query.setParameter("_idUser",user.getId());
		}
		else 
		{
			query  = session.createQuery( "select stato from VerbaleDTO WHERE type = :_type");
			query.setParameter("_type",VerbaleDTO.VERBALE);
		}

		
		lista = (ArrayList<StatoVerbaleDTO>) query.list();		
		
		int count = 1;
		for (StatoVerbaleDTO stato : lista) {
			
			if(map.containsKey(stato.getDescrizione().replace(" ", "_"))) {
				count++;
			}else {
				count=1;
			}			

			map.put(stato.getDescrizione().replace(" ", "_"), ""+count);
			color_map.put(stato.getDescrizione().replace(" ", "_"), stato.getColore(stato.getId()));
		}
		
		query  = session.createQuery( "from StatoVerbaleDTO");
		
		lista = (ArrayList<StatoVerbaleDTO>) query.list();	
		
		for (StatoVerbaleDTO stato : lista) {
			
			if(map.containsKey(stato.getDescrizione().replace(" ", "_"))) {
				
			}else {
				map.put(stato.getDescrizione().replace(" ", "_"), ""+0);
				color_map.put(stato.getDescrizione().replace(" ", "_"), stato.getColore(stato.getId()));
			}			

			
		}
		
		list_map.add(map);
		list_map.add(color_map);
		
		session.close();
		
		return list_map;
	}	

}
