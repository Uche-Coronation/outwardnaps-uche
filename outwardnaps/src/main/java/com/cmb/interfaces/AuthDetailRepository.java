package com.cmb.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmb.model.remita.AuthDetail;

public interface AuthDetailRepository extends JpaRepository<AuthDetail, Long> {
	//public AuthDetail findById(Long id);
	public AuthDetail findByRrr(String rrr);

}
