package database;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

    public static Connection getConnection() throws SQLException,
            ClassNotFoundException {

        // Using Oracle
        // You may be replaced by other Database.
        return MySQLconnUtils.getMySQLConnection();
    }

    //
    // Test Connection ...
    //
   /* public static void main(String[] args) throws SQLException,
            ClassNotFoundException {

        System.out.println("Get connection ... ");

        // Get a Connection object
        Connection conn = ConnectionUtils.getConnection();

        System.out.println("Get connection " + conn);

        System.out.println("Done!");
    }*/
}