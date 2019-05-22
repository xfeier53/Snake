package com.anu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register {
	
	public boolean userRegister(String account, String password, String email){
        boolean isRegisterSuccessful = false;
        String sql = "SELECT * FROM AndroidUser WHERE Account = '" + account + "' OR Email = '" + email + "'";

        try{
        	// Get the driver class
            Class.forName(CONSTANTS.DRIVER);
            // Create connection and insert the new row 
            Connection conn = DriverManager.getConnection(CONSTANTS.URL, CONSTANTS.USER, CONSTANTS.PASSWORD);
            Statement stm = conn.createStatement();
            // Check whether the account or email has been registered
            ResultSet rs = stm.executeQuery(sql);
            // !rs.next() means there is no duplicate record and we can register the new user
            if(!rs.next()){
                sql = "INSERT INTO AndroidUser (Account, Password, Email) VALUES ('" + account + "','" + password + "','" + email + "')";
                stm.execute(sql);
                isRegisterSuccessful = true;
            }
            // close Connection and ResultSet
            rs.close();
            stm.close();
            conn.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        return isRegisterSuccessful;
    }
}
