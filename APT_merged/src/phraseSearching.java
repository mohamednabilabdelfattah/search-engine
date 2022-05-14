import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class phraseSearching {
    public static String[] phraseSearch(String query,Set<String>arrayWords) throws IOException {

        QueryProcessor basicProcessor =new QueryProcessor();
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(query);
        if (m.find()) {
            query=m.group(1);
        }
        // words after stemming

        // links where words are mentioned
        Set<String> arrayLink = new HashSet<>();
        Set<String> newArrayLink = new HashSet<>();
        QueryProcessor.QueryProcessorFunc(query, arrayWords, arrayLink);



        // phrase searching started here
        for (String link:
                arrayLink) {
            Document currentPage = extract.downloadFile(link);
            String currnetPageContent = currentPage.body().text().toLowerCase();
            if(currnetPageContent.contains(query))
                newArrayLink.add(link);
        }
        return newArrayLink.toArray(new String[0]);
    }
//    public static void main(String[] args) throws IOException {
//        System.out.println("What do want to search about .....: ");
//        String query;
//        Scanner scan = new Scanner(System.in);
//        query = scan.nextLine();
//        scan.close();
//        String result[]=phraseSearch(query);
//        for(String link:result)
//        {
//            System.out.println(link+"\n");
//        }
//
//    }

}
