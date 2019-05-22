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
            Class.forName(CONSTANTS.DRIVER);
            Connection conn = DriverManager.getConnection(CONSTANTS.URL, CONSTANTS.USER, CONSTANTS.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
            	result = result + rs.getString("Account") + " " + rs.getInt("Score") + " ";
			}
            rs.close();
            stm.close();
            conn.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}
