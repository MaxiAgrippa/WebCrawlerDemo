import java.sql.*;
import java.util.ArrayList;

/**
 * @author Maxi Agrippa
 */

/**
 * SqLite database store url and text
 */
public final class Database {
    /**
     * Table Structure:
     * <p>
     * UrlTextTable
     * id(integer), url(text), text(text)
     * TablesTable
     * id(integer), table_name(text)
     */

    // Singleton Mode
    private static Database DATABASE = new Database();
    // Database Dictionary URL
    String DATABASE_DICTIONARY_URL = "jdbc:sqlite:./Database/";
    // Database URL
    String DATABASE_URL = "jdbc:sqlite:./Database/test.db";

    // Singleton Mode, Don't let anyone implement this
    private Database() {
        CreateTablesTable();
        CreateNewDatabase("test.db");
        CreateNewURLTextTable();
    }

    // Singleton Mode, Get Instance
    public static Database getInstance() {
        return DATABASE;
    }

    // Create a new Database
    private void CreateNewDatabase(String fileName) {
        // Form the url of the database
        String url = DATABASE_DICTIONARY_URL + fileName;
        try {
            // Try to connect it.
            Connection connection = DriverManager.getConnection(url);
            // When there is a connection
            if (connection != null) {
                // get metadata from the connection.
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                // print it.
                System.out.println("The driver name is " + databaseMetaData.getDriverName());
                System.out.println("A new database has been created.");
                DATABASE_URL = url;
            }
            // close connection when it's not closed
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("CreateNewDatabase() " + e.getMessage());
        }
    }

    // Create a table that indicate other table
    private void CreateTablesTable() {
        // form the sql command
        String sql = "CREATE TABLE IF NOT EXISTS TablesTable (\n" + "    id integer PRIMARY KEY,\n" + "    table_name text NOT NULL\n" + ");";
        try {
            // try to create a connection
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            // try to claim a statement
            Statement statement = connection.createStatement();
            // try to execute sql
            statement.execute(sql);
            // print result
            System.out.println("Table: TablesTable Created.");
            // close connection when it's not closed
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("CreateTablesTable() " + e.getMessage());
        }
    }

    // Create a new URL text Table
    private void CreateNewURLTextTable() {
        // form the sql command
        String sql = "CREATE TABLE IF NOT EXISTS UrlTextTable (\n" + "    id integer PRIMARY KEY,\n" + "    url text NOT NULL,\n" + "    text text\n" + ");";
        try {
            // try to create a connection
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            // try to claim a statement
            Statement statement = connection.createStatement();
            // try to execute sql
            statement.execute(sql);
            // print result
            System.out.println("Table: UrlTextTable Created.");
            // close connection when it's not closed
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("CreateNewURLTextTable() " + e.getMessage());
        }
    }

    /**
     * Return a Conncetion of the database
     *
     * @return Connection
     */
    private Connection connection() {
        // claim a new connection.
        Connection connection = null;
        try {
            // try to implement the connection.
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("connection() " + e.getMessage());
        }
        return connection;
    }

    /**
     * Select All data From UrlTextTable
     *
     * @return Data(ArrayList < List < String > >)
     */
    public ArrayList<String[]> SelectAllFromUrlTextTable() {
        // claim a data set
        ArrayList<String[]> Data = new ArrayList<String[]>();
        // form the sql command
        String sql = "SELECT id, url, text FROM UrlTextTable";
        try {
            // try to create a connection
            Connection connection = this.connection();
            // try to claim a statement
            Statement statement = connection.createStatement();
            // try to execute query sql and put the result into ResultSet
            ResultSet resultSet = statement.executeQuery(sql);
            // restore esult
            while (resultSet.next()) {
                // put results in ResultSet into data set
                Data.add(new String[]{String.valueOf(resultSet.getInt("id")), resultSet.getString("url"),
                        resultSet.getString("text")});
            }
            // close connection when it's not closed
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("SelectAllFromUrlTextTable() " + e.getMessage());
        }
        // return data set
        return Data;
    }

    /**
     * Count All data From UrlTextTable
     *
     * @return number of items in the table
     */
    public int CountAllFromUrlTextTable() {
        // the count variable
        int count = 0;
        // form the sql command
        String sql = "SELECT count(1) FROM UrlTextTable";
        try {
            // try to create a connection
            Connection connection = this.connection();
            // try to claim a statement
            Statement statement = connection.createStatement();
            // try to execute query sql and put the result into ResultSet
            ResultSet resultSet = statement.executeQuery(sql);
            // restore result
            resultSet.next();
            count = resultSet.getInt(1);
            // close connection when it's not closed
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("CountAllFromUrlTextTable() " + e.getMessage());
        }
        // return data set
        return count;
    }

    /**
     * Insert data to UrlTextTable
     *
     * @param url
     * @param text
     */
    public void InsertToUrlTextTable(String url, String text) {
        // form the sql command
        String sql = "INSERT INTO UrlTextTable(url,text) VALUES(?,?)";
        try {
            // try to create a connection
            Connection connection = this.connection();
            // try to claim a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // set the values in prepared statement
            preparedStatement.setString(1, url);
            preparedStatement.setString(2, text);
            // try to execute an update sql
            preparedStatement.executeUpdate();
            // close connection when it's not closed
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("InsertToUrlTextTable() " + e.getMessage());
        }
    }

    /**
     * Delete a data in UrlTextTable
     *
     * @param url
     */
    public void DeleteInUrlTextTable(String url) {
        // form the sql command
        String sql = "DELETE FROM UrlTextTable WHERE url = ?";
        try {
            // try to create a connection
            Connection connection = this.connection();
            // try to claim a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // set the values in prepared statement
            preparedStatement.setString(1, url);
            // try to execute an update sql
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("DeleteInUrlTextTable");
        }
    }

    /**
     * Clean UrlTextTable
     */
    private void CleanUrlTextTable() {
        // form the sql command
        String sql01 = "DELETE FROM UrlTextTable";
        String sql02 = "VACUUM";
        try {
            // try to create a connection
            Connection connection = this.connection();
            Statement statement = connection.createStatement();
            statement.execute(sql01);
            statement.execute(sql02);
            // close connection when it's not closed
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("CleanUrlTextTable() " + e.getMessage());
        }
    }


    /**
     * Show UrlTextTable
     */
    public void ShowUrlTextTable() {
        // form the sql command
        String sql = "SELECT id, url, text FROM UrlTextTable";
        try {
            // try to create a connection
            Connection connection = this.connection();
            // try to claim a statement
            Statement statement = connection.createStatement();
            // try to execute query sql and put the result into ResultSet
            ResultSet resultSet = statement.executeQuery(sql);
            // restore and print result
            System.out.println("UrlTextTable: ");
            while (resultSet.next()) {
                // print the result
                //System.out.println(resultSet.getInt("id") + "\t" + resultSet.getString("url") + "\t" + resultSet.getString("text"));
                // only print id and url
                System.out.println(resultSet.getInt("id") + "\t" + resultSet.getString("url"));
            }
            // close connection when it's not closed
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("SelectAllFromUrlTextTable() " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Database database = Database.getInstance();
        //database.InsertToUrlTextTable("url example", "text example");
        //database.InsertToUrlTextTable("url example01", "text example");
        //database.InsertToUrlTextTable("url example02", "text example");

        //database.SelectAllFromUrlTextTable();
        //database.DeleteInUrlTextTable("url example01");
        //database.SelectAllFromUrlTextTable();

        //Method to count rows in the table
        System.out.println(database.CountAllFromUrlTextTable());

        database.CleanUrlTextTable();
        //database.SelectAllFromUrlTextTable();
        database.ShowUrlTextTable();
    }
}
