/**
 * 
 */
package com.cmb.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * @author waliu.faleye
 *
 */
@Entity
//@Audited
@Table(name = "OUTWARD_NAPS_ITEMS",uniqueConstraints=@UniqueConstraint(columnNames = {
		 "REFERENCE_NUMBER", "UPLOAD_SESSION_ID" })) 
 
public class UploadRequestData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "UPLOAD_SESSION_ID", nullable = false, length = 30)
	private String uploadSessionId;

	@Column(name = "ITEM_SERIAL_NUMBER", nullable = false, length = 20)
	private Long itemSerialNumber;

	@Column(name = "ITEM_AMOUNT", nullable = false)
	private BigDecimal itemAmount;
	
	@Transient
	private String itemAmountStr;

	public String getItemAmountStr() {
		return itemAmountStr;
	}

	public void setItemAmountStr(String itemAmountStr) {
		this.itemAmountStr = itemAmountStr;
	}

	@Column(name = "ACCOUNT_SORT_CODE", nullable = false, length = 50)
	private String accountSortCode;

	@Column(name = "ACCOUNT_NUMBER", nullable = false, length = 15)
	private String accountNumber;

	@Column(name = "SENDER_NAME", nullable = true, length = 100)
	private String senderName;

	@Column(name = "RECIEVER_NAME", nullable = false, length = 100)
	private String recieverName;

	@Column(name = "TRAN_PARTICULARS", nullable = false, length = 50)
	private String transactionParticulars;

	@Column(name = "UPLOADED_BY", nullable = false, length = 50)
	private String uploadedBy;

	@Column(name = "UPLOADED_DATE", nullable = false, length = 50)
	private Date uploadedDate;

	@Transient
	private String displayUploadedDate;

	@Column(name = "UPLOADED_FILE_NAME", nullable = false, length = 50)
	private String uploadedFileName;

	@Column(name = "PROCESSOR_SOL", nullable = true, length = 5)
	private String processorSol;

	@Column(name = "ORDERING_PARTY_ACCOUNT_NUMBER", nullable = true, length = 15)
	private String orderingPartyAccountNumber;

	@Column(name = "AUTHORIZE", nullable = true, length = 50)
	private String authorize;

	@Column(name = "AUTHORIZE_BY", nullable = true, length = 50)
	private String authorizeBy;

	@Column(name = "AUTHORIZE_DATE", nullable = true)
	private Date authorizeDate;

	@Column(name = "POSTED_FLG", nullable = false, length = 1)
	private String postedFlg = "N";

	@Column(name = "POSTED_BY", nullable = true, length = 50)
	private String postedBy;

	@Column(name = "POSTED_DATE", nullable = true)
	private Date postedDate;

	@Column(name = "ERR_CODE", nullable = true, length = 3)
	private String errorCode;

	@Column(name = "REFERENCE_NUMBER", nullable = false)
	private String referenceNumber;

	@Column(name = "VERIFIED_NAME", nullable = true)
	private String verifiedName;
	
	@Column(name = "ACCEPT_ITEM", nullable = true, length = 1)
	private String acceptItem;
	
	@Column(name = "bank_name", nullable = true)
	private String bankName;
	
	@Column(name = "PAYMENT_STATUS", nullable = true)
	private String paymentStatus;

	@Column(nullable=true,name="customer_waive_charge")
	private boolean customerWaiveCharge = false;

	@ManyToOne
	@JoinColumn(name = "naps_batch_detail_id", nullable = false)
	private BatchDetail napsBatchDetail;

	// @Column(name = "ORDERING_PARTY_ACCOUNT_NAME", nullable = true, length =
	// 250)
	// private String orderingPartyAccountName;

	public Long getId() {
		return id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUploadSessionId() {
		return uploadSessionId;
	}

	public void setUploadSessionId(String uploadSessionId) {
		this.uploadSessionId = uploadSessionId;
	}

	public Long getItemSerialNumber() {
		return itemSerialNumber;
	}

	public void setItemSerialNumber(Long itemSerialNumber) {
		this.itemSerialNumber = itemSerialNumber;
	}

	public BigDecimal getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}

	public String getAccountSortCode() {
		return accountSortCode;
	}

	public void setAccountSortCode(String accountSortCode) {
		this.accountSortCode = accountSortCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getRecieverName() {
		return recieverName;
	}

	public void setRecieverName(String recieverName) {
		this.recieverName = recieverName;
	}

	public String getTransactionParticulars() {
		return transactionParticulars;
	}

	public void setTransactionParticulars(String transactionParticulars) {
		this.transactionParticulars = transactionParticulars;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public String getProcessorSol() {
		return processorSol;
	}

	public void setProcessorSol(String processorSol) {
		this.processorSol = processorSol;
	}

	public String getOrderingPartyAccountNumber() {
		return orderingPartyAccountNumber;
	}

	public void setOrderingPartyAccountNumber(String orderingPartyAccountNumber) {
		this.orderingPartyAccountNumber = orderingPartyAccountNumber;
	}

	public String getAuthorize() {
		return authorize;
	}

	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}

	public String getAuthorizeBy() {
		return authorizeBy;
	}

	public void setAuthorizeBy(String authorizeBy) {
		this.authorizeBy = authorizeBy;
	}

	public Date getAuthorizeDate() {
		return authorizeDate;
	}

	public void setAuthorizeDate(Date authorizeDate) {
		this.authorizeDate = authorizeDate;
	}

	public String getPostedFlg() {
		return postedFlg;
	}

	public void setPostedFlg(String postedFlg) {
		this.postedFlg = postedFlg;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/**
	 * @return the verifiedName
	 */
	public String getVerifiedName() {
		return verifiedName;
	}

	/**
	 * @param verifiedName the verifiedName to set
	 */
	public void setVerifiedName(String verifiedName) {
		this.verifiedName = verifiedName;
	}

	/**
	 * @return the acceptItem
	 */
	public String getAcceptItem() {
		return acceptItem;
	}

	/**
	 * @param acceptItem the acceptItem to set
	 */
	public void setAcceptItem(String acceptItem) {
		this.acceptItem = acceptItem;
	}

	public String getDisplayUploadedDate() {
		return displayUploadedDate;
	}

	public void setDisplayUploadedDate(String displayUploadedDate) {
		this.displayUploadedDate = displayUploadedDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public boolean isCustomerWaiveCharge() {
		return customerWaiveCharge;
	}

	public void setCustomerWaiveCharge(boolean customerWaiveCharge) {
		this.customerWaiveCharge = customerWaiveCharge;
	}

	/**
	 * @return the napsBatchDetail
	 */
	public BatchDetail getNapsBatchDetail() {
		return napsBatchDetail;
	}

	/**
	 * @param napsBatchDetail the napsBatchDetail to set
	 */
	public void setNapsBatchDetail(BatchDetail napsBatchDetail) {
		this.napsBatchDetail = napsBatchDetail;
	}

	/**
	 * @return the orderingPartyAccountName
	 */
	/**
	 * public String getOrderingPartyAccountName() { return
	 * orderingPartyAccountName; }
	 * 
	 * /**
	 * 
	 * @param orderingPartyAccountName
	 *            the orderingPartyAccountName to set
	 *//**
		 * public void setOrderingPartyAccountName(String
		 * orderingPartyAccountName) { this.orderingPartyAccountName =
		 * orderingPartyAccountName; }
		 **/

}
