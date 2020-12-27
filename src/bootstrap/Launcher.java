package bootstrap;

import db.DatabaseService;

public class Launcher {

    public static void main(String[] args) {
        DatabaseService db = new DatabaseService();
        db.establishConnection();
        db.initTables();
    }
}
