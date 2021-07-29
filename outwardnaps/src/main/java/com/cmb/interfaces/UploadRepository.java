/**
 * 
 */
package com.cmb.interfaces;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmb.model.BatchDetail;
import com.cmb.model.UploadRequestData;

/**
 * @author waliu.faleye
 *
 */
@Transactional
public interface UploadRepository extends JpaRepository<UploadRequestData, Long> {

	@Query("Select distinct uploadSessionId from UploadRequestData c ")
	public List<String> findDistinctBatchId();

	public List<UploadRequestData> findByUploadSessionId(String sessionId);

	public UploadRequestData findByUploadSessionIdAndAccountNumberAndItemAmount(String sessionId,String accountNumber, BigDecimal amount);

	public UploadRequestData findByUploadSessionIdAndReferenceNumber(String sessionId,String referenceNumber);
	
	public UploadRequestData findByIdAndPaymentStatus(Long id,String paymentStatus);

}
