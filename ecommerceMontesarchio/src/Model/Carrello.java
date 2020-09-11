package Model;

import java.util.ArrayList;
import java.util.List;

import 	Model.Prodotto;

/*
 * Classe Carrello, ha una lista di prodotti.
 * La classe contiene i vari metodi per la gestione del carrello.
*/
public class Carrello {
	 List<Prodotto> listProducts;
	    public Carrello(){
	        listProducts = new ArrayList<>();
	    }
	    
	    public void addProdotto(Prodotto P ){
	        listProducts.add(P);
	    }
	    
	    public float getTotalCost() {
	        float tot = 0; 

	        for(int k=0; k<listProducts.size(); k++)
	            tot += listProducts.get(k).getPrezzo();
	        return tot;
	    }


	    public List<Prodotto> getListProducts() {
	        return listProducts;
	    }
	    
	    
	    public void setListProducts(List<Prodotto> listProducts) {
			this.listProducts = listProducts;
		}

		public void clear(){
	        listProducts.clear();
	    }
		
	    public boolean remove(Prodotto P){
	        try {
	            listProducts.remove(P);
	            return true;
	        }catch (Exception e){
	            return false;
	        }
	    }
	    
	    public int size(){return listProducts.size();}
	}