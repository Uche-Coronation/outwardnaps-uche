/**
 * 
 */
package com.cmb.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import com.cmb.model.StpRequestDetailsUpload;
import com.cmb.model.UploadRequestData;


/**
 * @author waliu.faleye
 *
 */
public class StpDetailsItemProcessor implements ItemProcessor<UploadRequestData, StpRequestDetailsUpload> {

	@Override
	public StpRequestDetailsUpload process(UploadRequestData arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("arg0.getRecieverName() >> "+arg0.getRecieverName());
		final StpRequestDetailsUpload stpRequestDetailsUpload = new StpRequestDetailsUpload();
		stpRequestDetailsUpload.setAccountName(arg0.getRecieverName());
		stpRequestDetailsUpload.setAccountNumber(arg0.getAccountNumber());
		stpRequestDetailsUpload.setAddress("");
		stpRequestDetailsUpload.setAmount(arg0.getItemAmount().abs().multiply(new BigDecimal("100")).toString());
		stpRequestDetailsUpload.setBankCode(arg0.getAccountSortCode());
		stpRequestDetailsUpload.setBatchId(arg0.getUploadSessionId());
		stpRequestDetailsUpload.setBeneficiaryAccountNumber(arg0.getAccountNumber());
		stpRequestDetailsUpload.setBeneficiaryBankCode(arg0.getAccountSortCode());
		stpRequestDetailsUpload.setBeneficiaryName(arg0.getRecieverName());
		stpRequestDetailsUpload.setDescription(arg0.getTransactionParticulars());
		stpRequestDetailsUpload.setEmail("");
		stpRequestDetailsUpload.setMobileNumber("");
		stpRequestDetailsUpload.setNapsPaymentReference(arg0.getTransactionParticulars());
		stpRequestDetailsUpload.setPayerAccountNumber(arg0.getOrderingPartyAccountNumber());
		stpRequestDetailsUpload.setPayerBvn("");
		stpRequestDetailsUpload.setPayerName(arg0.getSenderName());
		stpRequestDetailsUpload.setPaymentReference("");
		stpRequestDetailsUpload.setRequestId("");
		stpRequestDetailsUpload.setSn(Long.valueOf(0));
		stpRequestDetailsUpload.setStatusDetails("");
		stpRequestDetailsUpload.setTransactionId("");
		stpRequestDetailsUpload.setTransDate(new Date());
		stpRequestDetailsUpload.setTranxStatus("");
		
		return stpRequestDetailsUpload;
	}

}
