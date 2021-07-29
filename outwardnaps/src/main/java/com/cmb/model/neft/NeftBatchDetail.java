/**
 * 
 */
package com.cmb.model.neft;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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

import com.cmb.model.TransactionStatus;
import com.cmb.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Table(name="neftbatchdetail")
@JsonPropertyOrder(value= {"appid","BankCode","TotalValue","MsgID","Date","ItemCount","SettlementTimeF","PFItemDataStores"})
public class NeftBatchDetail extends NeftBaseBatchDetail {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "neft_batch_generator")
	@SequenceGenerator(name = "neft_batch_generator", allocationSize = 1, sequenceName = "neft_batch_gen")
	@Id
	@JsonIgnore
	private Long id;
	

	@Convert(converter = LocalDateTimeConverter.class)
	@Transient
	private String settlementTimeF;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "neftBatchDetail")
	private Set<OutwardNeftItem> outwardNeftItems;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable=false,name="transaction_status")
	private TransactionStatus transactionStatus;
	
	@JsonIgnore
	@Transient
	private String displayDate;
	
	@JsonIgnore
	private String responseCode;
	
	@JsonIgnore
	private String responseDescription;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable=false,name="intiator_id")
	private User intiatorId;
	
	@JsonIgnore
	@Transient
	private String token;
	
	@JsonIgnore
	@Transient
	private String responseMessage;
	
	@JsonIgnore
	@Transient
	private String tokenRespMessage;
	
	@JsonIgnore
	private String comment;

	@JsonIgnore
	@Column(nullable=true,name="batch_Status")
	private String batchStatus;

	@JsonIgnore
	@Column(nullable=true,name="instrument_type")
	private String instrumentType;
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the responseMessage
	 */
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
	 * @return the uploadedFileName
	 */
	public String getUploadedFileName() {
		return uploadedFileName;
	}

	/**
	 * @param uploadedFileName the uploadedFileName to set
	 */
	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	@JsonIgnore
	@Column(nullable=false,name="uploaded_file_name",unique=true)
	private String uploadedFileName;
	
//	@JsonIgnore
//	@Column(nullable=false,name="customer_waive_charge")
//	private boolean customerWaiveCharge;
//
//	/**
//	 * @return the customerWaiveCharge
//	 */
//	public boolean isCustomerWaiveCharge() {
//		return customerWaiveCharge;
//	}
//
//	/**
//	 * @param customerWaiveCharge the customerWaiveCharge to set
//	 */
//	public void setCustomerWaiveCharge(boolean customerWaiveCharge) {
//		this.customerWaiveCharge = customerWaiveCharge;
//	}

	/**
	 * @return the intiatorId
	 */
	public User getIntiatorId() {
		return intiatorId;
	}

	/**
	 * @param intiatorId the intiatorId to set
	 */
	public void setIntiatorId(User intiatorId) {
		this.intiatorId = intiatorId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the settlementTimeF
	 */
	@JsonProperty(value="SettlementTimeF")
	public String getSettlementTimeF() {
		return settlementTimeF;
	}

	/**
	 * @param settlementTimeF the settlementTimeF to set
	 */
	public void setSettlementTimeF(String settlementTimeF) {
		this.settlementTimeF = settlementTimeF;
	}

	/**
	 * @return the outwardNeftItems
	 */
	@JsonProperty(value="PFItemDataStores")
	public Set<OutwardNeftItem> getOutwardNeftItems() {
		return outwardNeftItems;
	}

	/**
	 * @param outwardNeftItems the outwardNeftItems to set
	 */
	public void setOutwardNeftItems(Set<OutwardNeftItem> outwardNeftItems) {
		this.outwardNeftItems = outwardNeftItems;
	}

	/**
	 * @return the transactionStatus
	 */
	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	/**
	 * @param transactionStatus the transactionStatus to set
	 */
	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/**
	 * @return the displayDate
	 */
	public String getDisplayDate() {
		return displayDate;
	}

	/**
	 * @param displayDate the displayDate to set
	 */
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

	/**
	 * @return the responseCode
	 */
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
	 * @return the responseDescription
	 */
	public String getResponseDescription() {
		return responseDescription;
	}

	/**
	 * @param responseDescription the responseDescription to set
	 */
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	/**
	 * @return the tokenRespMessage
	 */
	public String getTokenRespMessage() {
		return tokenRespMessage;
	}

	/**
	 * @param tokenRespMessage the tokenRespMessage to set
	 */
	public void setTokenRespMessage(String tokenRespMessage) {
		this.tokenRespMessage = tokenRespMessage;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the batchStatus
	 */
	public String getBatchStatus() {
		return batchStatus;
	}

	/**
	 * @param batchStatus the batchStatus to set
	 */
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	/**
	 * @return the instrumentType
	 */
	public String getInstrumentType() {
		return instrumentType;
	}

	/**
	 * @param instrumentType the instrumentType to set
	 */
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}
	
}
