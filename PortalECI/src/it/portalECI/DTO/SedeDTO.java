package it.portalECI.DTO;

public class SedeDTO {

	private int  __id = 0;
	private String  indirizzo  = "";
	private String  comune = "";
	private String  cap = "";
	private Integer  id__cliente_ = 0;
	private Integer  id__provincia_ = 0 ;
	private String descrizione = "";

	public SedeDTO(){}

	public SedeDTO(int __id, String indirizzo, String comune, String cap,
			Integer id__cliente_, Integer id__provincia_, String _desc) {
		super();
		this.__id = __id;
		this.indirizzo = indirizzo;
		this.comune = comune;
		this.cap = cap;
		this.id__cliente_ = id__cliente_;
		this.id__provincia_ = id__provincia_;
		this.descrizione=_desc;
	}

	public int get__id() {
		return __id;
	}

	public void set__id(int __id) {
		this.__id = __id;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public Integer getId__cliente_() {
		return id__cliente_;
	}

	public void setId__cliente_(Integer id__cliente_) {
		this.id__cliente_ = id__cliente_;
	}

	public Integer getId__provincia_() {
		return id__provincia_;
	}

	public void setId__provincia_(Integer id__provincia_) {
		this.id__provincia_ = id__provincia_;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	};
	
	
}
