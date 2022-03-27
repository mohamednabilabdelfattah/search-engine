import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	//to select one URL for the thread
	public static ResultSet getOneURL(Connection dbConnection) throws SQLException {
		Statement stmt = dbConnection.createStatement();
		ResultSet result = stmt.executeQuery("SELECT ID, URL, compactString FROM akml.noncrawledurls LIMIT 1;");
		return result;
	}
	// To delete the Row From Non crawled
	public static void deleteRowNonCrawled(Connection dbConnection, Integer ID) throws SQLException {
		String query = "DELETE FROM akml.noncrawledurls WHERE ID = " + ID + ";";
		PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
		preparedStmt.execute();
	}
	// insert URL into crawledURLS
	public static void insertintoCrawledURLs(Connection dbConnection, String URLName, String URLCompactString) throws SQLException {
		String query = "INSERT INTO akml.crawledurls(URL, compactString)"
		        + " values (?,?)";
		PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
		preparedStmt.setString (1, URLName);
		preparedStmt.setString (2, URLCompactString);
		
		preparedStmt.execute();
	}
	
	public static void insertintoNonCrawledURLs(Connection dbConnection, String URLName, String URLCompactString) throws SQLException {
		//ResultSet resultCompactString = stmt.executeQuery("SELECT compactString FROM akml.noncrawledurls WHERE compactString like'" + URLCompactString + "';"); 
		//if(!resultCompactString.next()) {
			String query = "INSERT INTO akml.noncrawledurls(URL, compactString)"
			        + " values (?,?)";
			PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
			preparedStmt.setString (1, URLName);
			preparedStmt.setString (2, URLCompactString);
			
			preparedStmt.execute();
		//}
	}

}
