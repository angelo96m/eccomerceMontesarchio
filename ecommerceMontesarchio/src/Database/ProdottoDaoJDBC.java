package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Model.Prodotto;

import Database.IdBroker;
import Database.PersistenceException;
import Database.DBConnection;

/*
 * Classe Prodotto Dao dove ci sono i metodi per svolgere le query sul DB. 
 *  * Questa classe è intermedia tra il DB e le servlet. 
 * Tutti i metodi usati per scrivere e leggere su db.
 * 
 */
public class ProdottoDaoJDBC extends Prodotto {

	private DBConnection dbConnection;
	
	public ProdottoDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
public void save(Prodotto product) {
		
		Connection connection = this.dbConnection.getConnection();
		try {
			int id = IdBroker.getId(connection, "idProdotto", "prodotto");
			product.setIdProdotto(id); 
			String insert = "insert into prodotto(idProdotto, Nome, Prezzo, Descrizione, Categoria, ImgURL) values (?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setInt(1, product.getIdProdotto());
			statement.setString(2, product.getNome());
			statement.setFloat(3, product.getPrezzo());
			statement.setString(4, product.getDescrizione());
			statement.setString(5, product.getCategoria());
			statement.setString(6, product.getImgURL());

			
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch(SQLException excep) {
					throw new PersistenceException(e.getMessage());
				}
			} 
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		
	}//save

public Prodotto saveProdotto (Prodotto product) {
	
	Connection connection = this.dbConnection.getConnection();
	try {
		int id = IdBroker.getId(connection, "idProdotto", "prodotto");
		product.setIdProdotto(id); 
		String insert = "insert into prodotto(idProdotto, Nome, Prezzo, Descrizione, Categoria, ImgURL) values (?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(insert);
		statement.setInt(1, product.getIdProdotto());
		statement.setString(2, product.getNome());
		statement.setFloat(3, product.getPrezzo());
		statement.setString(4, product.getDescrizione());
		statement.setString(5, product.getCategoria());
		statement.setString(6, product.getImgURL());

		
	} catch (SQLException e) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch(SQLException excep) {
				throw new PersistenceException(e.getMessage());
			}
		} 
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	return product;

}//saveProdotto


public Prodotto findByPrimaryKeyJoin(int id) {
	Connection connection = this.dbConnection.getConnection();
	Prodotto product = null;
	try {
		PreparedStatement statement;
		String query = "SELECT prodotto.idProdotto, Nome, Prezzo, Descrizione, Categoria, ImgURL " + 
				"FROM prodotto " + 
				"WHERE prodotto.idProdotto = ? ";
				
		statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		boolean primaRiga = true;
		while (result.next()) {
			if (primaRiga) {
				product = new Prodotto();
				product.setNome(result.getString("Nome"));
				product.setIdProdotto(result.getInt("idProdotto"));				
				product.setPrezzo(result.getFloat("Prezzo"));
				product.setDescrizione(result.getString("Descrizione"));
				product.setCategoria(result.getString("Categoria"));
				product.setImgURL(result.getString("ImgURL"));
				primaRiga = false;
			}//if
		}//while
	}/*try*/ catch (SQLException e) {
		throw new PersistenceException(e.getMessage());
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}	
	return product;
}//findByPrimaryKeyJoin


public void update(Prodotto product) {
	
	Connection connection = this.dbConnection.getConnection();
	try {
		String update = "update prodotto SET Nome = ?, Prezzo = ?, Descizione = ? WHERE idProdotto = ?";
		PreparedStatement statement = connection.prepareStatement(update);
		statement.setString(1, product.getNome());
		statement.setFloat(2, product.getPrezzo());
		statement.setString(3,product.getDescrizione());
		statement.setInt(4, product.getIdProdotto());
		statement.setString(5, getImgURL());
			
		statement.executeUpdate();
		
	} catch (SQLException e) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch(SQLException excep) {
				throw new PersistenceException(e.getMessage());
			}
		} 
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
}//update 

public void delete(Prodotto product) {
	
	 //TODO this method
}
	

public List<Prodotto> findAll() {
	Connection connection = this.dbConnection.getConnection();
	List<Prodotto> list = null;
	Prodotto product = null;
	try {
		PreparedStatement statement;
		String query = "SELECT prodotto.idProdotto, Nome, Prezzo, Categoria, Descrizione, ImgURL " + 
				"FROM prodotto " ;
				
		statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		list = new ArrayList<>(); 
		while (result.next()) {
				product = new Prodotto();
				product.setNome(result.getString("Nome"));
				product.setIdProdotto(result.getInt("idProdotto"));				
				product.setPrezzo(result.getFloat("Prezzo"));
				product.setCategoria(result.getString("Categoria"));
				product.setDescrizione(result.getString("Descrizione"));
				product.setImgURL(result.getString("ImgURL"));
				product.setQuantità(1);
				
				
				
				list.add(product);
			}
	} catch (SQLException e) {
		throw new PersistenceException(e.getMessage());
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}	
	return list;
}
}
