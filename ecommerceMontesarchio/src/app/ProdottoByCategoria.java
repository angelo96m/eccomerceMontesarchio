package app;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

public class ProdottoByCategoria extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
		String categoria = req.getParameter("Categoria");
		
		int idNegozio = Integer.parseInt(req.getParameter("idNegozio"));

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		
		DBConnection dbConnection = new DBConnection(); 
		ProdottoDaoJDBC ProdDao = new ProdottoDaoJDBC(dbConnection);
		List<Prodotto> prodotti = (List<Prodotto>) ProdDao.findByPrimaryKeyJoin(idNegozio); //fatto il casting... 
		
		JSONArray jArray = new JSONArray();
		
		for(int k=0; k<prodotti.size(); k++)
		{
			JSONObject obj = new JSONObject();
			try
			{
				obj.put("id", prodotti.get(k).getIdProdotto());
				obj.put("Nome", prodotti.get(k).getNome());
				obj.put("Prezzo", prodotti.get(k).getPrezzo());
				obj.put("Categoria", prodotti.get(k).getCategoria());
				obj.put("Descrizione", prodotti.get(k).getDescrizione()); 
				obj.put("ImageURL", prodotti.get(k).getImgURL());
				obj.put("Quantità", prodotti.get(k).getQuantità());
				
				/*
				JSONArray jArrayR = new JSONArray();
				
				for(int i=0; i<products.get(k).getListReview().size(); i++)
				{
					JSONObject objR = new JSONObject();
					try
					{
						objR.put("idProdotto", products.get(k).getListReview().get(i).getIdProduct());
						objR.put("NumeroTelefono", products.get(k).getListReview().get(i).getNumeroTelefono());
						objR.put("Voto", products.get(k).getListReview().get(i).getVoto());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						objR.put("DataOra", dateFormat.format(products.get(k).getListReview().get(i).getDataOra()));
						jArrayR.put(objR);
						
					}catch(Exception e) {e.printStackTrace();}
				}
				obj.put("Reviews", jArrayR);
				jArray.put(obj);
				*/
			}catch(Exception e) {e.printStackTrace();}
		}
		
		
		resp.getWriter().write(jArray.toString());

	}
}
