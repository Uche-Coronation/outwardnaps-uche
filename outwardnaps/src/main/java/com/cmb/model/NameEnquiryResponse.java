/**
 * 
 */
package com.cmb.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author waliu.faleye
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class NameEnquiryResponse {

	private String responseCode;

	private String responseText;

	private String accountName;

	private String accountNumber;

	private String accountCurrency;

	private String status;

	private BigDecimal balance;

	private String restriction;

	private String cifId;

	private String accountSchmCode;

	private String misCode;

	private String bvn;


	/**
	 * @return the bvn
	 */
	public String getBvn() {
		return bvn;
	}

	/**
	 * @param bvn the bvn to set
	 */
	public void setBvn(String bvn) {
		this.bvn = bvn;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the restriction
	 */
	public String getRestriction() {
		return restriction;
	}

	/**
	 * @param restriction
	 *            the restriction to set
	 */
	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

	/**
	 * @return the cifId
	 */
	public String getCifId() {
		return cifId;
	}

	/**
	 * @param cifId
	 *            the cifId to set
	 */
	public void setCifId(String cifId) {
		this.cifId = cifId;
	}

	/**
	 * @return the accountSchmCode
	 */
	public String getAccountSchmCode() {
		return accountSchmCode;
	}

	/**
	 * @param accountSchmCode
	 *            the accountSchmCode to set
	 */
	public void setAccountSchmCode(String accountSchmCode) {
		this.accountSchmCode = accountSchmCode;
	}

	/**
	 * @return the misCode
	 */
	public String getMisCode() {
		return misCode;
	}

	/**
	 * @param misCode
	 *            the misCode to set
	 */
	public void setMisCode(String misCode) {
		this.misCode = misCode;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountCurrency() {
		return accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

}
