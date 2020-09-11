package Model;

/*
 * Classe Utente, ha le variabili uguali agli attributi 
 * presenti nel Database (più altre variabili e metodi aggiuntivi).
 * Per ogni variabile ha i metodi get/set.
 */

public class Utente extends Persona {
	String Nickname; 
	String Mail; 
	String Password; 
	int idNegozio; 
	
	public Utente() {}
	
	public Utente(String Nickname, String Mail, String Password, int idNegozio, String CodiceFiscale, String Nome, String Cognome, String Via) {
		super(CodiceFiscale, Nome, Cognome, Via); 
		this.Nickname = Nickname; 
		this.Mail = Mail; 
		this.Password = Password; 
		this.idNegozio = idNegozio;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public int getIdNegozio() {
		return idNegozio;
	}

	public void setIdNegozio(int idNegozio) {
		this.idNegozio = idNegozio;
	}
	
	
}
