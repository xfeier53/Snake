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

Team structure and roles

Feier Xiao u6609337 - Team leader, J2EE developer, DBA, Login/Register/Rank Activity Implementation
Yue Zhou u6682532 - Gesture Function/Game Activity Implementation
Yu Wang u5762606 - GameView Class/Game Activity Implementation, Documentation
Tao Xu u5527268 - Time Line/UI Implementation, Debugging

App Overview

The game we implemented on the Android phone is inspired by the popular game “Snake”
The object of the game is to direct a snake to eat different foods, meanwhile, avoid walls and obstacles to survive and win a high score
The snake starts at the center of the screen. It must eat foods and avoid walls which contain tracks with different moving speed.
The food has three types with different buff, normal type(white), earn 1 mark and slightly speed up, special type(dark blue), earn 5 marks and slow down a little, bonus type(sky blue, only 4 in each round), earn 10 marks.
The snake, by swiping the screen to control its direction. Once the snake hits walls or obstacles, the game will end and the top 5 scores will be displayed with the player’s name and their corresponding score at the leader board.
