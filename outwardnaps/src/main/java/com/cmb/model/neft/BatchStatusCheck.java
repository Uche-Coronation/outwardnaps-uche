/**
 * 
 */
package com.cmb.model.neft;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author waliu.faleye
 *
 */
//@Data
//@EqualsAndHashCode(callSuper=false)
public class BatchStatusCheck extends NeftBaseBatchDetail {
	private Long id;	

	private String settlementTimeF;
	
	List<OutwardNeftStatusCheckItem> items;

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
	 * @return the settlementTimeF
	 */
	public String getSettlementTimeF() {
		return settlementTimeF;
	}

	/**
	 * @param settlementTimeF the settlementTimeF to set
	 */
	public void setSettlementTimeF(String settlementTimeF) {
		this.settlementTimeF = settlementTimeF;
	}

	/**
	 * @return the items
	 */
	@JsonProperty(value="PFItemDataStores")
	public List<OutwardNeftStatusCheckItem> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<OutwardNeftStatusCheckItem> items) {
		this.items = items;
	}
}
