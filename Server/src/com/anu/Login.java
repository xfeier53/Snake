/*
Authorship: Feier Xiao
 */

package com.anu;
import java.sql.*;

public class Login {	

    public boolean userLogin(String account, String password){
        boolean isLoginSuccessful = false;
        String query = "SELECT * FROM AndroidUser WHERE Account = '" + account + "' and Password = '" + password + "'";
        try{
        	// Get the driver class
            Class.forName(CONSTANTS.DRIVER);
            // Create connection and retrieve the result 
            Connection conn = DriverManager.getConnection(CONSTANTS.URL, CONSTANTS.USER, CONSTANTS.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if(rs.next()){
            	isLoginSuccessful = true;
            }
            // close Connection and ResultSet
            rs.close();
            stm.close();
            conn.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        if(isLoginSuccessful){
            return true;
        } else {
        	return false;
        }
    }
}