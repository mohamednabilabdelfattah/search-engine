import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
		public Connection connect() throws SQLException, ClassNotFoundException{
			Class.forName("com.mysql.jdbc.Driver");  
			Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/akml","root","password");
			System.out.println("Connected!");
			return dbConnection;
			
		}
		
	

}
