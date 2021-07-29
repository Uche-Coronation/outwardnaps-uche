/**
 * 
 */
package com.cmb.services;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * @author waliu.faleye
 *
 */
public class LdapAuth {

	private String ip;

	private String port;

	private String baseDN;

	private String securityAuthentication;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		LdapAuth auth = new LdapAuth();
		auth.setBaseDN("CN=Configuration,DC=Coronationmb,DC=com");
		auth.setIp("172.31.250.10");
		auth.setPort("389");
		auth.setSecurityAuthentication("simple");
		//System.out.println(auth.validateCredentials("wfaleye@coronationmb.com", "Fortran45$"));
		System.out.println(auth.validateCredentials("rdiejomaoh@coronationmb.com", "I Stand In The Light!"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean validateCredentials(String username, String password) {
		boolean validUser = true;
		// Set up environment for creating initial context
		Hashtable env = new Hashtable(11);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://".concat(this.getIp()).concat(":").concat(this.getPort()).concat("/").concat(this.getBaseDN()));//172.31.250.10:389/CN=Configuration,DC=Coronationmb,DC=com");

		// Authenticate as S. User and password "mysecret"
		env.put(Context.SECURITY_AUTHENTICATION, this.getSecurityAuthentication());
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);

		try {
			// Create initial context
			DirContext ctx = new InitialDirContext(env);

			// System.out.println(ctx.lookup("ou=wfaleye@coronationmb.com"));

			// do something useful with ctx
			// System.out.println(ctx.get);

			// Close the context when we're done
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
			validUser = false;
		}

		return validUser;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the baseDN
	 */
	public String getBaseDN() {
		return baseDN;
	}

	/**
	 * @param baseDN the baseDN to set
	 */
	public void setBaseDN(String baseDN) {
		this.baseDN = baseDN;
	}

	/**
	 * @return the securityAuthentication
	 */
	public String getSecurityAuthentication() {
		return securityAuthentication;
	}

	/**
	 * @param securityAuthentication the securityAuthentication to set
	 */
	public void setSecurityAuthentication(String securityAuthentication) {
		this.securityAuthentication = securityAuthentication;
	}

}
