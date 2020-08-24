package app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.DBConnection;
import Database.OrdineDaoJDBC;

public class UpdateOrdineProdotto extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				
				int idOrdine = Integer.valueOf(req.getParameter("idOrdine"));
				int idProdotto = Integer.valueOf(req.getParameter("idProdotto"));
				int Quantit� = Integer.valueOf(req.getParameter("Quantit�"));
				
				
				DBConnection dbConnection = new DBConnection(); 
				OrdineDaoJDBC OrdineDao = new OrdineDaoJDBC(dbConnection);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				OrdineDao.updateOrderProduct(idOrdine, idProdotto, Quantit�);
	}
}
