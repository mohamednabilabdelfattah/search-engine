import java.sql.*;
import java.util.Scanner;
import java.io.*;
import java.net.URL;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler implements Runnable{
	private static Connection dbConnection;
	private static DatabaseConnection dbManager;
	public static void threadWork() throws SQLException {
		while(true) {
			ResultSet result;
			try {
				//get one URL by the thread
				result = DBManager.getOneURL(dbConnection);
			} catch (Exception e) {
				break;
			}
			if(!result.next()) {
				break;
			}
			String URLName = result.getString("URL");
			String URLCompactString = result.getString("compactString");
			Integer ID = result.getInt("ID");
			//delete it from NonCrawled
			DBManager.deleteRowNonCrawled(dbConnection,ID);
			Document doc = extract.downloadFile(URLName);
			//stmt = dbConnection.createStatement();
			//result = stmt.executeQuery("SELECT URL FROM akml.crawledurls WHERE URL = '" + URLName + "';");
			if(!result.next()) {
				try {
					// insert URL into crawledURLS
					DBManager.insertintoCrawledURLs(dbConnection,URLName,URLCompactString);
				} catch (Exception e) {
					continue;
				}
				try {
					if(doc != null) {
						// extractLinks
						Elements links = extract.extractLinks(doc);
						String linkName = "";
						// initialize linkURLSIndex to not exceed 50 URl from one site
						Integer linkURLSIndex = 0;
						for(Element link:links) {
							if(linkURLSIndex==50) {
								break;
							}
							System.out.println("linkURLSIndex :"+linkURLSIndex.toString());
							linkName = link.attr("abs:href");
						 	if((extract.isURLValid(linkName)) && (RobotParser.robotSafe(new URL(linkName))))
							{
								//push URL Into NonCrawled
								pushURLIntoNonCrawled(linkName);
								//update the url rank
								DBManager.updateTheRankOfTheURL(dbConnection,linkName);
							}
							else {
								continue;
							}
							linkURLSIndex++;
						}
					}
				} catch(Exception e) {
					continue;
				}
			}
		}
	}
	public static void pushURLIntoNonCrawled(String URLName) throws SQLException, IOException {
		//robot.txt


		//Statement stmt = dbConnection.createStatement();         //will be revised
		//ResultSet result = stmt.executeQuery("SELECT URL FROM akml.noncrawledurls WHERE URL like'" + URLName + "';"); 
		//if(!result.next()) {
		String URLCompactString = Compact_String.extractCompactString(URLName).replaceAll("'", "\\\\\\\\\\\\\\\\\\\\'");// 5*4 = 20 (Every \ needs 3 \ to skip it and we need 5 in query syntax)
		try {
			// inserting to non crawled
			DBManager.insertintoNonCrawledURLs(dbConnection,URLName,URLCompactString);
		} catch (Exception e) {
			return;
		}
		//}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException { // crawlerExecute()
		System.out.println("Enter number of Threads: ");
		int numberOfThreads;
		Scanner scan =new Scanner(System.in);
		numberOfThreads=scan.nextInt();
		scan.close();
		if(numberOfThreads>50) {
			System.out.println("Number of Threads is too much");
			return ;
		}
		dbManager = new DatabaseConnection();
		dbConnection = dbManager.connect();
		if(dbConnection == null) {
			System.out.println("Couldn't connect");
			return ;
		}
		// creating array of threads
		Thread[] crawlingThreads=new Thread[numberOfThreads];
		for(Integer i=0;i<numberOfThreads;i++)
		{
			//assigning the array
			crawlingThreads[i] = new Thread(new Crawler());
			// starting the thread
			crawlingThreads[i].start();

		}
	}
	@Override
	public void run() {
		try {
			Crawler.threadWork();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}