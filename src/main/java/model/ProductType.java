package model;

public class ProductType {

    private static final String SQL_TUPLE_FORMAT = "('%s', '%s'),";


    private String ID;
    private String name;

    public ProductType(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String toSQLTuple() {
        return String.format(SQL_TUPLE_FORMAT, ID, name);
    }
}
