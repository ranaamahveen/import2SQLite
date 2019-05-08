package com.sql.ms3.mavensql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.PropertyConfigurator;

import java.sql.Statement;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import java.util.*;



public class MainClass {
	private final static Logger LOGGER = Logger.getLogger(MainClass.class.getName());
	public static void main(String[] args) {
		
		try {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			PropertyConfigurator.configure(classLoader.getResourceAsStream("log4j.properties"));
			Class.forName("org.sqlite.JDBC");
			
			Connection connection = DriverManager.getConnection("jdbc:sqlite:ms3.db");
			
			// create a new table if table does not already exist
			String createSql = "CREATE TABLE IF NOT EXISTS PERSON " +
	                   "(A VARCHAR(255), " +
	                   " B VARCHAR(255), " + 
	                   " C VARCHAR(255), " + 
	                   " D VARCHAR(255), " + 
	                   " E VARCHAR(255), " +
	                   " F VARCHAR(255), " +
	                   " G VARCHAR(255), " + 
	                   " H VARCHAR(255), " +
	                   " I VARCHAR(255), " +
	                   " J VARCHAR(255))";
			  try  {
				  Statement stmt = connection.createStatement();
		            stmt.execute(createSql);
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
			//Query to insert to table Person with 10 values
			String query = "INSERT INTO PERSON VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			//Create prepare statement 
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			//Get list product from file text
			System.out.println("Please enter the source CSV filename: "); /*D:\WVU\Interviews\MS3\ms3Interview1.csv*/
			Scanner userFile = new Scanner(System.in);
			String inputFileName = userFile.nextLine().trim();
			ArrayList<CSVReader> listProduct = getListProductFromTextFile(inputFileName);
			//Insert list to SQLite DB
			System.out.println("Inserting good records to SQLite DB....");
			for(int i = 1; i < listProduct.size(); i ++) {
				preparedStatement.setString(1, listProduct.get(i).getA());
				preparedStatement.setString(2, listProduct.get(i).getB());
				preparedStatement.setString(3, listProduct.get(i).getC());
				preparedStatement.setString(4, listProduct.get(i).getD());
				preparedStatement.setString(5, listProduct.get(i).getE());
				preparedStatement.setString(6, listProduct.get(i).getF());
				preparedStatement.setString(7, listProduct.get(i).getG());
				preparedStatement.setString(8, listProduct.get(i).getH());
				preparedStatement.setString(9, listProduct.get(i).getI());
				preparedStatement.setString(10, listProduct.get(i).getJ());
				preparedStatement.executeUpdate();
			}
			LOGGER.info("Insert to SQLite DB is successful!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<CSVReader> getListProductFromTextFile(String filePath) {
		InputStreamReader isr = null;
		BufferedReader bReader = null;
		FileInputStream fis = null;
		PrintWriter pw;
		boolean blank;
		StringBuffer csvData = new StringBuffer("");
		int recordsFailedCount = 0;
		int recordsReceivedCount = 0;
		int validCols = 10;
		ArrayList<CSVReader> listFile = new ArrayList<CSVReader>();
		try {
			fis = new FileInputStream(filePath);
			isr = new InputStreamReader(fis);
			bReader = new BufferedReader(isr);
			String line = null;
			String[] str = null;
			String header = bReader.readLine();
			csvData.append(header);
			csvData.append('\n');
			//loop until the end of CSV is reached
			while(true) {
				blank = false;
				line = bReader.readLine();
				if(line == null) {
					break;
				} else {
					recordsReceivedCount++;
					str = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					for (String element : str) {
		                if (isBlank(element)) {
		                	blank = true;
		                	break;
		                }
		            }
					//checking for invalid column count
					if(!blank && (str.length>0 && str.length == validCols)) {
						listFile.add(new CSVReader(str[0], str[1], str[2], str[3], str[4], str[5], str[6], str[7],str[8], str[9]));
					}
					else {
						try {
							String fileName = new SimpleDateFormat("'bad-data'yyyyMMddHHmm'.csv'").format(new Date());
							pw = new PrintWriter(new File(fileName));
							csvData.append(line);
							csvData.append('\n');
							pw.write(csvData.toString());
					        pw.close();
						} catch (FileNotFoundException e) {
				            e.printStackTrace();
				        }
						recordsFailedCount++;
					}
				}
			}
			//logging the statics to log file
			LOGGER.info("number of records recieved : " +recordsReceivedCount);
			LOGGER.info("number of good records : " +(recordsReceivedCount-recordsFailedCount));
			LOGGER.info("number of bad records : " +recordsFailedCount);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bReader.close();
				isr.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listFile;
	}
	public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
