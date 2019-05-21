package com.anu;
import java.sql.*;

public class Login {	

    public boolean userLogin(String account, String password){
        boolean isLoginSuccessful = false;
        String query = "SELECT * FROM AndroidUser WHERE Account = '" + account + "' and Password = '" + password + "'";
        try{
            Class.forName(CONSTANTS.DRIVER);
            Connection conn = DriverManager.getConnection(CONSTANTS.URL, CONSTANTS.USER, CONSTANTS.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if(rs.next()){
            	isLoginSuccessful = true;
            }
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