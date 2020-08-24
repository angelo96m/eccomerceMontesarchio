package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.DBConnection;
import Database.PersistenceException;
import Model.Categoria;


public class CategoriaDaoJDBC extends Categoria {
	private DBConnection dbConnection;

	public CategoriaDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public Categoria findByPrimaryKeyJoin(String Tipo) {
		Connection connection = this.dbConnection.getConnection();
		Categoria type = null;
		try {
			PreparedStatement statement;
			String query = "select * from categoria where Nome=?";
					
			statement = connection.prepareStatement(query);
			statement.setString(1, Tipo);
			ResultSet result = statement.executeQuery();
			boolean primaRiga = true;
			while (result.next()) {
				if (primaRiga) {
					type = new Categoria(result.getString("Nome"));
					
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
		return type;
	} 
	
	public List<Categoria> findAll() {
		
		Connection connection = this.dbConnection.getConnection();
		List<Categoria> types = new ArrayList<>();
		try {			
			Categoria type;
			PreparedStatement statement;
			String query = "select * from categoria ";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				type = new Categoria(result.getString("Nome"));
				
				types.add(type);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}	 finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return types;
		
		
	}
	
}
