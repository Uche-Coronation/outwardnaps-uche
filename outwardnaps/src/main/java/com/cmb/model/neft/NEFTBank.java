/**
 * 
 */
package com.cmb.model.neft;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Data
@Table(name="neftbank")
public class NEFTBank {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String bankName;
	
	private String sortCode;
	
	private String branchName;
	
	private String branchAddress;
	
	private String cityName;
	
	private String state;
	
	private String nipBankCode;

}
