import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;

public class MongoConnection {
    public static double[] getT_DFScores(String word,String link)
    {
        MongoCollection collection = indexer.connectionMongo();
        double []arr = new double[11];
        Document found = (Document) collection.find(new Document("_id", word)).first();
        if (found != null){
            ArrayList<Document> links = (ArrayList<Document>) found.get("Links");
            for (Document currentLink : links) {
                if(link.equals((String)currentLink.get("Link"))) {
                    arr[0] = (double) currentLink.get("TF");
                    arr[1] = (double) currentLink.get("TFTitle");
                    arr[2] = (double) currentLink.get("TFDescription");
                    arr[3] = (double) currentLink.get("TFBody");
                    arr[4] = (double) currentLink.get("TFh1");
                    arr[5] = (double) currentLink.get("TFh2");
                    arr[6] = (double) currentLink.get("TFh3");
                    arr[7] = (double) currentLink.get("TFh4");
                    arr[8] = (double) currentLink.get("TFh5");
                    arr[9] = (double) currentLink.get("TFh6");
                    arr[10] = (double) found.get("DF");
                    return arr;
                }
            }
        }

        return null;
    }
}
