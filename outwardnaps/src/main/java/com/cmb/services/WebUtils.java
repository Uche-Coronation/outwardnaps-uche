/**
 * 
 */
package com.cmb.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author waliu.faleye
 *
 */
@Component
public class WebUtils {

	private static HttpServletRequest request;

	@Autowired
	public static void setRequest(HttpServletRequest request1) {
		request = request1;
	}

	public static String getClientIp() {

		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}
}
