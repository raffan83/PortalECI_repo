package it.portalECI.DTO;

import java.util.List;

public class RispostaTabellaQuestionarioDTO extends RispostaQuestionario {
		
	private List<ColonnaTabellaQuestionarioDTO> colonne;

	public List<ColonnaTabellaQuestionarioDTO> getColonne() {
		return colonne;
	}

	public void setColonne(List<ColonnaTabellaQuestionarioDTO> colonne) {
		this.colonne = colonne;
	}

	public RispostaTabellaQuestionarioDTO() {
	}

}
