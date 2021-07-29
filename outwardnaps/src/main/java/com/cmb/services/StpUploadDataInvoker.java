/**
 * 
 */
package com.cmb.services;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmb.interfaces.StpRequestDetailsUploadRepository;
import com.cmb.model.StpRequestDetailsUpload;

/**
 * @author waliu.faleye
 *
 */
public class StpUploadDataInvoker implements ItemWriter<StpRequestDetailsUpload>{

	@Autowired	
	StpRequestDetailsUploadRepository stpUploadRepo;
	
	@Override
	public void write(List<? extends StpRequestDetailsUpload> arg0) throws Exception {
		// TODO Auto-generated method stub
		stpUploadRepo.saveAll(arg0);
	}

}
