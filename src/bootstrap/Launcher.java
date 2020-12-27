package bootstrap;

public class Launcher {

    public static void main(String[] args) {
        DatabaseService db = new DatabaseService();
        db.establishConnection();
    }
}
