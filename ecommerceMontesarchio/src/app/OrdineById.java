package app;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import Database.DBConnection;
import Database.OrdineDaoJDBC;
import Model.Ordine;

public class OrdineById extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				int idOrdine = Integer.parseInt(req.getParameter("idOrdine"));
				
				DBConnection dbConnection = new DBConnection(); 
				OrdineDaoJDBC OrdineDao = new OrdineDaoJDBC(dbConnection);
				
				JSONObject obj = new JSONObject();

				
				Ordine ordine = OrdineDao.findByPrimaryKeyJoin(idOrdine);
				
				try{
					obj.put("idOrdine", ordine.getIdOrdine());
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
					obj.put("DataOra", dateFormat.format(ordine.getDataOra()));
					obj.put("Nickname", ordine.getNickname()); 
					
					JSONArray jArrayP = new JSONArray();
					for(int i=0; i<ordine.getListProdotti().size(); i++){
						
						JSONObject objP = new JSONObject();
						
						try{
							objP.put("idProdotto",ordine.getListProdotti().get(i).getIdProdotto());
							objP.put("Nome", ordine.getListProdotti().get(i).getNome());
							objP.put("Prezzo", ordine.getListProdotti().get(i).getPrezzo());
							objP.put("Descizione",ordine.getListProdotti().get(i).getDescrizione()); 
							objP.put("Categoria", ordine.getListProdotti().get(i).getCategoria());
							objP.put("Quantità", ordine.getListProdotti().get(i).getQuantità());
							objP.put("ImageURL", ordine.getListProdotti().get(i).getImgURL());
							
							jArrayP.put(objP);
							
						}catch(Exception e) {e.printStackTrace();}
					}
					obj.put("Prodotti", jArrayP);
					
				}catch(Exception e) {e.printStackTrace();}
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(obj.toString());
				
	}
			 
}
