/**
 * 
 */
package com.cmb.neft.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.TransactionStatus;
import com.cmb.model.neft.NeftBatchDetail;

/**
 * @author waliu.faleye
 *
 */
public interface NeftBatchDetailRepository extends JpaRepository<NeftBatchDetail, Long> {
	
	public List<NeftBatchDetail> findByTransactionStatusNot(TransactionStatus transactionStatus);
	
	public List<NeftBatchDetail> findByBatchStatusNot(String batchStatus);

}
