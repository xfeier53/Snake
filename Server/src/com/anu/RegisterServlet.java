package com.anu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Setting for the content type and encoding
    	response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        // Get parameters
        String account=request.getParameter("account");
        String password=request.getParameter("password");
        String email = request.getParameter("email");
        
        boolean isRegisterSuccessful = false;
        Register register = new Register();
        String result;
        isRegisterSuccessful = register.userRegister(account, password, email);
        PrintWriter out = response.getWriter();
        // Write in the result
        if(isRegisterSuccessful){
            result = "success";
        }
        else {
            result = "fail";
        }
        out.write(result);
        out.flush();
        out.close();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
}