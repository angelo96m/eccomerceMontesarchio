package app;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DBConnection;
import Database.OrdineDaoJDBC;
import Database.UtenteDaoJDBC;
import Model.Carrello;
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
		
		
		
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		
		Utente user = null;
		Carrello cart = null;
		
		HttpSession session = req.getSession(false);
		if(session != null)
		{
			user = (Utente)session.getAttribute("UserLogged");
			cart = (Carrello)session.getAttribute("Carrello");
			
			DBConnection dbConnection = new DBConnection(); 
			OrdineDaoJDBC OrderDao = new OrdineDaoJDBC(dbConnection);
			
			Ordine order = null;

			Date date1 = null;
			try {
				Date currentTime =  Calendar.getInstance().getTime();
	            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	            String dateStr = date.format(currentTime);
				date1 = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

				order = new Ordine();
				
				boolean prodottoEsaurito = false;
				String NomeProdottoE = "";
				for(int k =0; k < cart.getListProducts().size() && !prodottoEsaurito; k++)
				{
					if(cart.getListProducts().get(k).getQuantità() > 15)
					{
						prodottoEsaurito = true;
						NomeProdottoE = cart.getListProducts().get(k).getNome();
						System.out.println(NomeProdottoE + " ESAURITO" );
					}
				}
				if(!prodottoEsaurito)
				{
					order.setDataOra(date1);
					order.setListProdotti(null);
					order.setNickname(user.getNickname());
					order.setListProdotti(cart.getListProducts());
					
					OrderDao.save(order);
					
					session.setAttribute("Carrello", new Carrello());
					
					resp.getWriter().write("Ok");
				}
				else
					resp.getWriter().write(NomeProdottoE);

		}
		
		
	
			
	}
}
