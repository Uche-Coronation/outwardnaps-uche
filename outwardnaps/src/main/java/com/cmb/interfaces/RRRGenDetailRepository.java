package com.cmb.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.remita.RRRGenDetail;
import com.cmb.model.remita.RRRTranDetail;

public interface RRRGenDetailRepository extends JpaRepository<RRRGenDetail, Long> {
	//RRRGenDetail
	//public RRRGenDetail findById(Long id);
	public RRRGenDetail findByRrr(String rrr);

}
