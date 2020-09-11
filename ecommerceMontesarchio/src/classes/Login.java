package classes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import Database.DBConnection;
import Database.NegozioDaoJDBC;
import Database.UtenteDaoJDBC;
import Model.Carrello;
import Model.Negozio;
import Model.Utente;

/*
 * In questa servlet viene avviata la sessione. 
 */
public class Login extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Nickname = req.getParameter("Nickname");
				String Password = req.getParameter("Password");
			
				int Local = 1;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				HttpSession session = req.getSession(true);

				if(Local != 0){
					DBConnection dbConnection = new DBConnection(); 
					UtenteDaoJDBC UserDao = new UtenteDaoJDBC(dbConnection);
					NegozioDaoJDBC NegozioDao = new NegozioDaoJDBC(dbConnection);

					Utente user = UserDao.findByPrimaryKeyJoin(Nickname);
					Negozio N = NegozioDao.findByPrimaryKeyJoin(Local);
					
					if(user == null){
						JSONObject obj = new JSONObject();
						obj.put("Stato", "Utente Non Trovato");
						resp.getWriter().write(obj.toString());
					}
					else if(user.getPassword().equals(Password)){
											
						session.setAttribute("UserLogged", user); //stringa - valore 
						session.setAttribute("Carrello", new Carrello());
						session.setAttribute("Negozio", N);
						JSONObject obj = new JSONObject();
						obj.put("Stato", "Logged");
	
						resp.getWriter().write(obj.toString());
					}
					else{
						JSONObject obj = new JSONObject();
						obj.put("Stato", "Password errata");
						resp.getWriter().write(obj.toString());	
					}
				}
				
		
	}
}
