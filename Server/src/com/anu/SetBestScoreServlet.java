package com.anu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetBestScoreServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Setting for the content type and encoding
    	response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        // Get parameters
        String account = request.getParameter("account");
        String bestScore = request.getParameter("bestScore");
        SetBestScore setBestScore = new SetBestScore();
        String result = setBestScore.setBestScore(account, Integer.parseInt(bestScore));
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
