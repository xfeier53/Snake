/*
Authorship: Feier Xiao
 */

package com.anu;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetBestScoreServlet extends HttpServlet {
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Setting for the content type and encoding
    	response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        // Get parameters
        String account = request.getParameter("account");
        
        GetBestScore getBestScore = new GetBestScore();       
        int result = getBestScore.getBestScore(account);
        // Write in the result
        PrintWriter out = response.getWriter();
        out.write(result);
        out.flush();
        out.close();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
    
}