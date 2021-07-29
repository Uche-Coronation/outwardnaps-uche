/**
 * 
 */
package com.cmb.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Table(name="batchdetail")
public class BatchDetail {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_generator")
	@SequenceGenerator(name = "batch_generator", allocationSize = 1, sequenceName = "batch_gen")
	@Id
	private Long id;

	@Column(name = "UPLOAD_SESSION_ID", nullable = false, length = 30,unique=true)
	private String uploadSessionId;

	@Column(name = "UPLOADED_BY", nullable = false, length = 50)
	private String uploadedBy;

	@Column(name = "UPLOADED_DATE", nullable = false, length = 50)
	private Date uploadedDate;

	@Transient
	private String displayUploadedDate;

	@Column(name = "UPLOADED_FILE_NAME", nullable = false, length = 50, unique = true)
	private String uploadedFileName;

	@Column(name = "ORDERING_PARTY_ACCOUNT_NUMBER", nullable = true, length = 15)
	private String orderingPartyAccountNumber;

	@Column(name = "ORDERING_PARTY_ACCOUNT_NAME", nullable = true)
	private String orderingPartyAccountName;
	
	@ManyToOne
	@JoinColumn(nullable=false,name="transaction_status")
	private TransactionStatus transactionStatus;

	@Column(name = "comment", nullable = true)
	private String comment;	
	
	@Column(name = "response_Code", nullable = true, length = 10)
	private String responseCode;

	@Column(name = "response_Description", nullable = true)
	private String responseDescription;

	@Column(name="customer_waive_charge")
	private boolean customerWaiveCharge = false;

	@Column(nullable=true,name="batch_Status")
	private String batchStatus;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "napsBatchDetail")
	private Set<UploadRequestData> uploadRequestData;
	
	private Long counter=0l;

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Long getId() {
		return id;
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

	public String getOrderingPartyAccountNumber() {
		return orderingPartyAccountNumber;
	}

	public void setOrderingPartyAccountNumber(String orderingPartyAccountNumber) {
		this.orderingPartyAccountNumber = orderingPartyAccountNumber;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDisplayUploadedDate() {
		return displayUploadedDate;
	}

	public void setDisplayUploadedDate(String displayUploadedDate) {
		this.displayUploadedDate = displayUploadedDate;
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

	public String getOrderingPartyAccountName() {
		return orderingPartyAccountName;
	}

	public void setOrderingPartyAccountName(String orderingPartyAccountName) {
		this.orderingPartyAccountName = orderingPartyAccountName;
	}

	public boolean isCustomerWaiveCharge() {
		return customerWaiveCharge;
	}

	public void setCustomerWaiveCharge(boolean customerWaiveCharge) {
		this.customerWaiveCharge = customerWaiveCharge;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	/**
	 * @return the uploadRequestData
	 */
	public Set<UploadRequestData> getUploadRequestData() {
		return uploadRequestData;
	}

	/**
	 * @param uploadRequestData the uploadRequestData to set
	 */
	public void setUploadRequestData(Set<UploadRequestData> uploadRequestData) {
		this.uploadRequestData = uploadRequestData;
	}

	/**
	 * @return the counter
	 */
	public Long getCounter() {
		return counter;
	}

	/**
	 * @param counter the counter to set
	 */
	public void setCounter(Long counter) {
		this.counter = counter;
	}

}
