import java.sql.*;
import java.util.Queue;
import java.util.LinkedList;
import javafx.util.Pair;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	private static Queue<Pair<Integer, String>> URLS = new LinkedList<>();
	
	public static void pushInQueue(Connection dbConnection) throws SQLException {
		
		Statement stmt = dbConnection.createStatement();
		ResultSet result = stmt.executeQuery("SELECT URLID, URL FROM akml.URLS;"); // REQUIRED: Get only 50(seed set)
		String URL;
		Integer URLID;
		while(result.next()) {
		URL = result.getString("URL");
		URLID = result.getInt("URLID");
		Pair<Integer, String> URLPair = new Pair<Integer, String>(URLID, URL);
		URLS.add(URLPair);
		}
	}
	
	public static void pushURLInQueueAndDatabase(Connection dbConnection, String URLName) throws SQLException {
		Pair<Integer, String> URLPair = new Pair<Integer, String>(0, URLName);
		Statement stmt = dbConnection.createStatement();
		ResultSet result = stmt.executeQuery("SELECT URL FROM akml.URLS WHERE URL =" + String.valueOf(URLPair.getValue()) + ";"); 
		if(!result.next()) {
			stmt.executeQuery("INSERT INTO akml.URLS(URL) VALUES(" + URLPair.getValue() + ");" );
			
			if(!URLS.contains(URLPair))
				URLS.add(URLPair);
		}
		
	}
	
	public static void fetchPage(Pair<Integer, String> URLPair) {
		Integer URLID = URLPair.getKey();
		String URLName = URLPair.getValue();
		try {
			Document doc = Jsoup.connect(URLName).get();
	        Elements links = doc.select("a[href]");
	        for (Element link : links) {
	            System.out.println(link.attr("abs:href"));
	        }
	        
			URL url = new URL(URLName);
			BufferedReader readBuffer = new BufferedReader(new InputStreamReader(url.openStream()));
			BufferedWriter writeBuffer = new BufferedWriter(new FileWriter(String.valueOf(URLID) + ".txt"));
			String line;
            while ((line = readBuffer.readLine()) != null) {
            	writeBuffer.write(line);
            }
  
            readBuffer.close();
            writeBuffer.close();
            System.out.println("Successfully Downloaded.");
			
		}catch(MalformedURLException mException) {
			System.out.println("There is an error in fetching page.");
			System.out.println(mException);
		}catch(IOException ioException) {
			System.out.println("There is an error in reading and writing the file.");
			System.out.println(ioException);
		}
		
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Queue<Pair<Integer, String>> URLS = new LinkedList<>();
		DatabaseConnection dbManager = new DatabaseConnection();
		Connection dbConnection = dbManager.connect();
		pushInQueue(dbConnection, URLS);
		System.out.println(URLS);
		Pair<Integer, String> URLPair = new Pair<Integer, String>(45, "https://try.jsoup.org/");
		fetchPage(URLPair);
		
	}

}




