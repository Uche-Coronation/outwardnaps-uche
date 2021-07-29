package com.cmb.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmb.model.SevenupPayment;

@Repository
public interface SevenupRepository extends JpaRepository<SevenupPayment, Long>{

	public List<SevenupPayment> findByStatus(String status);
	public SevenupPayment findByInvoiceNumber(String invoiceNumber);
	
}
