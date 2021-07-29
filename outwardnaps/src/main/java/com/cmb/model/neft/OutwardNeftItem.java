/**
 * 
 */
package com.cmb.model.neft;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * @author waliu.faleye
 *
 */
@Entity
@Table(name="outwardneftitem")
@JsonPropertyOrder(value= {"ItemSequenceNo","SerialNo","SortCode","AccountNo","TranCode","Amount","Currency","BankOfFirstDepositDate","BankOfFirstDepositSortCode","PresentmentDate","PayerName","Beneficiary","BeneficiaryAccountNo","BVNBeneficiary","BVNPayer","CollectionType","InstrumentType","Narration","PresentingBankSortCode","SpecialClearing","InstrumentDate","MICRRepairInd","SettlementTime","CycleNo"})
public class OutwardNeftItem extends OutwardNeftBaseItem{

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "neft_generator")
	@SequenceGenerator(name = "neft_generator", allocationSize = 1, sequenceName = "neft_gen")
	@Id
	@JsonIgnore
	private Long id;

	@ManyToOne
	@JoinColumn(name = "neft_batch_detail_id", nullable = false)
	@JsonIgnore
	private NeftBatchDetail neftBatchDetail;

	@JsonIgnore
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime bankOfFirstDepositDate;

	@Transient
	private String bankOfFirstDepositDateDisplay;

	@Column(name="bank_of_first_Dep_sort_code")
	private String bankOfFirstDepositSortCode;

	@JsonIgnore
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name="presentment_date")
	private LocalDateTime presentmentDate;
	
	@Transient
	private String presentmentDateDisplay;

	@Column(name="payer_name")
	private String payerName;

	@Column(name="payer_account_no")
	private String payerAccountNo;

	private String beneficiary;

	@Column(name="beneficiary_account_no")
	private String beneficiaryAccountNo;

	@Column(name="bvn_beneficiary")
	private String bvnBeneficiary;

	@Column(name="bvn_payer")
	private String bvnPayer;

	private String narration;

	@Column(name="special_clearing")
	private boolean specialClearing;

	@JsonIgnore
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name="instrument_date")
	private LocalDateTime instrumentDate;

	@Transient
	private String instrumentDateDisplay;

	@Column(name="micr_repair_ind")
	private String micrRepairInd;

	@Column(name="cycle_no")
	private String cycleNo;

	@Column(name="customer_waive_charge")
	private boolean customerWaiveCharge = false;

	@JsonIgnore
	@Column(name="fi_request_uuid")
	private String fiRequestUuid;

	@JsonIgnore
	@Column(name="debit_response")
	private String debitResponse;

	@JsonIgnore
	@Column(name="debit_response_desc")
	private String debitResponseDesc;

	@JsonIgnore
	@Column(name="debit_tranid")
	private String debitTranId;

	@JsonIgnore
	@Column(name="debit_tran_datetime")
	private String debitTranDateTime;

	@JsonIgnore
	@Column(name="verified_name")
	private String verifiedName;

	@JsonIgnore
	private Long counter;

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
	 * @return the neftBatchDetail
	 */
	public NeftBatchDetail getNeftBatchDetail() {
		return neftBatchDetail;
	}

	/**
	 * @param neftBatchDetail the neftBatchDetail to set
	 */
	public void setNeftBatchDetail(NeftBatchDetail neftBatchDetail) {
		this.neftBatchDetail = neftBatchDetail;
	}


	/**
	 * @return the bankOfFirstDepositDate
	 */
	public LocalDateTime getBankOfFirstDepositDate() {
		return bankOfFirstDepositDate;
	}

	/**
	 * @param bankOfFirstDepositDate the bankOfFirstDepositDate to set
	 */
	public void setBankOfFirstDepositDate(LocalDateTime bankOfFirstDepositDate) {
		this.bankOfFirstDepositDate = bankOfFirstDepositDate;
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
	 * @return the presentmentDate
	 */
	public LocalDateTime getPresentmentDate() {
		return presentmentDate;
	}

	/**
	 * @param presentmentDate the presentmentDate to set
	 */
	public void setPresentmentDate(LocalDateTime presentmentDate) {
		this.presentmentDate = presentmentDate;
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
	 * @return the instrumentDate
	 */
	public LocalDateTime getInstrumentDate() {
		return instrumentDate;
	}

	/**
	 * @param instrumentDate the instrumentDate to set
	 */
	public void setInstrumentDate(LocalDateTime instrumentDate) {
		this.instrumentDate = instrumentDate;
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
	 * @return the mICRRepairInd
	 */
	@JsonProperty(value="MICRRepairInd")
	public String getMicrRepairInd() {
		return micrRepairInd;
	}

	/**
	 * @param mICRRepairInd the mICRRepairInd to set
	 */
	public void setMicrRepairInd(String mICRRepairInd) {
		micrRepairInd = mICRRepairInd;
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
	 * @return the customerWaiveCharge
	 */
	@JsonProperty(value="CustomerWaiveCharge")
	public boolean isCustomerWaiveCharge() {
		return customerWaiveCharge;
	}

	/**
	 * @param customerWaiveCharge the customerWaiveCharge to set
	 */
	public void setCustomerWaiveCharge(boolean customerWaiveCharge) {
		this.customerWaiveCharge = customerWaiveCharge;
	}

	/**
	 * @return the fiRequestUuid
	 */
	public String getFiRequestUuid() {
		return fiRequestUuid;
	}

	/**
	 * @param fiRequestUuid the fiRequestUuid to set
	 */
	public void setFiRequestUuid(String fiRequestUuid) {
		this.fiRequestUuid = fiRequestUuid;
	}

	/**
	 * @return the debitResponse
	 */
	public String getDebitResponse() {
		return debitResponse;
	}

	/**
	 * @param debitResponse the debitResponse to set
	 */
	public void setDebitResponse(String debitResponse) {
		this.debitResponse = debitResponse;
	}

	/**
	 * @return the debitResponseDesc
	 */
	public String getDebitResponseDesc() {
		return debitResponseDesc;
	}

	/**
	 * @param debitResponseDesc the debitResponseDesc to set
	 */
	public void setDebitResponseDesc(String debitResponseDesc) {
		this.debitResponseDesc = debitResponseDesc;
	}

	/**
	 * @return the debitTranId
	 */
	public String getDebitTranId() {
		return debitTranId;
	}

	/**
	 * @param debitTranId the debitTranId to set
	 */
	public void setDebitTranId(String debitTranId) {
		this.debitTranId = debitTranId;
	}

	/**
	 * @return the debitTranDateTime
	 */
	public String getDebitTranDateTime() {
		return debitTranDateTime;
	}

	/**
	 * @param debitTranDateTime the debitTranDateTime to set
	 */
	public void setDebitTranDateTime(String debitTranDateTime) {
		this.debitTranDateTime = debitTranDateTime;
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

	/**
	 * @return the payerAccountNo
	 */
	@JsonProperty(value="PayerAccountNo")
	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	/**
	 * @param payerAccountNo the payerAccountNo to set
	 */
	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}

}
