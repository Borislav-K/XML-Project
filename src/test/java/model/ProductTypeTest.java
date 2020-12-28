package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductTypeTest {

    @Test
    public void testToSQLTuple() {
        ProductType testProductType = new ProductType("id1", "p1");
        assertEquals("('id1', 'p1'),", testProductType.toSQLTuple());
    }

    @Test
    public void testToSQLTupleWithEmptyStrings() {
        ProductType testProductType = new ProductType("id1","");
        assertEquals("('id1', ''),", testProductType.toSQLTuple());
    }
}
