/**
 * 
 */
package com.cmb.model.neft;

/**
 * @author waliu.faleye
 *
 */

public class OutwardNeftStatusCheckItem extends OutwardNeftBaseItem {

	private Long id;

	private String bankOfFirstDepositDateDisplay;

	private String bankOfFirstDepositSortCode;

	//private String presentmentDateDisplay;

	private String payerName;

	private String beneficiary;

	private String beneficiaryAccountNo;

	private String bvnBeneficiary;

	private String bvnPayer;

	private String narration;

	private boolean specialClearing;

	private String instrumentDateDisplay;

	private String micrRepairInd;

	private String cycleNo;

	private String status;

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
	 * @return the bankOfFirstDepositDateDisplay
	 */
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
	//public String getPresentmentDateDisplay() {
	//	return presentmentDateDisplay;
	//}

	/**
	 * @param presentmentDateDisplay the presentmentDateDisplay to set
	 */
	//public void setPresentmentDateDisplay(String presentmentDateDisplay) {
	//	this.presentmentDateDisplay = presentmentDateDisplay;
	//}

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
	 * @return the beneficiary
	 */
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
	 * @return the cycleNo
	 */
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

}
