package com.anu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class Record {
	public String getRecord() {
		String result = "";
        String query = "SELECT * FROM Record";
        try{
        	// Get the driver class
            Class.forName(CONSTANTS.DRIVER);
            // Create connection and retrieve the result 
            Connection conn = DriverManager.getConnection(CONSTANTS.URL, CONSTANTS.USER, CONSTANTS.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            // While loop to collect every row of ResultSet
            while (rs.next()) {
            	result = result + rs.getString("Account") + " " + rs.getInt("Score") + " ";
			}
            // close Connection and ResultSet
            rs.close();
            stm.close();
            conn.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}
