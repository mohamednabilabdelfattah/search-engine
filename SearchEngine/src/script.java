import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class script {
    public static void scriptFunction() throws SQLException, ClassNotFoundException, IOException {
        Connection dbConnection;
        DatabaseConnection dbManager;
        dbManager = new DatabaseConnection();
        dbConnection = dbManager.connect();

        Statement stmt = dbConnection.createStatement();
        stmt.execute("DELETE FROM akml.crawledurls;");
        stmt.execute("DELETE FROM akml.crawledurlsv2;");
        stmt.execute("DELETE FROM akml.noncrawledurls;");
        stmt.execute("DELETE FROM akml.pagerank;");
        stmt.execute("DELETE FROM akml.indexed;");
        //read file
        BufferedReader reader=new BufferedReader(new FileReader("seedSet.txt"));
        String line = " ";
        String line2 = " ";
        while (line != null&&line2!=null) {
            line = reader.readLine();
            line2 = reader.readLine();
            try {
                DBManager.insertintoNonCrawledURLs(dbConnection,line,line2);
            }
            catch (Exception e)
            {
                return;
            }

        }
    }
    public static void main(String[]args) throws SQLException, ClassNotFoundException, IOException, InterruptedException {
        scriptFunction();
    }
}
