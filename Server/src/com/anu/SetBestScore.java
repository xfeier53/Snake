/*
Authorship: Feier Xiao
 */

package com.anu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SetBestScore {
	public String setBestScore(String account, int bestScore){
		String result = "fail";
		
		// Set query
        String query = "UPDATE AndroidUser SET BestScore = " + bestScore + " WHERE Account = '" + account + "'";        
        try{
        	// Get the driver class
            Class.forName(CONSTANTS.DRIVER);
            // Create connection and retrieve the result 
            Connection conn = DriverManager.getConnection(CONSTANTS.URL, CONSTANTS.USER, CONSTANTS.PASSWORD);
            Statement stm = conn.createStatement();
            stm.execute(query);
            result = "success";
        }catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}
