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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
                    Document temp = new Document("link", e.link)
                            .append("TF", e.TfBody+e.TfHeader+e.TfDescription)
                            .append("TFHeader",e.TfHeader)
                            .append("TFDescription",e.TfDescription)
                            .append("TFBody",e.TfBody);


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
                            .append("TF", e.TfBody+e.TfHeader+e.TfDescription)
                            .append("TFHeader",e.TfHeader)
                            .append("TFDescription",e.TfDescription)
                            .append("TFBody",e.TfBody);
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

        MongoCollection newCollection=connectionMongo();
        int indexedCount=0;
        //outer loop
        Connection dbConnection = connectionMysql();
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

            splitPage splitObject=new splitPage();
            splitObject.split(URLName,titleArr,descriptionArr,bodyArr);
            titleArr=titleArr[0].split(" ");
            descriptionArr=descriptionArr[0].split(" ");
            bodyArr=bodyArr[0].split(" ");
            stemmer stemObject = new stemmer();
            stemObject.stemThis(titleArr);
            stemObject.stemThis(bodyArr);
            stemObject.stemThis(descriptionArr);
            //word {tf title ,tf description , tf body}
            Map<String,int[]> tf = new HashMap<>();
            for (int i = 0; i < titleArr.length; i++) {
                if (tf.containsKey(titleArr[i]))
                    tf.get(titleArr[i])[0]++;
                else
                    tf.put(titleArr[i],new int[]{1,0,0} );
            }
            for (int i = 0; i < bodyArr.length; i++) {
                if (tf.containsKey(bodyArr[i]))
                    tf.get(bodyArr[i])[1]++;
                else
                    tf.put(bodyArr[i],new int[]{0,1,0} );
            }
            for (int i = 0; i < descriptionArr.length; i++) {
                if (tf.containsKey(descriptionArr[i]))
                    tf.get(descriptionArr[i])[2]++;
                else
                    tf.put(descriptionArr[i],new int[]{0,0,1} );
            }
            for (String s : tf.keySet()) {
                int[]arr = tf.get(s);
                entry URLentry = new entry(URLName,s,(double)arr[0],(double)arr[1],(double)arr[2]) ;
                insertToMongo(newCollection,URLentry);
            }

            //continue to make the form required by hany
            //........

        }
    }

}