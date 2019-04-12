package com.anu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register {
	
	public boolean userRegister(String userID, String password, String email){
        boolean isRegisterSuccessful = false;
        String sql = "SELECT * FROM AndroidUser WHERE UserID = '" + userID + "'";
 
        try{
            Class.forName(CONSTANTS.DRIVER);
            Connection conn = DriverManager.getConnection(CONSTANTS.URL, CONSTANTS.USER, CONSTANTS.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
 
            if(!rs.next()){
                sql = "INSERT INTO AndroidUser (UserID, Password, Email) VALUES ('" + userID + "','" + password + "','" + email + "')";
                stm.execute(sql);
                isRegisterSuccessful = true;
            }
 
            rs.close();
            stm.close();
            conn.close();
        }catch (Exception e) {
            System.out.println(e);
        }
 
        if(isRegisterSuccessful){
            return true;
        } else {
        	return false;
        }
    }
}
