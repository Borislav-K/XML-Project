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

    private Connection connection;
    private DataRetriever retriever;
    private DataInserter inserter;

    public DatabaseService() {
        establishConnection();
        this.retriever = new DataRetriever(connection);
        this.inserter = new DataInserter(connection);
    }

    private void establishConnection() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not connect to the database", e);
        }
    }

    public void insertVendors(List<Vendor> vendors) {
        inserter.insertVendors(vendors);
    }

    public void insertProductTypes(List<ProductType> productTypes) {
        inserter.insertProductTypes(productTypes);
    }

    public void insertProducts(List<Product> products) {
        inserter.insertProducts(products);
    }

    public List<Vendor> fetchVendors() {
        return retriever.fetchVendors();
    }

    public List<ProductType> fetchProductTypes() {
        return retriever.fetchProductTypes();
    }

    public List<Product> fetchProducts() {
        return retriever.fetchProducts();
    }

}
