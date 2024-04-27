# CS410 Final Project
## Boise State University

### Xavier Caracter  (xaviercaracter@u.boisestate.edu)
### Paul Vanderveeen   (paulvanderveen@u.boisestate.edu)


## Project Description

 The Main Java Program is a command line shell program used to manage 
 information for a gradebook database. It provides management for classes, students, 
 and grades.

## Listed programs and essential files

There are two Java programs for the application:

- Main.java\
This is the front-end driver program implementing the shell loop and command parsing.

- DatabaseConnector.java\
This is a backend program which manages connections to the database and processes SQL
queries. 

- mysql-connector-java-8.0.30.jar\
This is the JDBC driver for MySQL, which enables Java Applications to interact with
a MySQL database.

- model.pdf\
This is the ER model for the database schema used in the class management system.

- Schema.sql\
The SQL database schema (tables) used for the class management system.

- dump.sql\
An SQL file containing populated sample data of different courses, students, assignments, and grades


## Compiling and Using

### Setup

To proceed, you must first have Java installed on your machine as it is essential for running the shell program.
Additionally, you need to have a working connection to an SQL database server which you can access. You will need
the port number, username, and password for the server. 

### Compiling 

Once this information is obtained, start by compiling the two java programs:

To compile on both onyx and Windows (or other Unix like systems), run the following command:

javac -cp "." DatabaseConnector.java Main.java

### Using

To run, use either the following command:

java -cp ":mysql-connector-java-8.0.30.jar" Main.java <port> <username> <password>

java -cp ":mysql-connector-java-8.0.30.jar" Main.java <port> <username> <password> -d

The fourth argument (-d) switch is an optional command line switch which tells the shell application to 
delete the current Gradebook database.

Upon running the program, the connection is automatically tested. If a connection is successful, the program
will attempt to load the database schema and populated data by running the queries on the server.

After this, the program will enter the main shell loop as follows

\> 

where you can enter different database commands. A full comprehenisve documentation and list of all the commands
is not explicity stated here. To see all the implemented commands, and to get started using the program, run:

\> help

This will print a list of all commands, along with their documentation and description on how to use them.

To exit the shell, simply enter:

\> quit


NOTE: On Windows, you would change ":mysql-connector-java-8.0.30.jar" to ";mysql-connector-java-8.0.30.jar".
The Javac compiling command is the Same for Windows as it is with Unix/Linux.

Try the following credentials when running the program

port : 50565\
username : msandbox\
password : akarro17

run:

java -cp ":mysql-connector-java-8.0.30.jar" Main.java 50565 msandbox akarro17

NOTE: This connection Works on BSU's onyx cluster only! Otherwise, just set up your own connection


## Demo Video 

To see a quick demonstration and rundown of all the database commands, go to the YouTube link listed below. The demo video shows examples
of how to run particular commands

https://www.youtube.com/watch?v=wUU-q5V-6kk




