package classes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import Database.DBConnection;
import Database.ProdottoDaoJDBC;
import Model.Carrello;
import Model.Prodotto;

public class AggiungiACarello extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
		int idProduct = Integer.parseInt(req.getParameter("idProdotto"));
				System.out.println(idProduct);
				DBConnection dbConnection = new DBConnection(); 
				ProdottoDaoJDBC ProdDao = new ProdottoDaoJDBC(dbConnection);
				Prodotto product = ProdDao.findByPrimaryKeyJoin(idProduct);
		
				System.out.println(product.getNome());
				Carrello cart = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(false);
				if(session != null){
					System.out.println("SESSION");
					cart = (Carrello)session.getAttribute("Carrello");

					boolean presente = false;
					for(int k=0; k < cart.size() && !presente; k++){
				
						if(cart.getListProducts().get(k).getIdProdotto() == product.getIdProdotto()){
							cart.getListProducts().get(k).setQuantità(cart.getListProducts().get(k).getQuantità()+1);
							presente=true;
						}
					}
			
					if(!presente){
						product.setQuantità(1);
						cart.addProdotto(product);
						System.out.println("sssddwdw");
					}
					
					
					JSONArray jArray = new JSONArray();
					
					for(int k=0; k < cart.size(); k++){
						JSONObject obj = new JSONObject();
						try{
							obj.put("id", cart.getListProducts().get(k).getIdProdotto());
							obj.put("Nome", cart.getListProducts().get(k).getNome());
							obj.put("Prezzo", cart.getListProducts().get(k).getPrezzo());
							obj.put("Categoria", cart.getListProducts().get(k).getCategoria());
							obj.put("ImageURL", cart.getListProducts().get(k).getImgURL());
							obj.put("Quantita", cart.getListProducts().get(k).getQuantità());
							jArray.put(obj);
						}catch(Exception e) {e.printStackTrace();}
					}
					resp.getWriter().write(jArray.toString());
				}
				else{
					JSONArray jArray = new JSONArray();
					
					resp.getWriter().write(jArray.toString());
				}

	}
}
