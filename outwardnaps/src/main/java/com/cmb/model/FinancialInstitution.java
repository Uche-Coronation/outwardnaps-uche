/**
 * 
 */
package com.cmb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Table(name = "financial_institution")
public class FinancialInstitution {
	
	@Column(name = "institution_name")
	private String institutionName;

	// private String category;

	@Id
	@Column(name = "sort_code")
	private String sortCode;

	@Column(name = "nip_bank_code")
	private String nipBankCode;

	/**
	 * @return the institutionName
	 */
	public String getInstitutionName() {
		return institutionName;
	}

	/**
	 * @param institutionName the institutionName to set
	 */
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	/**
	 * @return the sortCode
	 */
	public String getSortCode() {
		return sortCode;
	}

	/**
	 * @param sortCode the sortCode to set
	 */
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getNipBankCode() {
		return nipBankCode;
	}

	public void setNipBankCode(String nipBankCode) {
		this.nipBankCode = nipBankCode;
	}
}
