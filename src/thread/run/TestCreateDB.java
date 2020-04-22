package thread.run;

import java.sql.Statement;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.sqlite.JDBC.PREFIX;

/**
 *
 * @author ahbuss
 */
public class TestCreateDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String dbName = args.length > 0 ? args[0]: "output/SimOut.SQLite";
        File dbFile = new File(dbName);
        String url = PREFIX.concat(dbName);
        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            
            String createTable = "CREATE TABLE IF NOT EXISTS SimOutput (SimID INTEGER, Name TEXT, Value REAL, Replications INTEGER)";
            statement.executeUpdate(createTable);
            
            PreparedStatement ps = connection.prepareStatement("INSERT INTO SimOutput VALUES (?, ?, ?, ?)");
            ps.setInt(1, 12);
            ps.setString(2, "Foo");
            ps.setDouble(3, 42.0);
            ps.setInt(4, 10);
            ps.executeUpdate();
            
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(TestCreateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
