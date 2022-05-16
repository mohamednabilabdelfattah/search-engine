import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class script {
    public static void main(String[]args) throws SQLException, ClassNotFoundException, IOException, InterruptedException {
        Connection dbConnection;
        DatabaseConnection dbManager;
        dbManager = new DatabaseConnection();
        dbConnection = dbManager.connect();

        Statement stmt = dbConnection.createStatement();
        stmt.execute("DELETE FROM akml.crawledurls;");
        stmt.execute("DELETE FROM akml.noncrawledurls;");
        //read file
        BufferedReader reader=new BufferedReader(new FileReader("seedSet.txt"));
        String line = " ";
        String line2 = " ";
        while (line != null&&line2!=null) {
            line = reader.readLine();
            line2 = reader.readLine();
            System.out.println(line);
            System.out.println(line2);

            try {
                DBManager.insertintoNonCrawledURLs(dbConnection,line,line2);
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
                return;
            }

        }
    }
}
