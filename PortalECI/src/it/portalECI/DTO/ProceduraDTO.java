package it.portalECI.DTO;

public class ProceduraDTO {
	
 int id = 0;
 String nome = "";
 
 
public ProceduraDTO() {
	 
}

public ProceduraDTO( String nome) {
	super();
 	this.nome = nome;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
}
