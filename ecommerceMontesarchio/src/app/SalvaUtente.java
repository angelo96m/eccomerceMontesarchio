package app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
				//int idNegozio = Integer.getInteger(req.getParameter("idNegozio"));
				int idNegozio = 1; 
				String CodiceFiscale = req.getParameter("CodiceFiscale");

				
				DBConnection dbConnection = new DBConnection(); 
				UtenteDaoJDBC UserDao = new UtenteDaoJDBC(dbConnection);
				PersonaDaoJDBC PersonaDao = new PersonaDaoJDBC(dbConnection);

				Persona persona = null ;
				Utente user = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				System.out.println("centro");

				user = new Utente();
				persona = new Persona();
				
				//la classe PersonaDaoJDBC in Database non esiste, non so se sia un problema... 
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

				String Message = "Registrazione effettuata con successo! \r\n" + "Mail: " + user.getMail() + "\r\n" + "Password: " + user.getPassword() +"\r\n"+ "Conferma il tuo account: http://localhost:8080/Ecommerce/ConfermaUtente.html?Mail="+user.getMail(); 
				
				String Message1 = "Registrazione effetuata con successo!"; 
				//Email mail = new Email();
				//mail.Send(user.getMail(), "Registrazione effettuata!", Message1);
					
				resp.getWriter().write("Ok");
		
			
		
	}
}
