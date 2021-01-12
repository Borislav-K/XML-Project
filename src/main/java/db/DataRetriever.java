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
        try {
            List<Vendor> vendors = new ArrayList<>();
            ResultSet rs = fetchAllRows(VENDOR_TABLE);
            while (rs.next()) {
                vendors.add(new Vendor(rs));
            }
            return vendors;
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read entries from the database", e);
        }
    }

    public List<ProductType> fetchProductTypes() {
        try {
            List<ProductType> productTypes = new ArrayList<>();
            ResultSet rs = fetchAllRows(PRODUCT_TYPE_TABLE);
            while (rs.next()) {
                productTypes.add(new ProductType(rs));
            }
            return productTypes;
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read entries from the database", e);
        }
    }

    public List<Product> fetchProducts() {
        try {
            List<Product> products = new ArrayList<>();
            ResultSet rs = fetchAllRows(PRODUCT_TABLE);
            while (rs.next()) {
                products.add(new Product(rs));
            }
            return products;
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read entries from the database", e);
        }
    }

    private ResultSet fetchAllRows(String tableName) throws SQLException {
        Statement statement = dbConnection.createStatement();
        return statement.executeQuery(String.format("SELECT * FROM %s", tableName));
    }
}
