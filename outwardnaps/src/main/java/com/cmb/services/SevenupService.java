package com.cmb.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.cmb.model.AcctId;
import com.cmb.model.Notification;
import com.cmb.model.NotificationResponse;
import com.cmb.model.NotificationStatusResponse;
import com.cmb.model.PaymentInfo;
import com.cmb.model.Rec;
import com.cmb.model.Recs;
import com.cmb.model.RecsResponse;
import com.cmb.model.SevenupConfig;
import com.cmb.model.SevenupPayment;
import com.cmb.model.TrnAmt;
import com.cmb.model.User;
import com.cmb.interfaces.SevenupRepository;

@Service
public class SevenupService {

	Logger logger = LoggerFactory.getLogger(SevenupService.class);
	
	@Autowired
	SevenupRepository sevenupRepository;
	
	@Autowired
	SevenupConfig sevenupConfig;
	
	public List<SevenupPayment> fetchAllInitiatedPayments(){
		return sevenupRepository.findByStatus("initiated");
	}
	
	public SevenupPayment savePayment(SevenupPayment sevenupPayment) {
		return sevenupRepository.save(sevenupPayment);
	}
	
	public SevenupPayment findPayment(long paymentID) {
		return sevenupRepository.findById(paymentID).get();
	}

	public RecsResponse performPayment(Recs rec) {
		logger.info(rec.toString());
		RestTemplate restTemplate = new RestTemplate();
		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(sevenupConfig.getPerformPayment()) ;
		return restTemplate.postForObject(builder.buildAndExpand().toUri().toString(), rec, RecsResponse.class);
	}

	public NotificationResponse notify(Notification notification) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(sevenupConfig.getNotify()) ;
		return restTemplate.postForObject(builder.buildAndExpand().toUri().toString(), notification, NotificationResponse.class);
	}
	
	public String savePaymentInfo(PaymentInfo paymentInfo) {
		RestTemplate restTemplate = new RestTemplate();
		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(sevenupConfig.getSaveInfo()) ;
		ResponseEntity<String> resp = restTemplate.postForEntity(builder.buildAndExpand().toUri().toString(), paymentInfo, String.class);
		return resp.getBody();
	}
	
	public NotificationStatusResponse getNotificationStatus(String executionID) {
		
		RestTemplate restTemplate = new RestTemplate();
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(sevenupConfig.getNotStatus()) ;
	    builder.queryParam("executionID", executionID);
	    HttpHeaders requestHeaders = new HttpHeaders();
	    HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<NotificationStatusResponse> responseEntity = restTemplate.exchange(builder.buildAndExpand().toUri().toString(), HttpMethod.GET, requestEntity,NotificationStatusResponse.class);

	   // Map<String, String> params = new HashMap<String, String>();
	   // params.put("executionID",executionID);
	     
	    //NotificationStatusResponse result = restTemplate.getForObject(notStatus, NotificationStatusResponse.class, params);
	    return responseEntity.getBody();
	}
	
	public SevenupPayment getInvoiceByInvoiceNumber(String invoiceNumber) {
		return sevenupRepository.findByInvoiceNumber(invoiceNumber);
	}
	
	public Page<SevenupPayment> fetchAllPayments(int page, int size){
		return sevenupRepository.findAll(PageRequest.of(page, size));
	}

	public void authPayment(String id, User user) {
		SevenupPayment sevenup = findPayment(Long.parseLong(id));
		Recs rec = new Recs();
		Rec rc = new Rec();
		AcctId acctId = new AcctId();
		acctId.setAcctId(sevenupConfig.getCollectionBankAccountCode());
		rc.setAcctId(acctId);
		rc.setCreditDebitFlg("C");
		rc.setSerialNum(1);
		rc.setTrnParticulars(sevenup.getTransactionParticulars());
		TrnAmt trnAmt = new TrnAmt();
		trnAmt.setAmountValue(sevenup.getAmount());
		trnAmt.setCurrencyCode("NGN");
		rc.setTrnAmt(trnAmt);
		rec.getRecs().add(rc);
		Rec rc2 = new Rec();
		AcctId acctId2 = new AcctId();
		acctId2.setAcctId(sevenup.getAccountNumber());
		rc2.setAcctId(acctId2);
		rc2.setCreditDebitFlg("D");
		rc2.setSerialNum(2);
		rc2.setTrnParticulars(sevenup.getTransactionParticulars());
		TrnAmt trnAmt2 = new TrnAmt();
		trnAmt2.setAmountValue(sevenup.getAmount());
		trnAmt2.setCurrencyCode("NGN");
		rc2.setTrnAmt(trnAmt2);
		rec.getRecs().add(rc2);
		String dt = getDateTime();
		String prf = createPaymentreference(dt);
		rec.setReqUuid("SV" + prf);
		RecsResponse resp = performPayment(rec);
		Notification notification = new Notification();
		notification.setCustomerCode(sevenup.getCustomerCode());
		notification.setCustomerName(sevenup.getCustomerName());
		notification.setInvoiceNumber(sevenup.getInvoiceNumber());
		notification.setSecurityCode(sevenup.getSecurityCode());
		notification.setSourceBankCode(sevenupConfig.getSourceBankCode());
		notification.setExecutionId(sevenup.getExecutionID());
		notification.setSBCFee(Double.parseDouble(sevenupConfig.getSBCFee()));
		notification.setCustomerFee(Double.parseDouble(sevenupConfig.getCustomerFee()));
		notification.setAmount(Double.parseDouble(sevenup.getAmount()));
		Double tAmt = notification.getAmount() + notification.getSBCFee() + notification.getCustomerFee();
		notification.setTotalAmount(tAmt);
		notification.setNarration("For Services rendered");
		notification.setChannelName(sevenupConfig.getChannelName());
		notification.setCustomerAccountNumber(sevenup.getAccountNumber());
		notification.setPaymentReference(prf);
		notification.setTransactionApprovalDate(dt);
		notification.setProviderCode("CR");
		notification.setCollectionBankCode(sevenupConfig.getCollectionBankCode());
		notification.setCollectionBankAccountCode(sevenupConfig.getCollectionBankAccountCode());
		notification.setPaymentStatus("Successful");
		notification.setStatus("pending");
		notification.setUpdatedDate(dt);
		notification.setUpdatedBy("Not yet Updated");
		NotificationResponse notrsp = notify(notification);
		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setDealerCode(sevenup.getCustomerCode());
		paymentInfo.setDealerName(sevenup.getCustomerName());
		paymentInfo.setAmount(Double.parseDouble(sevenup.getAmount()));
		paymentInfo.setCommission(Double.parseDouble(sevenupConfig.getSBCFee()));
		paymentInfo.setBalance(paymentInfo.getAmount() + paymentInfo.getCommission());
		paymentInfo.setTellerNumber(rec.getReqUuid());
		if(notrsp.getStatus().equals("200")) {
			paymentInfo.setStatus("confirmed");
		}else {
			paymentInfo.setStatus("pending");
		}
		paymentInfo.setPaymentMode("transfer");
		paymentInfo.setTransDate(notification.getTransactionApprovalDate());
		paymentInfo.setValueDate(notification.getTransactionApprovalDate());
		paymentInfo.setBranch("Victoria Island");
		paymentInfo.setPaymentReference(notification.getPaymentReference());
		paymentInfo.setCustomerRegionCode(sevenup.getCustomerRegionCode());
		paymentInfo.setCustomerRegionName(sevenup.getCustomerRegionName());
		logger.info(paymentInfo.toString());
		savePaymentInfo(paymentInfo);
		sevenup.setStatus("authorized");
		sevenup.setAuthorizer(user);
		sevenup.setAuthorizationDate(java.sql.Date.valueOf(LocalDate.now()));
		savePayment(sevenup);
	}
	
	private String getDateTime() {
		Date date = Calendar.getInstance().getTime() ;	    
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");  
	    return format.format(date);
	}
	
	private String createPaymentreference(String date) {
		String paymentReference = "";
		for(int i = 0; i <= 21; i++) {
			if(Character.isDigit(date.charAt(i))) {
				paymentReference += String.valueOf(date.charAt(i));
			}
		}
		String pref = paymentReference.substring(0,11);
		pref += "0101";
		return pref;
	}
	
	
}
