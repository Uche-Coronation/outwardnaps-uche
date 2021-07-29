/**
 * 
 */
package com.cmb.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author waliu.faleye
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findByUserNameAndPassword(String userName, String password); 
	
	public List<User> findByUserName(String userName);
	
	public User findByUserNameIgnoreCaseAndDeleteFlgNot(String userName,String delFlag); 
	
	public User findByUserNameIgnoreCaseAndStatusAndDeleteFlg(String userName,UserStatus status,String delFlag); 

}
