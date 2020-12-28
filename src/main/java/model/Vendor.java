package model;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Vendor {

    private static final String SQL_TUPLE_FORMAT = "('%s', '%s', '%s'),";

    private String ID;
    private String name;
    private String city;

    public Vendor(String ID, String name, String city) {
        this.ID = ID;
        this.name = name;
        this.city = city;
    }

    public Vendor(ResultSet rs) throws SQLException {
        this.ID = rs.getString("ID");
        this.name = rs.getString("Name");
        this.city = rs.getString("City");
    }

    public String toSQLTuple() {
        return String.format(SQL_TUPLE_FORMAT, ID, name, city);
    }

    public void convertToXML(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("vendor");
        writer.writeAttribute("vendorId", ID);
        writer.writeStartElement("name");
        writer.writeCharacters(name);
        writer.writeEndElement();
        writer.writeStartElement("city");
        writer.writeCharacters(city);
        writer.writeEndElement();
        writer.writeEndElement();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendor)) return false;
        Vendor vendor = (Vendor) o;
        return Objects.equals(ID, vendor.ID) &&
               Objects.equals(name, vendor.name) &&
               Objects.equals(city, vendor.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, city);
    }
}
