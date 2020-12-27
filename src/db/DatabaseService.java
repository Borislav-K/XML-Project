package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {

    private static final String JDBC_URL = "jdbc:h2:./database/market";
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


    public void initTables() {
        initVendorsTable();
        initProductTypesTable();
        initProductsTable();
    }

    private void initVendorsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Vendor (\n" +
                     "ID varchar(64) NOT NULL,\n" +
                     "Name varchar(255) NOT NULL,\n" +
                     "City varchar(255),\n" +
                     "PRIMARY KEY (ID)\n" +
                     ");";
        try {
            executeSQL(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not create the vendors table", e);
        }
    }

    private void initProductTypesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ProductType (\n" +
                     "ID varchar(64) NOT NULL,\n" +
                     "Name varchar(255) NOT NULL,\n" +
                     "PRIMARY KEY (ID)\n" +
                     ");";
        try {
            executeSQL(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not create the product types table", e);
        }
    }

    private void initProductsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Product(\n" +
                     "UPI varchar(64) NOT NULL,\n" +
                     "productTypeId varchar(64) NOT NULL,\n" +
                     "vendorId varchar(64) NOT NULL,\n" +
                     "price FLOAT NOT NULL,\n" +
                     "PRIMARY KEY (UPI),\n" +
                     "FOREIGN KEY (productTypeId) REFERENCES ProductType(ID),\n" +
                     "FOREIGN KEY (vendorId) REFERENCES Vendor(ID)\n" +
                     ");";
        try {
            executeSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not create the products table", e);
        }
    }

    private void executeSQL(String sql) throws SQLException {
        var statement = connection.createStatement();
        statement.execute(sql);
    }
}
