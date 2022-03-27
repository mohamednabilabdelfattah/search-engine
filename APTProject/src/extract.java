import java.io.IOException;  // Import the IOException class to handle errors
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.net.URL;
//import java.io.File;  // Import the File class
//import org.jsoup.nodes.Element;
//import java.lang.Object;
//import java.io.FileNotFoundException;  // Import this class to handle errors
//import java.util.Scanner; // Import the Scanner class to read text files

public class extract {
	public static boolean isURLValid(String url)
    {
		// check for valid URL by trying to cast it to URL
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
		// download file and return it in doc to work on it
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
		// select the links
		Elements links = doc.select("a[href]");
        return links;
	}
}
