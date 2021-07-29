/**
 * 
 */
package com.cmb.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;

/**
 * @author waliu.faleye
 *
 */
public class UploadData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long serial;

	private String refNumber;

	private String accountNumber;

	private String sortCode;

	private String beneficiary;

	private BigDecimal amount;

	private String narration;

	private String fileName;

	private String orderingPartyAccountNumber;

	private String orderingPartyAccountName;

	private String uploadSessionId;
	List<UploadRequestData> reqDataList;
	
	private String token;
	
	private String tokenRespMessage;
	
	private String responseMessage;
	
	private String comment;
	
	private String transactionStatus;

	private String customerWaiveCharge;
	
	private String orderingPartyAccountDailyLimit;
	
	private String orderingPartyAccountDailyBalance;
	
	private String totalTransactionAmount;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenRespMessage() {
		return tokenRespMessage;
	}

	public void setTokenRespMessage(String tokenRespMessage) {
		this.tokenRespMessage = tokenRespMessage;
	}

	public Long getSerial() {
		return serial;
	}

	public void setSerial(Long serial) {
		this.serial = serial;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the orderingPartyAccountNumber
	 */
	public String getOrderingPartyAccountNumber() {
		return orderingPartyAccountNumber;
	}

	/**
	 * @param orderingPartyAccountNumber
	 *            the orderingPartyAccountNumber to set
	 */
	public void setOrderingPartyAccountNumber(String orderingPartyAccountNumber) {
		this.orderingPartyAccountNumber = orderingPartyAccountNumber;
	}

	/**
	 * @return the orderingPartyAccountName
	 */
	public String getOrderingPartyAccountName() {
		return orderingPartyAccountName;
	}

	/**
	 * @param orderingPartyAccountName
	 *            the orderingPartyAccountName to set
	 */
	public void setOrderingPartyAccountName(String orderingPartyAccountName) {
		this.orderingPartyAccountName = orderingPartyAccountName;
	}

	/**
	 * @return the uploadSessionId
	 */
	public String getUploadSessionId() {
		return uploadSessionId;
	}

	/**
	 * @param uploadSessionId
	 *            the uploadSessionId to set
	 */
	public void setUploadSessionId(String uploadSessionId) {
		this.uploadSessionId = uploadSessionId;
	}

	/**
	 * @return the reqDataList
	 */
	public List<UploadRequestData> getReqDataList() {
		return reqDataList;
	}

	/**
	 * @param reqDataList
	 *            the reqDataList to set
	 */
	public void setReqDataList(List<UploadRequestData> reqDataList) {
		this.reqDataList = reqDataList;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getCustomerWaiveCharge() {
		return customerWaiveCharge;
	}

	public void setCustomerWaiveCharge(String customerWaiveCharge) {
		this.customerWaiveCharge = customerWaiveCharge;
	}

	public String getOrderingPartyAccountDailyLimit() {
		return orderingPartyAccountDailyLimit;
	}

	public void setOrderingPartyAccountDailyLimit(String orderingPartyAccountDailyLimit) {
		this.orderingPartyAccountDailyLimit = orderingPartyAccountDailyLimit;
	}

	public String getOrderingPartyAccountDailyBalance() {
		return orderingPartyAccountDailyBalance;
	}

	public void setOrderingPartyAccountDailyBalance(String orderingPartyAccountDailyBalance) {
		this.orderingPartyAccountDailyBalance = orderingPartyAccountDailyBalance;
	}

	public String getTotalTransactionAmount() {
		return totalTransactionAmount;
	}

	public void setTotalTransactionAmount(String totalTransactionAmount) {
		this.totalTransactionAmount = totalTransactionAmount;
	}

}
