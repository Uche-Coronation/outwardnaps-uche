/**
 * 
 */
package com.cmb.model;

/**
 * @author waliu.faleye
 *
 */
public class FeeRequest {
	
	private String tranAmount;
	
	private String transactionChannel;

	/**
	 * @param tranAmount
	 * @param transactionChannel
	 */
	public FeeRequest(String tranAmount, String transactionChannel) {
		super();
		this.tranAmount = tranAmount;
		this.transactionChannel = transactionChannel;
	}

	/**
	 * @return the tranAmount
	 */
	public String getTranAmount() {
		return tranAmount;
	}

	/**
	 * @param tranAmount the tranAmount to set
	 */
	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	/**
	 * @return the transactionChannel
	 */
	public String getTransactionChannel() {
		return transactionChannel;
	}

	/**
	 * @param transactionChannel the transactionChannel to set
	 */
	public void setTransactionChannel(String transactionChannel) {
		this.transactionChannel = transactionChannel;
	}

}
