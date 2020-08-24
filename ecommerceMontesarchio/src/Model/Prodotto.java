package Model;

/*
 * Classe Prodotto, ha le variabili uguali agli attributi 
 * presenti nel Database. Per ogni variabile ha i metodi get/set.
 */

public class Prodotto {
	int idProdotto; 
	String Nome; 
	Float Prezzo; 
	String Descrizione; 
	String Categoria; 
	int Quantità; 
	String ImgURL; 
	
	public Prodotto() {
		Quantità = 1; 
	}
	
	public Prodotto(int idProdotto, String Nome, Float Prezzo, String Descrizione, String Categoria, int Quantità, String ImgURL) {
		this.idProdotto = idProdotto; 
		this.Nome = Nome; 
		this.Prezzo = Prezzo; 
		this.Descrizione = Descrizione; 
		this.Categoria = Categoria; 
		this.Quantità = Quantità; 
		this.ImgURL = ImgURL; 
	}

	public String getImgURL() {
		return ImgURL;
	}

	public void setImgURL(String imgURL) {
		ImgURL = imgURL;
	}

	public int getQuantità() {
		return Quantità;
	}

	public void setQuantità(int quantità) {
		Quantità = quantità;
	}

	public int getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public Float getPrezzo() {
		return Prezzo;
	}

	public void setPrezzo(Float prezzo) {
		Prezzo = prezzo;
	}

	public String getDescrizione() {
		return Descrizione;
	}

	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}
}
