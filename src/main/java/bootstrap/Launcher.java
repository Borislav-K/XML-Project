package bootstrap;

import db.DatabaseService;
import parsing.XMLToSQLConverter;

public class Launcher {

    public static void main(String[] args) {
        DatabaseService db = new DatabaseService();
        db.establishConnection();
        db.initTables();

        XMLToSQLConverter converter = new XMLToSQLConverter(db);
        converter.convertXMLToSQL("resources/example_shop.xml");
    }
}
