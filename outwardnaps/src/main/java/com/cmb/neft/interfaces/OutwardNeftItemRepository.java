/**
 * 
 */
package com.cmb.neft.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.neft.OutwardNeftItem;

/**
 * @author waliu.faleye
 *
 */
public interface OutwardNeftItemRepository extends JpaRepository<OutwardNeftItem, Long> {
	
	public OutwardNeftItem findByIdAndPaymentStatus(Long id, String paymentStatus);

}
