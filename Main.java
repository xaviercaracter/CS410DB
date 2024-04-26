import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static String activeClass;
    private static int activeClass_pk; // use this to keep track of the primary key for the course

    public static void Usage() {
        System.out.println("Command line Usage:");
        System.out.println("-----------------------------------");
        System.out.println("Command Arguments are in <> brackets");
        System.out.println("Optional Command Arguments are indicated as *<>");
        System.out.println();
        System.out.println("help: Print this help message.");
        System.out.println(
                "new-class <Course> <Semester> <Section> <ClassName>: Create a new class with the specified course,"
                        + "\n semester, section number, and class name. \n Example: new-class CS410 Sp20 1 Databases");
        System.out.println("list-classes: List all classes, with the number of enrolled students for each.");
        System.out.println(
                "select-class <Course> *<Semester> *<Section>: Select and activate a class. If only course is specified,"
                        + "\n selects the most recent term's section. If multiple sections are found, it fails without semester and section.");
        System.out.println("show-class: Show details of the currently-active class.");
        System.out.println();
        System.out.println("Category and Assignment Management:");
        System.out.println("show-categories: List the assignment categories with their weights for the active class.");
        System.out
                .println("add-category <Name> <Weight>: Add a new assignment category with the given name and weight.");
        System.out.println("show-assignment: List the assignments with their point values, grouped by category.");
        System.out.println(
                "add-assignment <Name> <Category> <Description> <Points>: Add a new assignment under a category with the provided name and description and points.");
        System.out.println();
        System.out.println("Student Management:");
        System.out.println(
                "add-student <Username> <StudentID> <Last> <First>: Add a student and enroll them in the active class."
                        + "\nIf the student exists, enroll them in the class. If the name differs, update the name with a warning.");
        System.out.println(
                "add-student <Username>: Enroll an already-existing student in the active class. If the student does not exist, report an error.");
        System.out.println("show-students: Show all students in the active class.");
        System.out.println(
                "show-students <String>: Show all students with the given string in their name or username, case-insensitively.");
        System.out.println();
        System.out.println("Grade Reporting:");
        System.out.println(
                "grade <AssignmentName> <Username> <Grade>: Assign a grade for a student for a specific assignment." +
                        "\nReplace if a grade exists. Warn if points exceed.");
        System.out.println("student-grades <Username>: Show a student's grades, grouped by category, with" +
                "+\nsubtotals and overall grade in the class.");
        System.out.println(
                "gradebook: Show the current class's gradebook with students' usernames, IDs, names, and total grades.");
        System.out.println("-----------------------------------");
        System.out.println("End of Usage");
    }

    // returns a boolean whether to exit the main shell loop
    public static boolean parseCommand(String command, ArrayList<String> args, DatabaseConnector dbc) {
        System.out.println("Command: " + command); // to test
        int num_args = args.size();
        String query = ""; // query buffer

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
                    String courseNumber = args.get(0);
                    String term = args.get(1);
                    int sectionNumber = Integer.parseInt(args.get(2));
                    String description = args.get(3);
                    query = String.format("INSERT INTO Class (Course_Number, Term, Section_Number, Description) " +
                            "VALUES ('%s', '%s', %d, '%s')", courseNumber, term, sectionNumber, description);
                    dbc.executeSqlCommand(query);
                    System.out.println("New class added successfully.");
                } else
                    System.err.println(command + " requires 4 arguments: <Course> <Semester> <Section> <ClassName>.");
                break;

            case "list-classes":
                if (num_args == 0) {
                    query += "SELECT c.Course_Number, c.Term, c.Section_Number, c.Description, COUNT(e.Student_ID) AS Enrolled_Students\n";
                    query += "FROM Class c\n";
                    query += "LEFT JOIN Enrollment e ON c.Course_ID = e.Course_ID\n";
                    query += "GROUP BY c.Course_Number, c.Term, c.Section_Number, c.Description\n";
                    query += "ORDER BY c.Course_Number;";
                    String result = dbc.executeSqlCommand(query);
                    // Split the result into lines
                    String[] lines = result.split("\n");

                    // Print the header row
                    System.out.println("CNumb\tTerm\tSectNumb\tDesc\tStudents");

                    // Print each line in a tabular format
                    for (String line : lines) {
                        // Skip empty lines
                        if (!line.trim().isEmpty()) {
                            String[] columns = line.split("\t");
                            for (int i = 0; i < columns.length; i++) {
                                // Adjust the output to fit within the header length
                                String column = columns[i];
                                if (i == 3) { // Description column
                                    System.out.print(column + "\t"); // Print the full description
                                } else if (i == 4) { // Enrolled Students column
                                    System.out.print(column.substring(0, Math.min(column.length(), 8)) + "\t"); // Fit
                                                                                                                // "Students"
                                                                                                                // into
                                                                                                                // the
                                                                                                                // header
                                } else {
                                    System.out.print(column + "\t");
                                }
                            }
                            System.out.println(); // Move to the next line
                        }
                    }
                } else
                    System.err.println(command + " takes no arguments.");
                break;

            case "select-class":
                if (num_args == 1) {
                    String course = args.get(0);

                    // Build the SQL query to select the class
                    query += "SELECT Course_ID, Course_Number, Term, Section_Number, Description\n";
                    query += "FROM Class\n";
                    query += "WHERE Course_Number = '" + course + "'";
                    query += " ORDER BY Term DESC, Section_Number ASC;";

                    String result = dbc.executeSqlCommand(query);
                    String[] lines = result.split("\n");

                    if (lines.length == 1) {
                        System.out.println("Selected class:");
                        System.out.println(lines[0]); // Print the selected class details
                        activeClass = lines[0]; // Set the active class

                        // Extract course ID from class details
                        String[] classDetails = lines[0].split("\t");
                        if (classDetails.length >= 1) {
                            String courseID = classDetails[0];
                            activeClass_pk = Integer.parseInt(courseID); // set the primary key (for active class) to
                                                                         // courseID
                            System.out.println("Course ID: " + courseID);
                        } else {
                            System.err.println("Error parsing class details.");
                        }
                    } else if (lines.length > 1) {
                        System.err.println("Error: Multiple sections found for the selected course.");
                    } else {
                        System.err.println("Error: No matching class found for the selected course.");
                    }
                } else if (num_args == 2) {
                    String course = args.get(0);
                    String term = args.get(1);

                    // Build the SQL query to select the class
                    query += "SELECT Course_ID, Course_Number, Term, Section_Number, Description\n";
                    query += "FROM Class\n";
                    query += "WHERE Course_Number = '" + course + "'";
                    query += " AND Term = '" + term + "'";
                    query += " ORDER BY Section_Number ASC;";

                    String result = dbc.executeSqlCommand(query);
                    String[] lines = result.split("\n");

                    if (lines.length == 1) {
                        System.out.println("Selected class:");
                        System.out.println(lines[0]); // Print the selected class details
                        activeClass = lines[0]; // Set the active class

                        // Extract course ID from class details
                        String[] classDetails = lines[0].split("\t");
                        if (classDetails.length >= 1) {
                            String courseID = classDetails[0];
                            activeClass_pk = Integer.parseInt(courseID); // set the primary key (for active class) to
                                                                         // courseID
                            System.out.println("Course ID: " + courseID);
                        } else {
                            System.err.println("Error parsing class details.");
                        }
                    } else if (lines.length > 1) {
                        System.err.println("Error: Multiple sections found for the selected course and term.");
                    } else {
                        System.err.println("Error: No matching class found for the selected course and term.");
                    }
                } else if (num_args == 3) {
                    String course = args.get(0);
                    String term = args.get(1);
                    int section = -1;

                    try {
                        section = Integer.parseInt(args.get(2));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid section number.");
                        return false;
                    }

                    // Build the SQL query to select the class
                    query += "SELECT Course_ID, Course_Number, Term, Section_Number, Description\n";
                    query += "FROM Class\n";
                    query += "WHERE Course_Number = '" + course + "'";
                    query += " AND Term = '" + term + "'";
                    query += " AND Section_Number = " + section + ";";

                    String result = dbc.executeSqlCommand(query);
                    String[] lines = result.split("\n");

                    if (lines.length == 1) {
                        System.out.println("Selected class:");
                        System.out.println(lines[0]); // Print the selected class details
                        activeClass = lines[0]; // Set the active class

                        // Extract course ID from class details
                        String[] classDetails = lines[0].split("\t");
                        if (classDetails.length >= 1) {
                            String courseID = classDetails[0];
                            activeClass_pk = Integer.parseInt(courseID); // set the primary key (for active class) to
                                                                         // courseID
                            System.out.println("Course ID: " + courseID);
                        } else {
                            System.err.println("Error parsing class details.");
                        }
                    } else if (lines.length == 0) {
                        System.err
                                .println("Error: No matching class found for the selected course, term, and section.");
                    } else {
                        System.err
                                .println("Error: Multiple sections found for the selected course, term, and section.");
                    }
                } else {
                    System.err.println(command + " requires 1 to 3 arguments: <Course> *<Semester> *<Section>.");
                }
                break;

            case "show-class":
                if (num_args == 0) {
                    // Display the current active class, if set
                    if (activeClass != null) {
                        System.out.println("Currently active class: " + activeClass);
                    } else
                        System.err.println("No class is currently selected.");
                } else
                    System.err.println(command + " takes no arguments.");
                break;

            case "show-categories":
                if (num_args == 0) {
                    // check if there is an active class
                    if (activeClass_pk != -1) {
                        // Build the query to show categories
                        query += "SELECT Category_Name, Weight, Course_ID\n";
                        query += "FROM Category\n";
                        query += "WHERE Course_ID = '" + activeClass_pk + "';";
                        // execute the query
                        String result = dbc.executeSqlCommand(query);
                        System.out.println("Categories:");
                        System.out.println("Name\tWeight\tCourseID");
                        System.out.println(result);
                    } else
                        System.err.println("No class is currently selected.");
                } else
                    System.err.println(command + " takes no arguments.");
                break;

            case "add-category":
                if (num_args == 2) {
                    if (activeClass_pk != -1) {
                        String cat_name = args.get(0);
                        double weight = Double.parseDouble(args.get(1));
                        query = String.format("INSERT INTO Category (Category_Name, Weight, Course_ID) " +
                                "VALUES ('%s', '%.2f', '%d')", cat_name, weight, activeClass_pk);
                        dbc.executeSqlCommand(query);
                        System.out.println("New category added successfully.");
                    } else
                        System.err.println("No class is currently selected.");
                } else
                    System.err.println(command + " requires 2 arguments: <Name> <Weight>.");
                break;

            case "show-assignment":
                if (num_args == 0) {
                    if (activeClass_pk != -1) {
                        // show assignments with point values sorted (or grouped) into categories
                        query += "SELECT Name, Points_Possible, Category_ID\n";
                        query += "FROM Assignment\n";
                        query += "WHERE Course_ID = " + activeClass_pk + "\n";
                        query += "ORDER BY Category_ID;";
                        String result = dbc.executeSqlCommand(query);
                        System.out.println("Assignments:");
                        System.out.println("Name\tPoints\tCategoryID");
                        System.out.println(result);
                    } else
                        System.err.println("No class is currently selected.");
                } else
                    System.err.println(command + " takes no arguments.");
                break;

            case "add-assignment":
                if (num_args == 4) {
                    if (activeClass_pk != -1) {
                        String name = args.get(0);
                        String category = args.get(1);
                        String desc = args.get(2);
                        double points_poss = Double.parseDouble(args.get(3));

                        // get category ID
                        query = "SELECT Category_ID FROM Category WHERE Category_Name = '" + category
                                + "' AND Course_ID = " + activeClass_pk;
                        String result = dbc.executeSqlCommand(query);
                        int cat_id = Integer.parseInt(result.trim());

                        // reset query buffer
                        query = "";
                        query = String.format(
                                "INSERT INTO Assignment (Name, Description, Points_Possible, Category_ID, Course_ID) " +
                                        "VALUES ('%s', '%s', '%.2f', '%d', '%d')",
                                name, desc, points_poss, cat_id, activeClass_pk);
                        dbc.executeSqlCommand(query);
                        System.out.println("New assignment added successfully.");
                    } else
                        System.err.println("No class is currently selected.");
                } else
                    System.err.println(command + " requires 4 arguments: <Name> <Category> <Description> <Points>.");
                break;

            case "add-student":
            if (num_args == 1 || num_args == 4) {
                String username = args.get(0);
        
                // Check if the student already exists
                query = "SELECT * FROM Student WHERE Username = '" + username + "'";
                String studentResult = dbc.executeSqlCommand(query);
        
                if (!studentResult.isEmpty()) {
                    // Student already exists, enroll in the current class
                    enrollExistingStudent(username, dbc);
                } else if (num_args == 4) {
                    // Add new student and enroll in the current class
                    String studentID = args.get(1);
                    String lastname = args.get(2);
                    String firstname = args.get(3);
                    addAndEnrollNewStudent(username, studentID, lastname, firstname, dbc);
                } else {
                    System.err.println("Invalid arguments. Use 'add-student <username>' or 'add-student <username> <studentid> <Last> <First>'.");
                }
            } else {
                System.err.println("Invalid arguments. Use 'add-student <username>' or 'add-student <username> <studentid> <Last> <First>'.");
            }
                break;

            case "show-students":
                if (num_args == 0) {
                    // Check if there is an active class
                    if (activeClass_pk != -1) {
                        // Build the query to fetch all students in the active class
                        query += "SELECT s.Student_ID, s.Username, s.Firstname, s.Lastname FROM Student s " +
                                "INNER JOIN Enrollment e ON s.Student_ID = e.Student_ID " +
                                "WHERE e.Course_ID = " + activeClass_pk;

                        // Execute the query
                        String result = dbc.executeSqlCommand(query);
                        if (result != null && !result.isEmpty()) {
                            System.out.println("List of Students in the Current Class:");
                            System.out.println("Student ID\tUsername\tFirstname\tLastname");
                            System.out.println(result);
                        } else {
                            System.err.println("No students found in the current class.");
                        }
                    } else {
                        System.err.println("No class is currently selected.");
                    }
                } else if (num_args == 1) {
                    // Check if there is an active class
                    if (activeClass_pk != -1) {
                        String searchString = args.get(0);
                        // Build the query to fetch students with the search string in their name or
                        // username
                        query += "SELECT s.Student_ID, s.Username, s.Firstname, s.Lastname FROM Student s " +
                                "INNER JOIN Enrollment e ON s.Student_ID = e.Student_ID " +
                                "WHERE e.Course_ID = " + activeClass_pk +
                                " AND (LOWER(s.Username) LIKE '%" + searchString.toLowerCase() + "%' " +
                                "OR LOWER(CONCAT(s.Firstname, ' ', s.Lastname)) LIKE '%" + searchString.toLowerCase()
                                + "%')";

                        // Execute the query
                        String result = dbc.executeSqlCommand(query);
                        if (result != null && !result.isEmpty()) {
                            System.out
                                    .println("List of Students in the Current Class Matching '" + searchString + "':");
                            System.out.println(result);
                        } else {
                            System.out.println("No students found matching '" + searchString + "'.");
                        }
                    } else {
                        System.err.println("No class is currently selected.");
                    }
                } else {
                    System.err.println(command + " either takes no arguments or one string argument.");
                }
                break;

            case "grade":
                if (num_args >= 3) {
                    String assignmentName = String.join(" ", args.subList(0, num_args - 2)); // Join all arguments
                                                                                             // except the last two
                    String username = args.get(num_args - 2); // Username is the second-to-last argument
                    double grade = Double.parseDouble(args.get(num_args - 1)); // Grade is the last argument

                    // Check if the assignment exists for the active class
                    query = "SELECT Assignment_ID, Points_Possible FROM Assignment WHERE Name = '" + assignmentName
                            + "' AND Course_ID = " + activeClass_pk;
                    String assignmentResult = dbc.executeSqlCommand(query);

                    if (!assignmentResult.isEmpty()) {
                        String[] assignmentData = assignmentResult.split("\t");
                        int assignmentID = Integer.parseInt(assignmentData[0]);
                        double pointsPossible = Double.parseDouble(assignmentData[1]);

                        // Check if the student exists
                        query = "SELECT Student_ID FROM Student WHERE Username = '" + username + "'";
                        String studentResult = dbc.executeSqlCommand(query);

                        if (!studentResult.isEmpty()) {
                            int studentID = Integer.parseInt(studentResult.trim());

                            // Check if the grade already exists for this student and assignment
                            query = "SELECT Earned_Points FROM Grade WHERE Student_ID = " + studentID
                                    + " AND Assignment_ID = " + assignmentID;
                            String existingGradeResult = dbc.executeSqlCommand(query);

                            if (!existingGradeResult.isEmpty()) {
                                // Update the existing grade
                                double existingGrade = Double.parseDouble(existingGradeResult.trim());
                                query = "UPDATE Grade SET Earned_Points = " + grade + " WHERE Student_ID = " + studentID
                                        + " AND Assignment_ID = " + assignmentID;
                                dbc.executeSqlCommand(query);
                                System.out.println("Grade updated successfully.");
                            } else {
                                // Add a new grade
                                if (grade <= pointsPossible) {
                                    query = "INSERT INTO Grade (Student_ID, Assignment_ID, Earned_Points) VALUES ("
                                            + studentID + ", " + assignmentID + ", " + grade + ")";
                                    dbc.executeSqlCommand(query);
                                    System.out.println("Grade added successfully.");
                                } else {
                                    System.out.println("Warning: Number of points (" + grade
                                            + ") exceeds the configured points for assignment '" + assignmentName
                                            + "' (" + pointsPossible + ").");
                                }
                            }
                        } else {
                            System.err.println("Error: Student with username '" + username + "' not found.");
                        }
                    } else {
                        System.err.println(
                                "Error: Assignment with name '" + assignmentName + "' not found in the active class.");
                    }
                } else {
                    System.err
                            .println(command + " requires at least 3 arguments: <AssignmentName> <Username> <Grade>.");
                }
                break;

            case "gradebook":
                if (num_args == 0) {
                    // do something here
                } else
                    System.err.println(command + " takes no arguments.");
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

    private static void enrollExistingStudent(String username, DatabaseConnector dbc) {
        // Enroll the existing student in the current class
        String query = "INSERT INTO Enrollment (Student_ID, Course_ID) SELECT Student_ID, " + activeClass_pk + " FROM Student WHERE Username = '" + username + "'";
        dbc.executeSqlCommand(query);
        System.out.println("Student enrolled successfully.");
    }
    
    private static void addAndEnrollNewStudent(String username, String studentID, String lastname, String firstname, DatabaseConnector dbc) {
        // Add the new student to the Student table
        String query = "INSERT INTO Student (Student_ID, Username, Firstname, Lastname) VALUES (" + studentID + ", '" + username + "', '" + firstname + "', '" + lastname + "')";
        dbc.executeSqlCommand(query);
    
        // Enroll the new student in the current class
        query = "INSERT INTO Enrollment (Student_ID, Course_ID) VALUES (" + studentID + ", " + activeClass_pk + ")";
        dbc.executeSqlCommand(query);
        System.out.println("Student added and enrolled successfully.");
    }

    // Helpful command line usage (when running the main program)
    public static void CommandLineArgUsage() {
        System.out.println("-----------------------------------");
        System.err.println("Invalid number of arguments. Syntax:");
        System.err
                .println("java -cp \"mysql-connector-java-8.0.30.jar\" Main.java <port-number> <username> <password>");
        System.err.println(
                "java -cp \"mysql-connector-java-8.0.30.jar\" Main.java <port-number> <username> <password> -d");
        System.err.println("Optional : -d deletes the database once connection is successful");
        System.out.println("-----------------------------------");
    }

    // Run with
    // java -cp "mysql-connector-java-8.0.30.jar" Main.java <port-number> <username>
    // <password>
    // or
    // java -cp "mysql-connector-java-8.0.30.jar" Main.java <port-number> <username>
    // <password> -d
    //
    // Note : -d deletes the current database before re-creating it
    public static void main(String[] args) {
        // Initialize the active class to null
        activeClass = null;
        activeClass_pk = -1; // -1 isn't a valid primary key

        // check for valid arguments
        if (args.length != 3 && args.length != 4) {
            CommandLineArgUsage();
            return;
        }

        boolean clear = false;

        if (args.length == 4) {
            if (!args[3].equals("-d")) {
                CommandLineArgUsage();
                return;
            }
            clear = true;
        }

        // test a connection
        DatabaseConnector dbc = new DatabaseConnector();
        boolean connected = dbc.connect(args[0], args[1], args[2]);

        if (!connected) {
            System.out.println("Database connection failed.");
            return;
        }
        System.out.println("Database connection successful!");

        // clear the database if -d flag was set
        if (clear)
            dbc.executeSqlCommand("DROP DATABASE IF EXISTS Gradebook;");

        dbc.executeSqlFile("Schema.sql");
        // dbc.executeSqlCommand("Use Gradebook;");

        // dbc.executeSqlFile("GradebookSchema.sql");

        dbc.executeSqlFile("dump.sql");

        // dbc.clean();

        // open a new input scanner
        Scanner sc = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            // command line carrot
            System.out.print("\r> ");
            String line = sc.nextLine();
            // get tokens for each command and its arguments

            ArrayList<String> tokens = new ArrayList<String>();
            Pattern pattern = Pattern.compile("\"[^\"]+\"|\\S+");
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                tokens.add(matcher.group());
            }

            // nothing to parse
            if (line == "" || tokens.size() == 0) {
                continue;
            }

            // parse the command
            ArrayList<String> c_args = new ArrayList<String>();
            for (int i = 1; i < tokens.size(); i++) {
                String a = tokens.get(i);
                // string begin/end string literal quotes
                // if (a.startsWith("\"") && a.endsWith("\"") && a.length() > 0) {
                // a = a.substring(1, a.length() - 1);
                // }
                c_args.add(a);
            }
            quit = parseCommand(tokens.get(0), c_args, dbc);
        }
        // close the scanner
        sc.close();
    }
}