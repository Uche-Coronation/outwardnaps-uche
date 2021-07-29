/**
 * 
 */
package com.cmb.model.neft;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.cmb.model.TransactionStatus;
import com.cmb.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Table(name="pendingneftitem",uniqueConstraints = @UniqueConstraint(columnNames = { "msg_id", "item_sequence_no" }))
public class PendingNeftData {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pending_neft_generator")
	@SequenceGenerator(name = "pending_neft_generator", allocationSize = 1, sequenceName = "pending_neft_gen")
	@Id
	@JsonIgnore
	private Long id;
	
	private Long itemId;

	@Column(name="bank_code")
	private String bankCode;
	
	@Column(name="total_value")
	private BigDecimal totalValue;
	
	//@JsonIgnore
	//@Column(name="msg_id",unique=true)
	//private String msgIdStr;
	
	@Column(name="msg_id")
	private Long msgId;	
	
	@Column(name="item_count")
	private Long itemCount;
	
	@Column(name="item_sequence_no")
	private String itemSequenceNo;

	@Column(name="serial_no")
	private String serialNo;

	@Column(name="sort_code")
	private String sortCode;

	@Column(name="account_no")
	private String accountNo;

	@Column(name="tran_code")
	private String tranCode;

	private BigDecimal amount;

	@JsonIgnore
	@Transient
	private String amountStr;

	private String currency;

	//@JsonIgnore
	//@Convert(converter = LocalDateTimeConverter.class)
	//private LocalDateTime bankOfFirstDepositDate;

	//@Transient
	private String bankOfFirstDepositDateDisplay;

	@Column(name="bank_of_first_Dep_sort_code")
	private String bankOfFirstDepositSortCode;

	//@JsonIgnore
	//@Convert(converter = LocalDateTimeConverter.class)
	//@Column(name="presentment_date")
	//private LocalDateTime presentmentDate;
	
	//@Transient
	private String presentmentDateDisplay;

	@Column(name="payer_name")
	private String payerName;

	private String beneficiary;

	@Column(name="beneficiary_account_no")
	private String beneficiaryAccountNo;

	@Column(name="bvn_beneficiary")
	private String bvnBeneficiary;

	@Column(name="bvn_payer")
	private String bvnPayer;

	@Column(name="collection_type")
	private String collectionType;

	@Column(name="instrument_type")
	private String instrumentType;

	private String narration;
	
	@Column(name="presenting_bank_sort_code")
	private String presentingBankSortCode;

	@Column(name="special_clearing")
	private boolean specialClearing;

//	@JsonIgnore
//	@Convert(converter = LocalDateTimeConverter.class)
//	@Column(name="instrument_date")
//	private LocalDateTime instrumentDate;

	//@Transient
	private String instrumentDateDisplay;

	@Column(name="micr_repair_ind")
	private String micrRepairInd;

//	@JsonIgnore
//	@Convert(converter = LocalDateTimeConverter.class)
//	@Column(name="settement_time")
//	private LocalDateTime settlementTime;

	//@Transient
	private String settlementTimeDisplay;

	@Column(name="cycle_no")
	private String cycleNo;

	private String status;

	private String approval;

	private String finaclestatus;	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable=true,name="transaction_status")
	private TransactionStatus transactionStatus;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable=true,name="return_reason")
	private ReturnReason returnReason;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable=true,name="initiator_user_id")
	private User initiator;
	
	@Transient
	private String bankName;
	
	@JsonIgnore
	@JoinColumn(nullable=true,name="response_code")
	private String responseCode;
	
	@JsonIgnore
	@JoinColumn(nullable=true,name="response_description")
	private String responseDescription;
	
	private String statusdescription;
	
	private String finaclereversal;
	
	private String delflg;
	
	@JsonIgnore
	@Column(nullable=true,name="initiator_comment")
	private String initiatorCommment;
	
	private String date;
	
	private String selection;
	
	@Transient
	private String finacletranid;
	
	@Transient
	private String finacletrandate;
	
	@JsonIgnore
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(nullable=true,name="transaction_time")
	private LocalDateTime transactionTime;

	/**
	 * @return the itemId
	 */
	@JsonProperty(value="Id")
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
	 * @return the bankCode
	 */
	@JsonProperty(value="BankCode")
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
	 * @return the totalValue
	 */
	@JsonProperty(value="TotalValue")
	public BigDecimal getTotalValue() {
		return totalValue;
	}

	/**
	 * @param totalValue the totalValue to set
	 */
	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	/**
	 * @return the msgId
	 */
	@JsonProperty(value="MsgID")
	public Long getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the itemCount
	 */
	@JsonProperty(value="ItemCount")
	public Long getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * @return the itemSequenceNo
	 */
	@JsonProperty(value="ItemSequenceNo")
	public String getItemSequenceNo() {
		return itemSequenceNo;
	}

	/**
	 * @param itemSequenceNo the itemSequenceNo to set
	 */
	public void setItemSequenceNo(String itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

	/**
	 * @return the serialNo
	 */
	@JsonProperty(value="SerialNo")
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the sortCode
	 */
	@JsonProperty(value="SortCode")
	public String getSortCode() {
		return sortCode;
	}

	/**
	 * @param sortCode the sortCode to set
	 */
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	/**
	 * @return the accountNo
	 */
	@JsonProperty(value="AccountNo")
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the tranCode
	 */
	@JsonProperty(value="TranCode")
	public String getTranCode() {
		return tranCode;
	}

	/**
	 * @param tranCode the tranCode to set
	 */
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	/**
	 * @return the amount
	 */
	@JsonProperty(value="Amount")
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the amountStr
	 */
	public String getAmountStr() {
		return amountStr;
	}

	/**
	 * @param amountStr the amountStr to set
	 */
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	/**
	 * @return the currency
	 */
	@JsonProperty(value="Currency")
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the bankOfFirstDepositDateDisplay
	 */
	@JsonProperty(value="BankOfFirstDepositDate")
	public String getBankOfFirstDepositDateDisplay() {
		return bankOfFirstDepositDateDisplay;
	}

	/**
	 * @param bankOfFirstDepositDateDisplay the bankOfFirstDepositDateDisplay to set
	 */
	public void setBankOfFirstDepositDateDisplay(String bankOfFirstDepositDateDisplay) {
		this.bankOfFirstDepositDateDisplay = bankOfFirstDepositDateDisplay;
	}

	/**
	 * @return the bankOfFirstDepositSortCode
	 */
	@JsonProperty(value="BankOfFirstDepositSortCode")
	public String getBankOfFirstDepositSortCode() {
		return bankOfFirstDepositSortCode;
	}

	/**
	 * @param bankOfFirstDepositSortCode the bankOfFirstDepositSortCode to set
	 */
	public void setBankOfFirstDepositSortCode(String bankOfFirstDepositSortCode) {
		this.bankOfFirstDepositSortCode = bankOfFirstDepositSortCode;
	}

	/**
	 * @return the presentmentDateDisplay
	 */
	@JsonProperty(value="PresentmentDate")
	public String getPresentmentDateDisplay() {
		return presentmentDateDisplay;
	}

	/**
	 * @param presentmentDateDisplay the presentmentDateDisplay to set
	 */
	public void setPresentmentDateDisplay(String presentmentDateDisplay) {
		this.presentmentDateDisplay = presentmentDateDisplay;
	}

	/**
	 * @return the payerName
	 */
	@JsonProperty(value="PayerName")
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
	 * @return the beneficiary
	 */
	@JsonProperty(value="Beneficiary")
	public String getBeneficiary() {
		return beneficiary;
	}

	/**
	 * @param beneficiary the beneficiary to set
	 */
	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	/**
	 * @return the beneficiaryAccountNo
	 */
	@JsonProperty(value="BeneficiaryAccountNo")
	public String getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}

	/**
	 * @param beneficiaryAccountNo the beneficiaryAccountNo to set
	 */
	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}

	/**
	 * @return the bvnBeneficiary
	 */
	@JsonProperty(value="BVNBeneficiary")
	public String getBvnBeneficiary() {
		return bvnBeneficiary;
	}

	/**
	 * @param bvnBeneficiary the bvnBeneficiary to set
	 */
	public void setBvnBeneficiary(String bvnBeneficiary) {
		this.bvnBeneficiary = bvnBeneficiary;
	}

	/**
	 * @return the bvnPayer
	 */
	@JsonProperty(value="BVNPayer")
	public String getBvnPayer() {
		return bvnPayer;
	}

	/**
	 * @param bvnPayer the bvnPayer to set
	 */
	public void setBvnPayer(String bvnPayer) {
		this.bvnPayer = bvnPayer;
	}

	/**
	 * @return the collectionType
	 */
	@JsonProperty(value="CollectionType")
	public String getCollectionType() {
		return collectionType;
	}

	/**
	 * @param collectionType the collectionType to set
	 */
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * @return the instrumentType
	 */
	@JsonProperty(value="InstrumentType")
	public String getInstrumentType() {
		return instrumentType;
	}

	/**
	 * @param instrumentType the instrumentType to set
	 */
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}

	/**
	 * @return the narration
	 */
	@JsonProperty(value="Narration")
	public String getNarration() {
		return narration;
	}

	/**
	 * @param narration the narration to set
	 */
	public void setNarration(String narration) {
		this.narration = narration;
	}

	/**
	 * @return the presentingBankSortCode
	 */
	@JsonProperty(value="PresentingBankSortCode")
	public String getPresentingBankSortCode() {
		return presentingBankSortCode;
	}

	/**
	 * @param presentingBankSortCode the presentingBankSortCode to set
	 */
	public void setPresentingBankSortCode(String presentingBankSortCode) {
		this.presentingBankSortCode = presentingBankSortCode;
	}

	/**
	 * @return the specialClearing
	 */
	@JsonProperty(value="SpecialClearing")
	public boolean isSpecialClearing() {
		return specialClearing;
	}

	/**
	 * @param specialClearing the specialClearing to set
	 */
	public void setSpecialClearing(boolean specialClearing) {
		this.specialClearing = specialClearing;
	}

	/**
	 * @return the instrumentDateDisplay
	 */
	@JsonProperty(value="InstrumentDate")
	public String getInstrumentDateDisplay() {
		return instrumentDateDisplay;
	}

	/**
	 * @param instrumentDateDisplay the instrumentDateDisplay to set
	 */
	public void setInstrumentDateDisplay(String instrumentDateDisplay) {
		this.instrumentDateDisplay = instrumentDateDisplay;
	}

	/**
	 * @return the micrRepairInd
	 */
	@JsonProperty(value="MICRRepairInd")
	public String getMicrRepairInd() {
		return micrRepairInd;
	}

	/**
	 * @param micrRepairInd the micrRepairInd to set
	 */
	public void setMicrRepairInd(String micrRepairInd) {
		this.micrRepairInd = micrRepairInd;
	}

	/**
	 * @return the settlementTimeDisplay
	 */
	@JsonProperty(value="SettlementTime")
	public String getSettlementTimeDisplay() {
		return settlementTimeDisplay;
	}

	/**
	 * @param settlementTimeDisplay the settlementTimeDisplay to set
	 */
	public void setSettlementTimeDisplay(String settlementTimeDisplay) {
		this.settlementTimeDisplay = settlementTimeDisplay;
	}

	/**
	 * @return the cycleNo
	 */
	@JsonProperty(value="CycleNo")
	public String getCycleNo() {
		return cycleNo;
	}

	/**
	 * @param cycleNo the cycleNo to set
	 */
	public void setCycleNo(String cycleNo) {
		this.cycleNo = cycleNo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the approval
	 */
	public String getApproval() {
		return approval;
	}

	/**
	 * @param approval the approval to set
	 */
	public void setApproval(String approval) {
		this.approval = approval;
	}

	/**
	 * @return the finaclestatus
	 */
	public String getFinaclestatus() {
		return finaclestatus;
	}

	/**
	 * @param finaclestatus the finaclestatus to set
	 */
	public void setFinaclestatus(String finaclestatus) {
		this.finaclestatus = finaclestatus;
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
	 * @return the returnReason
	 */
	public ReturnReason getReturnReason() {
		return returnReason;
	}

	/**
	 * @param returnReason the returnReason to set
	 */
	public void setReturnReason(ReturnReason returnReason) {
		this.returnReason = returnReason;
	}

	/**
	 * @return the initiator
	 */
	public User getInitiator() {
		return initiator;
	}

	/**
	 * @param initiator the initiator to set
	 */
	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	 * @return the statusdescription
	 */
	public String getStatusdescription() {
		return statusdescription;
	}

	/**
	 * @param statusdescription the statusdescription to set
	 */
	public void setStatusdescription(String statusdescription) {
		this.statusdescription = statusdescription;
	}

	/**
	 * @return the finaclereversal
	 */
	public String getFinaclereversal() {
		return finaclereversal;
	}

	/**
	 * @param finaclereversal the finaclereversal to set
	 */
	public void setFinaclereversal(String finaclereversal) {
		this.finaclereversal = finaclereversal;
	}

	/**
	 * @return the delflg
	 */
	public String getDelflg() {
		return delflg;
	}

	/**
	 * @param delflg the delflg to set
	 */
	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}

	/**
	 * @return the initiatorCommment
	 */
	public String getInitiatorCommment() {
		return initiatorCommment;
	}

	/**
	 * @param initiatorCommment the initiatorCommment to set
	 */
	public void setInitiatorCommment(String initiatorCommment) {
		this.initiatorCommment = initiatorCommment;
	}

	/**
	 * @return the date
	 */
	@JsonProperty(value="Date")
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the selection
	 */
	public String getSelection() {
		return selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(String selection) {
		this.selection = selection;
	}

	/**
	 * @return the finacletranid
	 */
	public String getFinacletranid() {
		return finacletranid;
	}

	/**
	 * @param finacletranid the finacletranid to set
	 */
	public void setFinacletranid(String finacletranid) {
		this.finacletranid = finacletranid;
	}

	/**
	 * @return the finacletrandate
	 */
	public String getFinacletrandate() {
		return finacletrandate;
	}

	/**
	 * @param finacletrandate the finacletrandate to set
	 */
	public void setFinacletrandate(String finacletrandate) {
		this.finacletrandate = finacletrandate;
	}

	/**
	 * @return the transactionTime
	 */
	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}

	/**
	 * @param transactionTime the transactionTime to set
	 */
	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}
}
