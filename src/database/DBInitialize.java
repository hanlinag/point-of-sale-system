package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitialize {
	public static Statement statement;

	public void DBInitialize()
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		// Load the JDBC driver
		Class.forName(Messages.getString("DBInitialize.0")).newInstance(); //$NON-NLS-1$
		System.out.println(Messages.getString("DBInitialize.1")); //$NON-NLS-1$
		// Connect to a database
		// Connection connection = DriverManager.getConnection
		// ("jdbc:mysql://localhost:3306/acpcepos","root","");
		Connection connection = DriverManager.getConnection(Messages.getString("DBInitialize.2"), //$NON-NLS-1$
				Messages.getString("DBInitialize.4"), "");
		System.out.println(Messages.getString("DBInitialize.3")); // Create a statement //$NON-NLS-1$
		statement = connection.createStatement();
		// Execute a statement
	}
}