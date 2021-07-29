/**
 * 
 */
package com.cmb.neft.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.TransactionStatus;
import com.cmb.model.neft.PendingNeftData;

/**
 * @author waliu.faleye
 *
 */
public interface PendingNeftDataRepository extends JpaRepository<PendingNeftData, Long> {
	
	public List<PendingNeftData> findByTransactionStatusNot(TransactionStatus transactionStatus);
	
	public PendingNeftData findByMsgIdAndItemSequenceNo(Long msgId,String itemSequenceNo);

}
