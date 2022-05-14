import com.mongodb.BasicDBObject;
import com.mongodb.DocumentToDBRefTransformer;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Filter;

import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;

public class indexer {
//outer loop
    //retrieve from crawled --< done
    //call split page --> done
    //call stemmer
    //insert  into mongo
//end outer loop

    public static int indexedCount;


    public static Connection connectionMysql() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbManager;
        dbManager = new DatabaseConnection();
        return dbManager.connect();
    }


    public static MongoCollection connectionMongo()
    {
        String uri = "mongodb://localhost:27017";
        MongoClient client = MongoClients.create(uri);
        MongoDatabase db = client.getDatabase("Indexer");
        MongoCollection<Document> col = db.getCollection("Words");
//        Document NewEntry =new Document("_id","TotalNumberDoc").append("Number",0);
//        col.insertOne(NewEntry);
        return col;
    }



    public static void insertToMongo(MongoCollection col, entry e) throws SQLException, ClassNotFoundException {

            //check if the word is in database
            //if true --> increment df and insert the new entry
            //if false --> initialize df =0 and insert the new entry
            //increment the total number of document
            //calculate idf

            //int TotalNumDoc = 5000; // DBManager.getNumberOfCrawledPages(dbConnection);//edited

            try {
                Document found = (Document) col.find(new Document("_id", e.word)).first();
                if (found != null){
                    // exists
                    Document NewDoc = found;
                    Double df = NewDoc.getDouble("DF");
                    df++;
                    //Double idf = Math.log(TotalNumDoc/df);
                    NewDoc.put("DF",df);
                    Document temp = new Document("Link", e.link)
                            .append("TF", e.TfBody+e.TfTitle)
                            .append("TFTitle",e.TfTitle)
                            .append("TFDescription",e.TfDescription)
                            .append("TFBody",e.TfBody)
                            .append("TFh1",e.TFh1)
                            .append("TFh2",e.TFh2)
                            .append("TFh3",e.TFh3)
                            .append("TFh4",e.TFh4)
                            .append("TFh5",e.TFh5)
                            .append("TFh6",e.TFh6);


                    ArrayList<Document> arr = (ArrayList<Document>) NewDoc.get("Links");
                    arr.add(temp);

                    Document query = new Document("_id", e.word);

                    Document updateCommand = new Document("$set",NewDoc);
                    col.updateOne(query,updateCommand);

                }
                else{
                    // doesn't exist

                    Double df = 1.00;
                    //Double idf = Math.log(TotalNumDoc/df);
                    Document temp = new Document("Link", e.link)
                            .append("TF", e.TfBody+e.TfTitle)
                            .append("TFTitle",e.TfTitle)
                            .append("TFDescription",e.TfDescription)
                            .append("TFBody",e.TfBody)
                            .append("TFh1",e.TFh1)
                            .append("TFh2",e.TFh2)
                            .append("TFh3",e.TFh3)
                            .append("TFh4",e.TFh4)
                            .append("TFh5",e.TFh5)
                            .append("TFh6",e.TFh6);
                    Document NewEntry1 =new Document("_id",e.word)
                            .append("DF",df)
                            .append("Links",asList(temp));
                    col.insertOne(NewEntry1);
                }
            }
            catch (Exception q)
            {
                System.out.println(q);
            }


        // number of documents


        //idf = df/number of total document is search index

        // word first time added create object platinize df = 0
        // not first time incre df

        // variable total document  = 0 -> ++

        //  ({news,idf} : {link,tf,position},{link,tf,position},.......)


    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException, IOException {

        indexedCount=0;
        MongoCollection newCollection=connectionMongo();

        //outer loop
        Connection dbConnection = connectionMysql();
        //to test
        //Set<String> hash_Set = new HashSet<String>();
        while(true)
        {
            ResultSet result;
            try {
                //get one URL by the thread
                result = DBManager.getOneURLFromCrawled(dbConnection);
            } catch (Exception e) {
                break;
            }
            if(!result.next()) {
                break;
            }
            indexedCount++;
            String URLName = result.getString("URL");
            String [] bodyArr=new String[1];
            bodyArr[0]=" ";
            String [] titleArr=new String[1];
            titleArr[0]=" ";
            String [] descriptionArr=new String[1];
            descriptionArr[0]=" ";
            String[] h1=new String [1];
            String[] h2=new String [1];
            String[] h3=new String [1];
            String[] h4=new String [1];
            String[] h5=new String [1];
            String[] h6=new String [1];
            h1[0]=" ";
            h2[0]=" ";
            h3[0]=" ";
            h4[0]=" ";
            h5[0]=" ";
            h6[0]=" ";
            splitPage splitObject=new splitPage();
            splitObject.splitheaders(URLName,h1,1);
            splitObject.splitheaders(URLName,h2,2);
            splitObject.splitheaders(URLName,h3,3);
            splitObject.splitheaders(URLName,h4,4);
            splitObject.splitheaders(URLName,h5,5);
            splitObject.splitheaders(URLName,h6,6);
            splitObject.split(URLName,titleArr,descriptionArr,bodyArr);
            titleArr=titleArr[0].split(" ");
            descriptionArr=descriptionArr[0].split(" ");
            bodyArr=bodyArr[0].split(" ");
            h1=h1[0].split(" ");
            h2=h2[0].split(" ");
            h3=h3[0].split(" ");
            h4=h4[0].split(" ");
            h5=h5[0].split(" ");
            h6=h6[0].split(" ");
            stemmer stemObject = new stemmer();
            stemObject.stemThis(titleArr);
            stemObject.stemThis(bodyArr);
            stemObject.stemThis(descriptionArr);
            stemObject.stemThis(h1);
            stemObject.stemThis(h2);
            stemObject.stemThis(h3);
            stemObject.stemThis(h4);
            stemObject.stemThis(h5);
            stemObject.stemThis(h6);
            //word {tf title ,tf description , tf body}
            Map<String,int[]> tf = new HashMap<>();
            for (int i = 0; i < titleArr.length; i++) {
                if (tf.containsKey(titleArr[i]))
                    tf.get(titleArr[i])[0]++;
                else
                    tf.put(titleArr[i],new int[]{1,0,0,0,0,0,0,0,0} );
            }
            for (int i = 0; i < bodyArr.length; i++) {
                if (tf.containsKey(bodyArr[i]))
                    tf.get(bodyArr[i])[1]++;
                else
                    tf.put(bodyArr[i],new int[]{0,1,0,0,0,0,0,0,0} );
            }
            for (int i = 0; i < descriptionArr.length; i++) {
                if (tf.containsKey(descriptionArr[i]))
                    tf.get(descriptionArr[i])[2]++;
                else
                    tf.put(descriptionArr[i],new int[]{0,0,1,0,0,0,0,0,0} );
            }

            for (int i = 0; i < h1.length; i++) {
                if (tf.containsKey(h1[i]))
                    tf.get(h1[i])[3]++;
                else
                    tf.put(h1[i],new int[]{0,0,0,1,0,0,0,0,0} );
            }
            for (int i = 0; i < h2.length; i++) {
                if (tf.containsKey(h2[i]))
                    tf.get(h2[i])[4]++;
                else
                    tf.put(h2[i],new int[]{0,0,0,0,1,0,0,0,0} );
            }
            for (int i = 0; i < h3.length; i++) {
                if (tf.containsKey(h3[i]))
                    tf.get(h3[i])[5]++;
                else
                    tf.put(h3[i],new int[]{0,0,0,0,0,1,0,0,0} );
            }
            for (int i = 0; i < h4.length; i++) {
                if (tf.containsKey(h4[i]))
                    tf.get(h4[i])[6]++;
                else
                    tf.put(h4[i],new int[]{0,0,0,0,0,0,1,0,0} );
            }
            for (int i = 0; i < h5.length; i++) {
                if (tf.containsKey(h5[i]))
                    tf.get(h5[i])[7]++;
                else
                    tf.put(h5[i],new int[]{0,0,0,0,0,0,0,1,0} );
            }
            for (int i = 0; i < h6.length; i++) {
                if (tf.containsKey(h6[i]))
                    tf.get(h6[i])[8]++;
                else
                    tf.put(h6[i],new int[]{0,0,0,0,0,0,0,0,1} );
            }
            for (String s : tf.keySet()) {
                int[]arr = tf.get(s);
                entry URLentry = new entry(URLName,s,(double)arr[0]/titleArr.length,(double)arr[1]/bodyArr.length,(double)arr[2]/ descriptionArr.length,(double)arr[3]/ h1.length,(double)arr[4]/ h2.length,(double)arr[5]/ h3.length,(double)arr[6]/ h4.length,(double)arr[7]/ h5.length,(double)arr[8]/ h6.length);
                //to test
                //hash_Set.add(s);
                insertToMongo(newCollection,URLentry);
            }
            //remove the link
            DBManager.deleteRowCrawled(dbConnection,URLName);
            //continue to make the form required by hany
            //........

        }
        //      System.out.println(hash_set.size());

        Document indexedCountQuery = new Document("_id", "indexedCount").append("Value",indexedCount);
        newCollection.insertOne(indexedCountQuery);

    }

}