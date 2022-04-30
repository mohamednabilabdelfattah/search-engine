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
    public static void main(String[] args) throws IOException {
        QueryProcessor basicProcessor =new QueryProcessor();
        System.out.println("What do want to search about .....: ");
        String query;
        Scanner scan = new Scanner(System.in);
        query = scan.nextLine();
        scan.close();
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(query);
        if (m.find()) {
            query=m.group(1);
        }
        // words after stemming
        Set<String> arrayWords = new HashSet<>();

        // links where words are mentioned
        Set<String> arrayLink = new HashSet<>();
        Set<String> newArrayLink = new HashSet<>();

        basicProcessor.QueryProcessorFunc(query, arrayWords, arrayLink);


        // phrase searching started here
        for (String link:
             arrayLink) {
            Document currentPage = extract.downloadFile(link);
            String currnetPageContent = currentPage.body().text();
            if(currnetPageContent.contains(query))
                newArrayLink.add(link);
        }
        for (String link:
             newArrayLink) {
            System.out.println(link);

        }
    }

}
