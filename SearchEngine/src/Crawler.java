import java.sql.*;
import java.util.Scanner;
import java.io.*;
import java.net.URL;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static java.lang.Thread.sleep;

public class Crawler implements Runnable{
	private static Connection dbConnection;
	private static DatabaseConnection dbManager;

	public static void threadWork() throws SQLException {
		while(true) {
			ResultSet result;
			try {
				//get one URL by the thread
				if(DBManager.getNumOfCrawledURLS(dbConnection) >= 5000)
					return;
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
			if(!result.next()) {
				try {
					// insert URL into crawledURLS
					DBManager.insertintoCrawledURLsV2(dbConnection, URLCompactString, URLName, doc.title(), doc.body().text(), doc.select("meta[name=description]").get(0).attr("content").toLowerCase(), doc.select("h1").toString().toLowerCase(), doc.select("h2").toString().toLowerCase(), doc.select("h3").toString().toLowerCase(), doc.select("h4").toString().toLowerCase(), doc.select("h5").toString().toLowerCase(), doc.select("h6").toString().toLowerCase());
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
		String URLCompactString = Compact_String.extractCompactString(URLName).replaceAll("'", "\\\\\\\\\\\\\\\\\\\\'");// 5*4 = 20 (Every \ needs 3 \ to skip it and we need 5 in query syntax)
		try {
			// inserting to non crawled
			DBManager.insertintoNonCrawledURLs(dbConnection,URLName,URLCompactString);
		} catch (Exception e) {
			return;
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, InterruptedException { // crawlerExecute()
		System.out.println("Enter number of Threads: ");
		int numberOfThreads;
		Scanner scan =new Scanner(System.in);
		numberOfThreads=scan.nextInt();
		scan.close();
		if(numberOfThreads>100) {
			System.out.println("Number of Threads is too much");
			return ;
		}
		dbManager = new DatabaseConnection();
		dbConnection = dbManager.connect();
		while(true) {
			int count = DBManager.getNumOfCrawledURLS(dbConnection);
			if(count >= 5000 || count == 0)
			{
				script.scriptFunction();
			}
			if (dbConnection == null) {
				System.out.println("Couldn't connect");
				return;
			}
			// creating array of threads
			Thread[] crawlingThreads = new Thread[numberOfThreads];
			for (Integer i = 0; i < numberOfThreads; i++) {
				//assigning the array
				crawlingThreads[i] = new Thread(new Crawler());
				// starting the thread
				crawlingThreads[i].start();

			}
			for(int i = 0 ;i<numberOfThreads;i++)
			{
				crawlingThreads[i].join();
			}
			indexer.index(numberOfThreads);
			sleep(18000000);
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