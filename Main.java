// Main program to run command line loop

import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void Usage() {
        System.out.println("Command line Usage:");
        System.out.println("-----------------------------------");
        System.out.println("Command Arguments are in <> brackets");
        System.out.println("Optional Command Arguments are indicated as *<>");
        System.out.println();
        System.out.println("help: Print this help message.");
        System.out.println("new-class <Course> <Semester> <Section> <ClassName>: Create a new class with the specified course," 
        + "\n semester, section number, and class name.");
        System.out.println("list-classes: List all classes, with the number of enrolled students for each.");
        System.out.println("select-class <Course> *<Semester> *<Section>: Select and activate a class. If only course is specified," 
        + "\n selects the most recent term's section. If multiple sections are found, it fails without semester and section.");
        System.out.println("show-class: Show details of the currently-active class.");
        System.out.println();
        System.out.println("Category and Assignment Management:");
        System.out.println("show-categories: List the assignment categories with their weights for the active class.");
        System.out.println("add-category <Name> <Weight>: Add a new assignment category with the given name and weight.");
        System.out.println("show-assignment: List the assignments with their point values, grouped by category.");
        System.out.println("add-assignment <Name> <Category> <Description>: Add a new assignment under a category with the provided name and description.");
        System.out.println();
        System.out.println("Student Management:");
        System.out.println("add-student <Username> <StudentID> <Last> <First>: Add a student and enroll them in the active class." 
        + "\nIf the student exists, enroll them in the class. If the name differs, update the name with a warning.");
        System.out.println("add-student <Username>: Enroll an already-existing student in the active class. If the student does not exist, report an error.");
        System.out.println("show-students: Show all students in the active class.");
        System.out.println("show-students <String>: Show all students with the given string in their name or username, case-insensitively.");
        System.out.println();
        System.out.println("Grade Reporting:");
        System.out.println("grade <AssignmentName> <Username> <Grade>: Assign a grade for a student for a specific assignment." +  
        "\nReplace if a grade exists. Warn if points exceed.");
        System.out.println("student-grades <Username>: Show a student's grades, grouped by category, with" + 
        "+\nsubtotals and overall grade in the class.");
        System.out.println("gradebook: Show the current class's gradebook with students' usernames, IDs, names, and total grades.");
        System.out.println("-----------------------------------");
        System.out.println("End of Usage");
    }

    // returns a boolean whether to exit the main shell loop
    public static boolean parseCommand(String command, ArrayList<String> args) {
        System.out.println("Command: " + command); // to test
        int num_args = args.size();
        switch (command) {
        // quit the command loop
        case "quit":
            return true;

        // display the usage
        case "help":
            Usage();
            break;

        case "new-class":
            if (num_args == 4) {
                // do something here
            }
            else System.err.println(command + " requires 4 arguments: <Course> <Semester> <Section> <ClassName>.");
            break;

        case "list-classes":
            if (num_args == 0) {
                // do something here
            }
            else System.err.println(command + " takes no arguments.");
            break;

        case "select-class":
            if (num_args > 0 && num_args <= 3) {
                // do something here
            }
            else System.err.println(command + " requires 1 to 3 arguments: <Course> *<Semester> *<Section>.");
            break;

        case "show-class":
            if (num_args == 0) {
                // do something here
            }
            else System.err.println(command + " takes no arguments.");
            break;

        case "show-categories":
            if (num_args == 0) {
                // do something here
            }
            else System.err.println(command + " takes no arguments.");
            break;

        case "add-category":
            if (num_args == 2) {
                // do something here
            }
            else System.err.println(command + " requires 2 arguments: <Name> <Weight>.");
            break;

        case "show-assignment":
            if (num_args == 0) {
                // do something here
            }
            else System.err.println(command + " takes no arguments.");
            break;

        case "add-assignment":
            if (num_args == 3) {
                // do something here
            }
            else System.err.println(command + " requires 3 arguments: <Name> <Category> <Description>.");
            break;

        case "add-student":
            if (num_args == 2 || num_args == 4) {
                // do something here
            }
            else System.err.println(command + " requires 2 or 4 arguments: <Username> *<StudentID> *<Last> *<First>.");
            break;

        case "show-students":
            if (num_args == 0) {
                // do something here
            }
            else System.err.println(command + " takes no arguments.");
            break;

        case "grade":
            if (num_args == 3) {
                // do something here
            }
            else System.err.println(command + " requires 3 arguments: <AssignmentName> <Username> <Grade>.");
            break;

        case "gradebook":
            if (num_args == 0) {
                // do something here
            }
            else System.err.println(command + " takes no arguments.");
            break;

        // Print the default error
        default:
            System.err.println("Invalid Command : " + command);
            System.err.println("Use 'help' for a list of availible commands.");
            break;
        }
        // continue the command loop
        return false;
    }

    // Run with 
    //java -cp "mysql-connector-java-8.0.30.jar" Main.java <port-number> <username> <password>
    public static void main(String[] args) {
        // check for valid arguments
        if (args.length != 3) {
            System.err.println("Invalid number of arguments. Syntax:");
            System.err.println("java -cp \"mysql-connector-java-8.0.30.jar\" Main.java <port-number> <username> <password>");
            return;
        }
        
        // test a connection
        DatabaseConnector dbc = new DatabaseConnector();
        Connection connection = dbc.connect(args[0], args[1], args[2]);
        
        if (connection != null) {
             System.out.println("Database connection successful!.");
        } else {
             System.out.println("Database connection failed.");
             return;
        }
        
        // open a new input scanner
        Scanner sc = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            // command line carrot
            System.out.print("\r> ");
            String line = sc.nextLine();
            // get tokens for each command and its arguments
            String[] tokens = line.split("[ \\t]+");
            // nothing to parse
            if (line=="" || tokens.length==0) {
                continue;             
            }
            // parse the command
            ArrayList<String> c_args = new ArrayList<String>();
            for (int i = 1; i < tokens.length; i++) {
                c_args.add(tokens[i]);
            }
            quit = parseCommand(tokens[0], c_args);
        }
        // close the scanner
        sc.close();
    }
}
