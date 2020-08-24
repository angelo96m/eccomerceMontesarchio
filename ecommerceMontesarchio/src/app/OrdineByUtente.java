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
import Database.OrdineDaoJDBC;
import Model.Ordine;


public class OrdineByUtente extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
		
		int idNegozio = Integer.parseInt(req.getParameter("idNegozio")); 
		
		String Nickname = null; 
		
		try {
			Nickname = req.getParameter("Nickname");
		}catch(Exception e){}
		
		DBConnection dbConnection = new DBConnection(); 
		OrdineDaoJDBC OrdineDao = new OrdineDaoJDBC(dbConnection);
		
		JSONArray jArray = new JSONArray();
		
		List<Ordine> ordine = null;
		
		for(int k=0; k<ordine.size(); k++){
			
			JSONObject obj = new JSONObject();
			try{
				obj.put("idOrdine", ordine.get(k).getIdOrdine());
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				obj.put("DataOra", dateFormat.format(ordine.get(k).getDataOra()));
				obj.put("Nickname", ordine.get(k).getNickname());
				
				JSONArray jArrayP = new JSONArray();
				for(int i=0; i<ordine.get(k).getListProdotti().size(); i++){
					
					JSONObject objP = new JSONObject();
					
					try{
						objP.put("idProdotto", ordine.get(k).getListProdotti().get(i).getIdProdotto());
						objP.put("Nome", ordine.get(k).getListProdotti().get(i).getNome());
						objP.put("Prezzo", ordine.get(k).getListProdotti().get(i).getPrezzo());
						objP.put("Categoria", ordine.get(k).getListProdotti().get(i).getCategoria());
						objP.put("Descizione", ordine.get(k).getListProdotti().get(i).getDescrizione());
						objP.put("ImageURL", ordine.get(k).getListProdotti().get(i).getImgURL());
						objP.put("Quantità", ordine.get(k).getListProdotti().get(i).getQuantità());
								
						
						jArrayP.put(objP);
						
					}catch(Exception e) {e.printStackTrace();}
				}
				obj.put("Prodotti", jArrayP);
				
				jArray.put(obj);
			}catch(Exception e) {e.printStackTrace();}
		}
			
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(jArray.toString());
		
}
}
