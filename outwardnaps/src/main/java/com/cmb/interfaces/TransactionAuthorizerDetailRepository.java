/**
 * 
 */
package com.cmb.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.TransactionAuthorizerDetail;
import com.cmb.model.TransactionStatus;
import com.cmb.model.User;

/**
 * @author waliu.faleye
 *
 */
public interface TransactionAuthorizerDetailRepository extends JpaRepository<TransactionAuthorizerDetail, Long> {

	public List<TransactionAuthorizerDetail> findByUploadSessionId(String sessionId);
	public TransactionAuthorizerDetail findByUploadSessionIdAndLevel(String sessionId, Long level);
	public List<TransactionAuthorizerDetail> findByUploadSessionIdAndLevelNotAndTransactionStatus(String sessionId, Long level, TransactionStatus transactionStatus);
	public List<TransactionAuthorizerDetail> findByUploadSessionIdAndTransactionStatus(String sessionId, TransactionStatus transactionStatus);
	public TransactionAuthorizerDetail findByUploadSessionIdAndUser(String sessionId, User user);
	public List<TransactionAuthorizerDetail> findByUploadSessionIdAndLevelNot(String sessionId, Long level);
	public Optional<TransactionAuthorizerDetail> findByUploadSessionIdAndLevelAndTransactionStatus(String sessionId, Long level, TransactionStatus transactionStatus);


}
