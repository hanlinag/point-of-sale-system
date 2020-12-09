package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLconnUtils {



        public static Connection getMySQLConnection()
                throws ClassNotFoundException, SQLException {
            String hostName = Messages.getString("MySQLconnUtils.0"); //$NON-NLS-1$
            String dbName = Messages.getString("MySQLconnUtils.1"); //$NON-NLS-1$
            String userName = Messages.getString("MySQLconnUtils.2"); //$NON-NLS-1$
            String password = Messages.getString("MySQLconnUtils.3"); //$NON-NLS-1$
            return getMySQLConnection(hostName, dbName, userName, password);
        }

        public static Connection getMySQLConnection(String hostName, String dbName,
                String userName, String password) throws SQLException,
                ClassNotFoundException {

            Class.forName(Messages.getString("MySQLconnUtils.4")); //$NON-NLS-1$

            System.out.println(Messages.getString("MySQLconnUtils.5")); //$NON-NLS-1$
            String connectionURL = Messages.getString("MySQLconnUtils.6") + hostName + Messages.getString("MySQLconnUtils.7") + dbName; //$NON-NLS-1$ //$NON-NLS-2$

            Connection conn = DriverManager.getConnection(connectionURL, userName,
                    password);
            return conn;
        }
    }