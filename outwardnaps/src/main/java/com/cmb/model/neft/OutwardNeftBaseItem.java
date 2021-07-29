/**
 * 
 */
package com.cmb.model.neft;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author waliu.faleye
 *
 */
@MappedSuperclass
public class OutwardNeftBaseItem {
	
	public OutwardNeftBaseItem() {
		super();
	}
	/**
	 * @param itemSequenceNo
	 * @param serialNo
	 * @param sortCode
	 * @param bankName
	 * @param accountNo
	 * @param tranCode
	 * @param amount
	 * @param amountStr
	 * @param currency
	 * @param presentingBankSortCode
	 * @param collectionType
	 * @param instrumentType
	 * @param settlementTime
	 * @param settlementTimeDisplay
	 * @param paymentStatus
	 */
	public OutwardNeftBaseItem(OutwardNeftBaseItem a) {
		super();
		this.itemSequenceNo = a.itemSequenceNo;
		this.serialNo = a.serialNo;
		this.sortCode = a.sortCode;
		this.bankName = a.bankName;
		this.accountNo = a.accountNo;
		this.tranCode = a.tranCode;
		this.amount = a.amount;
		this.amountStr = a.amountStr;
		this.currency = a.currency;
		this.presentingBankSortCode = a.presentingBankSortCode;
		this.collectionType = a.collectionType;
		this.instrumentType = a.instrumentType;
		this.settlementTime = a.settlementTime;
		this.settlementTimeDisplay = a.settlementTimeDisplay;
		this.paymentStatus = a.paymentStatus;
	}
	
	@Column(name="item_sequence_no")
	private String itemSequenceNo;

	@Column(name="serial_no")
	private String serialNo;

	@Column(name="sort_code")
	private String sortCode;

	@JsonIgnore
	//@Transient
	private String bankName;


	@Column(name="account_no")
	private String accountNo;

	@Column(name="tran_code")
	private String tranCode;

	private BigDecimal amount;

	@JsonIgnore
	@Transient
	private String amountStr;

	private String currency;

	@Column(name="presenting_bank_sort_code")
	private String presentingBankSortCode;

	@Column(name="collection_type")
	private String collectionType;

	@Column(name="instrument_type")
	private String instrumentType;

	@JsonIgnore
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name="settement_time")
	private LocalDateTime settlementTime;

	@Transient
	private String settlementTimeDisplay;
	
	@JsonIgnore
	@Column(name="payment_status")
	private String paymentStatus;
	
	@Transient
	private String presentmentDateDisplay;
	
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
	 * @return the settlementTime
	 */
	public LocalDateTime getSettlementTime() {
		return settlementTime;
	}

	/**
	 * @param settlementTime the settlementTime to set
	 */
	public void setSettlementTime(LocalDateTime settlementTime) {
		this.settlementTime = settlementTime;
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
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	/**
	 * @return the presentmentDate
	 */
	@JsonProperty(value="PresentmentDate")
	public String getPresentmentDateDisplay() {
		return presentmentDateDisplay;
	}
	/**
	 * @param presentmentDate the presentmentDate to set
	 */
	public void setPresentmentDateDisplay(String presentmentDateDisplay) {
		this.presentmentDateDisplay = presentmentDateDisplay;
	}
	
}
