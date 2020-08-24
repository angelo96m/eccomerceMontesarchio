package app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Database.DBConnection;
import Database.UtenteDaoJDBC;
import Model.Utente;

public class UtenteById extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Nickname = req.getParameter("Nickname");
				System.out.println(Nickname);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				DBConnection dbConnection = new DBConnection(); 
				UtenteDaoJDBC UserDao = new UtenteDaoJDBC(dbConnection);
				Utente user = UserDao.findByPrimaryKeyJoin(Nickname);
				
				JSONObject obj = new JSONObject();
				
				try
				{
					if(user != null)
					{
						obj.put("Nickname", user.getNickname());
						obj.put("Nome", user.getNome());
						obj.put("Cognome", user.getCognome());
						obj.put("Mail", user.getMail());
						obj.put("Via", user.getVia());
						obj.put("Password", user.getPassword());
						
						obj.put("idNegozio", user.getIdNegozio());
						System.out.println(user.getIdNegozio());
						System.out.println(obj.toString());
					}
							
				}catch(Exception e) {e.printStackTrace();}
					
				
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(obj.toString());					
		
		
	}
}
