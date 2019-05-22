package com.anu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SetRecord {
	public String setRecord(String recordString){
		String result = "fail";
		
		// Set query
        String deleteQuery = "DELETE FROM Record WHERE TRUE;";

        String insertQuery = "INSERT INTO Record (Account, Score) VALUES ";

        String[] record = recordString.split(" ");
        for (int i = 0; i < 10; i = i + 2) {
        	insertQuery = insertQuery + "(\"" + record[i] + "\"," + record[i + 1] + "), ";
        }
        insertQuery = insertQuery.substring(0, insertQuery.length() - 2);
        insertQuery = insertQuery + ";";    
        
        try{
        	// Get the driver class
            Class.forName(CONSTANTS.DRIVER);
            // Create connection and retrieve the result 
            Connection conn = DriverManager.getConnection(CONSTANTS.URL, CONSTANTS.USER, CONSTANTS.PASSWORD);
            Statement stm = conn.createStatement();
            stm.execute(deleteQuery);
            stm.execute(insertQuery);
            result = "success";
        }catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}
