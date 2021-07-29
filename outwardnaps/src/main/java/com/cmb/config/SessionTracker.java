/**
 * 
 */
package com.cmb.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author waliu.faleye
 *
 */
public class SessionTracker implements Filter {

	private static final Log log = LogFactory.getLog(SessionTracker.class);
	private static ThreadLocal<HttpSession> _httpSession = new ThreadLocal<HttpSession>();
	private static ThreadLocal<HttpServletRequest> _httpRequest = new ThreadLocal<HttpServletRequest>();

	/**
	 * Initialize.
	 */
	public void init(FilterConfig config) {
		log.debug("initializing");
	}

	/**
	 * Finish.
	 */
	public void destroy() {
		log.debug("destroy");
	}

	/**
	 * Remember http session in current thread and check authentification.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// determine http session
		System.out.println("SessionTracker doFilter");
		HttpServletRequest hhtpRequest = (HttpServletRequest) request;
		HttpSession session = hhtpRequest.getSession(false);
		// don't create if it doesn't exist
		if(session != null && !session.isNew()) {
		    chain.doFilter(request, response);
		} else {
			redirectToLoginPage(response);
		}
	}

	/**
	 * Returns true if this path requires a login.
	 */
	private boolean isProtected(String path) {
		// add your code here
		return false;
	}

	/**
	 * Does a redirect to the login page.
	 */
	private void redirectToLoginPage(ServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("trying a redirect to the login page");
		}
		try {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.reset();
			httpResponse.sendRedirect(httpResponse.encodeRedirectURL("/login?expired=true"));
		} catch (Exception exc) {
			log.warn("could not redirect to login page", exc);
		}
	}

	/**
	 * Current http session.
	 *
	 * @return the current session
	 */
	public static HttpSession getHttpSession() {
		return _httpSession.get();
	}

	/**
	 * Current http request.
	 *
	 * @return the current request
	 */
	public static HttpServletRequest getHttpRequest() {
		return _httpRequest.get();
	}
}
