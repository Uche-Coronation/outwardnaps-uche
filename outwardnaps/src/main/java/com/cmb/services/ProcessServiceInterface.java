/**
 * 
 */
package com.cmb.services;

import java.util.List;

import com.cmb.model.BatchDetail;
import com.cmb.model.NameEnquiryResponse;
import com.cmb.model.NapsResponseData;
import com.cmb.model.NipNameEnquiryRequest;
import com.cmb.model.NipNameEnquiryResponse;
import com.cmb.model.RequestData;
import com.cmb.model.ResponseData;
import com.cmb.model.User;
import com.expertedge.entrustplugin.ws.AdminResponseDTO;

/**
 * @author waliu.faleye
 *
 */
public interface ProcessServiceInterface {

	public NipNameEnquiryResponse nipNameEnquiry(NipNameEnquiryRequest reqData);
	public NameEnquiryResponse getLocalAccountDetail(String accountNumber);
	public ResponseData outwardnapsTransfer(BatchDetail batchDetail,boolean ignoreLimitCheck);
	public List<NapsResponseData> napsuploadStatusCheck(String batchId);
	public AdminResponseDTO createEntrustUser(User updatingUser);
	public ResponseData getAccountDailyBalance(RequestData reqData);
	public ResponseData getAccountDailyTranLimit(RequestData reqData);
}
