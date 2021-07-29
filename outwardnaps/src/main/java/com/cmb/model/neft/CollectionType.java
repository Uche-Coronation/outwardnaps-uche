/**
 * 
 */
package com.cmb.model.neft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Data
public class CollectionType {

	@Id
	private Long id;
	
	@Column(name="collection_type")
	private String collectionType;
	
	@Column(name="return_type")
	private String returnType;
	
	private String description;
	
	@Column(name="instrument_type")
	private String instrumentType;
	
	private String type;
	
	@Column(name="currency_code")
	private String currencyCode;
}
