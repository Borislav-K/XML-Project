package parsing;

import db.DatabaseService;
import model.Product;
import model.ProductType;
import model.Vendor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SQLToXMLConverterTest {

    private static final List<Product> EMPTY_PRODUCT_LIST = new ArrayList<>();
    private static final List<ProductType> EMPTY_PRODUCT_TYPE_LIST = new ArrayList<>();
    private static final List<Vendor> EMPTY_VENDOR_LIST = new ArrayList<>();

    private static final List<Product> PRODUCT_LIST_OK = List.of(new Product("u1", "p1", "v1", "5$"));
    private static final List<ProductType> PRODUCT_TYPE_LIST_OK = List.of(new ProductType("p1", "apple"));
    private static final List<Vendor> VENDOR_LIST_OK = List.of(new Vendor("v1", "", "Sofia"));


    private static final String EMPTY_MARKET_XML = "<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE market [\n" +
                                                   "        <!ELEMENT market (productType*,vendor*,products?)>\n" +
                                                   "        <!ELEMENT productType (name)>\n" +
                                                   "        <!ELEMENT vendor (name,city)>\n" +
                                                   "        <!ELEMENT products (product*)>\n" +
                                                   "        <!ELEMENT product (price)>\n" +
                                                   "        <!ELEMENT name (#PCDATA)>\n" +
                                                   "        <!ELEMENT city (#PCDATA)>\n" +
                                                   "        <!ELEMENT price (#PCDATA)>\n" +
                                                   "        <!ATTLIST productType productTypeId ID #REQUIRED>\n" +
                                                   "        <!ATTLIST vendor vendorId ID #REQUIRED>\n" +
                                                   "        <!ATTLIST product\n" +
                                                   "                upi ID #REQUIRED\n" +
                                                   "                productTypeId IDREF #REQUIRED\n" +
                                                   "                vendorId IDREF #REQUIRED>\n" +
                                                   "        ]>\n" +
                                                   "<market><products></products></market>";

    private static final String OK_MARKET_XML = "<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE market [\n" +
                                                "        <!ELEMENT market (productType*,vendor*,products?)>\n" +
                                                "        <!ELEMENT productType (name)>\n" +
                                                "        <!ELEMENT vendor (name,city)>\n" +
                                                "        <!ELEMENT products (product*)>\n" +
                                                "        <!ELEMENT product (price)>\n" +
                                                "        <!ELEMENT name (#PCDATA)>\n" +
                                                "        <!ELEMENT city (#PCDATA)>\n" +
                                                "        <!ELEMENT price (#PCDATA)>\n" +
                                                "        <!ATTLIST productType productTypeId ID #REQUIRED>\n" +
                                                "        <!ATTLIST vendor vendorId ID #REQUIRED>\n" +
                                                "        <!ATTLIST product\n" +
                                                "                upi ID #REQUIRED\n" +
                                                "                productTypeId IDREF #REQUIRED\n" +
                                                "                vendorId IDREF #REQUIRED>\n" +
                                                "        ]>\n" +
                                                "<market>" +
                                                "<productType productTypeId=\"p1\"><name>apple</name></productType>" +
                                                "<vendor vendorId=\"v1\"><name></name><city>Sofia</city></vendor>" +
                                                "<products>" +
                                                "<product upi=\"u1\" productTypeId=\"p1\" vendorId=\"v1\"><price>5$</price></product>" +
                                                "</products>" +
                                                "</market>";

    private static DatabaseService mockDB = Mockito.mock(DatabaseService.class);

    private static SQLToXMLConverter testConverter;

    @BeforeClass
    public static void init() {
        testConverter = new SQLToXMLConverter(mockDB);
    }

    @Test
    public void testSQLToXMLWhenDatabaseIsEmpty() {
        when(mockDB.fetchProducts()).thenReturn(EMPTY_PRODUCT_LIST);
        when(mockDB.fetchProductTypes()).thenReturn(EMPTY_PRODUCT_TYPE_LIST);
        when(mockDB.fetchVendors()).thenReturn(EMPTY_VENDOR_LIST);

        StringWriter stringWriter = new StringWriter();
        testConverter.convertSQLToXML(stringWriter);
        assertEquals(EMPTY_MARKET_XML.replaceAll("\n", System.lineSeparator()), stringWriter.toString());
    }

    @Test
    public void testSQLToXMLWhenDatabaseIsNotEmpty() {
        when(mockDB.fetchProducts()).thenReturn(PRODUCT_LIST_OK);
        when(mockDB.fetchProductTypes()).thenReturn(PRODUCT_TYPE_LIST_OK);
        when(mockDB.fetchVendors()).thenReturn(VENDOR_LIST_OK);

        StringWriter stringWriter = new StringWriter();
        testConverter.convertSQLToXML(stringWriter);
        assertEquals(OK_MARKET_XML.replaceAll("\n", System.lineSeparator()), stringWriter.toString());
    }

}
