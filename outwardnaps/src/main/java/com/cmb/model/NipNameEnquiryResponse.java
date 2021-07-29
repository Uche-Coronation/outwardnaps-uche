/**
 * 
 */
package com.cmb.model;

/**
 * @author waliu.faleye
 *
 */
public class NipNameEnquiryResponse {
	
	private String responseCode;
	
	private String responseDescription;
	
	private String accountName;
	
	private String customerBVN;
	
	private String customerKYC;
	
	private String sessionId;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCustomerBVN() {
		return customerBVN;
	}

	public void setCustomerBVN(String customerBVN) {
		this.customerBVN = customerBVN;
	}

	public String getCustomerKYC() {
		return customerKYC;
	}

	public void setCustomerKYC(String customerKYC) {
		this.customerKYC = customerKYC;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
