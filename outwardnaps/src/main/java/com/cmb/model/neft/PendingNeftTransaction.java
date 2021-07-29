/**
 * 
 */
package com.cmb.model.neft;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author waliu.faleye
 *
 */
public class PendingNeftTransaction {
	
	private String responseCode;
	
	private String responseMessage;
	
	private List<PendingNeftData> transactionList;

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @param responseMessage the responseMessage to set
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	/**
	 * @return the transactionList
	 */
	@JsonProperty(value="IPFDataStorelist")
	public List<PendingNeftData> getTransactionList() {
		return transactionList;
	}

	/**
	 * @param transactionList the transactionList to set
	 */
	public void setTransactionList(List<PendingNeftData> transactionList) {
		this.transactionList = transactionList;
	}

}
