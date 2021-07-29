/**
 * 
 */
package com.cmb.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/**
 * @author waliu.faleye
 *
 */
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
	{
		Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        
        System.out.println("calling method when session expires=="+request.getParameter("expired"));
		if(request.getParameter("expired") != null)
		{
			response.sendRedirect(request.getContextPath()+"/login?expired=true");
		}
		else
		{
			response.sendRedirect(request.getContextPath() + "/login?logout=true");
		}
	}
}
