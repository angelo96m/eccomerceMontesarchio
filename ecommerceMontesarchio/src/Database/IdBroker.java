package Database;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Classe che serve per creare un nuovo id Automaticamente. 
 */
public class IdBroker {

	
	private static String query = "SELECT AUTO_INCREMENT " + 
			"From information_schema.TABLES " + 
			"WHERE TABLE_SCHEMA = 'ristorante' " + 
			"AND TABLE_NAME = ";

	public static int getId(Connection connection, String idColumn, String DBTable) {
		int id = 0;
		query = "SELECT MAX("+ idColumn +") as id From "+ DBTable;
		System.out.println(query);
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			id = result.getInt("id") + 1;
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
		return id;
	}
}