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



public class IncreaseQuantityProduct extends HttpServlet{
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
					for(int k=0; k<cart.size() && !presente; k++)
					{
						if(cart.getListProducts().get(k).getIdProdotto() == product.getIdProdotto())
						{
							cart.getListProducts().get(k).setQuantità(cart.getListProducts().get(k).getQuantità()+1);
							presente=true;
						}
					}
					if(presente)
						resp.getWriter().write("Ok");
					else
						resp.getWriter().write("Error");
				}
				
				
				
				
				
				
				
		
	}
}
