/**
 * 
 */
package com.cmb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Table(name="requestdata")
public class RequestData { 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// for transfer
	private String debitAccountNumber;

	// for transfer
	private String creditAccountNumber;

	private String tranAmount;

	private String naration;

	private String fee1Amount;

	private String fee1Account;

	private String fee2Amount;

	private String fee2Account;

	private String fee3Amount;

	private String fee3Account;

	private String fee4Amount;

	private String fee4Account;

	private String fee5Amount;

	private String fee5Account;

	private String accountNumber;

	private String transactionChannel;

	private String responseCode;

	private Date requestTime;

	private String responseDescription;

	private String uniqueIdentifier;

	private String transactionCurrency;

	private String cifId;

	private String drCifId;

	private String crCifId;

	private String batchId;

	@Column(nullable=true,length=1)
	private String reversalTransaction;

	@Transient
	private String currency;

	@Column(nullable=true,name="customerWaiveCharge")
	private boolean customerWaiveCharge = false;

	public String getDebitAccountNumber() {
		return debitAccountNumber;
	}

	public void setDebitAccountNumber(String debitAccountNumber) {
		this.debitAccountNumber = debitAccountNumber;
	}

	public String getCreditAccountNumber() {
		return creditAccountNumber;
	}

	public void setCreditAccountNumber(String creditAccountNumber) {
		this.creditAccountNumber = creditAccountNumber;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getNaration() {
		return naration;
	}

	public void setNaration(String naration) {
		this.naration = naration;
	}

	public String getFee1Amount() {
		return fee1Amount;
	}

	public void setFee1Amount(String fee1Amount) {
		this.fee1Amount = fee1Amount;
	}

	public String getFee1Account() {
		return fee1Account;
	}

	public void setFee1Account(String fee1Account) {
		this.fee1Account = fee1Account;
	}

	public String getFee2Amount() {
		return fee2Amount;
	}

	public void setFee2Amount(String fee2Amount) {
		this.fee2Amount = fee2Amount;
	}

	public String getFee2Account() {
		return fee2Account;
	}

	public void setFee2Account(String fee2Account) {
		this.fee2Account = fee2Account;
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
	 * @return the transactionChannel
	 */
	public String getTransactionChannel() {
		return transactionChannel;
	}

	/**
	 * @param transactionChannel
	 *            the transactionChannel to set
	 */
	public void setTransactionChannel(String transactionChannel) {
		this.transactionChannel = transactionChannel;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode
	 *            the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the requestTime
	 */
	public Date getRequestTime() {
		return requestTime;
	}

	/**
	 * @param requestTime
	 *            the requestTime to set
	 */
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	/**
	 * @return the responseDescription
	 */
	public String getResponseDescription() {
		return responseDescription;
	}

	/**
	 * @param responseDescription
	 *            the responseDescription to set
	 */
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	/**
	 * @return the uniqueIdentifier
	 */
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	/**
	 * @param uniqueIdentifier
	 *            the uniqueIdentifier to set
	 */
	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	/**
	 * @return the fee3Amount
	 */
	public String getFee3Amount() {
		return fee3Amount;
	}

	/**
	 * @param fee3Amount
	 *            the fee3Amount to set
	 */
	public void setFee3Amount(String fee3Amount) {
		this.fee3Amount = fee3Amount;
	}

	/**
	 * @return the fee3Account
	 */
	public String getFee3Account() {
		return fee3Account;
	}

	/**
	 * @param fee3Account
	 *            the fee3Account to set
	 */
	public void setFee3Account(String fee3Account) {
		this.fee3Account = fee3Account;
	}

	/**
	 * @return the fee4Amount
	 */
	public String getFee4Amount() {
		return fee4Amount;
	}

	/**
	 * @param fee4Amount
	 *            the fee4Amount to set
	 */
	public void setFee4Amount(String fee4Amount) {
		this.fee4Amount = fee4Amount;
	}

	/**
	 * @return the fee4Account
	 */
	public String getFee4Account() {
		return fee4Account;
	}

	/**
	 * @param fee4Account
	 *            the fee4Account to set
	 */
	public void setFee4Account(String fee4Account) {
		this.fee4Account = fee4Account;
	}

	/**
	 * @return the fee5Amount
	 */
	public String getFee5Amount() {
		return fee5Amount;
	}

	/**
	 * @param fee5Amount
	 *            the fee5Amount to set
	 */
	public void setFee5Amount(String fee5Amount) {
		this.fee5Amount = fee5Amount;
	}

	/**
	 * @return the fee5Account
	 */
	public String getFee5Account() {
		return fee5Account;
	}

	/**
	 * @param fee5Account
	 *            the fee5Account to set
	 */
	public void setFee5Account(String fee5Account) {
		this.fee5Account = fee5Account;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the reversalTransaction
	 */
	public String getReversalTransaction() {
		return reversalTransaction;
	}

	/**
	 * @param reversalTransaction the reversalTransaction to set
	 */
	public void setReversalTransaction(String reversalTransaction) {
		this.reversalTransaction = reversalTransaction;
	}

	/**
	 * @return the transactionCurrency
	 */
	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	/**
	 * @param transactionCurrency the transactionCurrency to set
	 */
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * @return the cifId
	 */
	public String getCifId() {
		return cifId;
	}

	/**
	 * @param cifId the cifId to set
	 */
	public void setCifId(String cifId) {
		this.cifId = cifId;
	}

	/**
	 * @return the drCifId
	 */
	public String getDrCifId() {
		return drCifId;
	}

	/**
	 * @param drCifId the drCifId to set
	 */
	public void setDrCifId(String drCifId) {
		this.drCifId = drCifId;
	}

	/**
	 * @return the crCifId
	 */
	public String getCrCifId() {
		return crCifId;
	}

	/**
	 * @param crCifId the crCifId to set
	 */
	public void setCrCifId(String crCifId) {
		this.crCifId = crCifId;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public boolean isCustomerWaiveCharge() {
		return customerWaiveCharge;
	}

	public void setCustomerWaiveCharge(boolean customerWaiveCharge) {
		this.customerWaiveCharge = customerWaiveCharge;
	}

}
