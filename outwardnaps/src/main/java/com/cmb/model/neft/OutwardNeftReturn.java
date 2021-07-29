/**
 * 
 */
package com.cmb.model.neft;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author waliu.faleye
 *
 */
public class OutwardNeftReturn extends OutwardNeftBaseItem {
	public OutwardNeftReturn() {
		super();
		// TODO Auto-generated constructor stub
	}	
	/**
	 * 
	 */
	public OutwardNeftReturn(OutwardNeftBaseItem base) {
		super(base);
		// TODO Auto-generated constructor stub
	}

	private String sessionId;

	private String returnReason;

	private String expiryDate;

	private String checkDigit;
	
	@Column(name="item_sequence_no")
	private String itemSequenceNo;

	/**
	 * @return the sessionId
	 */
	@JsonProperty(value = "SessionID")
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the returnReson
	 */
	@JsonProperty(value = "ReturnReason")
	public String getReturnReason() {
		return returnReason;
	}

	/**
	 * @param returnReson the returnReson to set
	 */
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	/**
	 * @return the expiryDate
	 */
	@JsonProperty(value = "ExpiryDate")
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the checkDigit
	 */
	@JsonProperty(value = "CheckDigit")
	public String getCheckDigit() {
		return checkDigit;
	}

	/**
	 * @param checkDigit the checkDigit to set
	 */
	public void setCheckDigit(String checkDigit) {
		this.checkDigit = checkDigit;
	}
	
	/**
	 * @return the itemSequenceNo
	 */
	@JsonProperty(value="ItemSequence")
	public String getItemSequenceNo() {
		return itemSequenceNo;
	}

	/**
	 * @param itemSequenceNo the itemSequenceNo to set
	 */
	public void setItemSequenceNo(String itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

}
