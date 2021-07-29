/**
 * 
 */
package com.cmb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Table(name="transactionstatus")
public class TransactionStatus {

	@Column(length = 1)
	@Id
	private String status;

	private String description;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
