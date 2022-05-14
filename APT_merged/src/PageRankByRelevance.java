import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageRankByRelevance {
    public static void splitheaders(String URL, String[] h, int index) throws IOException {
        Document doc = Jsoup.connect(URL).get();
        h[0] = " ";
        if (doc.select("h" + index).toString() != null) {
            splitPage s = new splitPage();
            h[0] = doc.select("h" + index).toString();
            h[0] = s.readFile(h[0]);
        }
    }

    public static double[] calculateIDF(int[] numOfDocContainingTerm, int totalNumOfDoc, String[] q) {

        double[] IDF = new double[q.length];
        for (int i = 0; i < q.length; i++) {
            if (numOfDocContainingTerm[i] != 0)
                IDF[i] = Math.log10((float) totalNumOfDoc / numOfDocContainingTerm[i]);
            else
                IDF[i] = 0;
        }
        return IDF;
    }

    public static ArrayList<pair> rankByRelevance(Set<String> arrayWords, Set<String>arrayLink) throws IOException {
        String uri = "mongodb://localhost:27017";
        MongoClient client = MongoClients.create(uri);
        MongoDatabase db = client.getDatabase("Indexer");
        MongoCollection<org.bson.Document> col = db.getCollection("Words");

        int indexedCount = (int) col.find(new org.bson.Document("_id", "indexedCount")).first().get("Value");


        double [] scores = new double[10];
        double [][] TF = new double[arrayWords.size()][11];

        double [] IDF=new double [arrayWords.size()];

        ArrayList<pair> arr=new ArrayList<>();

        int iter2 = 0;
        double linkIDF;
        for (String link : arrayLink)
        {
            linkIDF = 0;
            for(String word:arrayWords)
            {
                TF[iter2] = MongoConnection.getT_DFScores(word,link);
                IDF[iter2]=Math.log10((double)indexedCount/TF[iter2][10]);
                for(int i = 0 ;i<10;i++)
                    scores[i]+=TF[iter2][i];
                iter2++;
                linkIDF += IDF[iter2];
            }
            iter2 = 0;
            arr.add(new pair(link,scores[0]*linkIDF+scores[1]*0.8+scores[2]*0.6+scores[3]*0.1+scores[4]*0.7+scores[5]*0.6+scores[6]*0.5+scores[7]*0.4+scores[8]*0.3+scores[9]*0.2));
        }

        return arr;
    }
}