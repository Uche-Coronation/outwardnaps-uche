/**
 * 
 */
package com.cmb.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.TransactionStatus;

/**
 * @author waliu.faleye
 *
 */
public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, String> {

}
