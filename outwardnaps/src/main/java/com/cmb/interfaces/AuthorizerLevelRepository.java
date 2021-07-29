/**
 * 
 */
package com.cmb.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.AuthorizerLevel;
import com.cmb.model.User;

/**
 * @author waliu.faleye
 *
 */
public interface AuthorizerLevelRepository extends JpaRepository<AuthorizerLevel, Long> {
	
	public AuthorizerLevel findByUser(User user);

}
