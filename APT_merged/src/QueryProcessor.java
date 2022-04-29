import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.io.IOException;
import java.util.*;

public class QueryProcessor {


    public static void QueryProcessorFunc(String query,Set<String> arrayWords, Set<String> arrayLinks) throws IOException {
        // stop words
        splitPage splitObject=new splitPage();
        String temp = splitObject.readFile(query);
        String[] arr = temp.split(" ");
        // stemming
        stemmer stem = new stemmer();
        stem.stemThis(arr);

        for (int i = 0; i < arr.length; i++) {
            arrayWords.add(arr[i]);
        }

        // Links
        MongoCollection collection = indexer.connectionMongo();
        for (String word : arrayWords) {
            Document found = (Document) collection.find(new Document("_id", word)).first();
            if (found != null){
                ArrayList<Document> links = (ArrayList<Document>) found.get("Links");
                for (Document link : links) {
                    arrayLinks.add((String) link.get("Link"));
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("What do want to search about .....: ");
        String query;
        Scanner scan =new Scanner(System.in);
        query=scan.nextLine();
        scan.close();
        // words after stemming
        Set<String> arrayWords = new HashSet<>();

        // links where words are mentioned
        Set<String> arrayLinks = new  HashSet<>();

        QueryProcessorFunc(query,arrayWords,arrayLinks);
        System.out.println();
        System.out.println(arrayWords);
        System.out.println(arrayLinks);
    }
}
