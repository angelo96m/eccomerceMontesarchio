package app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import Database.DBConnection;
import Database.OrdineDaoJDBC;
import Database.ProdottoDaoJDBC;
import Model.Ordine;
import Model.Prodotto;


public class UpdateOrdine extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
				
				int idOrdine = Integer.valueOf(req.getParameter("idOrdine"));

				String Nickname = req.getParameter("Nickname");
				Date DataOra = Date.valueOf("DataOra"); 
				Float Prezzo = Float.valueOf(req.getParameter("Prezzo"));
				int Quantità = Integer.getInteger(req.getParameter("Quantità")); 
				int idProdotto = Integer.valueOf(req.getParameter("idProdotto"));
				
				
				
				DBConnection dbConnection = new DBConnection(); 
				OrdineDaoJDBC OrderDao = new OrdineDaoJDBC(dbConnection);
				ProdottoDaoJDBC ProdDao = new ProdottoDaoJDBC(dbConnection); 
				
				
				Ordine order = OrderDao.findByPrimaryKeyJoin(idOrdine);
				Prodotto prod = ProdDao.findByPrimaryKeyJoin(idProdotto); 
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				
				order.setDataOra(DataOra);
				order.setNickname(Nickname);
				
				System.out.println("Costo: " + Prezzo * Quantità);
				

				
				OrderDao.update(order);
					
					
				resp.getWriter().write("Ok");
			
	}
}	
