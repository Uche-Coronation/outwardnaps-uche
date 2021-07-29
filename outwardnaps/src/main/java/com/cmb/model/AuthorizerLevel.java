/**
 * 
 */
package com.cmb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Table(name="authorizerlevel")
public class AuthorizerLevel {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_level_generator")
	@SequenceGenerator(name = "auth_level_generator", allocationSize = 1, sequenceName = "auth_level_gen")
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false,unique = true)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	private Long level;

}
