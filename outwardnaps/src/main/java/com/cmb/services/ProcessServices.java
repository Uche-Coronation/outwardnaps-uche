/**
 * 
 */
package com.cmb.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cmb.interfaces.BatchDetailRepository;
import com.cmb.interfaces.TransactionStatusRepository;
import com.cmb.interfaces.UploadRepository;
import com.cmb.model.BatchDetail;
import com.cmb.model.NameEnquiryResponse;
import com.cmb.model.NapsResponseData;
import com.cmb.model.NipNameEnquiryRequest;
import com.cmb.model.NipNameEnquiryResponse;
import com.cmb.model.RequestData;
import com.cmb.model.ResponseData;
import com.cmb.model.StpRequestDetailsUpload;
import com.cmb.model.UploadRequestData;
import com.cmb.model.User;
import com.expertedge.entrustplugin.ws.AdminResponseDTO;
import com.expertedge.entrustplugin.ws.EntrustMultiFactorAuthImplService;
import com.expertedge.entrustplugin.ws.UserAdminDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
public class ProcessServices implements ProcessServiceInterface {
	@Value("${staff.entrust.group}")
	String internalUserEntrustGroup;

	@Value("${outwardnaps.url}")
	String outwardNapsUrl;

	@Autowired
	BatchDetailRepository batchDetailRepo;

	@Autowired
	UploadRepository uploadRepository;

	@Autowired
	TransactionStatusRepository transactionStatusRepo;

	public String nameEnquiryBySortCode(String sortCode, String accountNumber) {
		// TODO Auto-generated method stub
		try {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			// Gson gson = new Gson();
			ObjectMapper mapper = new ObjectMapper();
			MediaType mediaType = MediaType.parse("application/json");
			// System.out.println("sortCode >>> " + sortCode);

			RequestBody body = RequestBody.create(mediaType,
					"{\"accountNumber\": " + accountNumber + ",\"sortCode\":" + sortCode + "}");
			Request request = new Request.Builder().url(outwardNapsUrl.concat("/nameEnquiryBySortCode")).post(body)
					.addHeader("cache-control", "no-cache")
					.addHeader("postman-token", "3fb23054-ac16-4d06-a1a1-3e792a533c4f").build();
			Response response = client.newCall(request).execute();
			String responseBodyStr = response.body().string();
			// System.out.println("responseBodyStr >>> " + responseBodyStr);
			if (!Strings.isNullOrEmpty(sortCode)) {
				if (sortCode.equals("459")) {
					NameEnquiryResponse resp = mapper.readValue(responseBodyStr, NameEnquiryResponse.class);
					// System.out.println("resp.getResponseCode() >>> " + resp.getResponseCode());
					String accountName = Strings.isNullOrEmpty(resp.getAccountName()) ? " " : resp.getAccountName();
					return accountName;
				} else {
					ResponseData resp = mapper.readValue(responseBodyStr, ResponseData.class);
					// System.out.println("resp.getResponseCode() >>> " + resp.getResponseCode());
					String accountName = Strings.isNullOrEmpty(resp.getAccountName()) ? " " : resp.getAccountName();
					return accountName;

				}

			} else {

			}
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		return null;

	}

	public static void main(String args[]) {
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonStr = "{\"status\":\"A\",\"balance\":25571.53,\"restriction\":\"N\",\"cifId\":\"R001934\",\"accountSchmCode\":\"610\",\"misCode\":\"MDO11\",\"responseCode\":\"00\",\"accountCurrency\":\"NGN\",\"accountNumber\":\"1990006442\",\"accountName\":\"KESIENA O ESIEVO\",\"responseText\":\"SUCCESS\"}";
			NameEnquiryResponse enqResp = gson.fromJson(jsonStr, NameEnquiryResponse.class);
			System.out.println(enqResp.getAccountName());
			NameEnquiryResponse staff1 = mapper.readValue(jsonStr, NameEnquiryResponse.class);

			System.out.println(staff1.getAccountName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public AdminResponseDTO createEntrustUser(User updatingUser) {
		EntrustMultiFactorAuthImplService srv = new EntrustMultiFactorAuthImplService();
		String group = "";
		UserAdminDTO arg0 = new UserAdminDTO();
		arg0.setEnableOTP(true);
		arg0.setFullname(updatingUser.getUserName());
		group = internalUserEntrustGroup;

		arg0.setGroup(group);
		arg0.setUserName(updatingUser.getUserName());
		AdminResponseDTO res = srv.getEntrustMultiFactorAuthImplPort().performCreateEntrustUser(arg0);

		return res;

	}

	@Override
	public NameEnquiryResponse getLocalAccountDetail(String accountNumber) {
		NameEnquiryResponse resp = new NameEnquiryResponse();
		Gson gson = new Gson();
		try {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, "{\r\n  \"accountNumber\":" + accountNumber + "\r\n}");
			Request request = new Request.Builder().url(outwardNapsUrl.concat("/accountDetail")).post(body)
					.addHeader("Content-Type", "application/json").addHeader("Cache-Control", "no-cache")
					.addHeader("Postman-Token", "5c44c523-2ad4-4f2b-b6ac-5fed488781a6").build();

			Response response = client.newCall(request).execute();
			String rs = response.body().string();
			System.out.println("rs===" + rs);
			resp = gson.fromJson(rs, NameEnquiryResponse.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resp;
	}

	@Override
	public ResponseData outwardnapsTransfer(BatchDetail batchDetail, boolean ignoreLimitCheck) {
		ResponseData resp = new ResponseData();
		Gson gson = new Gson();
		try {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			String list = gson.toJson(convert(batchDetail, ignoreLimitCheck));
			System.out.println(
					"outwardnapsTransfer batchDetail.getUploadSessionId()>>>>" + batchDetail.getUploadSessionId());
			System.out.println("list>>>>" + list);
			MediaType mediaType = MediaType.parse("application/json");
			// RequestBody body = RequestBody.create(mediaType, "[ \r\n{
			// \"description\":\"Test NAPS\", \"beneficiaryName\":\"OKOLI CHUKWUMA PAUL\",
			// \"email\":\"test@test.com\", \"amount\":\"20.00\",
			// \"beneficiaryAccountNumber\":\"5050007512\",
			// \"paymentReference\":\"0123456789\", \"accountName\":\"Chude Onyeibo\",
			// \"accountNumber\":\"1990003472\", \"beneficiaryBankCode\":\"999070\",
			// \"batchId\":\"123456\", \"payerName\":\"Chude Onyeibo\",
			// \"payerAccountNumber\":\"1990003472\", \"creditAccount\":\"1990008116\"
			// },\r\n{ \"description\":\"Test NAPS\", \"beneficiaryName\":\"OKOLI CHUKWUMA
			// PAUL\", \"email\":\"test@test.com\", \"amount\":\"10.00\",
			// \"beneficiaryAccountNumber\":\"5050007512\",
			// \"paymentReference\":\"0123456789\", \"accountName\":\"Chude Onyeibo\",
			// \"accountNumber\":\"1990003472\", \"beneficiaryBankCode\":\"999070\",
			// \"batchId\":\"123456\", \"payerName\":\"Chude Onyeibo\",
			// \"payerAccountNumber\":\"1990003472\", \"creditAccount\":\"1990008116\"
			// }\r\n]\r\n");
			RequestBody body = RequestBody.create(mediaType, list);
			Request request = new Request.Builder().url(outwardNapsUrl.concat("/outwardnapsTransfer")).post(body)
					.addHeader("Content-Type", "application/json").addHeader("Cache-Control", "no-cache")
					.addHeader("Postman-Token", "e55a4648-ad1f-4fa7-89a9-1c19e4ba4e8b").build();

			Response response = client.newCall(request).execute();
			String rs = response.body().string();
			System.out.println("rs===" + rs);
			resp = gson.fromJson(rs, ResponseData.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resp;
	}

	private List<StpRequestDetailsUpload> convert(BatchDetail batchDetail, boolean ignoreLimitCheck) {
		List<StpRequestDetailsUpload> myList = new ArrayList<StpRequestDetailsUpload>();

		for (UploadRequestData uploadData : batchDetail.getUploadRequestData()) {
			StpRequestDetailsUpload srd = new StpRequestDetailsUpload();
			srd.setAccountName(uploadData.getSenderName());
			srd.setAccountNumber(uploadData.getOrderingPartyAccountNumber());
			// srd.setAddress(uploadData.get);
			srd.setAmount(uploadData.getItemAmount().toString());
			srd.setBankCode(uploadData.getAccountSortCode());
			srd.setBatchId(uploadData.getUploadSessionId().concat(String.valueOf(batchDetail.getCounter())));
			srd.setBeneficiaryAccountNumber(uploadData.getAccountNumber());
			srd.setBeneficiaryBankCode(uploadData.getAccountSortCode());
			srd.setBeneficiaryName(uploadData.getRecieverName());
			srd.setDescription(uploadData.getTransactionParticulars());
			srd.setEmail("");
			srd.setMobileNumber("");
			String paymentRef = uploadData.getReferenceNumber().concat(String.valueOf(batchDetail.getCounter()));
			if (paymentRef.length() > 15)
				paymentRef = paymentRef.substring(paymentRef.length() - 15, paymentRef.length());
			srd.setNapsPaymentReference(paymentRef);
			srd.setPayerAccountNumber(uploadData.getOrderingPartyAccountNumber());
			srd.setPayerBvn("");
			srd.setPayerName(uploadData.getSenderName());
			srd.setPaymentReference(paymentRef);
			// srd.setRequestId(requestId);
			// srd.set
			srd.setCustomerWaiveCharge(uploadData.isCustomerWaiveCharge());
			srd.setIgnoreLimitCheck(ignoreLimitCheck);

			myList.add(srd);
		}

		return myList;
	}

	@Override
	public List<NapsResponseData> napsuploadStatusCheck(String batchId) {
		List<NapsResponseData> resp = new ArrayList<NapsResponseData>();
		Gson gson = new Gson();
		try {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, "{\r\n  \"batchId\":" + batchId + "\r\n}");
			Request request = new Request.Builder().url(outwardNapsUrl.concat("/napsuploadStatusCheck")).post(body)
					.addHeader("Content-Type", "application/json").addHeader("Cache-Control", "no-cache")
					.addHeader("Postman-Token", "e55a4648-ad1f-4fa7-89a9-1c19e4ba4e8b").build();

			Response response = client.newCall(request).execute();
			String rs = response.body().string();
			System.out.println("rs===" + rs);
			Type listType = new TypeToken<List<NapsResponseData>>() {
			}.getType();

			resp = gson.fromJson(rs, listType);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resp;
	}

	@Override
	public NipNameEnquiryResponse nipNameEnquiry(NipNameEnquiryRequest reqData) {
		NipNameEnquiryResponse resp = new NipNameEnquiryResponse();
		Gson gson = new Gson();
		try {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();
			// client.setConnectTimeout(60000, TimeUnit.MILLISECONDS);
			// client.setReadTimeout(60000, TimeUnit.MILLISECONDS);
			reqData.setChannelCode("1");
			String jsonStr = gson.toJson(reqData, NipNameEnquiryRequest.class);

			System.out.println(jsonStr);

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, jsonStr);
			Request request = new Request.Builder().url(outwardNapsUrl.concat("/generalnipne")).post(body)
					.addHeader("Content-Type", "application/json").addHeader("Cache-Control", "no-cache")
					.addHeader("Postman-Token", "e3929ec3-fe48-49d2-9ea4-6191b1bb6ef2").build();

			Response response = client.newCall(request).execute();
			// System.out.println(response.body().string());
			resp = gson.fromJson(response.body().string(), NipNameEnquiryResponse.class);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resp;
	}

	@Override
	public ResponseData getAccountDailyBalance(RequestData reqData) {
		ResponseData resp = new ResponseData();
		Gson gson = new Gson();
		try {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, gson.toJson(reqData));
			Request request = new Request.Builder().url(outwardNapsUrl.concat("/accountDailyBalance")).post(body)
					.addHeader("Content-Type", "application/json").addHeader("cache-control", "no-cache")
					.addHeader("Postman-Token", "6de49b98-c1c3-4675-b7ab-2e23e009f7e4").build();

			Response response = client.newCall(request).execute();
			resp = gson.fromJson(response.body().string(), ResponseData.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resp;
	}

	@Override
	public ResponseData getAccountDailyTranLimit(RequestData reqData) {
		ResponseData resp = new ResponseData();
		Gson gson = new Gson();
		try {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(30, TimeUnit.SECONDS);
			builder.readTimeout(30, TimeUnit.SECONDS);
			builder.writeTimeout(30, TimeUnit.SECONDS);
			OkHttpClient client = builder.build();

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, gson.toJson(reqData));
			Request request = new Request.Builder().url(outwardNapsUrl.concat("/accountDailyTranLimit")).post(body)
					.addHeader("Content-Type", "application/json").addHeader("cache-control", "no-cache")
					.addHeader("Postman-Token", "2e9e3e43-7dee-4b2a-9050-57cf43a7cc16").build();

			Response response = client.newCall(request).execute();
			resp = gson.fromJson(response.body().string(), ResponseData.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resp;
	}
}
