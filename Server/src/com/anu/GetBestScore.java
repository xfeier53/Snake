package com.anu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetBestScore {
	public int getBestScore(String account){
		int score = 0;
        String query = "SELECT BestScore FROM AndroidUser WHERE Account = '" + account + "'";
        try{
            Class.forName(CONSTANTS.DRIVER);
            Connection conn = DriverManager.getConnection(CONSTANTS.URL, CONSTANTS.USER, CONSTANTS.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
            	score = rs.getInt(1);
			}
            rs.close();
            stm.close();
            conn.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        return score;
    }
}
