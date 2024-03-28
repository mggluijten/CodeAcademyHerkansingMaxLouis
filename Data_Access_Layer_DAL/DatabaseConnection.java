package Data_Access_Layer_DAL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.PreparedStatement;

public class DatabaseConnection {
    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // Adjusted DB_URL for local server and integrated security
    private static final String DB_URL = "jdbc:sqlserver://.;databaseName=CodecademyMaxLouis;integratedSecurity=true;";
    // USER and PASSWORD constants are not needed for Windows Authentication

    private Connection connection;
    private Statement statement;

    public DatabaseConnection() {
        openConnection();
    }

    public boolean openConnection() {
        try {
            // Laad de JDBC driver
            Class.forName(JDBC_DRIVER);

            // Maak connectie met de database using Windows Authentication (integrated security)
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement(); // Initialisatie van statement
            System.out.println("Connection made successfully!");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("An ERROR has occurred, trying to connect to the database:");
            e.printStackTrace();
            return false;
        }
    }


//Deze code laat de gegevens uit de database zien in Cursist- en Cursusoverzicht
public CachedRowSet executeSQLSelectStatement(String query) throws SQLException {
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

        CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
        cachedRowSet.populate(resultSet);

        return cachedRowSet;
    }
}



public ResultSet executeSQLSelectStatement(String query, Object... params) {
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    try {
        if (query != null && connectionIsOpen()) {
            preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeResultSet(resultSet);
        closeStatement(preparedStatement);
    }

    return resultSet;
}

public int executeSQLUpdateStatement(String query) {
    int rowsAffected = 0;

    // Controleer eerst of er een query is opgegeven en of de verbinding met
    // de database is geopend.
    if (query != null && connectionIsOpen()) {
        // Probeer vervolgens de query uit te voeren.
        try {
            rowsAffected = statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
            rowsAffected = 0;
        }
    }

    return rowsAffected;
}

public void closeResources() {
    closeStatement(statement);
    closeConnection();
}



public void closeConnection() {
    try {   if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connection closed successfully!");
        } 
    } catch (SQLException e) {
        System.out.println("An ERROR has occurred while closing the connection:");
        e.printStackTrace();
    }
}

private boolean connectionIsOpen() {
    try {
        return connection != null && !connection.isClosed();
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

private void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
    for (int i = 0; i < params.length; i++) {
        preparedStatement.setObject(i + 1, params[i]);
    }
}

private void closeResultSet(ResultSet resultSet) {
    try {
        if (resultSet != null) {
            resultSet.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void closeStatement(Statement statement) {
    try {
        if (statement != null) {
            statement.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public Connection getConnection() {
    return connection;
}

public PreparedStatement prepareStatement(String query) throws SQLException {
    if (query != null && connectionIsOpen()) {
        return connection.prepareStatement(query);
    }
    return null;
}


}