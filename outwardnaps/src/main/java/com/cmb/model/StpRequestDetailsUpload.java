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
@Table(name = "STP_REQUEST_DETAILS_UPLOAD")
public class StpRequestDetailsUpload {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long STP_REQUEST_DETAILS_UPLOAD_ID;
	
	@Column(name="DESCRIPTION", nullable=true, length=255)
	private String description;
	
	@Column(name="BENEFICIARY_NAME", nullable=false, length=255)
	private String beneficiaryName;
	
	@Column(name="MOBILE_NUMBER", nullable=true, length=20)
	private String mobileNumber;
	
	@Column(name="EMAIL", nullable=true, length=50)
	private String email;
	
	@Column(name="AMOUNT", nullable=false, length=50)
	private String amount;
	
	@Column(name="BEN_ACCOUNT_NUMBER", nullable=false, length=20)
	private String beneficiaryAccountNumber;
	
	@Column(name="PAYMENT_REF", nullable=true, length=15)
	private String paymentReference;
	
	@Column(name="BATCH_PROCESSING_STATUS", nullable=true, length=6)
	private String batchProcessingStatus;
	
	@Column(name="TRANX_STATUS", nullable=true, length=50)
	private String tranxStatus;
	
	@Column(name="TRANSDATE", nullable=true, length=50)
	private Date transDate;
	
	@Column(name="BANK_CODE", nullable=true, length=10)
	private String bankCode;
	
	@Column(name="ACCOUNT_NAME", nullable=false, length=255)
	private String accountName;
	
	@Column(name="ACCOUNT_NUMBER", nullable=false, length=20)
	private String accountNumber;
	
	@Column(name="ADDRESS", nullable=true, length=255)
	private String address;
	
	@Column(name="REQUEST_ID", nullable=true, length=50)
	private String requestId;
	
	@Column(name="TRANSACTION_ID", nullable=true, length=20)
	private String transactionId;
	
	@Column(name="BEN_BANK_CODE", nullable=true, length=15)
	private String beneficiaryBankCode;
	
	@Column(name="BATCH_ID", nullable=true, length=25)
	private String batchId;
	
	@Column(name="PAYER_NAME", nullable=true, length=255)
	private String payerName;
	
	@Column(name="PAYER_ACCOUNT_NUMBER", nullable=true, length=20)
	private String payerAccountNumber;
	
	@Column(name="PAYER_BVN", nullable=true, length=20)
	private String payerBvn;
	
	@Column(name="NAPS_PAYMENT_REFERENCE", nullable=true, length=50)
	private String napsPaymentReference;
	
	@Column(name="STATUS_DETAILS", nullable=true, length=255)
	private String statusDetails;
	
	@Column(name="SN", nullable=true)
	private Long sn;

	@Transient
	private boolean customerWaiveCharge = false;
	
	@Transient
	private boolean ignoreLimitCheck = false;

	/**
	 * @return the sTP_REQUEST_DETAILS_UPLOAD_ID
	 */
	public Long getSTP_REQUEST_DETAILS_UPLOAD_ID() {
		return STP_REQUEST_DETAILS_UPLOAD_ID;
	}

	/**
	 * @param sTP_REQUEST_DETAILS_UPLOAD_ID the sTP_REQUEST_DETAILS_UPLOAD_ID to set
	 */
	public void setSTP_REQUEST_DETAILS_UPLOAD_ID(Long sTP_REQUEST_DETAILS_UPLOAD_ID) {
		STP_REQUEST_DETAILS_UPLOAD_ID = sTP_REQUEST_DETAILS_UPLOAD_ID;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the beneficiaryName
	 */
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	/**
	 * @param beneficiaryName the beneficiaryName to set
	 */
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the beneficiaryAccountNumber
	 */
	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	/**
	 * @param beneficiaryAccountNumber the beneficiaryAccountNumber to set
	 */
	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	/**
	 * @return the paymentReference
	 */
	public String getPaymentReference() {
		return paymentReference;
	}

	/**
	 * @param paymentReference the paymentReference to set
	 */
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * @return the batchProcessingStatus
	 */
	public String getBatchProcessingStatus() {
		return batchProcessingStatus;
	}

	/**
	 * @param batchProcessingStatus the batchProcessingStatus to set
	 */
	public void setBatchProcessingStatus(String batchProcessingStatus) {
		this.batchProcessingStatus = batchProcessingStatus;
	}

	/**
	 * @return the tranxStatus
	 */
	public String getTranxStatus() {
		return tranxStatus;
	}

	/**
	 * @param tranxStatus the tranxStatus to set
	 */
	public void setTranxStatus(String tranxStatus) {
		this.tranxStatus = tranxStatus;
	}

	/**
	 * @return the transDate
	 */
	public Date getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the beneficiaryBankCode
	 */
	public String getBeneficiaryBankCode() {
		return beneficiaryBankCode;
	}

	/**
	 * @param beneficiaryBankCode the beneficiaryBankCode to set
	 */
	public void setBeneficiaryBankCode(String beneficiaryBankCode) {
		this.beneficiaryBankCode = beneficiaryBankCode;
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

	/**
	 * @return the payerName
	 */
	public String getPayerName() {
		return payerName;
	}

	/**
	 * @param payerName the payerName to set
	 */
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	/**
	 * @return the payerAccountNumber
	 */
	public String getPayerAccountNumber() {
		return payerAccountNumber;
	}

	/**
	 * @param payerAccountNumber the payerAccountNumber to set
	 */
	public void setPayerAccountNumber(String payerAccountNumber) {
		this.payerAccountNumber = payerAccountNumber;
	}

	/**
	 * @return the payerBvn
	 */
	public String getPayerBvn() {
		return payerBvn;
	}

	/**
	 * @param payerBvn the payerBvn to set
	 */
	public void setPayerBvn(String payerBvn) {
		this.payerBvn = payerBvn;
	}

	/**
	 * @return the napsPaymentReference
	 */
	public String getNapsPaymentReference() {
		return napsPaymentReference;
	}

	/**
	 * @param napsPaymentReference the napsPaymentReference to set
	 */
	public void setNapsPaymentReference(String napsPaymentReference) {
		this.napsPaymentReference = napsPaymentReference;
	}

	/**
	 * @return the statusDetails
	 */
	public String getStatusDetails() {
		return statusDetails;
	}

	/**
	 * @param statusDetails the statusDetails to set
	 */
	public void setStatusDetails(String statusDetails) {
		this.statusDetails = statusDetails;
	}

	/**
	 * @return the sn
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}

	public boolean isCustomerWaiveCharge() {
		return customerWaiveCharge;
	}

	public void setCustomerWaiveCharge(boolean customerWaiveCharge) {
		this.customerWaiveCharge = customerWaiveCharge;
	}

	public boolean isIgnoreLimitCheck() {
		return ignoreLimitCheck;
	}

	public void setIgnoreLimitCheck(boolean ignoreLimitCheck) {
		this.ignoreLimitCheck = ignoreLimitCheck;
	}
}
