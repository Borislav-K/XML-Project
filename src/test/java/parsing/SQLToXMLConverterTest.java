package parsing;

import db.DatabaseService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static parsing.TestConverterConstants.*;

public class SQLToXMLConverterTest {

    private static DatabaseService mockDB = Mockito.mock(DatabaseService.class);

    private static SQLToXMLConverter testConverter;

    @BeforeClass
    public static void init() {
        testConverter = new SQLToXMLConverter(mockDB);
    }

    @Test
    public void testSQLToXMLWhenDatabaseIsEmpty() throws Exception{
        when(mockDB.fetchProducts()).thenReturn(EMPTY_PRODUCT_LIST);
        when(mockDB.fetchProductTypes()).thenReturn(EMPTY_PRODUCT_TYPE_LIST);
        when(mockDB.fetchVendors()).thenReturn(EMPTY_VENDOR_LIST);

        StringWriter stringWriter = new StringWriter();
        testConverter.convertSQLToXML(stringWriter);
        assertEquals(EMPTY_MARKET_XML.replaceAll("\n", System.lineSeparator()), stringWriter.toString());
    }

    @Test
    public void testSQLToXMLWhenDatabaseIsNotEmpty() throws Exception{
        when(mockDB.fetchProducts()).thenReturn(PRODUCT_LIST_OK);
        when(mockDB.fetchProductTypes()).thenReturn(PRODUCT_TYPE_LIST_OK);
        when(mockDB.fetchVendors()).thenReturn(VENDOR_LIST_OK);

        StringWriter stringWriter = new StringWriter();
        testConverter.convertSQLToXML(stringWriter);
        assertEquals(OK_MARKET_XML.replaceAll("\n", System.lineSeparator()), stringWriter.toString());
    }

}
