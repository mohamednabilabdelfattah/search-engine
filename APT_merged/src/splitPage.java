import java.io.*;
import java.util.Scanner;

import jakarta.servlet.ServletContext;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class splitPage {
    @SuppressWarnings("resource")
    //[ToDO] : add the headers
    public String readFile(String object) throws IOException
    {
        object=" "+object+" ";
        object=object.replaceAll("[^a-zA-Z0-9]", " ");
        @SuppressWarnings("resource")
        BufferedReader reader=new BufferedReader(new FileReader("stopwords.txt"));
        String line=reader.readLine();
        while (line != null)
        {
            if(object.contains(line))
            {
                object=object.replaceAll(" "+line+" ", " ");
            }
            line=reader.readLine();
        }
        object=object.replaceAll("\\s+", " ");
        object=object.trim();
        return object;
    }
    public void split (String URL,String[] titleArray, String[] descriptionArray,String[] bodyArray/*,String title,String body,String description*/) throws IOException
    {
        Document doc=Jsoup.connect(URL).get();
//		____________split_title______________


        if (doc.title() != null)
        {
            titleArray[0]=doc.title().toLowerCase();
            titleArray[0]=readFile(titleArray[0]);
        }

//		____________split_description____________

        if(doc.select("meta[name=description]").size() != 0)
        {
            descriptionArray[0]=doc.select("meta[name=description]").get(0).attr("content").toLowerCase();
            descriptionArray[0]=readFile(descriptionArray[0]);
        }

//		____________split_body_____________

        if(doc.body().text() != null)
        {
            bodyArray[0]=doc.body().text().toLowerCase();
            bodyArray[0]=readFile(bodyArray[0]);
        }

    }
}
