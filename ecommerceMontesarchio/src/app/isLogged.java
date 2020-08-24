package app;

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
import Database.UtenteDaoJDBC;
import Model.Utente;



public class isLogged extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				Utente user = null;
				
				HttpSession session = req.getSession(false);
				if(session != null)
					user = (Utente)session.getAttribute("UserLogged");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				if(user != null){
										
					JSONObject obj = new JSONObject();
					obj.put("Nickname", user.getNickname());
					obj.put("Nome", user.getNome());
					obj.put("Cognome", user.getCognome());
					obj.put("Mail", user.getMail());
					obj.put("Via", user.getVia());
					obj.put("Password", user.getPassword());
					obj.put("idNegozio", 1);

					resp.getWriter().write(obj.toString());
				}
				else{
					resp.getWriter().write("Not Logged");	
				}
				
	}
}
