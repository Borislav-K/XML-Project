package parsing;


import db.DatabaseService;
import model.Product;
import model.ProductType;
import model.Vendor;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class SQLToXMLConverter {

    private static final XMLOutputFactory factory = XMLOutputFactory.newInstance();
    private static final String DTD = loadDTD();

    private DatabaseService db;

    public SQLToXMLConverter(DatabaseService db) {
        this.db = db;
    }

    public void convertSQLToXML(Writer output) throws XMLStreamException {
        List<Vendor> vendors = db.fetchVendors();
        List<ProductType> productTypes = db.fetchProductTypes();
        List<Product> products = db.fetchProducts();

        XMLStreamWriter writer = factory.createXMLStreamWriter(output);
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeDTD(DTD);
        writer.writeStartElement("market");
        for (ProductType productType : productTypes) {
            productType.convertToXML(writer);
        }
        for (Vendor vendor : vendors) {
            vendor.convertToXML(writer);
        }
        writer.writeStartElement("products");
        for (Product product : products) {
            product.convertToXML(writer);
        }
        writer.writeEndDocument();
    }

    private static String loadDTD() {
        StringBuilder sb = new StringBuilder();
        try (FileReader fileReader = new FileReader("resources/inline.dtd");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            bufferedReader.lines().forEach(line -> sb.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            throw new IllegalStateException("Could not load the DTD from the given file", e);
        }
        return sb.toString();
    }
}
