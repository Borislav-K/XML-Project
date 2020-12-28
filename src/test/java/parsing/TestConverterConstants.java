package parsing;

import model.Product;
import model.ProductType;
import model.Vendor;

import java.util.ArrayList;
import java.util.List;

public class TestConverterConstants {

    private static final Product testProduct = new Product("u1", "p1", "v1", "5$");
    private static final ProductType testProductType = new ProductType("p1", "apple");
    private static final Vendor testVendor = new Vendor("v1", "", "Sofia");

    public static final List<Product> EMPTY_PRODUCT_LIST = new ArrayList<>();
    public static final List<ProductType> EMPTY_PRODUCT_TYPE_LIST = new ArrayList<>();
    public static final List<Vendor> EMPTY_VENDOR_LIST = new ArrayList<>();

    public static final List<Product> PRODUCT_LIST_OK = List.of(testProduct);
    public static final List<ProductType> PRODUCT_TYPE_LIST_OK = List.of(testProductType);
    public static final List<Vendor> VENDOR_LIST_OK = List.of(testVendor);


    public static final String EMPTY_MARKET_XML = "<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE market [\n" +
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

    public static final String OK_MARKET_XML = "<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE market [\n" +
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

    public static final String BADLY_STRUCTURED_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                                                      "<market>\n" +
                                                      "<vendor>\n" +
                                                      "</market>\n" +
                                                      "</vendor>";
    public static final String INVALID_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                             "<!DOCTYPE market SYSTEM \"resources\\market.dtd\">\n" +
                                             "<market>\n" +
                                             "    <productType productTypeId=\"p1\">\n" +
                                             "        <name>Apple</name>\n" +
                                             "    </productType>\n" +
                                             "    <vendor vendorId=\"v1\">\n" +
                                             "        <name>vendor1</name>\n" +
                                             "        <city>Sofia</city>\n" +
                                             "    </vendor>\n" +
                                             "    <products>\n" +
                                             "        <product upi=\"upi1\" productTypeId=\"non-existent\" vendorId=\"v1\">\n" +
                                             "            <price>0.95BGN</price>\n" +
                                             "        </product>\n" +
                                             "    </products>\n" +
                                             "</market>\n";
}
