package parsing;

import com.ctc.wstx.exc.WstxParsingException;
import com.ctc.wstx.exc.WstxValidationException;
import db.DatabaseService;
import exceptions.BadlyStructuredXMLException;
import exceptions.InvalidXMLException;
import model.Product;
import model.ProductType;
import model.Vendor;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class XMLToSQLConverter {

    private static final String PRODUCT_TYPE_XML_ELEMENT = "productType";
    private static final String PRODUCT_XML_ELEMENT = "product";
    private static final String VENDOR_XML_ELEMENT = "vendor";

    private static final String VENDOR_ID_XML_ATTRIBUTE = "vendorId";
    private static final String UPI_XML_ATTRIBUTE = "upi";
    private static final String PRODUCT_TYPE_ID_XML_ATTRIBUTE = "productTypeId";

    private static final String NO_NAMESPACE = "";

    private static final XMLInputFactory factory;

    static {
        factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_VALIDATING, "true");
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, "true");
    }

    private List<Product> products;
    private List<ProductType> productTypes;
    private List<Vendor> vendors;

    private DatabaseService db;

    public XMLToSQLConverter(DatabaseService db) {
        this.db = db;
    }

    public void convertXMLToSQL(Reader input) throws BadlyStructuredXMLException, InvalidXMLException {
        try {
            XMLStreamReader reader = factory.createXMLStreamReader(input);
            resetLists();
            while (reader.hasNext()) {
                if (reader.next() == XMLEvent.START_ELEMENT) {
                    handleStartElement(reader);
                }
            }
            insertDataIntoDB();
            reader.close();
        } catch (WstxValidationException e) {
            throw new InvalidXMLException(String.format("The XML file is not valid: %s", e.getMessage()));
        } catch (WstxParsingException e) {
            throw new BadlyStructuredXMLException(String.format("The XML file is badly structured: %s", e.getMessage()));
        } catch (XMLStreamException e) {
            System.out.printf("There was an error while parsing the file: %s \n Aborting conversion\n", e.getMessage());
        }
    }


    private void handleStartElement(XMLStreamReader reader) throws XMLStreamException {
        switch (reader.getLocalName()) {
            case VENDOR_XML_ELEMENT -> {
                handleVendorElement(reader);
            }
            case PRODUCT_TYPE_XML_ELEMENT -> {
                handleProductTypeElement(reader);
            }
            case PRODUCT_XML_ELEMENT -> {
                handleProductElement(reader);
            }
        }
    }

    private void handleVendorElement(XMLStreamReader reader) throws XMLStreamException {
        String vendorId = reader.getAttributeValue(NO_NAMESPACE, VENDOR_ID_XML_ATTRIBUTE);
        skipToNextStartElement(reader);
        String vendorName = reader.getElementText();//DTD ensures that name is before city
        skipToNextStartElement(reader);
        String vendorCity = reader.getElementText();

        vendors.add(new Vendor(vendorId, vendorName, vendorCity));
    }

    private void handleProductTypeElement(XMLStreamReader reader) throws XMLStreamException {
        String productTypeId = reader.getAttributeValue(NO_NAMESPACE, PRODUCT_TYPE_ID_XML_ATTRIBUTE);
        skipToNextStartElement(reader);
        String productName = reader.getElementText();

        productTypes.add(new ProductType(productTypeId, productName));
    }

    private void handleProductElement(XMLStreamReader reader) throws XMLStreamException {
        String UPI = reader.getAttributeValue(NO_NAMESPACE, UPI_XML_ATTRIBUTE);
        String productTypeId = reader.getAttributeValue(NO_NAMESPACE, PRODUCT_TYPE_ID_XML_ATTRIBUTE);
        String vendorId = reader.getAttributeValue(NO_NAMESPACE, VENDOR_ID_XML_ATTRIBUTE);
        skipToNextStartElement(reader);
        String price = reader.getElementText();

        products.add(new Product(UPI, productTypeId, vendorId, price));
    }

    private void resetLists() {
        vendors = new ArrayList<>();
        products = new ArrayList<>();
        productTypes = new ArrayList<>();
    }

    private void skipToNextStartElement(XMLStreamReader reader) throws XMLStreamException {
        while (reader.next() != XMLEvent.START_ELEMENT) {
        }
    }

    private void insertDataIntoDB() {
        db.insertProductTypes(productTypes);
        db.insertVendors(vendors);
        db.insertProducts(products);
    }

}
