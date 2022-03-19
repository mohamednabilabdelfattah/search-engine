import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import javafx.util.Pair;

import org.jsoup.Jsoup;
import java.io.FileWriter;

public class Compact_String 
{
	
	public static String extractCompactString(String URLName) throws IOException
	{
		Document doc = extract.downloadFile(URLName);
		String str=doc.body().text();
		int count=str.length();
		String compact_String="";
		String [] compact_Str=new String[5];
		int j=0;
		if (count > 250)
		{
			for (int i = 0; i < 5; i++)
			{
				compact_Str[i]=doc.body().text().substring(j,j+50);
				j+=count/5;
			}
			for (int i = 0; i < 5; i++)
			{
				compact_String+=compact_Str[i];
			}
		}
		else
			compact_String=str;
		
		return compact_String;
	}
}
