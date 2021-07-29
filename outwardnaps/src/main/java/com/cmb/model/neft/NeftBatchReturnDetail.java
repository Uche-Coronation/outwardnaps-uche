/**
 * 
 */
package com.cmb.model.neft;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author waliu.faleye
 *
 */
public class NeftBatchReturnDetail extends NeftBaseBatchDetail {


	public NeftBatchReturnDetail() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	/**
	 * 
	 */
	public NeftBatchReturnDetail(NeftBaseBatchDetail base) {
		super(base);
		// TODO Auto-generated constructor stub
	}

	private Set<OutwardNeftReturn> outwardNeftReturns;

	/**
	 * @return the outwardNeftReturns
	 */
	@JsonProperty(value = "RFItemDataStores")
	public Set<OutwardNeftReturn> getOutwardNeftReturns() {
		return outwardNeftReturns;
	}

	/**
	 * @param outwardNeftReturns the outwardNeftReturns to set
	 */
	public void setOutwardNeftReturns(Set<OutwardNeftReturn> outwardNeftReturns) {
		this.outwardNeftReturns = outwardNeftReturns;
	}
}
