/**
 * 
 */
package com.cmb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author USER
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			
			.formLogin()
				.loginPage("/")
				.defaultSuccessUrl("/detailLoginRedirect")
				.failureUrl("/login-error")
				.usernameParameter("userName")
				.passwordParameter("password")
	    		.and()
	    		
	    	.authorizeRequests()
	    		.antMatchers("/js/**","/font/**","/css/**", "/img/**", "/vendors/**","/","/login-error","/error","/login-access-denied").permitAll()
	    		.anyRequest().authenticated()
				.and()
	    		
	    	.logout()     
	    		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
	    		.logoutSuccessUrl("/")
	    		.deleteCookies("JSESSIONID")
    			//.logoutSuccessHandler(new CustomLogoutSuccessHandler())
	    		.invalidateHttpSession(true)
	    		.and()
	    		
	    	.exceptionHandling() //exception handling configuration
	    		.accessDeniedPage("/login-access-denied")
	            .and()
	            
	        .sessionManagement()
	        	//.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	            //.sessionFixation()
	            //.migrateSession().invalidSessionUrl("/")
	            .maximumSessions(1)
	            .maxSessionsPreventsLogin(true).expiredUrl("/login?expired=true").and().invalidSessionUrl("/");
		
	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);

	}    
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
}
