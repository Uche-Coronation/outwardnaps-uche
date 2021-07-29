/**
 * 
 */
package com.cmb.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author waliu.faleye
 *
 */
public class AcctId {
	
	@NotEmpty @NotNull @NotBlank
	private String acctId;

	/**
	 * @return the acctId
	 */
	@JsonProperty(value = "acctId")
	public String getAcctId() {
		return acctId;
	}

	/**
	 * @param acctId the acctId to set
	 */
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

}
