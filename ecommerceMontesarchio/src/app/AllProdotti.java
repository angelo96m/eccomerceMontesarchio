package app;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import Database.DBConnection;
import Database.ProdottoDaoJDBC;
import Model.Prodotto;


public class AllProdotti extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				

				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				DBConnection dbConnection = new DBConnection(); 
				ProdottoDaoJDBC ProdDao = new ProdottoDaoJDBC(dbConnection);
				List<Prodotto> products = ProdDao.findAll();
				
				
				JSONArray jArray = new JSONArray();
				
				for(int k=0; k<products.size(); k++)
				{
					JSONObject obj = new JSONObject();
					try
					{
						obj.put("idProdotto", products.get(k).getIdProdotto());
						obj.put("Nome", products.get(k).getNome());
						obj.put("Prezzo", products.get(k).getPrezzo());
						obj.put("Categoria", products.get(k).getCategoria());
						obj.put("Descrizione", products.get(k).getDescrizione());
						obj.put("Quantità", products.get(k).getQuantità());
						obj.put("ImgURL", products.get(k).getImgURL()); 
						
						
						jArray.put(obj);
					}catch(Exception e) {e.printStackTrace();}
				}
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());
	}		 
}
