package it.portalECI.DTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RuoloDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2449826109627625610L;

	private int id;
	private String sigla;
	private String descrizione;
	
	private Set<PermessoDTO> listaPermessi = new HashSet<PermessoDTO>(0);
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Set<PermessoDTO> getListaPermessi() {
		return listaPermessi;
	}
	public void setListaPermessi(Set<PermessoDTO> listaPermessi) {
		this.listaPermessi = listaPermessi;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public boolean checkPermesso(String chiavepermesso)
	{
		 Iterator<PermessoDTO> iterator = listaPermessi.iterator(); 
	      
		   while (iterator.hasNext()){
			   
			   PermessoDTO permesso= (PermessoDTO) iterator.next(); 
			
			if(permesso.getChiave_permesso().equals(chiavepermesso))
			{
				return true;
			}
		   }
		   
		return false;
	}
	
}
