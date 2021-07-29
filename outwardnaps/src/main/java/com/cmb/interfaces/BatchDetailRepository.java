/**
 * 
 */
package com.cmb.interfaces;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.BatchDetail;
import com.cmb.model.TransactionStatus;

/**
 * @author waliu.faleye
 *
 */
public interface BatchDetailRepository extends JpaRepository<BatchDetail, Long> {
	
	public BatchDetail findByUploadSessionId(String sessionId);
	
	public List<BatchDetail> findByTransactionStatus(TransactionStatus transactionStatus);
	
	public List<BatchDetail> findByTransactionStatusNot(TransactionStatus transactionStatus);
	
	public List<BatchDetail> findByBatchStatusNot(String batchStatus);

}
