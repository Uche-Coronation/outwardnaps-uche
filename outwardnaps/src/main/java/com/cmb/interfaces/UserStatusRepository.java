/**
 * 
 */
package com.cmb.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.UserStatus;

/**
 * @author waliu.faleye
 *
 */
public interface UserStatusRepository extends JpaRepository<UserStatus, String> {

}
