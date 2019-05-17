package it.portalECI.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;

import it.portalECI.DAO.GestioneAnagraficaRemotaDAO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.SedeDTO;

public class GestioneAnagraficaRemotaBO {


	
	public static List<ClienteDTO> getListaClienti(String id_company) throws HibernateException, Exception {

		return GestioneAnagraficaRemotaDAO.getListaClienti(id_company);
	}
	

	public static List<SedeDTO> getListaSedi() throws SQLException {

		return GestioneAnagraficaRemotaDAO.getListaSedi();
	}
	
	public static HashMap<String, String> getListaNominativiSediClienti() throws SQLException
	{
		return GestioneAnagraficaRemotaDAO.getListaNominativiSediClienti();
	}

	public static HashMap<String, String> getListaNominativiClienti() throws SQLException
	{
		return GestioneAnagraficaRemotaDAO.getListaNominativiClienti();
	}
	
	public static ClienteDTO getClienteById(String id_cliente) throws Exception {
		
		return GestioneAnagraficaRemotaDAO.getClienteById(id_cliente);
	}

	
	
	public static ClienteDTO getClienteFromSede(String id_cliente, String id_sede) throws Exception {
		
		return GestioneAnagraficaRemotaDAO.getClienteFromSede(id_cliente, id_sede);
	}

	public static List<ClienteDTO> getListaFornitori(String id_company) throws Exception {
		
		return GestioneAnagraficaRemotaDAO.getListaFornitori(id_company);
	}
	
	public static SedeDTO getSedeFromId(List<SedeDTO> listaSedi, int id_sede, int id_cliente) {
		
		SedeDTO sede= null;
		
		for (SedeDTO iterator : listaSedi) {
			if(iterator.get__id()==id_sede && iterator.getId__cliente_()==id_cliente) {
				sede = iterator;
				
			}
		}
		
		
		return sede;
	}
	
	public static ArrayList<SedeDTO> getSediFromCliente(List<SedeDTO> listaSedi, int id_cliente) {
		
		ArrayList<SedeDTO> sedi= new ArrayList<SedeDTO>();
		
		for (SedeDTO iterator : listaSedi) {
			if(iterator.getId__cliente_()==id_cliente) {
				
				sedi.add(iterator);
			}
		}
		
		
		return sedi;
	}
	
	
	public static HashMap<String, String> getListaSediAll() throws Exception
	{
		return GestioneAnagraficaRemotaDAO.getListaSedeAll();
	}
	
	public static HashMap<Integer, String> getListaClientiAll() throws Exception
	{
		return GestioneAnagraficaRemotaDAO.getListaClientiAll();
	}
	

}
