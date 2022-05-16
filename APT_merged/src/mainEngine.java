import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class mainEngine {
    /*
    * here we collect the query processor and the phrase searching to handle the case of entering multiple phrases
    * in the same query and check also if any phrases exist or not
    */


    //this function that will be called by the interface inshallah
    public static ArrayList<String> SearchEngine(String query) throws IOException, SQLException, ClassNotFoundException {
        query=query.toLowerCase();
        //check if the query contain phrases or not

        DatabaseConnection db = new DatabaseConnection();
        // words after stemming
        Set<String> arrayWords = new HashSet<>();
        // links where words are mentioned
        Set<String> arrayLink = new HashSet<>();

        if(!query.contains("\""))
        {
            //if not
            //will run queryprocessor only and return the result

            QueryProcessor.QueryProcessorFunc(query,arrayWords,arrayLink);
            ArrayList<pair> result = PageRankByRelevance.rankByRelevance(arrayWords,arrayLink);
            return pageRank.rankByPopluarity(db.connect(),result);
        }

        //here we are sure that the query has phrases
        ArrayList<String>phrases = new ArrayList<>();
        int iter = 0;

        while (iter<= query.length()){
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(query.substring(iter));
            String currentPhrase = "";
            if (m.find()) {
                currentPhrase = m.group(1);
                phrases.add(currentPhrase);
                iter = query.indexOf(currentPhrase)+currentPhrase.length()+1;
            }
            else{
                break;
            }
        }
        String []currentLinks;
        for(String phrase:phrases)
        {
            currentLinks=phraseSearching.phraseSearch(phrase,arrayWords);
            for(String link:currentLinks)
            {
                arrayLink.add(link);
            }
        }

        ArrayList<pair> result;
        result = PageRankByRelevance.rankByRelevance(arrayWords,arrayLink);
        return pageRank.rankByPopluarity(db.connect(),result);
    }
    public static void main(String []args) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("What do want to search about .....: ");
        String query;
        Scanner scan = new Scanner(System.in);
        query = scan.nextLine();

        scan.close();
        ArrayList<String> result = SearchEngine(query);
        for(String link: result)
        {
            System.out.println(link);
        }
    }
}
