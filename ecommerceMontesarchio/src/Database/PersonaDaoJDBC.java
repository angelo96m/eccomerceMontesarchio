package Database;

import Model.Persona;
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
 *  * Questa classe è intermedia tra il DB e le servlet. 
 * Tutti i metodi usati per scrivere e leggere su db.
 * 
 */
public class PersonaDaoJDBC extends Utente {
	private DBConnection dbConnection;

	public PersonaDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}//costruttore, instaura la connessione con il DB. 
	
public void save(Persona persona) {
		
		if (persona.getCodiceFiscale() == null){
			throw new PersistenceException("Utente non memorizzato: un utente deve avere una mail");
		}
		Connection connection = this.dbConnection.getConnection();
		try {
			
			String insert = "insert into persona(CodiceFiscale, Nome, Cognome, Via) values (?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, persona.getCodiceFiscale());
			statement.setString(2,persona.getNome());
			statement.setString(3, persona.getCognome());
			statement.setString(4, persona.getVia());

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



}
