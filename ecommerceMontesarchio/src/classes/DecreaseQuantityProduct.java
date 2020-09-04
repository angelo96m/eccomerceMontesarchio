package classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
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



public class DecreaseQuantityProduct extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				int idProduct = Integer.valueOf(req.getParameter("idProduct"));
				DBConnection dbConnection = new DBConnection(); 
				ProdottoDaoJDBC ProdDao = new ProdottoDaoJDBC(dbConnection);
				Prodotto product = ProdDao.findByPrimaryKeyJoin(idProduct);
				Carrello cart = null;


				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				HttpSession session = req.getSession(false);
				if(session != null)
				{
					cart = (Carrello)session.getAttribute("Carrello");
					
					boolean presente=false;
					boolean removed = false;
					for(int k=0; k<cart.size() && !presente; k++)
					{
						if(cart.getListProducts().get(k).getIdProdotto() == product.getIdProdotto())
						{
							if(cart.getListProducts().get(k).getQuantità() == 1)
							{
								cart.getListProducts().remove(k);
								removed = true;
								JSONArray jArray = new JSONArray();
								
								for(int i=0; i<cart.size(); i++)
								{
									JSONObject obj = new JSONObject();
									try
									{
										obj.put("id", cart.getListProducts().get(i).getIdProdotto());
										obj.put("Name", cart.getListProducts().get(i).getNome());
										obj.put("Price", cart.getListProducts().get(i).getPrezzo());
										obj.put("ImageURL", cart.getListProducts().get(i).getImgURL());
										obj.put("Quantity", cart.getListProducts().get(i).getQuantità());
										jArray.put(obj);
									}catch(Exception e) {e.printStackTrace();}
								}
								resp.getWriter().write(jArray.toString());
							}
							else
								cart.getListProducts().get(k).setQuantità(cart.getListProducts().get(k).getQuantità()-1);
							presente=true;
						}
					}
					if(presente && !removed )
						resp.getWriter().write("Ok");
					else if(!removed || !presente)
						resp.getWriter().write("Error");
					else if(removed)
						resp.getWriter().write("Removed");

						
				}
				
				
				
				
				
				
				
		
	}
}
