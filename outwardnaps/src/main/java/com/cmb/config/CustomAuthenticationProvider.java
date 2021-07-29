/**
 * 
 */
package com.cmb.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cmb.model.Privilege;
import com.cmb.model.User;
import com.cmb.model.UserRepository;
import com.cmb.model.UserRole;
import com.cmb.model.UserStatus;
import com.cmb.services.BaseInterface;

/**
 * @author waliu.faleye
 *
 */
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	BaseInterface baseInterface;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	List<UserStatus> userStatuses;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String name = authentication.getName();
		String password = "";
		Authentication auth = null;
		//List<User> users = new ArrayList<User>();
		List<UserRole> roles = new ArrayList<UserRole>();
		User user = null;
		// System.out.println("authentication.getCredentials()===" +
		// authentication.getCredentials());
		try {
			if (authentication.getCredentials() != null) {
				password = authentication.getCredentials().toString();

				System.out.println("name===" + name);
				// System.out.println("password===" + password);
				Boolean validUser = baseInterface.validateCredentials(name, password);
				System.out.println("validUser===" + validUser);
				if (!validUser) {
					throw new BadCredentialsException("Not a valid AD credential");
				}else {
					UserStatus activeStatus = userStatuses.stream().filter(a -> "A".equalsIgnoreCase(a.getStatus())).collect(Collectors.toList()).get(0);
					user = userRepo.findByUserNameIgnoreCaseAndStatusAndDeleteFlg(name,activeStatus,"N");
					if(user != null) {
						roles.add(user.getUserRole());
					}else {
						throw new UsernameNotFoundException("Not a valid User credential");					
					}
				}
			}
			auth = new UsernamePasswordAuthenticationToken(user, password,this.getAuthorities(roles));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<UserRole> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<UserRole> roles) {

		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		for (UserRole role : roles) {
			if(role.getPrivileges() != null)
			collection.addAll(role.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

}
