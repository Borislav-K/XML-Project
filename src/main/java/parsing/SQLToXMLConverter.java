package parsing;


import com.ctc.wstx.exc.WstxValidationException;
import db.DatabaseService;
import model.Product;
import model.ProductType;
import model.Vendor;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SQLToXMLConverter {

    private static final XMLOutputFactory factory;

    private static String DTD;

    static {
        loadDTD();
        factory = XMLOutputFactory.newInstance();
    }

    private DatabaseService db;

    public SQLToXMLConverter(DatabaseService db) {
        this.db = db;
    }

    public void convertSQLToXML(String outputFilePath) {
        List<Vendor> vendors = db.fetchVendors();
        List<ProductType> productTypes = db.fetchProductTypes();
        List<Product> products = db.fetchProducts();

        try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
            XMLStreamWriter writer = factory.createXMLStreamWriter(fileWriter);
            writer.writeStartDocument();
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

            System.out.println("Conversion successful. The data from the database is converted into XML");
        } catch (IOException e) {
            System.out.printf("Could not open file %s", outputFilePath);
            e.printStackTrace();
        } catch (WstxValidationException e) {
            System.out.println("The XML file is not valid!");
        } catch (XMLStreamException e) {
            System.out.println("There was an error while parsing the file");
            e.printStackTrace();
        }
    }

    private static void loadDTD() {
        StringBuilder sb = new StringBuilder();
        try (FileReader fileReader = new FileReader("resources/inline.dtd");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            bufferedReader.lines().forEach(line -> sb.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            throw new IllegalStateException("Could not load the DTD from the given file", e);
        }
        DTD = sb.toString();
    }
}
