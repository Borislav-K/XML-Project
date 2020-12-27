package bootstrap;

import db.DatabaseService;
import parsing.SQLToXMLConverter;
import parsing.XMLToSQLConverter;

public class Launcher {

    public static void main(String[] args) {
        DatabaseService db = new DatabaseService();

        XMLToSQLConverter converter = new XMLToSQLConverter(db);
        converter.convertXMLToSQL("resources/example_shop.xml");


        SQLToXMLConverter c2 = new SQLToXMLConverter(db);
        c2.convertSQLToXML("test.xml");
    }
}
