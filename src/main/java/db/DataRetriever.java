package db;


import model.Product;
import model.ProductType;
import model.Vendor;

import java.sql.Connection;
import java.util.List;

public class DataRetriever {

    private Connection dbConnection;

    public DataRetriever(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Vendor> fetchVendors() {
        return null;
    }

    public List<ProductType> fetchProductTypes() {
        return null;
    }

    public List<Product> fetchProducts() {
        return null;
    }
}
