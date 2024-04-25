# CS410DB

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
