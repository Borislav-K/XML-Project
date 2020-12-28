package model;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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

    public void convertToXML(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("product");
        writer.writeAttribute("upi", UPI);
        writer.writeAttribute("productTypeId",productTypeId);
        writer.writeAttribute("vendorId",vendorId);
        writer.writeStartElement("price");
        writer.writeCharacters(price);
        writer.writeEndElement();
        writer.writeEndElement();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(UPI, product.UPI) &&
               Objects.equals(productTypeId, product.productTypeId) &&
               Objects.equals(vendorId, product.vendorId) &&
               Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UPI, productTypeId, vendorId, price);
    }
}
