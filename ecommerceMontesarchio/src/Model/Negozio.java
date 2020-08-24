package Model;

import java.util.ArrayList;
import java.util.List;

/*
 * Classe Negozio, ha le variabili uguali agli attributi 
 * presenti nel Database. Per ogni variabile ha i metodi get/set.
 *  La lista listUtenti indica la lista degli utenti registati a questo negozio, 
 * nel DB è una chiave esterna. 
 */

public class Negozio {
	int idNegozio; 
	String Nome; 
	String Via; 
	List<Utente> listUtenti; 

	public Negozio() {
		listUtenti = new ArrayList<>(); 
	}
	
	public Negozio(int idNegozio, String Nome, String Via) {
		this.idNegozio = idNegozio; 
		this.Nome = Nome; 
		this.Via = Via; 
		listUtenti = new ArrayList<>(); 
	}
	
	public int getIdNegozio() {
		return idNegozio;
	}

	public void setIdNegozio(int idNegozio) {
		this.idNegozio = idNegozio;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getVia() {
		return Via;
	}

	public void setVia(String via) {
		Via = via;
	}
	
	public List<Utente> getListUtenti() {
		return listUtenti;
	}

	public void setListUtenti(List<Utente> listUtenti) {
		this.listUtenti = listUtenti;
	}
	
}
