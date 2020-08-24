package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.Ordine;
import Model.Prodotto;
import Database.DBConnection;
import Database.IdBroker;
import Database.PersistenceException;

/*
 * Classe Ordine Dao dove ci sono i metodi per svolgere le query sul DB. 
 * 
 */
public class OrdineDaoJDBC extends Ordine{
	private DBConnection dbConnection;
	
	public OrdineDaoJDBC(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
public void save(Ordine order) {
		
		/*
		if ( (order.getListProducts() == null) 
				|| order.getListProducts().isEmpty()){
			throw new PersistenceException("Ordine non memorizzato: un ordine deve avere almeno un prodotto");
		}*/
		Connection connection = this.dbConnection.getConnection();
		try {
			int id = IdBroker.getId(connection, "idOrdine", "ordine");
			order.setIdOrdine(id); 
			String insert = "insert into ordine(idOrdine, DataOra, Nickname) values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, order.getIdOrdine());
			//statement.setString(2, order.getStato());
			//statement.setInt(3, 1);
			//System.out.println(order.getAsporto());
			
			//if(order.getAsporto())
			//	statement.setInt(3, 1);
			//else
			//	statement.setInt(3, 0);
			
			//statement.setString(4, order.getNumeroTelefono());
			
			
			//Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            
            //order.setDateTime(currentTime);
            
            statement.setString(2, datetime.format(order.getDataOra()));
			statement.setString(3, order.getNickname()); //prima era setLong
			//statement.setFloat(7, order.getTotaleCosto());
			//if(order.getPagato())
			//	statement.setInt(8, 1);
			//else
			//	statement.setInt(8, 0);
            
			//connection.setAutoCommit(false);
			//serve in caso gli studenti non siano stati salvati. Il DAO studente apre e chiude una transazione nuova.
			//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);			
			statement.executeUpdate();
			// salviamo anche tutti gli studenti del gruppo in CASACATA
			//this.updateOrderProduct(order, connection);
			//connection.commit();
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
		
	} //save
	

public void saveOrderProduct(int idOrdine, int idProdotto, int Quantità)  {

	Connection connection = this.dbConnection.getConnection();

	try {

		String query = "insert into dettagliordine(idProdotto, idOrdine, Quantità) values (?,?,?)";
		PreparedStatement statementIscrivi = connection.prepareStatement(query);
		statementIscrivi.setInt(1, idProdotto);
		statementIscrivi.setLong(2, idOrdine);
		statementIscrivi.setInt(3, Quantità);
		statementIscrivi.executeUpdate();
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

@SuppressWarnings("unused")
private void updateOrderProduct(Ordine order, Connection connection) throws SQLException {
	
	if(order.getListProdotti() != null)
	{
		for (Prodotto product : order.getListProdotti()) {
					
				String query = "insert into dettagliordine(idProdotto, idOrdine, Quantità) values (?,?,?)";
				PreparedStatement statementIscrivi = connection.prepareStatement(query);
				statementIscrivi.setInt(1, product.getIdProdotto());
				statementIscrivi.setLong(2, order.getIdOrdine());
				statementIscrivi.setInt(3, product.getQuantità());
				statementIscrivi.executeUpdate();
		}
	}	
}

public void updateOrderProduct(int idOrdine, int idProdotto, int Quantità){
	Connection connection = this.dbConnection.getConnection();
	try {
		String update = "update dettagliordine SET Quantità = ? WHERE idOrdine = ? AND idProdotto = ?";
		PreparedStatement statement = connection.prepareStatement(update);
		statement.setInt(1, Quantità);
		statement.setLong(2, idOrdine);
		statement.setInt(3, idProdotto);
		//connection.setAutoCommit(false);
		//connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
		statement.executeUpdate();
		//connection.commit();
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

/* 
 * versione con Join
 */
public Ordine findByPrimaryKeyJoin(int id) {
	
	Connection connection = this.dbConnection.getConnection();
	Ordine order = null;
	try {
		PreparedStatement statement;
		String query = "SELECT idOrdine, DataOra, Nickname " + 
				"FROM ordine " + 
				"WHERE idOrdine = ? ";
				
		
		statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		boolean primaRiga = true;
		while (result.next()) {
			if (primaRiga) {
				order = new Ordine();
				order.setIdOrdine(result.getInt("idOrdine"));
				System.out.println(result.getDate("DataOra"));
				SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
				
				try {
					order.setDataOra((java.sql.Date) datetime.parse(result.getString("DataOra")));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					System.out.println(datetime.parse(result.getString("DataOra")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
				
				query = "SELECT prodotto.idProdotto, prodotto.Nome, Prezzo, Descrizione, Categoria, Quantità " + 
						"FROM prodotto, dettagliordine " + 
						"WHERE prodotto.idProdotto = dettagliordine.idProdotto AND dettagliordine.idOrdine = ?";
				statement = connection.prepareStatement(query);
				statement.setInt(1, (int) order.getIdOrdine());
				ResultSet res = statement.executeQuery();
				List<Prodotto> listP = new ArrayList<>();
				while (res.next()) {
					Prodotto product = new Prodotto();
					product.setNome(res.getString("Nome"));
					product.setIdProdotto((int) res.getLong("idProdotto"));				
					product.setPrezzo(res.getFloat("Prezzo"));
					
					product.setQuantità(res.getInt("Quantità"));
					
					//query = "SELECT ingrediente.idIngrediente, Nome, Costo " + 
					//		"FROM ingrediente, prodottiingredienti " + 
					//		"WHERE prodottiingredienti.idProdotto = ? AND ingrediente.idIngrediente = prodottiingredienti.idIngrediente";
					statement = connection.prepareStatement(query);
					statement.setInt(1, (int) res.getLong("idProdotto"));
					ResultSet res1 = statement.executeQuery();
					
					listP.add(product);
				}
				order.setListProdotti(listP);				
					
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
	return order;
	
}

public void update(Ordine order) {
	
	Connection connection = this.dbConnection.getConnection();
	try {
		String update = "update ordine SET DataOra = ?,  Nickname = ? WHERE idOrdine = ?";
		PreparedStatement statement = connection.prepareStatement(update);
	
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm");            
		statement.setString(1, datetime.format(order.getDataOra()));
		
		statement.setString(2, order.getNickname());
		statement.setLong(3, order.getIdOrdine());
		//connection.setAutoCommit(false);
		//connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);			
		statement.executeUpdate();
		//connection.commit();
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

public void delete(int idOrdine) {
	
	Connection connection = this.dbConnection.getConnection();
	try {
		String delete = "delete FROM dettaglioordini WHERE idOrdine = ? ";
		PreparedStatement statement = connection.prepareStatement(delete);
		statement.setLong(1, idOrdine);
		statement.executeUpdate();
		//connection.commit();
		
		delete = "delete FROM ordine WHERE idOrdine = ? ";
		statement = connection.prepareStatement(delete);
		statement.setLong(1, idOrdine);

		statement.executeUpdate();
		//connection.commit();
	} catch (SQLException e) {
		throw new PersistenceException(e.getMessage());
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}

public void deleteOrderProduct(int idOrdine, int idProdotto){
	Connection connection = this.dbConnection.getConnection();
	try {
		String delete = "delete FROM dettaglioordine WHERE idOrdine = ? AND idProdotto = ?";
		PreparedStatement statement = connection.prepareStatement(delete);
		statement.setLong(1, idOrdine);
		statement.setInt(2, idProdotto);
		//connection.commit();
		
	} catch (SQLException e) {
		throw new PersistenceException(e.getMessage());
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}

}
