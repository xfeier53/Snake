# AssignApp2019s1

In this project, we planned to use personal laptop as the server using Apache Tomcat. The Android application should be in the same network as the server. The server should receive the request from the application, and make SQL query using JDBC to connect to MySQL database, then respond with the returned result.

Therefore, components used are listed below:
1. Apache Tomcat 8.5.39
2. MySQL 5.7.25-0
3. ubuntu0.18.04.2

Steps to set up the environment:
1. Run the SQL (initDB.sql) to set up the database, test data is also given. Change the setting in CONSTANTS interface if needed
2. Run the server, and make sure the port 8080 is not used, or you might need to change the PORT variable in CONSTANTS interface
3. Make sure the Android device and server are in the same network, and retrieve the IP address of the server
4. Change the IPAddress variable in CONSTANTS interface to the IP address of the server
5. Run the Android application
