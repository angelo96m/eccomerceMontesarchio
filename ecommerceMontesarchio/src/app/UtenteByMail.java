package app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import Database.DBConnection;
import Database.UtenteDaoJDBC;
import Model.Utente;

public class UtenteByMail extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Nickname = req.getParameter("Nickname");
				String Mail = req.getParameter("Mail");
				System.out.println(Mail);
				System.out.println(Nickname);
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				DBConnection dbConnection = new DBConnection(); 
				UtenteDaoJDBC UserDao = new UtenteDaoJDBC(dbConnection);
				Utente user = UserDao.findByPrimaryKeyJoin(Nickname);
				JSONArray jArray = new JSONArray();
				
				JSONObject obj = new JSONObject();
				try{
					obj.put("Nickname", user.getNickname());
					obj.put("Nome", user.getNome());
					obj.put("Cognome", user.getCognome());
					obj.put("Mail", user.getMail());
					obj.put("Via", user.getVia());
					obj.put("Password", user.getPassword());
					obj.put("CodiceFiscale", user.getCodiceFiscale());
					obj.put("idNegozio", user.getIdNegozio());
					System.out.println(user.getIdNegozio());
					jArray.put(obj);
							
				}catch(Exception e) {e.printStackTrace();}
					
				
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(jArray.toString());					
	
	}
}
