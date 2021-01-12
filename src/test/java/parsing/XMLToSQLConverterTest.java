package parsing;

import db.DatabaseService;
import exceptions.BadlyStructuredXMLException;
import exceptions.InvalidXMLException;
import model.Product;
import model.ProductType;
import model.Vendor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.StringReader;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static parsing.TestConverterConstants.*;

public class XMLToSQLConverterTest {

    private static DatabaseService mockDB = Mockito.mock(DatabaseService.class);

    private static XMLToSQLConverter testConverter;

    @BeforeClass
    public static void init() throws Exception{
        testConverter = new XMLToSQLConverter(mockDB);
        doNothing().when(mockDB).insertVendors(anyListOf(Vendor.class));
        doNothing().when(mockDB).insertProductTypes(anyListOf(ProductType.class));
        doNothing().when(mockDB).insertProducts(anyListOf(Product.class));
    }

    @Test(expected = BadlyStructuredXMLException.class)
    public void testXMLToSQLWithBadlyStructuredXMLFile() throws Exception {
        testConverter.convertXMLToSQL(new StringReader(BADLY_STRUCTURED_XML));
    }

    @Test(expected = InvalidXMLException.class)
    public void testXMLToSQLWithInvalidXMLFile() throws Exception {
        testConverter.convertXMLToSQL(new StringReader(INVALID_XML));
    }

    @Test
    public void testXMLToSQLWithEmptyXMLFile() throws Exception {
        testConverter.convertXMLToSQL(new StringReader(EMPTY_MARKET_XML));
        verify(mockDB).insertVendors(EMPTY_VENDOR_LIST);
        verify(mockDB).insertProductTypes(EMPTY_PRODUCT_TYPE_LIST);
        verify(mockDB).insertProducts(EMPTY_PRODUCT_LIST);
    }

    @Test
    public void testXMLToSQLBaseCase() throws Exception {
        testConverter.convertXMLToSQL(new StringReader(OK_MARKET_XML));

        verify(mockDB).insertVendors(VENDOR_LIST_OK);
        verify(mockDB).insertProductTypes(PRODUCT_TYPE_LIST_OK);
        verify(mockDB).insertProducts(PRODUCT_LIST_OK);
    }

}
