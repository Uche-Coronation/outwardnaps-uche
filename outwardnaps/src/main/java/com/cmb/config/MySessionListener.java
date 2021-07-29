/**
 * 
 */
package com.cmb.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author waliu.faleye
 *
 */
public class MySessionListener implements HttpSessionListener {
	
	public void sessionCreated(HttpSessionEvent se) {
        System.out.println("calling method when session is created==");

        return; //or maybe do something, depends on what you need
   }

   public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        
        System.out.println("calling method when session is destroyed==");
        //session.getServletContext().get
        session.getServletContext().getRequestDispatcher("/login?expired=true");
        //response.sendRedirect(request.getContextPath()+"/login?expired=true");
   }
}
