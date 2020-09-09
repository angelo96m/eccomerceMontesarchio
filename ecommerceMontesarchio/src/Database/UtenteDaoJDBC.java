package Database;

import Model.Prodotto;
import Model.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.DBConnection;

/*
 * Classe Utente Dao dove ci sono i metodi per svolgere le query sul DB. 
 * 
 */
public class UtenteDaoJDBC extends Utente {
	private DBConnection dbConnection;

	public UtenteDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}//costruttore, instaura la connessione con il DB. ? 
	
public void save(Utente user) {
		
		if (user.getMail() == null){
			throw new PersistenceException("Utente non memorizzato: un utente deve avere una mail");
		}
		Connection connection = this.dbConnection.getConnection();
		try {
			
			String insert = "insert into utente(Nickname, Mail, Password, CodiceFiscale, idNegozio) values (?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, user.getNickname());
			statement.setString(2, user.getMail());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getCodiceFiscale());
			statement.setInt(5, user.getIdNegozio());

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
		
	}  //save

public Utente findByPrimaryKeyJoin(String Nickname) { 
	Connection connection = this.dbConnection.getConnection();
	Utente user = null;
	try {
		PreparedStatement statement;
		String query = "SELECT utente.Nickname, Mail, Password, idNegozio " + 
				"FROM utente " + 
				"WHERE utente.Nickname = ? ";
				
		statement = connection.prepareStatement(query);
		statement.setString(1, Nickname);
		ResultSet result = statement.executeQuery();
		boolean primaRiga = true;
		while (result.next()) {
			if (primaRiga) {
				user = new Utente();
				user.setNickname(result.getString("Nickname"));
				user.setMail(result.getString("Mail"));
				user.setPassword(result.getString("Password"));
				user.setIdNegozio(result.getInt("idNegozio"));
				System.out.println("::"+user.getNickname());
				primaRiga = false;	
			}
			
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
	return user;
} 

public void update(Utente user) {
	
	Connection connection = this.dbConnection.getConnection();
	try {
		String update = "update utente SET Nickname = ? WHERE Mail = ?";
		PreparedStatement statement = connection.prepareStatement(update);
		statement.setString(1, user.getNome());
		statement.setString(2, user.getCognome());
		statement.setString(3, user.getVia());
		statement.setString(4, user.getPassword());
		statement.setString(5, user.getMail());
			
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
	
} 

public List<Utente> findAll() {
	Connection connection = this.dbConnection.getConnection();
	List<Utente> list = null;
	Utente utente = null;
	try {
		PreparedStatement statement;
		String query = "SELECT utente.Nickname, Mail, Password, idNegozio " + 
				"FROM utente " ;
				
		statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		list = new ArrayList<>(); 
		while (result.next()) {
				utente = new Utente();
				utente.setNickname(result.getString("Nickname"));
				utente.setMail(result.getString("Mail"));				
				utente.setPassword(result.getString("Password"));
				utente.setIdNegozio(result.getInt("idNegozio")); 
				
				list.add(utente);
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
