/**
 * 
 */
package com.cmb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Table(name="maximumauthorizationlevels")
public class MaximumAuthorizationLevels {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "max_auth_level_gen")
	@SequenceGenerator(name = "max_auth_level_gen", allocationSize = 1, sequenceName = "max_auth_level_gen")
	@Id
	private Long id;

	@Column(nullable = false,name="maxlevel")
	private Long maxLevel;
	
	@Column(nullable = false,name="created_by")
	private String createdBy;
	
	@Column(nullable = false,name="created_date")
	private Date createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(Long maxLevel) {
		this.maxLevel = maxLevel;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
