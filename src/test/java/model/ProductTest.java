package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductTest {

    @Test
    public void testToSQLTuple() {
        Product testProduct = new Product("u1", "p1", "v1", "5$");
        assertEquals("('u1', 'p1', 'v1', '5$'),", testProduct.toSQLTuple());
    }

    @Test
    public void testToSQLTupleWithEmptyStrings() {
        Product testProduct = new Product("", "", "", "5$");
        assertEquals("('', '', '', '5$'),",testProduct.toSQLTuple());
    }
}
