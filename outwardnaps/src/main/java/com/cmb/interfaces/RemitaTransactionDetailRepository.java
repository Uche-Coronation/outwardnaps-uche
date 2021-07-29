package com.cmb.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmb.model.remita.RemitaTransactionDetail;

@Repository
public interface RemitaTransactionDetailRepository extends JpaRepository<RemitaTransactionDetail, Long> {
	//public RemitaTransactionDetail findById(Long id);
	public RemitaTransactionDetail findByRrr(String rrr);
}
