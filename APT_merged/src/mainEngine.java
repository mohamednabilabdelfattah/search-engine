import java.io.IOException;
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
    public static String[] SearchEngine(String query) throws IOException {
        //check if the query contain phrases or not
        if(!query.contains("\""))
        {
            //if not
            //will run queryprocessor only and return the result

            // words after stemming
            Set<String> arrayWords = new HashSet<>();
            // links where words are mentioned
            Set<String> arrayLink = new HashSet<>();

            QueryProcessor.QueryProcessorFunc(query,arrayWords,arrayLink);
            return arrayLink.toArray(new String[0]);
        }

        //here we are sure that the query has phrases
        ArrayList<String>phrases = new ArrayList<>();
        ArrayList<String>links = new ArrayList<>();
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
            currentLinks=phraseSearching.phraseSearch(phrase);
            for(String link:currentLinks)
            {
                links.add(link);
            }
        }

        return links.toArray(new String[0]);
    }
    public static void main(String []args) throws IOException {
        System.out.println("What do want to search about .....: ");
        String query;
        Scanner scan = new Scanner(System.in);
        query = scan.nextLine();
        scan.close();
        String result[]=SearchEngine(query);
    }
}
