package bootstrap;

import db.DatabaseService;
import exceptions.BadlyStructuredXMLException;
import exceptions.InvalidXMLException;
import parsing.SQLToXMLConverter;
import parsing.XMLToSQLConverter;

import javax.xml.stream.XMLStreamException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Application {

    private static final String QUIT_COMMAND = "quit";
    private static final String CONVERT_COMMAND = "convert";
    private static final String XML_TO_SQL_FLAG = "--tosql";
    private static final String SQL_TO_XML_FLAG = "--toxml";

    private static final int CONVERT_ARGUMENTS_LENGTH = 2;
    private static final int INDEX_CONSTRAINT_ERROR_CODE = 23505;

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
        switch (flag) {
            case XML_TO_SQL_FLAG -> handleXMLToSQLCommand(file);
            case SQL_TO_XML_FLAG -> handleSQLToXMLCommand(file);
            default -> printConvertUsage();
        }
    }

    private void handleXMLToSQLCommand(String inputFile) {
        try (FileReader reader = new FileReader(inputFile);) {
            xsConverter.convertXMLToSQL(reader);
            System.out.println("Conversion successful. The XML file's content was added into the database");
        } catch (SQLException e) {
            if (e.getErrorCode() == INDEX_CONSTRAINT_ERROR_CODE) {
                System.out.printf("The data in %s violates duplicate key constraints in the database: %s\n",
                        inputFile, e.getMessage().split("SQL statement")[0]); // Shows a proper portion of the msg
            } else {
                System.out.printf("Could not flush the content to the database: %s\n", e.getMessage());
            }
        } catch (IOException e) {
            System.out.printf("The file %s does not exist or could not be opened\n", inputFile);
        } catch (BadlyStructuredXMLException | InvalidXMLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleSQLToXMLCommand(String outputFile) {
        try (FileWriter writer = new FileWriter(outputFile);) {
            sxConverter.convertSQLToXML(writer);
            System.out.printf("Conversion successful. The content of the database can be found at %s\n", outputFile);
        } catch (IOException e) {
            System.out.printf("The file %s does not exist or could not be opened\n", outputFile);
        } catch (XMLStreamException e) {
            System.out.printf("There was an error while parsing %s: %s \n Aborting conversion\n", outputFile, e.getMessage());
        }
    }

    private void printConvertUsage() {
        System.out.println("Error. Usage: convert [--tosql|--toxml] <file>");
    }
}
