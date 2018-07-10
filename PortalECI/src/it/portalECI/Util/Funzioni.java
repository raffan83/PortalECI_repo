package it.portalECI.Util;


public enum Funzioni {
	 ADDIZIONE ("Addizione")
	,SOTTAZIONE ("Sottrazione")
	,MOLTIPLICAZIONE ("Moltiplicazione")
	,DIVISIONE ("Divisione")
	,POTENZA ("Potenza")
	;
	String funzione;
	Funzioni (String funzione){
		this.funzione = funzione;
	}
	
	public String toString() {
		return this.funzione;
	}
	
	public double calcola (double a, double b) {
        switch (this) {
        case ADDIZIONE:
            return a + b;
        case SOTTAZIONE:
            return a - b;
        case MOLTIPLICAZIONE:
            return a * b;
        case DIVISIONE:
            return a / b;
        case POTENZA:
        	return Math.pow(a, b);
        default:
            throw new AssertionError("Unknown operations " + this);
    }
	}
}
