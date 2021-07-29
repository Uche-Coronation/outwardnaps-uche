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
public class FITransferResponse {
	
	private String responseCode;
	
	private String responseDescription;

	private String tranId;
	
	private String tranDateTime;
}
