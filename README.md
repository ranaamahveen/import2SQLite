# import2SQLite
List of files in the repository
-----------------------------------------------------------------------------------------------------------------------
1. Source files in mavensql folder
2. READ.md file with all the instructions
3. Source CSV file 
4. DDL file with create and insert queries

Prerequisites
-----------------------------------------------------------------------------------------------------------------------
1. Java and maven should be installed in the machine prior to building the project
2. Set Java and maven home in environmental variables path
3. Open command line and enter 'java -version' and 'mvn -version' commands to see if the installation is successful

Instructions to build the project
-----------------------------------------------------------------------------------------------------------------------
1. Navigate to repository https://github.com/ranaamahveen/import2SQLite.git 
2. Download the source files from git repository 
3. Open command prompt and navigate to the folder mavensql where pom.xml is located
4. Run the command 'mvn clean package' to clean and build the jar package
5. After successful build, 'target' folder is created, a jar file named 'importCSV-0.0.1-SNAPSHOT.jar' and other files including .class files are generated within other folders in target folder

Instructions to run the program
-----------------------------------------------------------------------------------------------------------------------
1. Run the command 'java -jar target/importCSV-0.0.1-SNAPSHOT.jar' from the same path where pom.xml is located
2. A prompt to enter csv file name will be shown, enter the source csv file name(ms3Interview.csv) with path
3. After a successful run, ms3log.txt, ms3.db and bad-data<time-stamp>.csv will be generated in your working directory
4. Load ms3.db to check the inserted table using SQLite manager addon or SQLite DB manager

Technologies and Versions used
----------------------------------------
I used the following technologies:

Java, JDBC, maven build tool, log4j for logging purposes, DB browser for SQLite for testing and Eclipse IDE

maven - apache-maven-3.6.1

Jdk – jdk-10

SQLite JDBC driver - sqlite-jdbc-3.8.6.jar

Log4j – log4j-1.2.17.jar

Requirements
------------------------------------------------
1. CSV file is inserted into the SQLite DB
2. Records with any blank cells or records containing more than the given number of columns are verified and written to bad-data-<timestamp>.csv
3. Mentioned statistics are written to log file named ms3log.txt
4. DDL file with all used queries is submitted
5. Maven build tool is used to build and run the project

Approach
---------------------------------------------
Eclipse IDE is used to build this project, I explored a little about SQLite DB 
After reading the programming challenge instruction file, I researched about SQLite DB and JDBC connection to SQLite. I tried both SQLite manager addon for google chrome and SQLite DB browser to get familiar with SQLite. Then, I proceeded with the code to parse the CSV and used SQLite JDBC driver to initiate the connection between java and SQLIte.

I had a little difficulty in parsing the column value which was separated with comma within the same column, but I figured it out after re-reading the instructions. I used log4j for logging the statistics to the log file.

I modified the code to build and run it using maven build tool and also included DDL file and sample source csv file.
