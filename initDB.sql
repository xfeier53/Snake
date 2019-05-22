CREATE DATABASE AndroidUserDB;
USE AndroidUserDB;

CREATE TABLE AndroidUser (
	Account varchar(100) PRIMARY KEY,
	Password varchar(100) NOT NULL,
	Email varchar(100) UNIQUE NOT NULL,
	BestScore int DEFAULT '0'
);

CREATE TABLE Record (
	Account varchar(100) NOT NULL,
	Score int DEFAULT '0'
);


INSERT INTO Record (Account, Score) VALUES 
	("Feier", 15),
	("Yuan", 10),
	("ANU", 5),
	("Nobody4", 0),
	("Nobody5", 0);

SELECT * FROM Record;

INSERT INTO AndroidUser (Account, Password, Email, BestScore) VALUES 
	("Feier", "123", "u6609337@anu.edu.au", 15),
	("Yuan", "456", "u6479455@anu.edu.au", 10),
	("ANU", "789", "u1234567@anu.edu.au", 5);


SELECT * FROM AndroidUser;


