import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class PageRankByRelevance {
    public static ArrayList<pair> rankByRelevance(Set<String> arrayWords, Set<String>arrayLink) throws IOException {
        String uri = "mongodb://localhost:27017";
        MongoClient client = MongoClients.create(uri);
        MongoDatabase db = client.getDatabase("Indexer");
        MongoCollection<org.bson.Document> col = db.getCollection("Words");

        int indexedCount = (int) col.find(new org.bson.Document("_id", "indexedCount")).first().get("Value");


        double [] scores = new double[10];
        double [][] TF = new double[arrayWords.size()][11];

        double [] IDF_TF=new double [arrayWords.size()];

        ArrayList<pair> arr=new ArrayList<>();

        int word_iter=0;
        double linkTF_IDF;
        for (String link : arrayLink)
        {
            linkTF_IDF = 0;
            for(String word:arrayWords)
            {
                TF[word_iter] = MongoConnection.getT_DFScores(word,link);
                if(TF[word_iter]==null) {   //if null then the link isn't in the links of other words
                    IDF_TF[word_iter] = 0;
                    word_iter++;
                    continue;
                }
                IDF_TF[word_iter]=(Math.log10((double)indexedCount/(TF[word_iter][10])))*(Math.sqrt(TF[word_iter][0]));
                for(int i = 1 ;i<10;i++)
                    scores[i]+=TF[word_iter][i];

                linkTF_IDF += IDF_TF[word_iter];
                word_iter++;
            }
            word_iter = 0;
            System.out.println(link);
            if(Double.isNaN(scores[2]))
                scores[2] = 0;
            System.out.println(scores[1]*0.8+scores[2]*0.6+scores[3]*0.1+scores[4]*0.7+scores[5]*0.6+scores[6]*0.5+scores[7]*0.4+scores[8]*0.3+scores[9]*0.2);
            arr.add(new pair(link,linkTF_IDF+scores[1]*0.8+scores[2]*0.6+scores[3]*0.1+scores[4]*0.7+scores[5]*0.6+scores[6]*0.5+scores[7]*0.4+scores[8]*0.3+scores[9]*0.2));
            Arrays.fill(scores, 0);
        }

        return arr;
    }
}