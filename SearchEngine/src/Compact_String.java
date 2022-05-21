import java.io.IOException;
import org.jsoup.nodes.Document;


public class Compact_String
{
	public static String extractCompactString(String URLName) throws IOException
	{
		// to download HTML file
		Document doc = extract.downloadFile(URLName);
		// to get the body of the site
		String str=doc.body().text();
		int count=str.length();
		String compact_String="";
		String [] compact_Str=new String[5];
		int j=0;
		// if it is less than 250 it will take it all
		// if its more than 250 it will divide it to 5 sections takes 50 word from each one
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
		{
			compact_String=str;
		}
		return compact_String;
	}
}