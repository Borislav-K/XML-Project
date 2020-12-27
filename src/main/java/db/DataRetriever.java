package db;

import model.Product;
import model.ProductType;
import model.Vendor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private static final String PRODUCT_TYPE_TABLE = "ProductType";
    private static final String PRODUCT_TABLE = "Product";
    private static final String VENDOR_TABLE = "Vendor";

    private Connection dbConnection;

    public DataRetriever(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Vendor> fetchVendors() {
        List<Vendor> vendors = new ArrayList<>();
        try {
            ResultSet rs = fetchAllRows(VENDOR_TABLE);
            while (rs.next()) {
                vendors.add(new Vendor(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read entries from the database", e);
        }
        return vendors;
    }

    public List<ProductType> fetchProductTypes() {
        List<ProductType> productTypes = new ArrayList<>();
        try {
            ResultSet rs = fetchAllRows(PRODUCT_TYPE_TABLE);
            while (rs.next()) {
                productTypes.add(new ProductType(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read entries from the database", e);
        }
        return productTypes;
    }

    public List<Product> fetchProducts() {
        List<Product> products = new ArrayList<>();
        try {
            ResultSet rs = fetchAllRows(PRODUCT_TABLE);
            while (rs.next()) {
                products.add(new Product(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read entries from the database", e);
        }
        return products;
    }

    private ResultSet fetchAllRows(String tableName) throws SQLException {
        Statement statement = dbConnection.createStatement();
        return statement.executeQuery(String.format("SELECT * FROM %s", tableName));
    }
}
