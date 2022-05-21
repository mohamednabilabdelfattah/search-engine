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



public class indexer implements Runnable {

//outer loop
    //retrieve from crawled --< done
    //call split page --> done
    //call stemmer
    //insert  into mongo
//end outer loop


    Object lock = new Object();

    public static Connection connectionMysql() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbManager;
        dbManager = new DatabaseConnection();
        return dbManager.connect();
    }


    public synchronized ResultSet data(Connection dbConnection) throws SQLException {
        //get one URL by the thread

        ResultSet result;
        String URLName = null;
        String compactString = null;

        result = DBManager.getOneURLFromCrawled(dbConnection);
        if (!result.next())
        {
            return null;
        }
        URLName = result.getString("URL");
        compactString = result.getString("compactString");

        DBManager.deleteRowCrawled(dbConnection, URLName);

        DBManager.insertintoIndexed(dbConnection, URLName, compactString);

        return result;
    }

    public void run()
    {
        Connection dbConnection = null;

        try {
            dbConnection = connectionMysql();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MongoCollection newCollection=connectionMongo();

        while(true) {
            ResultSet result;
            String URLName = null;
            String compactString = null;
            try {
                result = data(dbConnection);
                if (result == null) {
                    break;
                }
                URLName = result.getString("URL");
                compactString = result.getString("compactString");

            } catch (Exception e) {
                break;
            }
            String h6Text = "",h5Text="",h4Text="",h3Text="",h2Text="",h1Text="",descriptionText="",bodyText="",titleText="";
            try {
                URLName = result.getString("URL");
                titleText = result.getString("title");
                bodyText = result.getString("body");
                descriptionText = result.getString("description");
                h1Text = result.getString("h1"); h2Text = result.getString("h2"); h3Text = result.getString("h3");
                h4Text = result.getString("h4"); h5Text = result.getString("h5"); h6Text = result.getString("h6");
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
            String [] bodyArr=new String[1];
            bodyArr[0]=" ";
            String [] titleArr=new String[1];
            titleArr[0]=" ";
            String [] descriptionArr=new String[1];
            descriptionArr[0]=" ";
            String[] h1=new String [1]; String[] h2=new String [1]; String[] h3=new String [1];
            String[] h4=new String [1]; String[] h5=new String [1]; String[] h6=new String [1];
            h1[0]=" "; h2[0]=" "; h3[0]=" "; h4[0]=" ";
            h5[0]=" "; h6[0]=" ";
            splitPage splitObject=new splitPage();
            try {
                splitObject.splitheaders(h1Text, h1);splitObject.splitheaders(h2Text, h2);splitObject.splitheaders(h3Text, h3);
                splitObject.splitheaders(h4Text, h4); splitObject.splitheaders(h5Text, h5); splitObject.splitheaders(h6Text, h6);
                splitObject.split(titleText.toLowerCase(), bodyText.toLowerCase(), descriptionText, titleArr, descriptionArr, bodyArr);
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }titleArr=titleArr[0].split(" ");
            descriptionArr=descriptionArr[0].split(" ");
            bodyArr=bodyArr[0].split(" ");
            h1=h1[0].split(" "); h2=h2[0].split(" ");
            h3=h3[0].split(" "); h4=h4[0].split(" ");
            h5=h5[0].split(" "); h6=h6[0].split(" ");
            stemmer stemObject = new stemmer();
            stemObject.stemThis(titleArr); stemObject.stemThis(bodyArr); stemObject.stemThis(descriptionArr);
            stemObject.stemThis(h1); stemObject.stemThis(h2); stemObject.stemThis(h3);
            stemObject.stemThis(h4); stemObject.stemThis(h5); stemObject.stemThis(h6);
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
                int[] arr = tf.get(s);
                entry URLentry = new entry(URLName, s, (double) arr[0] / titleArr.length, (double) arr[1] / bodyArr.length, (double) arr[2] / descriptionArr.length, (double) arr[3] / h1.length, (double) arr[4] / h2.length, (double) arr[5] / h3.length, (double) arr[6] / h4.length, (double) arr[7] / h5.length, (double) arr[8] / h6.length);
                //to test
                try {
                    insertToMongo(newCollection, URLentry);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static MongoCollection connectionMongo()
    {
        String uri = "mongodb://localhost:27017";
        MongoClient client = MongoClients.create(uri);
        MongoDatabase db = client.getDatabase("Indexer");
        MongoCollection<Document> col = db.getCollection("Words");
        return col;
    }



    public synchronized static void insertToMongo(MongoCollection col, entry e) throws SQLException, ClassNotFoundException {


        if(e.word.equals(""))
            return;
        //check if the word is in database
        //if true --> increment df and insert the new entry
        //if false --> initialize df =0 and insert the new entry
        //increment the total number of document
        //calculate idf

        if((e.TfBody+e.TfTitle) >= 0.5)
            return;

        try {
            Document found = (Document) col.find(new Document("_id", e.word)).first();
            if (found != null){
                // exists
                Document NewDoc = found;
                ArrayList<Document> arr = (ArrayList<Document>) NewDoc.get("Links");
                for (Document doc : arr)
                {
                    if(doc.get("Link").equals(e.link))
                        return;
                }
                Double df = NewDoc.getDouble("DF");
                df++;
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

                arr.add(temp);

                Document query = new Document("_id", e.word);

                Document updateCommand = new Document("$set",NewDoc);
                col.updateOne(query,updateCommand);

            }
            else{
                // doesn't exist

                Double df = 1.00;
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
    }

    public static void index(int numberOfThreads) throws InterruptedException, SQLException, ClassNotFoundException {

        Thread.currentThread().setName("main Thread");
        Thread[] threads = new Thread[numberOfThreads];
        for(int i = 0 ;i<threads.length;i++)
        {
            threads[i] = new Thread(new indexer());
            threads[i].setName(String.valueOf(i));
            threads[i].start();
        }

        for(int i = 0 ;i<threads.length;i++)
        {
            threads[i].join();
        }
        Connection dbConnection = null;

        dbConnection = connectionMysql();
        int indexedCount = DBManager.getNumOfIndexed(dbConnection);


        MongoCollection<Object> col = connectionMongo();
        Document found = (Document) col.find(new Document("_id", "indexedCount")).first();
        if (found != null){
            // exists
            Document indexedCountQuery = new Document("_id", "indexedCount").append("Value",indexedCount);
            Document query = new Document("_id", "indexedCount");
            Document updateCommand = new Document("$set",indexedCountQuery);
            col.updateOne(query,updateCommand);

        }
        else{
            // doesn't exist
            Document indexedCountQuery = new Document("_id", "indexedCount").append("Value",indexedCount);
            col.insertOne(indexedCountQuery);
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException, IOException {

        index(50);
    }

}