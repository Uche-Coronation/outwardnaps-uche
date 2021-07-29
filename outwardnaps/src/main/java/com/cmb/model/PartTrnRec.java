/**
 * 
 */
package com.cmb.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author waliu.faleye
 *
 */
public class PartTrnRec {
	
	@JsonIgnore
	private Long id;
	
	@Valid
	private AcctId acctId;
	
	@JsonIgnore
	private String accountId;	
	
	@NotEmpty @NotNull @Size(max=1) @Pattern(regexp="C|D")
	private String creditDebitFlg;
	
	@Valid
	private TrnAmt trnAmt;
	
	@JsonIgnore
	private String amountValue;	
	
	@JsonIgnore
	private String currencyCode;	
	
	@NotNull
	private Long serialNum;
	
	@NotEmpty @NotNull @NotBlank
	private String trnParticulars;
	
	@JsonIgnore
	private String valueDt;
	
	private String rateCode;
	
	private String misCode;	
	
	@JsonIgnore
	private Long fiTransferRequestId;

	/**
	 * @return the acctId
	 */
	public AcctId getAcctId() {
		return acctId;
	}

	/**
	 * @param acctId the acctId to set
	 */
	public void setAcctId(AcctId acctId) {
		this.acctId = acctId;
	}

	/**
	 * @return the creditDebitFlg
	 */
	public String getCreditDebitFlg() {
		return creditDebitFlg;
	}

	/**
	 * @param creditDebitFlg the creditDebitFlg to set
	 */
	public void setCreditDebitFlg(String creditDebitFlg) {
		this.creditDebitFlg = creditDebitFlg;
	}

	/**
	 * @return the trnAmt
	 */
	public TrnAmt getTrnAmt() {
		return trnAmt;
	}

	/**
	 * @param trnAmt the trnAmt to set
	 */
	public void setTrnAmt(TrnAmt trnAmt) {
		this.trnAmt = trnAmt;
	}

	/**
	 * @return the serialNum
	 */
	public Long getSerialNum() {
		return serialNum;
	}

	/**
	 * @param serialNum the serialNum to set
	 */
	public void setSerialNum(Long serialNum) {
		this.serialNum = serialNum;
	}

	/**
	 * @return the trnParticulars
	 */
	public String getTrnParticulars() {
		return trnParticulars;
	}

	/**
	 * @param trnParticulars the trnParticulars to set
	 */
	public void setTrnParticulars(String trnParticulars) {
		this.trnParticulars = trnParticulars;
	}

	/**
	 * @return the valueDt
	 */
	public String getValueDt() {
		return valueDt;
	}

	/**
	 * @param valueDt the valueDt to set
	 */
	public void setValueDt(String valueDt) {
		this.valueDt = valueDt;
	}

	/**
	 * @return the rateCode
	 */
	public String getRateCode() {
		return rateCode;
	}

	/**
	 * @param rateCode the rateCode to set
	 */
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	/**
	 * @return the misCode
	 */
	public String getMisCode() {
		return misCode;
	}

	/**
	 * @param misCode the misCode to set
	 */
	public void setMisCode(String misCode) {
		this.misCode = misCode;
	}

	/**
	 * @return the fiTransferRequest
	 */
	public Long getFiTransferRequestId() {
		return fiTransferRequestId;
	}

	/**
	 * @param fiTransferRequest the fiTransferRequest to set
	 */
	public void setFiTransferRequestId(Long fiTransferRequestId) {
		this.fiTransferRequestId = fiTransferRequestId;
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
	 * @return the amountValue
	 */
	public String getAmountValue() {
		return amountValue;
	}

	/**
	 * @param amountValue the amountValue to set
	 */
	public void setAmountValue(String amountValue) {
		this.amountValue = amountValue;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() { 
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
