package com.anu;
import java.sql.*;

public class Login {
	// Database configuration
	public static final String USER = "root";
	public static final String PASSWORD = "";
	public static final String DATABSEPORT = "3306";
	
    String drv = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:" + DATABSEPORT + "/AndroidUserDB?useLegacyDatetimeCode=false&serverTimezone=Australia/Canberra";

    public boolean userLogin(String userID, String password){
        boolean isLoginSuccessful = false;
        String query = "SELECT * FROM AndroidUser WHERE UserID = '" + userID + "' and Password = '" + password + "'";
        try{
            Class.forName(drv);
            Connection conn = DriverManager.getConnection(url,USER,PASSWORD);
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