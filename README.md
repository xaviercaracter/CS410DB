# CS410DB

## Project Description

 The Main Java Program is a command line shell program used to manage 
 information for a gradebook database. It provides management for classes, students, 
 and grades.

## Listed programs and essential files

There are two Java programs for the application:

- Main.java
This is the front-end driver program implementing the shell loop and command parsing.

- DatabaseConnector.java
This is a backend program which manages connections to the database and processes SQL
queries. 

- mysql-connector-java-8.0.30.jar
This is the JDBC driver for MySQL, which enables Java Applications to interact with
a MySQL database.

- model.pdf
This is the ER model for the database schema used in the class management system.

- GradebookSchema.sql
This is the SQL database schema (tables) for the class management system.


- Schema.sql
This is an updated version of the SQL database schema (tables) for the class management system.

- d
- d
- d
- d
- 
- 

## Compiling and Using

### Setup

To proceed, you must first have Java installed on your machine as it is essential for running the shell program.
Additionally, you need to have a working connection to an SQL database server which you can access. You will need
the port number, username, and password for the server. 

### Compiling 

Once this information is obtained, start by compiling the 



port : 50565\
username : msandbox\
password : akarro17


To compile on onyx (or other Unix like systems), run:

javac -cp "." DatabaseConnector.java Main.java

java -cp ":mysql-connector-java-8.0.30.jar" Main.java <port> <username> <password>

or

java -cp ":mysql-connector-java-8.0.30.jar" Main.java <port> <username> <password> -d

Use this java command for Windows (javac command remains the same):


java -cp ":mysql-connector-java-8.0.30.jar" Main.java <port> <username> <password>

or

java -cp ":mysql-connector-java-8.0.30.jar" Main.java <port> <username> <password> -d

Note: -d is an optional argument that tells the program to delete the current database (clean operation). It will then create a new copy of the database.

Try using\

port : 50565\
username : msandbox\
password : akarro17

as an example.

e.g.

java -cp ":mysql-connector-java-8.0.30.jar" Main.java 50565 msandbox akarro17

(Works on BSU Onyx cluster only!) Otherwise, just set up your own connection
