/**
 * 
 */
package com.cmb.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.FinancialInstitution;

/**
 * @author waliu.faleye
 *
 */
public interface FinancialInstitutionRepository extends JpaRepository<FinancialInstitution, String> {

}
