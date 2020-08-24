package Model;

/*
 * Classe Persona, ha le variabili uguali agli attributi 
 * presenti nel Database. Per ogni variabile ha i metodi get/set.
 */

public class Persona {
	String CodiceFiscale; 
	String Nome; 
	String Cognome; 
	String Via; 
	
	public Persona() {}
	
	public Persona(String CodiceFiscale, String Nome, String Cognome, String Via) {
		this.CodiceFiscale= CodiceFiscale; 
		this.Nome = Nome; 
		this.Cognome = Cognome; 
		this.Via = Via; 
	}

	public String getCodiceFiscale() {
		return CodiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		CodiceFiscale = codiceFiscale;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getCognome() {
		return Cognome;
	}

	public void setCognome(String cognome) {
		Cognome = cognome;
	}

	public String getVia() {
		return Via;
	}

	public void setVia(String via) {
		Via = via;
	}
	 
	
	
}
