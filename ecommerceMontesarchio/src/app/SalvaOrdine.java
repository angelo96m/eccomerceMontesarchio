package app;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.DBConnection;
import Database.OrdineDaoJDBC;
import Database.UtenteDaoJDBC;
import Model.Ordine;
import Model.Utente;




/*
 * controllare bene questa classe... 
 * forse ci sono errori... 
 */
public class SalvaOrdine extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
		
		
		int idNegozio = Integer.parseInt(req.getParameter("idNegozio")); //mettere solo 1
		String Nickname = req.getParameter("Nickname");
		float Prezzo = Float.valueOf(req.getParameter("Prezzo"));
		String DateTime = req.getParameter("DataOra");
		
		DBConnection dbConnection = new DBConnection(); 
		OrdineDaoJDBC OrderDao = new OrdineDaoJDBC(dbConnection);
		UtenteDaoJDBC UserDao = new UtenteDaoJDBC(dbConnection);
		Utente user = UserDao.findByPrimaryKeyJoin(Nickname);

		Ordine order = null;
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		
		
		Date date1 = null;
		try {
			date1 = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(DateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

			order = new Ordine();
			
			
			order.setDataOra(date1);
			order.setListProdotti(null);
			//order.setIdNegozio(idNegozio);
			
			//order.setPrezzo(Prezzo);
			
			OrderDao.save(order);
			
			System.out.println(order.getIdOrdine());
			
			String Message = "Ordine effettuato con successo! \r\n" + "ID: " + order.getIdOrdine()+"\r\n"+ "Controlla lo stato: http://localhost:8080/Restaurant/MyAccount.html";
			
			/*
			Email mail = new Email();
			mail.Send(user.getMail(), "Ordine effettuato!", Message);
			*/
			resp.getWriter().write("Inserito \n" + order.getIdOrdine());
	
			
	}
}
