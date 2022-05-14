import java.sql.Connection;
import java.sql.SQLException;

public class script {
    public static void main(String[]args) throws SQLException, ClassNotFoundException {
        Connection dbConnection;
        DatabaseConnection dbManager;
        dbManager = new DatabaseConnection();
        dbConnection = dbManager.connect();
        DBManager.insertintoNonCrawledURLs(dbConnection,"https://www.wikipedia.org/","bolbol");
        DBManager.deleteRowCrawled(dbConnection,"https://www.wikipedia.org/");
    }
}
