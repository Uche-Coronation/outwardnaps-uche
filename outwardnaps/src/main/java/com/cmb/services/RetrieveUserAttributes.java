/**
 * 
 */
package com.cmb.services;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * @author waliu.faleye
 *
 */
public class RetrieveUserAttributes {
	
	public static void main(String[] args) {
        RetrieveUserAttributes retrieveUserAttributes = new RetrieveUserAttributes();
        retrieveUserAttributes.getUserBasicAttributes("wfaleye@coronationmb.com", retrieveUserAttributes.getLdapContext());
    }
 
    public LdapContext getLdapContext(){
        LdapContext ctx = null;
        try{
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
            env.put(Context.SECURITY_PRINCIPAL, "wfaleye@coronationmb.com");
            env.put(Context.SECURITY_CREDENTIALS, "Fortran44$");
            env.put(Context.PROVIDER_URL, "ldap://172.31.250.10:389");
            // To get rid of the PartialResultException when using Active Directory
            env.put(Context.REFERRAL, "follow");
            ctx = new InitialLdapContext(env, null);
            System.out.println("Connection Successful.");
        }catch(NamingException nex){
            System.out.println("LDAP Connection: FAILED");
            nex.printStackTrace();
        }
        return ctx;
    }
 
    private void getUserBasicAttributes(String username, LdapContext ctx) {
        //User user=null;
        try {
 
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = { "distinguishedName",
                    "sn",
                    "givenname",
                    "mail",
                    "telephonenumber"};
            constraints.setReturningAttributes(attrIDs);
            //First input parameter is search bas, it can be "CN=Users,DC=YourDomain,DC=com"
            //Second Attribute can be uid=username
            NamingEnumeration answer = ctx.search("CN=Configuration,DC=Coronationmb,DC=com", "mail="
                    + username, constraints);
            if (answer.hasMore()) {
                Attributes attrs = ((SearchResult) answer.next()).getAttributes();
                System.out.println("distinguishedName "+ attrs.get("distinguishedName"));
                System.out.println("givenname "+ attrs.get("givenname"));
                System.out.println("sn "+ attrs.get("sn"));
                System.out.println("mail "+ attrs.get("mail"));
                System.out.println("telephonenumber "+ attrs.get("telephonenumber"));
            }else{
                throw new Exception("Invalid User");
            }
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //return user;
    }
 
}
