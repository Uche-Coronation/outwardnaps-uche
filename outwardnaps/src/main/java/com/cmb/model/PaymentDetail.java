/**
 * 
 */
package com.cmb.model;

import lombok.Data;

/**
 * @author waliu.faleye
 *
 */
@Data
public class PaymentDetail {
	private String originatorAccountName;
	private String maskedOriginatorAccountNumber;
	private String beneficiaryAccountName;
	private String beneficiaryAccountNumber;
	private String requestTime;
	private String sessionID;
	private String beneficiaryBankName;
	private String amount;
	private String narration;	
	private String printingDate;

}
