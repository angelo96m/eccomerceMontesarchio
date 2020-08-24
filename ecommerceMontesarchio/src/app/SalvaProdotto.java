package app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.DBConnection;
import Database.ProdottoDaoJDBC;
import Model.Prodotto;

public class SalvaProdotto  extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
	
				String Nome = req.getParameter("Nome");
				Float Prezzo = Float.valueOf(req.getParameter("Prezzo"));
				//Long idLocale = Long.valueOf(req.getParameter("idLocale"));
				String Categoria = req.getParameter("Categoria");
				String ImgURL = req.getParameter("ImgURL");
				
				DBConnection dbConnection = new DBConnection(); 
				ProdottoDaoJDBC ProdottoDao = new ProdottoDaoJDBC(dbConnection);
				Prodotto prodotto = null;
				
				resp.setContentType("text/plain");
				resp.setCharacterEncoding("UTF-8");
				
				prodotto = new Prodotto();
				//prodotto.setIdLocale(idLocale);
				prodotto.setImgURL(ImgURL);
				
				prodotto.setNome(Nome);
				prodotto.setPrezzo(Prezzo);
				
				prodotto.setCategoria(Categoria);
				prodotto = ProdottoDao.saveProdotto(prodotto);
				
				resp.getWriter().write("Inserito \n" + prodotto.getIdProdotto());
					
	}
}
