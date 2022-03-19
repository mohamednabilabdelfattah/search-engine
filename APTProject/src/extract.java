import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URL;
import java.lang.Object;

import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class extract {
	
	public static boolean isURLValid(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }

        catch (Exception e) {
            return false;
        }
    }
	
	public static Document downloadFile(String URLName){
		try {
		Connection con=Jsoup.connect(URLName);
		Document doc = con.get();
		return doc;
		}catch(Exception e) {
			System.out.println("Error in downloading!");
			System.out.println(e);
			return null;
		}
	}
	
	
	public static Elements extractLinks(Document doc) throws IOException // you have to create the file first
	{
		Elements links = doc.select("a[href]");
        return links;
	}
}
