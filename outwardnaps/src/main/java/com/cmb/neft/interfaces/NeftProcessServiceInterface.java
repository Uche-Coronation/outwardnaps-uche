/**
 * 
 */
package com.cmb.neft.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.cmb.model.neft.CollectionType;
import com.cmb.model.neft.NEFTBank;
import com.cmb.model.neft.NeftBatchDetail;
import com.cmb.model.neft.NeftOutwardResponse;
import com.cmb.model.neft.OutwardNeftItem;
import com.cmb.model.neft.PendingNeftData;
import com.cmb.model.neft.ReturnReason;

/**
 * @author waliu.faleye
 *
 */
public interface NeftProcessServiceInterface {
	
	public List<NeftBatchDetail> findNeftBatchDetails();
	
	public NeftBatchDetail saveNeftBatchDetail(NeftBatchDetail neftBatchDetail);
	
	public Optional<NeftBatchDetail> findNeftBatchDetailById(Long neftBatchId);
	
	public String formatAmount(BigDecimal value);
	
	public NeftOutwardResponse processNeftBatchTransactions(NeftBatchDetail neftBatchDetail);
	
	public NeftOutwardResponse processNeftBatchReturn(NeftBatchDetail neftBatchDetail); 
	
	public List<NEFTBank> findNeftBanks(); 
	
	public List<NEFTBank> findNeftBankBySortCode(String sortCode);
	
	public Optional<OutwardNeftItem> findOutwardNeftItemById(Long itemId);
	
	public OutwardNeftItem findOutwardNeftItemByIdAndPaymentStatus(Long itemId,String paymentStatus);
	
	public NeftOutwardResponse processNeftDebitBatchTransactions(NeftBatchDetail neftBatchDetail);
	
	public PendingNeftData savePendingNeftData(PendingNeftData pendingNeftData);
	
	public List<PendingNeftData> saveAllPendingNeftData(List<PendingNeftData> pendingNeftData);
	
	public List<PendingNeftData> findAllPendingNeftData();
	
	public Optional<PendingNeftData> findPendingNeftDataById(Long id);
	
	public List<CollectionType> findAllCollectionTypes();
	
	public List<ReturnReason> findAllReturnReasons();
	
	public PendingNeftData findPendingNeftDataByMsgIdAndItemSequenceNo(Long msgId,String itemSequenceNo);

}
