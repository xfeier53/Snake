/*
Authorship: Feier Xiao
 */

package com.anu;

public interface CONSTANTS {
	// Setting for the server and database
	public static final String USER = "root";
	public static final String PASSWORD = "123456";
	public static final String DATABSEPORT = "3306";
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:" + CONSTANTS.DATABSEPORT + "/AndroidUserDB?useLegacyDatetimeCode=false&serverTimezone=Australia/Canberra";
}
