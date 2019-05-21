package com.anu;

public interface CONSTANTS {
	public static final String USER = "root";
	public static final String PASSWORD = "123456";
	public static final String DATABSEPORT = "3306";
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:" + CONSTANTS.DATABSEPORT + "/AndroidUserDB?useLegacyDatetimeCode=false&serverTimezone=Australia/Canberra";
}
