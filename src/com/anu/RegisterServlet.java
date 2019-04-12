package com.anu;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        String userID=request.getParameter("userID");
        String password=request.getParameter("password");
        String email = request.getParameter("email");
        
        boolean isRegisterSuccessful = false;
        Register register = new Register();
        isRegisterSuccessful=register.userRegister(userID, password, email);
        if(isRegisterSuccessful){
        	System.out.println("yes");
        } else{
        	System.out.println("no");
        }
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    }
}