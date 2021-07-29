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
public class InflowApprovalData {
	
	private String appId;
	
	private List<PendingNeftData> transactionList;

	/**
	 * @return the appId
	 */
	@JsonProperty(value="appid")
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
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
