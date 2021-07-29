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
public class FITransferReversal {
	
	/**
	 * @param reqUuid
	 * @param tranDateTime
	 * @param tranId
	 */
	public FITransferReversal(String reqUuid, String tranDateTime, String tranId) {
		super();
		this.reqUuid = reqUuid;
		this.tranDateTime = tranDateTime;
		this.tranId = tranId;
	}

	private String reqUuid;
	
	private String tranDateTime;
	
	private String tranId;

}
