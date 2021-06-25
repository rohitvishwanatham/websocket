package com.websocket;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class userServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 static RequestDispatcher rd;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter p=response.getWriter();
		
		
		HttpSession session=request.getSession(true);
		  
		String user=request.getParameter("username");
		session.setAttribute("username",user);
		
		if(user!=null && user.equals("doctor") ) {
			rd=request.getRequestDispatcher("/doctor.jsp");
			rd.forward(request,response);
		}
		else if(user!=null && user.equals("ambulance")) {
			rd=request.getRequestDispatcher("/ambulance.jsp");
			rd.forward(request,response);
		}
		else if(user!=null ) {
			rd=request.getRequestDispatcher("/patient.jsp");
			rd.forward(request,response);
		}
		
				
	}

	
	
}
