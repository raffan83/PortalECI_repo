package it.portalECI.DTO;


public class AccessorioDTO  implements Cloneable {
	private int id=0;
	private CompanyDTO company;
	private String nome="";
	private String descrizione="";
	private int quantitaFisica;
	private int quantitaPrenotata;
	private int quantitaNecessaria;
	private String componibile="";
	private String idComponibili="";
	private int capacita=0;
	private String unitaMisura="";
    private TipologiaAccessoriDTO tipologia;
	private UtenteDTO user;
	
	public UtenteDTO getUser() {
		return user;
	}
	
	 @Override
	public Object clone() throws CloneNotSupportedException {
	        return super.clone();
	    }
	
	public void setUser(UtenteDTO user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CompanyDTO getCompany() {
		return company;
	}
	public void setCompany(CompanyDTO company) {
		this.company = company;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public TipologiaAccessoriDTO getTipologia() {
		return tipologia;
	}
	public void setTipologia(TipologiaAccessoriDTO tipologia) {
		this.tipologia = tipologia;
	}
	public int getQuantitaFisica() {
		return quantitaFisica;
	}
	public void setQuantitaFisica(int quantitaFisica) {
		this.quantitaFisica = quantitaFisica;
	}
	public int getQuantitaPrenotata() {
		return quantitaPrenotata;
	}
	public void setQuantitaPrenotata(int quantitaPrenotata) {
		this.quantitaPrenotata = quantitaPrenotata;
	}
	public int getQuantitaNecessaria() {
		return quantitaNecessaria;
	}
	public void setQuantitaNecessaria(int quantitaNecessaria) {
		this.quantitaNecessaria = quantitaNecessaria;
	}
	public String getComponibile() {
		return componibile;
	}
	public void setComponibile(String componibile) {
		this.componibile = componibile;
	}
	public String getIdComponibili() {
		return idComponibili;
	}
	public void setIdComponibili(String idComponibili) {
		this.idComponibili = idComponibili;
	}
	public int getCapacita() {
		return capacita;
	}
	public void setCapacita(int capacita) {
		this.capacita = capacita;
	}
	public String getUnitaMisura() {
		return unitaMisura;
	}
	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}
	
}
