/**
 * 
 */
package com.cmb.model;

/**
 * @author waliu.faleye
 *
 */
public class FeeResponse {
	
	private String feeName;
	
	private String feeValue;
	
	private String feeDescription;

	/**
	 * @return the feeName
	 */
	public String getFeeName() {
		return feeName;
	}

	/**
	 * @param feeName the feeName to set
	 */
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	/**
	 * @return the feeValue
	 */
	public String getFeeValue() {
		return feeValue;
	}

	/**
	 * @param feeValue the feeValue to set
	 */
	public void setFeeValue(String feeValue) {
		this.feeValue = feeValue;
	}

	/**
	 * @return the feeDescription
	 */
	public String getFeeDescription() {
		return feeDescription;
	}

	/**
	 * @param feeDescription the feeDescription to set
	 */
	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}
}
