package Database;

import java.sql.Connection;
import java.sql.DriverManager;
/*
 * Classe che stabilisce la connessione con il DB. 
 */
public class DBConnection {

	String dbURI;
	String Username;
	String Password;
	
	public DBConnection(String dbURI, String User, String Password){
		this.dbURI = dbURI;
		this.Username = User;
		this.Password = Password; 		  
	} 
	
	public DBConnection(){
		
		this.dbURI = "jdbc:mysql://localhost:3306/ecommerce?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		
		this.Username = "root";
		this.Password = "00angelo0"; 
	}
	
	public Connection getConnection(){
		Connection con = null;
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection(dbURI, Username, Password);  	
		}catch(Exception e){ System.out.println(e);}  
		return con;
	}
}
