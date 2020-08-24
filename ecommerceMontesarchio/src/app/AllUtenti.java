package app;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import Database.DBConnection;
import Database.UtenteDaoJDBC;
import Model.Utente;


public class AllUtenti  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
		
		
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		
		DBConnection dbConnection = new DBConnection(); 
		UtenteDaoJDBC UserDao = new UtenteDaoJDBC(dbConnection);
		List<Utente> users = UserDao.findAll(); 
		
		JSONArray jArray = new JSONArray();
	
		for(int k=0; k<users.size(); k++)
		{
			
				JSONObject obj = new JSONObject();
				try
				{
					obj.put("Nickname", users.get(k).getNickname());
					obj.put("Mail", users.get(k).getMail());
					obj.put("Password", users.get(k).getPassword());
					obj.put("idNegozio", users.get(k).getIdNegozio());
					
					jArray.put(obj);
				}catch(Exception e) {e.printStackTrace();}
			
			
		}
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(jArray.toString());					
			
	}
}
