/**
 * 
 */
package com.cmb.config;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.cmb.interfaces.BatchDetailRepository;
import com.cmb.interfaces.TransactionStatusRepository;
import com.cmb.interfaces.UploadRepository;
import com.cmb.model.BatchDetail;
import com.cmb.model.NapsResponseData;
import com.cmb.model.UploadRequestData;
import com.cmb.model.neft.BatchStatusCheck;
import com.cmb.model.neft.NeftBatchDetail;
import com.cmb.model.neft.NeftOutwardStatusCheckResponse;
import com.cmb.model.neft.OutwardNeftStatusCheckItem;
import com.cmb.model.neft.PendingNeftData;
import com.cmb.model.neft.PendingNeftTransaction;
import com.cmb.neft.interfaces.NeftBatchDetailRepository;
import com.cmb.neft.interfaces.NeftProcessServiceInterface;
import com.cmb.services.BaseInterface;
import com.cmb.services.ProcessServiceInterface;

/**
 * @author waliu.faleye
 *
 */
@Configuration
public class WebConfig {// extends WebMvcConfigurerAdapter {

	@Value("${batch.paid.status}")
	String paidStatus;

	@Value("${outwardnaps.url}")
	String outwardNapsUrl;

	@Autowired
	ProcessServiceInterface psi;

	@Autowired
	BaseInterface baseInterface;

	@Autowired
	NeftProcessServiceInterface neftProcessServiceInterface;

	@Autowired
	NeftBatchDetailRepository neftBatchRepo;

	BatchDetailRepository batchRepo;
	UploadRepository uploadRepository;
	TransactionStatusRepository transactionStatusRepo;

	public WebConfig(BatchDetailRepository batchRepo, UploadRepository uploadRepository,
			TransactionStatusRepository transactionStatusRepo) {
		this.batchRepo = batchRepo;
		this.uploadRepository = uploadRepository;
		this.transactionStatusRepo = transactionStatusRepo;
	}

	@Scheduled(cron = "0 0/2 * 1/1 * ?")
	public void reportJob() {
		try {
			System.out.println("the cron job NAPS");
			List<BatchDetail> pendingBatch = batchRepo.findByBatchStatusNot(paidStatus);
			System.out.println("pendingBatch.size()====" + pendingBatch.size());

			for (BatchDetail batchDetail : pendingBatch) {
				System.out.println("the cron job NAPS 2");
				String pendingTrans = "P";
				List<NapsResponseData> responseList = psi.napsuploadStatusCheck(
						batchDetail.getUploadSessionId().concat(String.valueOf(batchDetail.getCounter() - 1l)));
				System.out.println("responseList.size()====" + responseList.size());
				for (NapsResponseData response : responseList) {
//					UploadRequestData uploadData = uploadRepository.findByUploadSessionIdAndAccountNumberAndItemAmount(
//							response.getBatchId(), response.getBeneficiaryAccountNumber(),
//							new BigDecimal(response.getAmount()));
					String uploadSessionId = "";
					//if(response.getBatchId().length() >=22) {
					//	uploadSessionId = response.getBatchId().substring(0, 22);
					//}else {
					Long counter = batchDetail.getCounter()-1; 
					int len = counter.toString().length();
						uploadSessionId = response.getBatchId().substring(0,response.getBatchId().length() - len);
					//}
					String paymentRefrence = response.getPaymentReference().substring(0, 8);
					System.out.println("the cron job NAPS 2 uploadSessionId==" + uploadSessionId);
					System.out.println("the cron job NAPS 2 paymentRefrence==" + paymentRefrence);
					UploadRequestData uploadData = uploadRepository
							.findByUploadSessionIdAndReferenceNumber(uploadSessionId, paymentRefrence);
					if(uploadData != null){
					System.out.println("the cron job NAPS response===="+response);
					uploadData.setPaymentStatus(response.getTranxStatus());
					if (!paidStatus.equals(response.getTranxStatus())) {
						pendingTrans = response.getTranxStatus();
					}
					uploadRepository.save(uploadData);}
				}

				if (responseList != null && !responseList.isEmpty()) {
					if (!"P".equals(pendingTrans)) {
						if (!Strings.isNullOrEmpty(pendingTrans)) {
							batchDetail.setBatchStatus(pendingTrans);
						}
					} else {
						batchDetail.setBatchStatus(paidStatus);
					}

					batchRepo.save(batchDetail);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	//@Scheduled(cron = "0 0/2 * 1/1 * ?")
	public void reportNeftJobProcessing() {
		try {
			//System.out.println("the cron job NEFT");
			List<NeftBatchDetail> pendingBatch = neftBatchRepo.findByBatchStatusNot(paidStatus);
			//System.out.println("NEFT pendingBatch.size()===" + pendingBatch.size());
			for (NeftBatchDetail batchDetail : pendingBatch) {
				//System.out.println("the cron job NEFT 2");
				String pendingTrans = "P";
				NeftOutwardStatusCheckResponse response = baseInterface.neftOutwardCheckStatus(batchDetail);
				//System.out.println("the cron job NEFT 2 response ===" + response);

				if (response != null) {
					BatchStatusCheck statusCheck = response.getBatchStatusCheck();
					if (statusCheck != null) {
						for (OutwardNeftStatusCheckItem item : statusCheck.getItems()) {
							String status = item.getStatus();
							if (!"P".equals(pendingTrans)) {
								if (!Strings.isNullOrEmpty(pendingTrans)) {
									batchDetail.setBatchStatus(pendingTrans);
								}
							} else {
								// batchDetail.setBatchStatus(paidStatus);
							}
						}
						neftProcessServiceInterface.saveNeftBatchDetail(batchDetail);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Scheduled(cron = "0 0/2 * 1/1 * ?")
	public void saveUnprocessedNeftJobProcessing() throws SQLIntegrityConstraintViolationException {
		//try {
			PendingNeftTransaction pendingTrans = baseInterface.getUnprocessedNeftDebit();
			if (pendingTrans != null && "00".equals(pendingTrans.getResponseCode())) {
				List<PendingNeftData> pendingList = pendingTrans.getTransactionList();
				//System.out.println("pendingList==="+pendingList);				
				for(PendingNeftData data : pendingList) {
					PendingNeftData existingData = neftProcessServiceInterface.findPendingNeftDataByMsgIdAndItemSequenceNo(data.getMsgId(), data.getItemSequenceNo());
					//System.out.println("existingData==="+existingData);
					if(existingData == null) {
						//System.out.println("insertion of non existing data===");
						data.setTransactionTime(LocalDateTime.now());
						neftProcessServiceInterface.savePendingNeftData(data);
					}
				}
			}
		/*} catch (Exception ex) {
			ex.printStackTrace();
		}*/
	}

	@Scheduled(cron = "0 0/2 * 1/1 * ?")
	public void saveCreditFailedNeftJobProcessing() throws SQLIntegrityConstraintViolationException {
		//try {
			
			PendingNeftTransaction crFailedTrans = baseInterface.getFailedInwardNeftCredit();
			//System.out.println("crFailedTrans==="+crFailedTrans);
			if (crFailedTrans != null && "00".equals(crFailedTrans.getResponseCode())) {
				List<PendingNeftData> crFailedList = crFailedTrans.getTransactionList();
				//System.out.println("crFailedList.size()==="+crFailedList.size());
				for(PendingNeftData data : crFailedList) {
					PendingNeftData existingData = neftProcessServiceInterface.findPendingNeftDataByMsgIdAndItemSequenceNo(data.getMsgId(), data.getItemSequenceNo());
					//System.out.println("existingData==="+existingData);
					if(existingData == null) {
						//System.out.println("insertion of non existing data===");
						data.setTransactionTime(LocalDateTime.now());
						neftProcessServiceInterface.savePendingNeftData(data);
					}
				}
			}
		/*} catch (Exception ex) {
			ex.printStackTrace();
		}*/
	}
}
