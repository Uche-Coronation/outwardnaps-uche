/**
 * 
 */
package com.cmb.model.neft;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author waliu.faleye
 *
 */
public class NeftOutwardStatusCheckResponse {	
	private String responseCode;
	
	private String responseMessage;
	
	private BatchStatusCheck batchStatusCheck;

	/**
	 * @return the responseCode
	 */
	@JsonProperty(value="ResponseCode")
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
	@JsonProperty(value="ResponseMessage")
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
	 * @return the batchStatusCheck
	 */
	@JsonProperty(value="Pfdata")
	public BatchStatusCheck getBatchStatusCheck() {
		return batchStatusCheck;
	}

	/**
	 * @param batchStatusCheck the batchStatusCheck to set
	 */
	public void setBatchStatusCheck(BatchStatusCheck batchStatusCheck) {
		this.batchStatusCheck = batchStatusCheck;
	}
}
