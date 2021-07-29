/**
 * 
 */
package com.cmb.neft.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.neft.ReturnReason;

/**
 * @author waliu.faleye
 *
 */
public interface ReturnReasonRepository extends JpaRepository<ReturnReason, Long> {

}
