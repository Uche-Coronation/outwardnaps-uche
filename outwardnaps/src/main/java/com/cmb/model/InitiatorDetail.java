/**
 * 
 */
package com.cmb.model;

import java.util.ArrayList;
import java.util.List;
import com.cmb.model.BatchDetail;
import com.cmb.model.neft.NeftBatchDetail;
import com.cmb.model.neft.PendingNeftData;

/**
 * @author waliu.faleye
 *
 */
public class InitiatorDetail {
	
	private List<BatchDetail> batchDetails = new ArrayList<BatchDetail>();
	
	private Long totalTransactionsCount;
	
	private Long totalPendingTransactionsCount;
	
	private String responseMessage;
	
	private List<NeftBatchDetail> neftBatchDetails = new ArrayList<NeftBatchDetail>();
	
	private List<PendingNeftData> inflowDetails = new ArrayList<PendingNeftData>();

	public List<BatchDetail> getBatchDetails() {
		return batchDetails;
	}

	public void setBatchDetails(List<BatchDetail> batchDetails) {
		this.batchDetails = batchDetails;
	}

	public Long getTotalTransactionsCount() {
		return totalTransactionsCount;
	}

	public void setTotalTransactionsCount(Long totalTransactionsCount) {
		this.totalTransactionsCount = totalTransactionsCount;
	}

	public Long getTotalPendingTransactionsCount() {
		return totalPendingTransactionsCount;
	}

	public void setTotalPendingTransactionsCount(Long totalPendingTransactionsCount) {
		this.totalPendingTransactionsCount = totalPendingTransactionsCount;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	/**
	 * @return the neftBatchDetails
	 */
	public List<NeftBatchDetail> getNeftBatchDetails() {
		return neftBatchDetails;
	}

	/**
	 * @param neftBatchDetails the neftBatchDetails to set
	 */
	public void setNeftBatchDetails(List<NeftBatchDetail> neftBatchDetails) {
		this.neftBatchDetails = neftBatchDetails;
	}

	/**
	 * @return the inflowDetails
	 */
	public List<PendingNeftData> getInflowDetails() {
		return inflowDetails;
	}

	/**
	 * @param inflowDetails the inflowDetails to set
	 */
	public void setInflowDetails(List<PendingNeftData> inflowDetails) {
		this.inflowDetails = inflowDetails;
	}	

}
