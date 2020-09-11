package app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DBConnection;
import Database.PersonaDaoJDBC;
import Database.UtenteDaoJDBC;
import Model.Utente;
import Model.Persona;

public class SalvaUtente extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				System.out.println("Inizio");
				String Nome = req.getParameter("Nome");
				String Cognome = req.getParameter("Cognome");
				String Password = req.getParameter("Password");
				String Mail = req.getParameter("Mail");
				String Via = req.getParameter("Via");
				String Nickname = req.getParameter("Nickname");
				
				int idNegozio = 1; 
				String CodiceFiscale = req.getParameter("CodiceFiscale");	
				
				DBConnection dbConnection = new DBConnection(); 
				UtenteDaoJDBC UserDao = new UtenteDaoJDBC(dbConnection);
				PersonaDaoJDBC PersonaDao = new PersonaDaoJDBC(dbConnection);

				Persona persona = null ;
				Utente user = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				user = new Utente();
				persona = new Persona();
				
				
				persona.setCodiceFiscale(CodiceFiscale);
				persona.setNome(Nome);
				persona.setCognome(Cognome);
				persona.setVia(Via); 
				
				user.setNickname(Nickname); 
				user.setPassword(Password);
				user.setMail(Mail);
				user.setIdNegozio(idNegozio);
				user.setCodiceFiscale(CodiceFiscale);
				
				PersonaDao.save(persona);
				UserDao.save(user);
				System.out.println("fine");
				
				resp.getWriter().write("Ok");
				
				/*
				 * Verifica se un utente è loggato, fa fare le modifiche al profilo utente,
				 * se non è già loggato fa registrare un nuovo utente. 
				 */
			/*	HttpSession session = req.getSession(false);
				if(session != null)
					user = (Utente)session.getAttribute("UserLogged");
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				if(UserDao.findByPrimaryKeyJoin(user.getNickname()) != null)
				{
					user.setNickname(Nickname); 
					user.setPassword(Password);
					user.setMail(Mail);
					user.setIdNegozio(idNegozio);
					user.setCodiceFiscale(CodiceFiscale);
					user.setNome(Nome);
					user.setCognome(Cognome);
					user.setVia(Via);
					
					UserDao.update(user);
					System.out.println("update FINE");
				}
				else
				{
					System.out.println("centro");

					user = new Utente();
					persona = new Persona();
					
					
					persona.setCodiceFiscale(CodiceFiscale);
					persona.setNome(Nome);
					persona.setCognome(Cognome);
					persona.setVia(Via); 
					
					user.setNickname(Nickname); 
					user.setPassword(Password);
					user.setMail(Mail);
					user.setIdNegozio(idNegozio);
					user.setCodiceFiscale(CodiceFiscale);
					
					PersonaDao.save(persona);
					UserDao.save(user);
					System.out.println("fine");
				}
				
				
					
				resp.getWriter().write("Ok");
		*/
			
		
	}
}
