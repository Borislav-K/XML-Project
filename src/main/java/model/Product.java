package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {

    private static final String SQL_TUPLE_FORMAT = "('%s', '%s', '%s', '%s'),";


    private String UPI;
    private String productTypeId;
    private String vendorId;
    private String price;

    public Product(String UPI, String productTypeId, String vendorId, String price) {
        this.UPI = UPI;
        this.productTypeId = productTypeId;
        this.vendorId = vendorId;
        this.price = price;
    }

    public Product(ResultSet rs) throws SQLException {
        this.UPI = rs.getString("UPI");
        this.productTypeId = rs.getString("productTypeId");
        this.vendorId = rs.getString("vendorId");
        this.price = rs.getString("price");
    }

    public String toSQLTuple() {
        return String.format(SQL_TUPLE_FORMAT, UPI, productTypeId, vendorId, price);
    }
}
