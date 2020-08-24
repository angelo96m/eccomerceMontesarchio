package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DBConnection;
import Model.Negozio;

public class NegozioDaoJDBC extends Negozio {
	private DBConnection dbConnection;

	public NegozioDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
public void save(Negozio Neg) {
		
		
		Connection connection = this.dbConnection.getConnection();
		try {
			
			String insert = "insert into negozio(idNegozio, Nome, Via) values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setInt(1, Neg.getIdNegozio());
			statement.setString(2, Neg.getNome());
			statement.setString(3, Neg.getVia());

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

public Negozio findByPrimaryKeyJoin(int idNegozio) {
	Connection connection = this.dbConnection.getConnection();
	Negozio Neg = null;
	try {
		PreparedStatement statement;
		String query = "SELECT idNegozio, Nome, Via " + 
				"FROM negozio " + 
				"WHERE idNegozio = ?";
				
		statement = connection.prepareStatement(query);
		statement.setInt(1, idNegozio);
		ResultSet result = statement.executeQuery();
		boolean primaRiga = true;
		while (result.next()) {
			if (primaRiga) {
				Neg = new Negozio();
				Neg.setVia(result.getString("Via"));
				Neg.setIdNegozio(result.getInt("idNegozio"));				
				Neg.setNome(result.getString("Nome"));
				
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
	return Neg;
}

public void update(Negozio Neg) {
	Connection connection = this.dbConnection.getConnection();
	try {
		String update = "update negozio SET Nome = ?, Via = ? WHERE idNegozio = ?";
		PreparedStatement statement = connection.prepareStatement(update);
		statement.setString(1, Neg.getNome());
		statement.setString(2, Neg.getVia());
		statement.setInt(3, Neg.getIdNegozio());
			
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

}
