DROP DATABASE IF EXISTS AndroidUserDB;
CREATE DATABASE AndroidUserDB;
USE AndroidUserDB;

CREATE TABLE AndroidUser (
	UserID varchar(100) PRIMARY KEY,
	Password varchar(100) NOT NULL,
	Email varchar(100) NOT NULL
);

SELECT * FROM AndroidUser;

INSERT INTO AndroidUser (UserID, Password, Email) VALUES 
	("phil", "123", "xfeier53@gmail.com"),
	("xfeier53", "456", "u6609337@anu.edu.au"),
	("anu", "789", "anu@gmail.com");

SELECT * FROM AndroidUser;


