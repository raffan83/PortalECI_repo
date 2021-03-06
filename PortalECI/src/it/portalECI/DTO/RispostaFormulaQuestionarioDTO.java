package it.portalECI.DTO;

public class RispostaFormulaQuestionarioDTO extends RispostaQuestionario {
		
	private String valore1;
	private String valore2;
	private String operatore;
	private String risultato;
	
	public RispostaFormulaQuestionarioDTO() {
	}

	public String getValore1() {
		return valore1;
	}

	public void setValore1(String valore1) {
		this.valore1 = valore1;
	}

	public String getValore2() {
		return valore2;
	}

	public void setValore2(String valore2) {
		this.valore2 = valore2;
	}

	public String getOperatore() {
		return operatore;
	}
	
	public String getSimboloOperatore() {
		switch (operatore) {
		 
	    	case "Somma":
	        	return "+";
	        	
	    	case "Sottrazione":
	    		return "-";
	        
	    	case "Moltiplicazione":
	        	return "x";
	        
	    	case "Divisione":
	    		return ":";
	        
	    	case "Potenza":
	    		return "^";
	    	
	    	default:
	    		return "";
		}
		
	}
	
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getRisultato() {
		return risultato;
	}

	public void setRisultato(String risultato) {
		this.risultato = risultato;
	}
	
}
