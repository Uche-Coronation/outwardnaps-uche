/**
 * 
 */
package com.cmb.services;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * @author waliu.faleye
 *
 */
public class ADSService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		fetchEmployees("sakinola@coronationmb.com","Ayodeji$123456");
	}
	
	private static DirContext ldapContext;
	  private static String LDAP_URL = "ldap://172.31.250.10:389";
	  private static String SECURITY_AUTH = "simple";
	  private static String CTXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

	  public ADSService() {
	  }


	  public static void fetchEmployees(String username, String password){
	        try
	        {
	          Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
	          ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, CTXT_FACTORY);
	          ldapEnv.put(Context.PROVIDER_URL, LDAP_URL);
	          ldapEnv.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTH);
	          ldapEnv.put(Context.SECURITY_PRINCIPAL, username );
	          ldapEnv.put(Context.SECURITY_CREDENTIALS, password);
	          DirContext ldapContext = new InitialDirContext(ldapEnv);
	          int count = 0;

	          // Create the search controls         
	          SearchControls searchCtls = new SearchControls();

	          //Specify the attributes to return
	          //String returnedAtts[]={"sn","givenName", "samAccountName", "mail", "employeeID"};
	          //searchCtls.setReturningAttributes(returnedAtts);

	          //Specify the search scope
	          searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

	          //specify the LDAP search filter
	          //String searchFilter = "(&(objectCategory=Person)(objectClass=user))";
	          String searchFilter = "(&(objectClass=user)(mail=" + username + "))";

	          //Specify the Base for the search
	          //String searchBase = "dc=dom,dc=fr";
	          //initialize counter to total the results
	          int totalResults = 0;
	          String searchBase = "CN=Configuration,DC=Coronationmb,DC=com";
	          // Search for objects using the filter
	          NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);

	          //Loop through the search results
	          while (answer.hasMoreElements())
	          {
	        	  System.out.println("checking****");
	            SearchResult sr = (SearchResult) answer.next();      
	            Attributes attrs = sr.getAttributes();
	            count++;
	            if (attrs != null)
	            {
	                NamingEnumeration ne = attrs.getAll();
	                while (ne.hasMore())
	                {
	                  Attribute attr = (Attribute) ne.next();
	                  System.out.println("Attribute  :: " + attr);

	                }
	                ne.close();
	              }
	          }

	        }catch(NamingException ne){
	            ne.printStackTrace();
	        } catch (Exception e){
	          e.printStackTrace();
	        }

	    }

}
