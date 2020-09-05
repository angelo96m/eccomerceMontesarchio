package classes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.Carrello;

public class GetCart extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				
				Carrello cart = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(false);
				JSONArray jArray = new JSONArray();
				
				
				if(session != null){
					cart = (Carrello)session.getAttribute("Carrello");
					
					for(int k=0; k<cart.size(); k++){
						JSONObject obj = new JSONObject();
						try{
							obj.put("idProdotto", cart.getListProducts().get(k).getIdProdotto());
							obj.put("Nome", cart.getListProducts().get(k).getNome());
							obj.put("Prezzo", cart.getListProducts().get(k).getPrezzo());
							obj.put("Categoria", cart.getListProducts().get(k).getCategoria());
							obj.put("ImageURL", cart.getListProducts().get(k).getImgURL());
							obj.put("Quantità", cart.getListProducts().get(k).getQuantità());
							jArray.put(obj);
						}catch(Exception e) {e.printStackTrace();}
					}
					resp.getWriter().write(jArray.toString());
				}
				else
					resp.getWriter().write(jArray.toString());	
	}
}

