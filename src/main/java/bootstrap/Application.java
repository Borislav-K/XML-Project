package bootstrap;

import db.DatabaseService;
import exceptions.BadlyStructuredXMLException;
import exceptions.InvalidXMLException;
import parsing.SQLToXMLConverter;
import parsing.XMLToSQLConverter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Application {

    private static final String QUIT_COMMAND = "quit";
    private static final String CONVERT_COMMAND = "convert";
    private static final String XML_TO_SQL_FLAG = "--tosql";
    private static final String SQL_TO_XML_FLAG = "--toxml";
    private static final int CONVERT_ARGUMENTS_LENGTH = 2;


    private DatabaseService db;
    private XMLToSQLConverter xsConverter;
    private SQLToXMLConverter sxConverter;

    public Application() {
        db = new DatabaseService();
        xsConverter = new XMLToSQLConverter(db);
        sxConverter = new SQLToXMLConverter(db);
    }

    public void start() {
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter command: ");
                String cmd = in.nextLine();

                if (cmd.trim().equalsIgnoreCase(QUIT_COMMAND)) {
                    return;
                }
                String[] tokens = cmd.split("\\s+");
                if (tokens[0].equals(CONVERT_COMMAND)) {
                    handleConvertCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
                    continue;
                }
                System.out.println("Unknown command");
            }
        }
    }

    private void handleConvertCommand(String[] args) {
        if (args.length != CONVERT_ARGUMENTS_LENGTH) {
            printConvertUsage();
            return;
        }
        String flag = args[0];
        String file = args[1];
        if (flag.equals(XML_TO_SQL_FLAG)) {
            try (FileReader reader = new FileReader(file);) {
                xsConverter.convertXMLToSQL(reader);
                System.out.println("Conversion successful. The XML file's content was added into the database");
            } catch (IOException e) {
                System.out.printf("The file %s does not exist or could not be opened\n", file);
            } catch (BadlyStructuredXMLException | InvalidXMLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
            return;
        }
        if (flag.equals(SQL_TO_XML_FLAG)) {
            try (FileWriter writer = new FileWriter(file);) {
                sxConverter.convertSQLToXML(writer);
                System.out.printf("Conversion successful. The content of the database can be found at %s\n", file);
            } catch (IOException e) {
                System.out.printf("The file %s does not exist or could not be opened\n", file);
            }
            return;
        }
        printConvertUsage();
    }

    private void printConvertUsage() {
        System.out.println("Error. Usage: convert [--tosql|--toxml] <file>");
    }
}
