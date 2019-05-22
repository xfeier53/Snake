/*
Authorship: Feier Xiao
 */

package com.anu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetBestScore {
	public int getBestScore(String account){
		int score = 0;
		// Set query
        String query = "SELECT BestScore FROM AndroidUser WHERE Account = '" + account + "'";
        try{
        	// Get the driver class
            Class.forName(CONSTANTS.DRIVER);
            // Create connection and retrieve the result 
            Connection conn = DriverManager.getConnection(CONSTANTS.URL, CONSTANTS.USER, CONSTANTS.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
            	score = rs.getInt(1);
			}
            // close Connection and ResultSet
            rs.close();
            stm.close();
            conn.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        return score;
    }
}
