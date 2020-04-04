/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Made with Singleton Pattern
 *
 * @author Beast
 */
public class DatabaseConnectionModel {

    public static DatabaseConnectionModel instance;
    public Connection connection;
    private String url =  "jdbc:oracle:thin:@localhost:1521:xe";
    //private String url = "jdbc:oracle:thin:@25.43.240.217:1521:xe";
    //private String url = "jdbc:oracle:thin:@25.80.70.211:1521:xe";
    //private String url =  "jdbc:oracle:thin:@localhost:1521:xe";
    private String username = "javadev";
    private String password = "javadev";

    public DatabaseConnectionModel() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            System.out.println("Database Connection Creation Failed: " + ex.getMessage());
        } catch (Exception e) {
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConnectionModel getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnectionModel();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnectionModel();
        }
        return instance;
    }

}
