/**
 * 
 */
package com.cmb.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author waliu.faleye
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class ResponseData {

	private String responseCode;

	private String responseDescription;

	private String accountNumber;

	private String accountName;

	private String customerBVN;

	private String customerKYC;

	private BigDecimal totalTranAmount;

	private BigDecimal totalDailyLimit;

	private String totalDailyLimitStr;

	private String feeName;

	private String feeValue;

	private String feeDescription;

	private Double availableBalance;

	private Double effectiveBalance;

	private String sessionId;

	private BigDecimal dailyBalance;

	private String dailyBalanceStr;

	public ResponseData(String responseCode, String responseDescription, String accountNumber, String accountName,
			String customerBVN, String customerKYC, Double availableBalance, Double effectiveBalance,String sessionId) {
		this.responseCode = responseCode;
		this.responseDescription = responseDescription;
		this.accountNumber = accountNumber;
		this.accountName = accountName;
		this.customerBVN = customerBVN;
		this.customerKYC = customerKYC;
		this.availableBalance = availableBalance;
		this.effectiveBalance = effectiveBalance;
		this.sessionId = sessionId;
	}

	public ResponseData() {
	}

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

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the customerBVN
	 */
	public String getCustomerBVN() {
		return customerBVN;
	}

	/**
	 * @param customerBVN
	 *            the customerBVN to set
	 */
	public void setCustomerBVN(String customerBVN) {
		this.customerBVN = customerBVN;
	}

	/**
	 * @return the customerKYC
	 */
	public String getCustomerKYC() {
		return customerKYC;
	}

	/**
	 * @param customerKYC
	 *            the customerKYC to set
	 */
	public void setCustomerKYC(String customerKYC) {
		this.customerKYC = customerKYC;
	}

	/**
	 * @return the totalTranAmount
	 */
	public BigDecimal getTotalTranAmount() {
		return totalTranAmount;
	}

	/**
	 * @param totalTranAmount
	 *            the totalTranAmount to set
	 */
	public void setTotalTranAmount(BigDecimal totalTranAmount) {
		this.totalTranAmount = totalTranAmount;
	}

	/**
	 * @return the totalDailyLimit
	 */
	public BigDecimal getTotalDailyLimit() {
		return totalDailyLimit;
	}

	/**
	 * @param totalDailyLimit
	 *            the totalDailyLimit to set
	 */
	public void setTotalDailyLimit(BigDecimal totalDailyLimit) {
		this.totalDailyLimit = totalDailyLimit;
	}

	/**
	 * @return the feeName
	 */
	public String getFeeName() {
		return feeName;
	}

	/**
	 * @param feeName
	 *            the feeName to set
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
	 * @param feeValue
	 *            the feeValue to set
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
	 * @param feeDescription
	 *            the feeDescription to set
	 */
	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}

	/**
	 * @return the availableBalance
	 */
	public Double getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance
	 *            the availableBalance to set
	 */
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	/**
	 * @return the effectiveBalance
	 */
	public Double getEffectiveBalance() {
		return effectiveBalance;
	}

	/**
	 * @param effectiveBalance
	 *            the effectiveBalance to set
	 */
	public void setEffectiveBalance(Double effectiveBalance) {
		this.effectiveBalance = effectiveBalance;
	}

	/**
	 * @return the sessionId
	 */
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
	 * @return the dailyBalance
	 */
	public BigDecimal getDailyBalance() {
		return dailyBalance;
	}

	/**
	 * @param dailyBalance the dailyBalance to set
	 */
	public void setDailyBalance(BigDecimal dailyBalance) {
		this.dailyBalance = dailyBalance;
	}

	public String getTotalDailyLimitStr() {
		return totalDailyLimitStr;
	}

	public void setTotalDailyLimitStr(String totalDailyLimitStr) {
		this.totalDailyLimitStr = totalDailyLimitStr;
	}

	public String getDailyBalanceStr() {
		return dailyBalanceStr;
	}

	public void setDailyBalanceStr(String dailyBalanceStr) {
		this.dailyBalanceStr = dailyBalanceStr;
	}

}
