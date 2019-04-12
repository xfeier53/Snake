# AssignApp2019s1

In this project, we planned to use personal laptop as the server using Apache Tomcat. The Android application should be in the same network as the server. The server should receive the request from the application, and make SQL query using JDBC to connect to MySQL database, then respond with the returned result.

Therefore, components used are listed below:
Apache Tomcat 8.5.39
MySQL 5.7.25-0ubuntu0.18.04.2

1. Run the SQL code to set up the database, test data is also given.
2. Run the server, and make sure the port 8080 is not used, or you might need to change the PORT variable in **
3. Make sure the Android device and server are in the same network, and retrieve the IP address of the server
4. Change the IPAddress variable in ** to the IP address of the server
5. Run the Android application