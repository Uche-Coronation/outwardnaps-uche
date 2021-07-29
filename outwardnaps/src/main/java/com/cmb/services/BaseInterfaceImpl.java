/**
 * 
 */
package com.cmb.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cmb.interfaces.BatchDetailRepository;
import com.cmb.interfaces.UploadRepository;
import com.cmb.model.BatchDetail;
import com.cmb.model.FITransactionStatus;
import com.cmb.model.FITransferRequest;
import com.cmb.model.FITransferResponse;
import com.cmb.model.FITransferReversal;
import com.cmb.model.FeeRequest;
import com.cmb.model.FeeResponse;
import com.cmb.model.InitiatorDetail;
import com.cmb.model.PaymentDetail;
import com.cmb.model.TransactionStatus;
import com.cmb.model.UploadRequestData;
import com.cmb.model.neft.InflowApprovalData;
import com.cmb.model.neft.NEFTBank;
import com.cmb.model.neft.NeftBatchDetail;
import com.cmb.model.neft.NeftBatchReturnDetail;
import com.cmb.model.neft.NeftOutwardResponse;
import com.cmb.model.neft.NeftOutwardStatusCheckResponse;
import com.cmb.model.neft.OutwardNeftItem;
import com.cmb.model.neft.PendingNeftData;
import com.cmb.model.neft.PendingNeftTransaction;
import com.cmb.neft.interfaces.NeftBatchDetailRepository;
import com.cmb.neft.interfaces.NeftProcessServiceInterface;
import com.cmb.neft.interfaces.OutwardNeftItemRepository;
import com.cmb.neft.interfaces.PendingNeftDataRepository;
import com.expertedge.entrustplugin.ws.AuthResponseDTO;
import com.expertedge.entrustplugin.ws.EntrustMultiFactorAuthImplService;
import com.expertedge.entrustplugin.ws.TokenAuthDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author waliu.faleye
 *
 */
@Service
public class BaseInterfaceImpl implements BaseInterface {

	@Autowired
	NeftBatchDetailRepository batchRepo;

	@Autowired
	OutwardNeftItemRepository itemRepo;

	@Autowired
	BatchDetailRepository batchDetailRepo;

	@Autowired
	UploadRepository uploadRepository;

	// @Autowired
	// TransactionStatusRepository transactionStatusRepo;

	@Autowired
	List<TransactionStatus> tranStatusList;

	@Value("${outwardnaps.url}")
	private String serviceUrl;

	@Value("${neft.base.url}")
	private String neftBaseUrl;

	@Value("${payment.evidence.url}")
	private String paymentEvidenceUrl;

	@Autowired
	NeftProcessServiceInterface neftInterface;

	@Value("${job.reportgenerated.path}")
	private String reportPath;

	@Value("${spring.ldap.ip}")
	String ldapIp;
	@Value("${spring.ldap.port}")
	String ldapPort;
	@Value("${spring.ldap.base}")
	String ldapBase;
	@Value("${spring.ldap.secauth}")
	String ldapSecAuth;

	@Autowired
	PendingNeftDataRepository pendingNeftRepo;

	@Override
	public InitiatorDetail getInitiatorDetails() {
		InitiatorDetail initiatorDet = new InitiatorDetail();
		//Pageable paging = PageRequest.of(0, 10, Sort.by("uploadedDate").descending());
		//initiatorDet.setTotalPendingTransactionsCount(new Long(this.getTotalPendingTransactions().size()));
		//initiatorDet.setTotalTransactionsCount(new Long(this.getTotalTransactions().size()));
		List<BatchDetail> myList = batchDetailRepo.findByTransactionStatusNot(getTransactionStatus("I"));
		SimpleDateFormat sdf = new SimpleDateFormat();
		myList.forEach(a -> a.setDisplayUploadedDate(sdf.format(a.getUploadedDate())));
		// myList.sort(Comparator.comparing(BatchDetail :: getUploadedDate).reversed());
		// myList.forEach(a -> System.out.println(a.getUploadedDate()));
		initiatorDet.setBatchDetails(myList);

		return initiatorDet;
	}

	public List<UploadRequestData> getTotalTransactions() {
		//Pageable paging = PageRequest.of(0, 10, Sort.by("uploadedDate").descending());
		List<BatchDetail> detailList = batchDetailRepo.findByTransactionStatusNot(getTransactionStatus("I"));
		List<UploadRequestData> reqDetails = new ArrayList<UploadRequestData>();

		for (BatchDetail batchDetail : detailList) {
			reqDetails.addAll(uploadRepository.findByUploadSessionId(batchDetail.getUploadSessionId()));
		}

		return reqDetails;
	}

	public List<UploadRequestData> getTotalPendingTransactions() {
		System.out.println("batchDetailRepo>>>" + batchDetailRepo);
		//Pageable paging = PageRequest.of(0, 10, Sort.by("uploadedDate").descending());
		List<BatchDetail> detailList = batchDetailRepo.findByTransactionStatusNot(getTransactionStatus("I"));
		List<BatchDetail> pendingList = detailList.stream()
				.filter(a -> !a.getTransactionStatus().getStatus().equals("P")).collect(Collectors.toList());
		List<UploadRequestData> reqDetails = new ArrayList<UploadRequestData>();
		for (BatchDetail batchDetail : pendingList) {
			reqDetails.addAll(uploadRepository.findByUploadSessionId(batchDetail.getUploadSessionId()));
		}

		return reqDetails;
	}

	@Override
	public InitiatorDetail getNeftInitiatorDetails(String instrumentType) {
		InitiatorDetail initiatorDet = new InitiatorDetail();

		//initiatorDet.setTotalPendingTransactionsCount(
		//		new Long(this.getTotalPendingNeftTransactions(instrumentType).size()));
		//initiatorDet.setTotalTransactionsCount(new Long(this.getTotalNeftTransactions(instrumentType).size()));
		// List<NeftBatchDetail> myCrList = new ArrayList<NeftBatchDetail>();
		// List<NeftBatchDetail> myDrList = new ArrayList<NeftBatchDetail>();
		List<NeftBatchDetail> myList = batchRepo.findByTransactionStatusNot(getTransactionStatus("I"));
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd-MM-uuuu");
		myList.forEach(a -> a.setDisplayDate(sdf.format(a.getDate())));
		myList = myList.parallelStream().filter(a -> instrumentType.equalsIgnoreCase(a.getInstrumentType()))
				.collect(Collectors.toList());
		initiatorDet.setNeftBatchDetails(myList);

		return initiatorDet;
	}

	public List<OutwardNeftItem> getTotalNeftTransactions(String instrumentType) {
		List<NeftBatchDetail> detailList = batchRepo.findByTransactionStatusNot(getTransactionStatus("I"));
		List<OutwardNeftItem> reqDetails = new ArrayList<OutwardNeftItem>();
		detailList = detailList.stream().filter(a -> instrumentType.equalsIgnoreCase(a.getInstrumentType()))
				.collect(Collectors.toList());

		for (NeftBatchDetail batchDetail : detailList) {
			reqDetails.addAll(batchDetail.getOutwardNeftItems());
		}

		return reqDetails;
	}

	public List<OutwardNeftItem> getTotalPendingNeftTransactions(String instrumentType) {
		System.out.println("batchRepo>>>" + batchRepo);
		List<NeftBatchDetail> detailList = batchRepo.findByTransactionStatusNot(getTransactionStatus("I"));
		List<NeftBatchDetail> pendingList = detailList.stream()
				.filter(a -> !a.getTransactionStatus().getStatus().equals("P")
						&& instrumentType.equalsIgnoreCase(a.getInstrumentType()))
				.collect(Collectors.toList());
		List<OutwardNeftItem> reqDetails = new ArrayList<OutwardNeftItem>();
		for (NeftBatchDetail batchDetail : pendingList) {
			reqDetails.addAll(batchDetail.getOutwardNeftItems());
		}

		return reqDetails;
	}

	@Override
	public FeeResponse getChannelFee(FeeRequest feeRequest) {
		FeeResponse feeResponse = null;
		try {
			Gson gson = new Gson();
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, gson.toJson(feeRequest));
			Request request = new Request.Builder().url(serviceUrl + "/channelTransactionFee").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				String resp = response.body().string();
				System.out.println("getChannelFee resp===" + resp);
				feeResponse = gson.fromJson(resp, FeeResponse.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return feeResponse;
	}

	@Override
	public FITransferResponse fiFundTransfer(FITransferRequest fiRequest) {
		FITransferResponse fiResp = null;
		try {
			Gson gson = new Gson();
			String req = gson.toJson(fiRequest);
			System.out.println("fiFundTransfer req===" + req);
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, req);
			Request request = new Request.Builder().url(serviceUrl + "/fiFundTransfer").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				String resp = response.body().string();
				System.out.println("fiFundTransfer resp===" + resp);
				fiResp = gson.fromJson(resp, FITransferResponse.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return fiResp;
	}

	@Override
	public FITransferResponse fiTransferReversal(FITransferReversal fiRequest) {
		FITransferResponse fiResp = null;
		try {
			Gson gson = new Gson();
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, gson.toJson(fiRequest));
			Request request = new Request.Builder().url(serviceUrl + "/fiFundTransferReversal").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				String resp = response.body().string();
				System.out.println("fiTransferReversal resp===" + resp);
				fiResp = gson.fromJson(resp, FITransferResponse.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return fiResp;
	}

	@Override
	public NeftOutwardResponse neftOutwardSubmit(NeftBatchDetail neftBatchDetail) {
		NeftOutwardResponse resp = null;
		try {
			Gson gson = new Gson();
			System.out.println("neftBatchDetail ===" + neftBatchDetail);
			ObjectMapper mapper = new ObjectMapper();
			String reqStr = mapper.writeValueAsString(neftBatchDetail);
			System.out.println("neftBatchDetail req===" + reqStr);
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, reqStr);
			Request request = new Request.Builder().url(neftBaseUrl.concat("/neftOutWard/Submit")).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				String value = response.body().string();
				System.out.println("response value neftOutwardSubmit ===" + value);
				resp = gson.fromJson(value, NeftOutwardResponse.class);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}

	@Override
	public NeftOutwardResponse neftOutwardSubmitReturn(NeftBatchReturnDetail returnDetail) {
		// TODO Auto-generated method stub
		NeftOutwardResponse resp = null;
		try {
			Gson gson = new Gson();
			System.out.println("returnDetail ===" + returnDetail);
			ObjectMapper mapper = new ObjectMapper();
			String reqStr = mapper.writeValueAsString(returnDetail);
			System.out.println("returnDetail req===" + reqStr);
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, reqStr);
			Request request = new Request.Builder().url(neftBaseUrl.concat("/neftOutWard/SubmitReturn"))
					.method("POST", body).addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				String value = response.body().string();
				System.out.println("response value neftOutwardSubmitReturn ===" + value);
				resp = gson.fromJson(value, NeftOutwardResponse.class);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}

	@Override
	public NeftOutwardStatusCheckResponse neftOutwardCheckStatus(NeftBatchDetail neftBatchDetail) {
		// TODO Auto-generated method stub
		NeftOutwardStatusCheckResponse resp = null;
		try {
			Gson gson = new Gson();
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			// MediaType mediaType = MediaType.parse("application/json");
			// RequestBody body = RequestBody.create(mediaType,
			// neftBatchDetail.getMsgIdStr());
			// http://132.10.200.171:8089/api/neftOutWard/CheckStatus?msgIDvalue=201116124345
			Request request = new Request.Builder()
					.url(neftBaseUrl.concat("/neftOutWard/CheckStatus?msgIDvalue=" + neftBatchDetail.getMsgIdStr()))
					.get().addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				String value = response.body().string();
				System.out.println("response value neftOutwardCheckStatus ===" + value);
				resp = gson.fromJson(value, NeftOutwardStatusCheckResponse.class);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}

	@Override
	public NeftOutwardStatusCheckResponse neftOutwardCheckReturnStatus(NeftBatchDetail neftBatchDetail) {
		// TODO Auto-generated method stub
		NeftOutwardStatusCheckResponse resp = null;
		try {
			Gson gson = new Gson();
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, neftBatchDetail.getMsgIdStr());
			Request request = new Request.Builder().url(neftBaseUrl.concat("/neftOutWard/CheckReturnStatus"))
					.method("POST", body).addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				String value = response.body().string();
				System.out.println("response value neftOutwardCheckReturnStatus ===" + value);
				resp = gson.fromJson(value, NeftOutwardStatusCheckResponse.class);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}

	public TransactionStatus getTransactionStatus(String value) {

		List<TransactionStatus> transOpt = tranStatusList.parallelStream().filter(a -> value.equals(a.getStatus()))
				.collect(Collectors.toList());
		return !transOpt.isEmpty() ? transOpt.get(0) : new TransactionStatus();
	}

	@Override
	public AuthResponseDTO entrustValidation(TokenAuthDTO arg0) {
		// TODO Auto-generated method stub
		EntrustMultiFactorAuthImplService srv = new EntrustMultiFactorAuthImplService();
		return srv.getEntrustMultiFactorAuthImplPort().performTokenAuth(arg0);
	}

	@Override
	public FITransactionStatus fiTransactionQueryStatus(String reqUuid) {
		// TODO Auto-generated method stub
		FITransactionStatus fiTransactionStatus = null;
		try {
			Gson gson = new Gson();
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			Request request = new Request.Builder().url(serviceUrl + "/fiTransactionQueryStatus?reqUuid=" + reqUuid)
					.method("GET", null).build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				String value = response.body().string();
				System.out.println("response value FITransactionStatus ===" + value);
				fiTransactionStatus = gson.fromJson(value, FITransactionStatus.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fiTransactionStatus;
	}

	@Override
	public String genericPaymentEvidence(PaymentDetail paymentDetail) throws IOException {
		Gson gson = new Gson();
		String returnValue = null;
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(300, TimeUnit.SECONDS);
		builder.readTimeout(300, TimeUnit.SECONDS);
		builder.writeTimeout(300, TimeUnit.SECONDS);
		OkHttpClient client = builder.build();
		String jsonStr = gson.toJson(paymentDetail);
		System.out.println("genericPaymentEvidence jsonStr ==== " + jsonStr);
		System.out.println("genericPaymentEvidence paymentEvidenceUrl ==== " + paymentEvidenceUrl);
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, jsonStr);
		Request request = new Request.Builder().url(paymentEvidenceUrl).method("POST", body)
				.addHeader("Content-Type", "application/json").build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new IOException("Failed to download file:" + response);
		}
		returnValue = reportPath.concat(paymentDetail.getSessionID()).concat(".pdf");
		FileOutputStream fos = new FileOutputStream(returnValue);
		fos.write(response.body().bytes());
		fos.close();
		/*
		 * if(response != null && response.body() != null) { InputStream is =
		 * response.body().byteStream(); BufferedInputStream input = new
		 * BufferedInputStream(is); OutputStream output = new FileOutputStream(new
		 * File(reportPath.concat(paymentDetail.getSessionID()).concat(".pdf")));
		 * 
		 * byte[] data = new byte[1024];
		 * 
		 * //long total = 0; int count=0;
		 * 
		 * while ((count = input.read(data)) != -1) { //System.out.println(data); count
		 * = count + 1; output.write(data, 0, count); }
		 * 
		 * output.flush(); output.close(); input.close(); }
		 */
		return returnValue;
	}

	@Override
	public Boolean validateCredentials(String userName, String password) {
		// TODO Auto-generated method stub
		LdapAuth ldap = new LdapAuth();
		ldap.setBaseDN(ldapBase);
		ldap.setIp(ldapIp);
		ldap.setPort(ldapPort);
		ldap.setSecurityAuthentication(ldapSecAuth);
		return ldap.validateCredentials(userName, password);
	}

	@Override
	public PendingNeftTransaction getUnprocessedNeftDebit() {
		// TODO Auto-generated method stub
		PendingNeftTransaction resp = null;
		try {
			//Gson gson = new Gson();
			///ObjectMapper mapper = new ObjectMapper();
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			//MediaType mediaType = MediaType.parse("application/json");
			Request request = new Request.Builder().url(neftBaseUrl.concat("/getUnProcessedNeftDebit")).get()
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				ObjectMapper mapper = new ObjectMapper();
				String value = response.body().string();
				System.out.println("response value getUnprocessedNeftDebit ===" + value);
				resp = mapper.readValue(value, PendingNeftTransaction.class);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}

	@Override
	public InitiatorDetail getNeftInflowInitiatorDetails() {
		// TODO Auto-generated method stub
		InitiatorDetail initiatorDet = new InitiatorDetail();

		//initiatorDet.setTotalPendingTransactionsCount(
		//		new Long(this.getTotalInflowPendingNeftTransactions().size()));
		//initiatorDet.setTotalTransactionsCount(new Long(this.getTotalInflowNeftTransactions().size()));
		// List<NeftBatchDetail> myCrList = new ArrayList<NeftBatchDetail>();
		// List<NeftBatchDetail> myDrList = new ArrayList<NeftBatchDetail>();
		List<PendingNeftData> myList = pendingNeftRepo.findAll();//ByTransactionStatusNot(getTransactionStatus("I"));
		//DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd-MM-uuuu");
		myList.forEach(a -> {
			List<NEFTBank> banks = neftInterface
					.findNeftBankBySortCode(a.getPresentingBankSortCode().trim());
			if(!banks.isEmpty()) {
				a.setBankName(banks.get(0).getBankName());
			}
			a.setAmountStr(neftInterface.formatAmount(a.getAmount()));
			a.setTransactionStatus(a.getTransactionStatus() == null ? new TransactionStatus():a.getTransactionStatus());
		
		});
		initiatorDet.setInflowDetails(myList);

		return initiatorDet;
	}

	public List<PendingNeftData> getTotalInflowPendingNeftTransactions() {
		System.out.println("pendingNeftRepo>>>" + pendingNeftRepo);
		List<PendingNeftData> detailList = pendingNeftRepo.findByTransactionStatusNot(getTransactionStatus("I"));
		List<PendingNeftData> pendingList = detailList.stream()
				.filter(a -> !a.getTransactionStatus().getStatus().equals("P"))
				.collect(Collectors.toList());

		return pendingList;
	}

	public List<PendingNeftData> getTotalInflowNeftTransactions() {
		List<PendingNeftData> detailList = pendingNeftRepo.findByTransactionStatusNot(getTransactionStatus("I"));

		return detailList;
	}

	@Override
	public NeftOutwardResponse submitNeftDebitApproval(InflowApprovalData transactions) {
		// TODO Auto-generated method stub
		NeftOutwardResponse resp = null;
		try {
			Gson gson = new Gson();
			System.out.println("transactions ===" + transactions);
			ObjectMapper mapper = new ObjectMapper();
			String reqStr = mapper.writeValueAsString(transactions);
			System.out.println("transactions req===" + reqStr);
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, reqStr);
			Request request = new Request.Builder().url(neftBaseUrl.concat("/submitNeftDebitApproval")).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				String value = response.body().string();
				System.out.println("response value neftOutwardSubmit ===" + value);
				resp = gson.fromJson(value, NeftOutwardResponse.class);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}

	@Override
	public PendingNeftTransaction getFailedInwardNeftCredit() {
		// TODO Auto-generated method stub
		PendingNeftTransaction resp = null;
		try {
			//Gson gson = new Gson();
			///ObjectMapper mapper = new ObjectMapper();
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			//MediaType mediaType = MediaType.parse("application/json");
			Request request = new Request.Builder().url(neftBaseUrl.concat("/getFailedInwardNeftCredit")).get()
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			if (response != null && response.body() != null) {
				ObjectMapper mapper = new ObjectMapper();
				String value = response.body().string();
				System.out.println("response value getFailedInwardNeftCredit ===" + value);
				resp = mapper.readValue(value, PendingNeftTransaction.class);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resp;
	}

}
