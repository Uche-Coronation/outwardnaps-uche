/**
 * 
 */
package com.cmb.neft.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.neft.NEFTBank;

/**
 * @author waliu.faleye
 *
 */
public interface NEFTBankRepository extends JpaRepository<NEFTBank, Long> {
	
	public List<NEFTBank> findBySortCode(String sortCode);

}
