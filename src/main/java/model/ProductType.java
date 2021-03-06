package model;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ProductType {

    private static final String SQL_TUPLE_FORMAT = "('%s', '%s'),";


    private String ID;
    private String name;

    public ProductType(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public ProductType(ResultSet rs) throws SQLException {
        this.ID = rs.getString("ID");
        this.name = rs.getString("Name");
    }

    public String toSQLTuple() {
        return String.format(SQL_TUPLE_FORMAT, ID, name);
    }

    public void convertToXML(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("productType");
        writer.writeAttribute("productTypeId", ID);
        writer.writeStartElement("name");
        writer.writeCharacters(name);
        writer.writeEndElement();
        writer.writeEndElement();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductType)) return false;
        ProductType that = (ProductType) o;
        return Objects.equals(ID, that.ID) &&
               Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name);
    }
}
