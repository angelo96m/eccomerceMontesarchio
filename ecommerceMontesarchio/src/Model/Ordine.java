package Model;

import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

/*
 * Classe Ordine, ha le variabili uguali agli attributi 
 * presenti nel Database. Per ogni variabile ha i metodi get/set.
 * La lista listProdotti indica la lista dei prodotti ordinati, 
 * nel DB è una chiave esterna. 
 */

public class Ordine {
	int idOrdine; 
	Date DataOra; 
	String Nickname; 
	List<Prodotto> listProdotti; 
	
	public Ordine() {
		listProdotti = new ArrayList<>(); 
	}
	
	public Ordine(int idOrdine, Date DataOra, String Nickname) {
		this.idOrdine = idOrdine; 
		this.DataOra = DataOra; 
		this.Nickname = Nickname; 
		listProdotti = new ArrayList<>(); 
	}
	
	public long getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int id) {
		this.idOrdine = id;
	}

	public Date getDataOra() {
		return DataOra;
	}

	public void setDataOra(Date dataOra) {
		DataOra = dataOra;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}
	
	public void AggiungiProdotto(Prodotto p) {
		listProdotti.add(p); 
	}

	public List<Prodotto> getListProdotti() {
		return listProdotti;
	}

	public void setListProdotti(List<Prodotto> listProdotti) {
		this.listProdotti = listProdotti;
	}
	
	public float getCostoTotale() {
		float tot = 0; 
		
		for (int i = 0; i<listProdotti.size(); i++)
			if (listProdotti.get(i).getQuantità() >1)
				tot += listProdotti.get(i).getPrezzo() * listProdotti.get(i).getQuantità(); 
			else 
				tot += listProdotti.get(i).getPrezzo(); 
		return tot; 
	}
}
