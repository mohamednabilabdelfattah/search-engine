import java.sql.*;
import java.util.Queue;
import java.util.Scanner;
import java.util.LinkedList;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.lang.Object;

public class Crawler implements Runnable {
	private static Queue<String> URLS = new LinkedList<>();
    public void run() {
    	 
    	System.out.println("Hello from a thread!");

    	// 5. Uncomment the following to see 2 threads running! 
    	
    	for (int i = 0 ; i < 5 ; i++) {
       		System.out.println("Hello from a thread!");
			
			try {
				 //Thread.sleep causes the current thread to suspend execution for a specified period
				 Thread.sleep(1000);                 //1000 milliseconds is one second.
			
			//This is an exception that sleep throws when another thread interrupts the current thread while sleep is active.
			} catch(InterruptedException ex) { 
			   return;
			}
       	}
       	
    }
	
	public static void pushSeedInQueue(Connection dbConnection) throws SQLException {
		
		Statement stmt = dbConnection.createStatement();
		ResultSet result = stmt.executeQuery("SELECT URL FROM akml.urls LIMIT 50;"); // REQUIRED: frequency of visited pages every crawling
		String URLName;
		
		while(result.next()) {
		URLName = result.getString("URL");
		URLS.add(URLName);
		}
	}
	
	public static void pushURLInQueueAndDatabase(Connection dbConnection, String URLName) throws SQLException, IOException {
		//robot.txt
		Statement stmt = dbConnection.createStatement();
		ResultSet result = stmt.executeQuery("SELECT URL FROM akml.urls WHERE URL like'" + URLName + "';"); 
		if(!result.next()) {
			String URLCompactString = Compact_String.extractCompactString(URLName).replaceAll("'", "\\\\\\\\\\\\\\\\\\\\'");// 5*4 = 20 (Every \ needs 3 \ to skip it and we need 5 in query syntax)
			ResultSet resultCompactString = stmt.executeQuery("SELECT compactString FROM akml.urls WHERE compactString like'" + URLCompactString + "';"); 
			if(!resultCompactString.next()) {
				String query = "INSERT INTO akml.urls(URL, compactString)"
				        + " values (?,?)";
				PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
				preparedStmt.setString (1, URLName);
				preparedStmt.setString (2, URLCompactString);
				
				preparedStmt.execute();
				URLS.add(URLName);
			}
		}
	}
	
	
	
	public static void crawl(Integer start) 
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException { // crawlerExecute()
		int numberOfThreads;
		Scanner scan =new Scanner(System.in);
		numberOfThreads=scan.nextInt();
		if(numberOfThreads>50)
			return ;
		
		Thread[] crawlingThreads=new Thread[numberOfThreads];
		for(Integer i=0;i<numberOfThreads;i++)
		{
			crawlingThreads[i]=new Thread(new Crawler());
			crawlingThreads[i].setName(i.toString());
			crawlingThreads[i].start();
		}//that we will see in the next EP. inshaallah
		
		DatabaseConnection dbManager = new DatabaseConnection();
		Connection dbConnection = dbManager.connect();
		pushSeedInQueue(dbConnection);
		int crawlingIndex = 0;
		while(!URLS.isEmpty()) {
			try {
			if(crawlingIndex == 5000)
				break;
			String URLName = URLS.poll();
			Document doc = extract.downloadFile(URLName);
			
			if(doc != null) {
				Elements links = extract.extractLinks(doc);
				String linkName = "";
				int linkURLSIndex = 0;
				for(Element link:links) {
					if(linkURLSIndex==50) {
						break;
					}
					linkName = link.attr("abs:href");
					if((extract.isURLValid(linkName)) && (!RobotParser.robotSafe(new URL(linkName))))
						pushURLInQueueAndDatabase(dbConnection, linkName);
					linkURLSIndex++;
					crawlingIndex++;
				}
			}
			} catch(Exception e) {
				continue;
			}
		}
		//String URLTest = "https://www.bbc.co.uk/";
		
		
	}

}




