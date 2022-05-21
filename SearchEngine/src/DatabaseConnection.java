import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class DatabaseConnection {
	public Connection connect() throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		// connect to the data base with user name and password
		try {
			Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/akml","root","");
			System.out.println("Connected!");
			return dbConnection;
		} catch (Exception e) {
			return null;
		}
	}

}
