package it.portalECI.bo;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneCompanyDAO;

import it.portalECI.DTO.CompanyDTO;



public class GestioneCompanyBO {

	
	public static CompanyDTO getCompanyById(String id_str, Session session) throws Exception {


		return GestioneCompanyDAO.getCompanyById(id_str, session);
	}

	public static int saveCompany(CompanyDTO company, String action, Session session) {
		int toRet=0;
		
		try{
		int idCompany=0;
		
		if(action.equals("modifica")){
			session.update(company);
			idCompany=company.getId();
		}
		else if(action.equals("nuovo")){
			idCompany=(Integer) session.save(company);

		}
		
		toRet=0;	
			
		}catch (Exception ex)
		{
			toRet=1;
			throw ex;
	 		
	 		
		}
		return toRet;
		
	}
}
