package app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.DBConnection;
import Database.OrdineDaoJDBC;

public class EliminaOrdine extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -246942213705688897L;

	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				int idOrdine = Integer.parseInt(req.getParameter("idOrdine"));
					
				DBConnection dbConnection = new DBConnection(); 
				OrdineDaoJDBC OrdineDao = new OrdineDaoJDBC(dbConnection);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				OrdineDao.delete(idOrdine);	
	}
}
