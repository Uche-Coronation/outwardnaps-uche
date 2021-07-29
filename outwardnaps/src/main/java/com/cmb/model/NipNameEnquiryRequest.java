/**
 * 
 */
package com.cmb.model;

/**
 * @author waliu.faleye
 *
 */
public class NipNameEnquiryRequest {
	
	private String accountNumber;
	
	private String destinationInstitutionCode;
	
	private String channelCode;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDestinationInstitutionCode() {
		return destinationInstitutionCode;
	}

	public void setDestinationInstitutionCode(String destinationInstitutionCode) {
		this.destinationInstitutionCode = destinationInstitutionCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
}
