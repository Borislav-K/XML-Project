package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorTest {

    @Test
    public void testToSQLTuple() {
        Vendor testVendor = new Vendor("id1","n1","c1");
        assertEquals("('id1', 'n1', 'c1'),",testVendor.toSQLTuple());
    }

    @Test
    public void testToSQLTupleWithEmptyStrings() {
        Vendor testVendor = new Vendor("id1","","");
        assertEquals("('id1', '', ''),",testVendor.toSQLTuple());
    }
}
