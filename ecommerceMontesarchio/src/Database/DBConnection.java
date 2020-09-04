package Database;

import java.sql.Connection;
import java.sql.DriverManager;

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
		//this.dbURI = "jdbc:mysql://remotemysql.com:3306/kZEBaP1qWY?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		this.dbURI = "jdbc:mysql://localhost:3306/ecommerce?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		//this.dbURI = "jdbc:mysql://212.237.3.25:3306/ristorante?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		//this.Username = "Remote2"; FOR ONLINE SERVER
		this.Username = "root";
		//this.Password = "sparafra"; FOR ONLINE SERVER
		this.Password = "sparafra"; 
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
