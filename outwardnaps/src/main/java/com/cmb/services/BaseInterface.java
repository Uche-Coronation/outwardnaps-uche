/**
 * 
 */
package com.cmb.services;

import java.io.IOException;
import java.util.List;

import com.cmb.model.FITransactionStatus;
import com.cmb.model.FITransferRequest;
import com.cmb.model.FITransferResponse;
import com.cmb.model.FITransferReversal;
import com.cmb.model.FeeRequest;
import com.cmb.model.FeeResponse;
import com.cmb.model.InitiatorDetail;
import com.cmb.model.PaymentDetail;
import com.cmb.model.TransactionStatus;
import com.cmb.model.neft.InflowApprovalData;
import com.cmb.model.neft.NeftBatchDetail;
import com.cmb.model.neft.NeftBatchReturnDetail;
import com.cmb.model.neft.NeftOutwardResponse;
import com.cmb.model.neft.NeftOutwardStatusCheckResponse;
import com.cmb.model.neft.PendingNeftData;
import com.cmb.model.neft.PendingNeftTransaction;
import com.expertedge.entrustplugin.ws.AuthResponseDTO;
import com.expertedge.entrustplugin.ws.TokenAuthDTO;

/**
 * @author waliu.faleye
 *
 */
public interface BaseInterface {
	public InitiatorDetail getInitiatorDetails();
	
	public InitiatorDetail getNeftInitiatorDetails(String instrumentType);
	
	public FeeResponse getChannelFee(FeeRequest feeRequest);
	
	public FITransferResponse fiFundTransfer(FITransferRequest fiRequest);
	
	public FITransferResponse fiTransferReversal(FITransferReversal fiRequest);
	
	public FITransactionStatus fiTransactionQueryStatus(String reqUuid);
	
	public NeftOutwardResponse neftOutwardSubmit(NeftBatchDetail neftBatchDetail);
	
	public NeftOutwardResponse neftOutwardSubmitReturn(NeftBatchReturnDetail returnDetail);
	
	public NeftOutwardStatusCheckResponse neftOutwardCheckStatus(NeftBatchDetail neftBatchDetail);
	
	public NeftOutwardStatusCheckResponse neftOutwardCheckReturnStatus(NeftBatchDetail neftBatchDetail);
	
	public TransactionStatus getTransactionStatus(String value);
	
	public AuthResponseDTO entrustValidation(TokenAuthDTO arg0);
	
	public String genericPaymentEvidence(PaymentDetail paymentDetail) throws IOException;
	
	public Boolean validateCredentials(String userName,String password);
	
	public PendingNeftTransaction getUnprocessedNeftDebit();
	
	public InitiatorDetail getNeftInflowInitiatorDetails();
	
	public NeftOutwardResponse submitNeftDebitApproval(InflowApprovalData transactions);
	
	public PendingNeftTransaction getFailedInwardNeftCredit();
}
