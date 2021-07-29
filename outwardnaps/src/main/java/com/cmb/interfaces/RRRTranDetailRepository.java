package com.cmb.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmb.model.remita.RRRTranDetail;
import com.cmb.model.remita.RemitaTransactionDetail;

@Repository
public interface RRRTranDetailRepository extends JpaRepository<RRRTranDetail, Long>{
	//public RRRTranDetail findById(Long id);
	public RRRTranDetail findByRrr(String rrr);

}
