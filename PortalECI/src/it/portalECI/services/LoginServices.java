package it.portalECI.services;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DTO.UtenteDTO;

public class LoginServices {

	private String user;
	private String password;
	
	
	public boolean checkLogin(String usr, String pwd) throws Exception {
		
		
		  UtenteDTO utente=GestioneAccessoDAO.controllaAccesso(usr,pwd);
		
		if(utente!=null) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public LoginServices() {
		
	};
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return password;
	}
	public void setPwd(String password) {
		this.password = password;
	}
	
	
	
}
