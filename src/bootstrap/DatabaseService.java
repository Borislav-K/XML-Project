package bootstrap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {

    private static final String JDBC_URL = "jdbc:h2:./resources/test";
    private static final String DATABASE_USERNAME = "sa";
    private static final String DATABASE_PASSWORD = "";

    private Connection connection;

    public DatabaseService() {

    }

    public void establishConnection() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not connect to the database", e);
        }
    }


    public void initTables() throws SQLException {
        initProductsTable();
        initProductsTable();
    }

    private void initVendorsTable() throws SQLException {
        var statement = connection.createStatement();
        String sql = "CREATE TABLE Vendors (\n" +
                     "    column1 int,\n" +
                     "    column2 varchar(255),\n" +
                     "    column3 varchar(255)\n" +
                     ");";

        statement.execute(sql);

    }
    private void initProductsTable() {

    }



}
