/**
 * 
 */
package com.cmb.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author waliu.faleye
 *
 */
public class TrnAmt {
	
	@NotEmpty @NotNull @NotBlank
	private String amountValue;
	
	@NotEmpty @NotNull @NotBlank
	private String currencyCode;

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

}
