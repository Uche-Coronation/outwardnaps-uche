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
public class ReturnReason {

	@Id
	private Long id;
	
	private String code;
	
	private String description;
	
	@Column(name="collection_type")
	private String collectionType;

}
