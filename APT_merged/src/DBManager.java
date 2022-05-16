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
		Statement stmt = dbConnection.createStatement();
		URLCompactString = URLCompactString.replace("'", "\\'");
		ResultSet resultCompactString = stmt.executeQuery("SELECT compactString FROM akml.noncrawledurls WHERE compactString like'" + URLCompactString + "';");
		if(!resultCompactString.next()) {
			String query = "INSERT INTO akml.noncrawledurls(URL, compactString)"
					+ " values (?,?)";
			PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
			preparedStmt.setString (1, URLName);
			preparedStmt.setString (2, URLCompactString);

			preparedStmt.execute();
		}
	}

	//to get the total number of crawled documents to use it in the indexer
	public static int getNumberOfCrawledPages(Connection dbConnection) throws SQLException {
		Statement stmt = dbConnection.createStatement();
		ResultSet resultNumberOfPages = stmt.executeQuery("SELECT COUNT(*) AS recordCount FROM akml.crawledurls");
		resultNumberOfPages.next();
		int result = resultNumberOfPages.getInt("recordCount");
		return result;

	}
	//to get urls from crawled pages
	public static ResultSet getOneURLFromCrawled(Connection dbConnection) throws SQLException {
		Statement stmt = dbConnection.createStatement();
		ResultSet result = stmt.executeQuery("SELECT URL FROM akml.crawledurls LIMIT 1;");
		return result;
	}
	//to delete from crawled
	public static void deleteRowCrawled(Connection dbConnection, String URL) throws SQLException {
		String query = "DELETE FROM akml.crawledurls WHERE URL = '" + URL + "';";
		PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
		preparedStmt.execute();
	}
	public static void updateTheRankOfTheURL(Connection dbConnection,String url)throws SQLException{
		Statement stmt = dbConnection.createStatement();
		String query = "SELECT * FROM akml.pageRank WHERE URL = '"+url+"';";
		ResultSet result = stmt.executeQuery(query);
		if(result.next())
		{
			int rank = result.getInt("rank");
			rank++;
			stmt.execute("UPDATE akml.pagerank SET rank ="+rank+" WHERE url = '"+url+"';");
		}
		else
		{
			query= "INSERT INTO akml.pagerank(url,rank)"
					+ " values (?,?)";
			PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
			preparedStmt.setString (1, url);
			preparedStmt.setInt (2, 1);
			preparedStmt.execute();
		}
	}
	public static int getTheRankOfURL(Connection dbConnection ,String url) throws SQLException {
		Statement stmt = dbConnection.createStatement();
		String query = "SELECT * FROM akml.pagerank WHERE url = '" + url + "';";
		ResultSet result = stmt.executeQuery(query);
		if (result.next()) {
			return result.getInt("rank");
		}
		return 0;
	}


	public static void insertintoQueries(Connection dbConnection, String querySearch) throws SQLException {
		String query = "INSERT INTO akml.queries(query)"
				+ " values (?)";
		PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
		preparedStmt.setString (1, querySearch);

		preparedStmt.execute();
	}
	public static ResultSet getFromQueries(Connection dbConnection) throws SQLException {
		Statement stmt = dbConnection.createStatement();
		ResultSet result = stmt.executeQuery("SELECT query FROM akml.queries;");
		return result;
	}

}