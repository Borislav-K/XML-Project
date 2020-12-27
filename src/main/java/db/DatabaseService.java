package db;

import model.Product;
import model.ProductType;
import model.Vendor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DatabaseService {


    private static final String JDBC_URL = "jdbc:h2:./database/market";
    private static final String DATABASE_USERNAME = "sa";
    private static final String DATABASE_PASSWORD = "";

    private static final String PRODUCT_TYPE_TABLE = "ProductType";
    private static final String PRODUCT_TABLE = "Product";
    private static final String VENDOR_TABLE = "Vendor";

    private static final String VENDOR_INSERTION_HEADER = "INSERT INTO Vendor (ID, Name, City) VALUES\n";
    private static final String PRODUCT_TYPE_INSERTION_HEADER = "INSERT INTO ProductType (ID, Name) VALUES\n";
    private static final String PRODUCT_INSERTION_HEADER = "INSERT INTO Product (UPI, productTypeId, vendorId, price) VALUES\n";

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

    public void executeQuery(String sql) throws SQLException {
        var statement = connection.createStatement();
        statement.execute(sql);
    }

    public void insertVendors(List<Vendor> vendors) {
        StringBuilder sb = new StringBuilder(VENDOR_INSERTION_HEADER);
        vendors.forEach(vendor -> sb.append(vendor.toSQLTuple()));
        try {
            executeQuery(stripComma(sb));
        } catch (SQLException e) {
            throw new IllegalStateException("Could not insert vendors to the database. Reason %s", e);
        }
    }

    public void insertProductTypes(List<ProductType> productTypes) {
        StringBuilder sb = new StringBuilder(PRODUCT_TYPE_INSERTION_HEADER);
        productTypes.forEach(p -> sb.append(p.toSQLTuple()));
        try {
            executeQuery(stripComma(sb));
        } catch (SQLException e) {
            throw new IllegalStateException("Could not insert product types to the database. Reason %s", e);
        }
    }

    public void insertProducts(List<Product> products) {
        StringBuilder sb = new StringBuilder(PRODUCT_INSERTION_HEADER);
        products.forEach(product -> sb.append(product.toSQLTuple()));
        try {
            executeQuery(stripComma(sb));
        } catch (SQLException e) {
            throw new IllegalStateException("Could not insert product types to the database", e);
        }
    }

    private void initVendorsTable() {
        String createTable = "CREATE TABLE Vendor (\n" +
                             "ID varchar(64) NOT NULL,\n" +
                             "Name varchar(255) NOT NULL,\n" +
                             "City varchar(255) NOT NULL,\n" +
                             "PRIMARY KEY (ID)\n" +
                             ");";
        try {
            dropTable(VENDOR_TABLE);
            executeQuery(createTable);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not create the vendors table", e);
        }
    }

    private void initProductTypesTable() {
        String createTable = "CREATE TABLE ProductType (\n" +
                             "ID varchar(64) NOT NULL,\n" +
                             "Name varchar(255) NOT NULL,\n" +
                             "PRIMARY KEY (ID)\n" +
                             ");";
        try {
            dropTable(PRODUCT_TYPE_TABLE);
            executeQuery(createTable);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not create the product types table", e);
        }
    }

    private void initProductsTable() {
        String createTable = "CREATE TABLE Product(\n" +
                             "UPI varchar(64) NOT NULL,\n" +
                             "productTypeId varchar(64) NOT NULL,\n" +
                             "vendorId varchar(64) NOT NULL,\n" +
                             "price varchar(64) NOT NULL,\n" +
                             "PRIMARY KEY (UPI),\n" +
                             "FOREIGN KEY (productTypeId) REFERENCES ProductType(ID),\n" +
                             "FOREIGN KEY (vendorId) REFERENCES Vendor(ID)\n" +
                             ");";
        try {
            dropTable(PRODUCT_TABLE);
            executeQuery(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not create the products table", e);
        }
    }

    private void dropTable(String tableName) {
        try {
            executeQuery(String.format("DROP TABLE IF EXISTS %s", tableName));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private String stripComma(StringBuilder sb) {
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
