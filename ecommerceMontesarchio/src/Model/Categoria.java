package Model;

/*
 * Classe Categoria, ha le variabili uguali agli attributi 
 * presenti nel Database. Per ogni variabile ha i metodi get/set.
 */ 
public class Categoria {
	String Nome; 
	
	public Categoria() {
	}
	
	public Categoria(String Nome) {
		this.Nome=Nome; 
	}
	
	
	public void setNome(String nome) {
		Nome = nome;
	}//set
	
	public String getNome() {
		return Nome;
	}//get
	
}
