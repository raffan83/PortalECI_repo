package it.portalECI.DTO;

public class TipoMisuraDTO {

	private int id = 0;
	private String nome = "";
	private int id_tipoStrumento = 0;
	
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
	public int getId_tipoStrumento() {
		return id_tipoStrumento;
	}
	public void setId_tipoStrumento(int id_tipoStrumento) {
		this.id_tipoStrumento = id_tipoStrumento;
	}
}
