package com.cmb.services;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cmb.interfaces.AuthDetailRepository;
import com.cmb.interfaces.RRRGenDetailRepository;
import com.cmb.model.remita.AuthDetail;
import com.cmb.model.remita.BillerReq;
import com.cmb.model.remita.BillerRes;
import com.cmb.model.remita.RRRGenDetail;
import com.cmb.model.remita.RRRTranDetail;
import com.cmb.model.remita.billpaymentnotification.BillPaymtReq;
import com.cmb.model.remita.billpaymentnotification.BillPaymtRes;
import com.cmb.model.remita.customfield.CfValue;
import com.cmb.model.remita.customfield.CustomFieldReq;
import com.cmb.model.remita.customfield.Customfield;
import com.cmb.model.remita.genrrr.CustomField;
import com.cmb.model.remita.genrrr.GenrrrReq;
import com.cmb.model.remita.genrrr.GenrrrRes;
import com.cmb.model.remita.paymentstatus.PaymtstatusReq;
import com.cmb.model.remita.paymentstatus.PaymtstatusRes;
import com.cmb.model.remita.receipt.Biller;
import com.cmb.model.remita.receipt.RemitaReceiptReq;
import com.cmb.model.remita.rrrdetail.RRRdetRes;
import com.cmb.model.remita.rrrdetail.RrrdetReq;
import com.cmb.model.remita.servicetype.BillerServicetypeReq;
import com.cmb.model.remita.servicetype.BillerServicetypeRes;
import com.cmb.model.remita.validaterequest.VdReq;
import com.cmb.model.remita.validaterequest.res.VdRes;
import com.expertedge.entrustplugin.ws.AuthResponseDTO;
import com.expertedge.entrustplugin.ws.EntrustMultiFactorAuthImplService;
import com.expertedge.entrustplugin.ws.TokenAuthDTO;
import com.google.gson.Gson;

@Service
public class RemitaServiceImpl implements RemitaService {
	
	 Logger logger = LoggerFactory.getLogger(RemitaServiceImpl.class);
	 @Autowired
	 private Environment env;
	    @Value("${remita.billpayment.biller.url}")
	    private String billerUrl;
	   //generaterrr  paymentstatus
	    @Value("${remita.billpayment.generaterrr.baseUrl}")
	    private String genrrrUrl;
	    @Value("${remita.billpayment.paynotify.baseUrl}")
	    private String paynotifyUrl;
	    @Value("${remita.billpayment.paymentstatus.baseUrl}")
	    private String paymentstatusUrl;
	    @Value("${remita.billpayment.rrrdetail.baseUrl}")
	    private String rrrdetailUrl;
	    @Value("${remita.billpayment.billerservicetype.baseUrl}")
	    private String bilsvrbaseUrl;
	    @Value("${remita.billpayment.customfields.baseUrl}")
	    private String cusfieldUrl;
	    
	    @Value("${remita.billpayment.fundingSource}")
	    private String fdsource;
	    @Value("${remita.billpayment.branchCode}")
	    private String bcode;
	    @Value("${remita.billpayment.incomeAccount}")
	    private String inacct;
	    
	    @Value("${remita.billpayment.customfields.publickey}")
	    private String cpubkey;
	    
	    @Autowired
	    RRRGenDetailRepository rrrGenDetailRepository;
	    
	    @Autowired
	    AuthDetailRepository authDetailRepository;
	    
	    
	    @Value("${remita.billpayment.billpaymentnotification.secret}")
	    private String secret;
	    
		@Value("${remita.billpayment.remitareceipt}")
        private String remitareceipturl;
	    
	    @Value("${remita.billpayment.appid}")
	    private String appid;
	    
		@Value("${entrust.app.code}")
		String entrustAppCode;

		@Value("${entrust.app.desc}")
		String entrustAppDesc;

		@Value("${staff.entrust.group}")
		String staffEntrustGroup;
	    
	public RemitaServiceImpl() {
			super();
			// TODO Auto-generated constructor stub
		}

	@Override
	public BillerRes getBiller() {
		// TODO Auto-generated method stub
		logger.info("Inside get Biller service");
		BillerReq billerReq = new BillerReq();
		billerReq.setAppid("cmb01");
		billerReq.setHash("");
		// logger.info("Request received : "+billerServicetypeReq.toString());
		BillerRes bil = new BillerRes();
		long requestId = new Date().getTime();
		String rid = String.valueOf(requestId);
		HttpHeaders httpHeaders = new HttpHeaders();
		try {

			httpHeaders.set("Content-Type", "application/json");

			httpHeaders.set("cache-control", "no-cache");

			 String billerUrlx = billerUrl;
			//String billerUrlx = "http://localhost:8182/cmbRemitaBillPayment/getBiller";
			logger.info("Get Biller url : " + billerUrlx);

			Gson gsonReq = new Gson();
			String gsonReqx = gsonReq.toJson(billerReq);
			JSONObject data = null;
			try {
				data = new JSONObject(gsonReqx);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Biller Request Json message is : " + data.toString());
			HttpEntity<String> httpEntity = new HttpEntity<String>(data.toString(), httpHeaders);

			// HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
			RestTemplate restTemplate = new RestTemplate();
			// ResponseEntity<BillerRes> response = restTemplate.exchange(billerUrlx,
			// HttpMethod.GET, httpEntity, BillerRes.class);
			ResponseEntity<BillerRes> response = restTemplate.exchange(billerUrlx, HttpMethod.POST, httpEntity,
					BillerRes.class);

			bil = (BillerRes) (response.getBody());
			logger.info("get biller response code: message is : " + bil.getResponseCode() + "" + bil.getResponseMsg());
		} catch (Exception e) {
			logger.info("Exception occurred getting biller : " + e.getMessage());
		}

		return bil;
	}
	
	@Override
	public BillerServicetypeRes getBillerServicetype(String billerid ) {
		BillerServicetypeReq billerServicetypeReq = new BillerServicetypeReq();
		BillerServicetypeRes billerServicetypeRes = new BillerServicetypeRes();
		
		billerServicetypeReq.setAppid("cmb01");
		billerServicetypeReq.setBillerid(billerid);
		billerServicetypeReq.setHash("");
		//long requestId = new Date().getTime();
		//String rid = String.valueOf(requestId);
		HttpHeaders httpHeaders = new HttpHeaders();
		
		
		 httpHeaders.set("Content-Type", "application/json");
		
		httpHeaders.set("cache-control", "no-cache");

     String billerUrlx = bilsvrbaseUrl;
     //String billerUrlx = "http://localhost:8182/cmbRemitaBillPayment/getBillerServicetype";
     logger.info("Get ServiceType url : " + billerUrlx);
     
     Gson gsonReq = new Gson();
     String gsonReqx = gsonReq.toJson(billerServicetypeReq);
		JSONObject data=null;
		try {
			data = new JSONObject(gsonReqx);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
     logger.info("ServiceType Request Json message is : " + data.toString());
		HttpEntity<String> httpEntity = new HttpEntity<String>(data.toString(),httpHeaders);
    
     
     RestTemplate restTemplate = new RestTemplate();
    // ResponseEntity<BillerRes> response = restTemplate.exchange(billerUrlx, HttpMethod.GET, httpEntity, BillerRes.class);
	 ResponseEntity<BillerServicetypeRes> response =restTemplate.exchange(billerUrlx, HttpMethod.POST, httpEntity,BillerServicetypeRes.class);
		
	
		billerServicetypeRes = (BillerServicetypeRes) (response.getBody());

		return billerServicetypeRes;
	}

	@Override
	public Customfield customFields(CustomFieldReq customFieldReq) {
		//CustomFieldReq customFieldReq = new CustomFieldReq();
		Customfield customfield = new Customfield();
		
		long requestId = new Date().getTime();
		String rid = String.valueOf(requestId);
		
		HttpHeaders httpHeaders = new HttpHeaders();		
		
		 httpHeaders.set("Content-Type", "application/json");
		
		httpHeaders.set("cache-control", "no-cache");
		httpHeaders.set("transactionId", rid);
		httpHeaders.set("publicKey", cpubkey);
     String billerUrlx = cusfieldUrl;
     logger.info("Get Custom fields url : " + billerUrlx);
     
     Gson gsonReq = new Gson();
     String gsonReqx = gsonReq.toJson(customFieldReq);
		JSONObject data=null;
		try {
			data = new JSONObject(gsonReqx);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
     logger.info("Custom Fields Request Json message is : " + data.toString());
		HttpEntity<String> httpEntity = new HttpEntity<String>(data.toString(),httpHeaders);
    
     
     RestTemplate restTemplate = new RestTemplate();
    // ResponseEntity<BillerRes> response = restTemplate.exchange(billerUrlx, HttpMethod.GET, httpEntity, BillerRes.class);
	 ResponseEntity<Customfield> response =restTemplate.exchange(billerUrlx, HttpMethod.POST, httpEntity,Customfield.class);
	
		customfield = (Customfield) (response.getBody());

		
		// TODO Auto-generated method stub
		return customfield;
	}

	@Override
	public VdRes validateRequest(VdReq vdReq) {
		VdRes vdRes= new VdRes();
		// TODO Auto-generated method stub
		String VdUrl="http://localhost:8182/cmbRemitaBillPayment/ValidateRequest";
		logger.info("Inside Validate request service  " );
		
		// ----------------------------------------------------------------

		List<com.cmb.model.remita.validaterequest.CustomField> cfgCustomList = new ArrayList<com.cmb.model.remita.validaterequest.CustomField>();
		com.cmb.model.remita.validaterequest.CustomField cfgCustom = new com.cmb.model.remita.validaterequest.CustomField();
		List<com.cmb.model.remita.validaterequest.Value> vallist = new ArrayList<com.cmb.model.remita.validaterequest.Value>();
		com.cmb.model.remita.validaterequest.Value val = new com.cmb.model.remita.validaterequest.Value();
		if (vdReq.getCotyp() != null) {
			if (vdReq.getCotyp() == "DD") {
				val.setValue(vdReq.getCfcid());
				val.setQuntity(1);
				val.setAmount(0);

			} else if (vdReq.getCotyp() == "SL") {
				val.setValue(vdReq.getCfcid());
				val.setQuntity(1);
				val.setAmount(0);

			} else if (vdReq.getCotyp() == "SP") {
				val.setValue(vdReq.getCfcid());
				val.setQuntity(1);
				val.setAmount(Double.parseDouble(vdReq.getCfcamt()));

			} else if (vdReq.getCotyp() == "D") {
				val.setValue(vdReq.getCfcid());
				val.setQuntity(0);
				val.setAmount(0);

			} else {
				val.setValue(vdReq.getCfcid());
				val.setQuntity(0);
				val.setAmount(Double.parseDouble(vdReq.getCfcamt() !=""?vdReq.getCfcamt():"0"));

			}
		}
		
		vallist.add(val);
		cfgCustom.setValues(vallist);
		cfgCustom.setId(vdReq.getCfpid());
		cfgCustomList.add(cfgCustom);
		vdReq.setCustomFields(cfgCustomList);
		//vdReq.s
		// ----------------------------------------------------------------
		
		long requestId = new Date().getTime();
		String rid = String.valueOf(requestId);
		HttpHeaders httpHeaders = new HttpHeaders();
	//	httpHeaders.set("publicKey", pkeybilsvr);
		httpHeaders.set("Content-Type", "application/json");
		//httpHeaders.set("transactionId", rid);
		httpHeaders.set("cache-control", "no-cache");
		
		String billerUrlx = VdUrl;
		logger.info("Validate request url : " + billerUrlx);
		logger.info("VDRQ request payload to Remita : " + vdReq.toString());
     Gson gsonReq = new Gson();
     String gsonReqx = gsonReq.toJson(vdReq);
		JSONObject data=null;
		try {
			data = new JSONObject(gsonReqx);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     logger.info("VDrq Request Json message is : " + data.toString());
		HttpEntity<String> httpEntity = new HttpEntity<String>(data.toString(),httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<VdRes> response = restTemplate.exchange(billerUrlx, HttpMethod.POST, httpEntity,
				VdRes.class);

		vdRes = (VdRes) (response.getBody());

		return vdRes;
	}

	@Override
	public GenrrrRes generaterrr(GenrrrReq genrrrReq,String acct,String user) {
		// TODO Auto-generated method stub ,RRRGenDetailRepository rrrGenDetailReposit
		GenrrrRes genrrrRes = new GenrrrRes();
		try {
		long requestId = new Date().getTime();
		String rid = String.valueOf(requestId);
		RRRGenDetail rrrGenDetail = new RRRGenDetail();
		rrrGenDetail.setBillidName(genrrrReq.getBillidName());
		rrrGenDetail.setBillId(genrrrReq.getBillId());
		rrrGenDetail.setServicetypeName(genrrrReq.getServicetypeName());
		rrrGenDetail.setMerchantId(genrrrReq.getBillId());
		rrrGenDetail.setServiceType(genrrrReq.getServiceType());
		rrrGenDetail.setAmount(genrrrReq.getAmount());
		rrrGenDetail.setCurrency(genrrrReq.getCurrency());
		rrrGenDetail.setPayerEmail(genrrrReq.getPayerEmail());
		rrrGenDetail.setPayerName(genrrrReq.getPayerName());
		rrrGenDetail.setPayerPhone(genrrrReq.getPayerPhone());
		rrrGenDetail.setDebittedAccount(acct);    
		List<CustomField> ee = genrrrReq.getCustomFields();
		ArrayList<String> aa = new ArrayList<String>();
		if(ee != null) {
			for (CustomField e : ee) {
				aa.add(e.toString());
			}			
			//rrrGenDetail.setCustomFields(aa);
		}else {
		
		}	
		 DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 Date date = new Date();
		 Date dt= new Date();
		 try {
			dt= sdf.parse(sdf.format(date));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			logger.info("Error parsing request date : "+e1.getMessage());
			//e1.printStackTrace();
		}
		
		 rrrGenDetail.setRequestTime(dt);		
		rrrGenDetail.setInitiatedUser(genrrrReq.getInitiatedUser() != null?genrrrReq.getInitiatedUser():"");		
		RRRGenDetail rrrGenDetailresp=rrrGenDetailRepository.save(rrrGenDetail);
		rrrGenDetail.setBillId(rrrGenDetail.getBillidName());
		HttpHeaders httpHeaders = new HttpHeaders();
		//httpHeaders.set("publicKey", pkeybilsvr);
		httpHeaders.set("Content-Type", "application/json");
	//	httpHeaders.set("transactionId", rid);
		httpHeaders.set("cache-control", "no-cache");
		String billerUrlx = genrrrUrl;   // "http://localhost:8182/cmbRemitaBillPayment/performGenerateRRR";
		logger.info("Generaterrr url : " + billerUrlx);
		//genrrrReq.setBillId(genrrrReq.getServiceType());
     Gson gsonReq = new Gson();
     String gsonReqx = gsonReq.toJson(genrrrReq);
		JSONObject data=null;
		try {
			data = new JSONObject(gsonReqx);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			logger.info("Error parsing generate RRR JSON request : "+e.getMessage());
			//e.printStackTrace();
		}
     logger.info("genrrrUrl Request Json message is : " + data.toString());
		HttpEntity<String> httpEntity = new HttpEntity<String>(data.toString(),httpHeaders);
		
		//HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<GenrrrRes> response = restTemplate.exchange(billerUrlx, HttpMethod.POST, httpEntity,
				GenrrrRes.class);

		genrrrRes = (GenrrrRes) (response.getBody());
		
		 DateFormat sdfa = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 Date datea = new Date();
		 Date dta= new Date();
		 try {
			dta= sdfa.parse(sdfa.format(datea));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			logger.info("Error parsing response date : "+e1.getMessage());
			//e1.printStackTrace();
		}
		 rrrGenDetailresp.setResponseCode(genrrrRes.getResponseCode());
		 rrrGenDetailresp.setResponseMsg(genrrrRes.getResponseMsg());
		 rrrGenDetailresp.setResponseTime(dta);
		 if (genrrrRes.getResponseCode().equalsIgnoreCase("00") ) {
			 if( genrrrRes.getResponseData().get(0).getRrr().equalsIgnoreCase("null")) {
				 
			 }else {
				 rrrGenDetailresp.setRrr(genrrrRes.getResponseData().get(0).getRrr());
			 }
		 }
		 
		 RRRGenDetail rrrGenDetailresp1=rrrGenDetailRepository.save(rrrGenDetailresp);
		}catch(Exception s) {
			logger.info("Error While generating RRR : "+s.getMessage());
		}
		return genrrrRes;
	}

	@Override
	public RRRdetRes rrrdetails(String rrr,String user) {
		// TODO Auto-generated method stub
		RrrdetReq rrrdetReq = new RrrdetReq();
		RRRdetRes rrrdetRes = new RRRdetRes();
		rrrdetReq.setAppid(appid);
		rrrdetReq.setHash("");
		rrrdetReq.setRrr(rrr);
		
		long requestId = new Date().getTime();
		String rid = String.valueOf(requestId);
		HttpHeaders httpHeaders = new HttpHeaders();
		//httpHeaders.set("publicKey", pkeybilsvr);
		httpHeaders.set("Content-Type", "application/json");
		httpHeaders.set("transactionId", rid);
		httpHeaders.set("cache-control", "no-cache");
		String billerUrlx = rrrdetailUrl;
		logger.info("Get custom fields url : " + billerUrlx);
		//HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
		
	     Gson gsonReq = new Gson();
	     String gsonReqx = gsonReq.toJson(rrrdetReq);
			JSONObject data=null;
			try {
				data = new JSONObject(gsonReqx);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     logger.info("genrrrUrl Request Json message is : " + data.toString());
			HttpEntity<String> httpEntity = new HttpEntity<String>(data.toString(),httpHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		 DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 Date date = new Date();
		 Date dt= new Date();
		ResponseEntity<RRRdetRes> response = restTemplate.exchange(billerUrlx, HttpMethod.POST, httpEntity,
				RRRdetRes.class);

		rrrdetRes = (RRRdetRes) (response.getBody());

		//storing RRR details in db
		if (rrrdetRes.getResponseCode()!=null &&  rrrdetRes.getResponseCode().equalsIgnoreCase("00")) {
			RRRGenDetail rrrGenDetail = new RRRGenDetail();
			String split=rrrdetRes.getResponseData().get(0).getDescription();
			String[] sp =split.split("-");
			
			rrrGenDetail.setBillId(sp[0]);
			rrrGenDetail.setServiceType(sp[1]);
			rrrGenDetail.setAmount(BigDecimal.valueOf(rrrdetRes.getResponseData().get(0).getAmountDue()));
			rrrGenDetail.setAmountDue(String.valueOf(rrrdetRes.getResponseData().get(0).getAmountDue()));
			rrrGenDetail.setCurrency(rrrdetRes.getResponseData().get(0).getCurrency());
			rrrGenDetail.setPayerEmail(rrrdetRes.getResponseData().get(0).getPayerEmail());
			rrrGenDetail.setPayerName(rrrdetRes.getResponseData().get(0).getPayerName());
			rrrGenDetail.setPayerPhone(rrrdetRes.getResponseData().get(0).getPayerPhone());
			rrrGenDetail.setRrr(rrrdetRes.getResponseData().get(0).getRrr());

			try {
				dt = sdf.parse(sdf.format(date));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				logger.info("Error parsing request date : " + e1.getMessage());
				// e1.printStackTrace();
			}

			rrrGenDetail.setRequestTime(dt);
			rrrGenDetail.setInitiatedUser(user);
			DateFormat sdfa = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date datea = new Date();
			Date dta = new Date();
			try {
				dta = sdfa.parse(sdfa.format(datea));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				logger.info("Error parsing response date : " + e1.getMessage());
				// e1.printStackTrace();
			}
			rrrGenDetail.setResponseCode(rrrdetRes.getResponseCode());
			rrrGenDetail.setResponseMsg(rrrdetRes.getResponseMsg());
			rrrGenDetail.setResponseTime(dta);

			if (rrrdetRes.getResponseData().get(0).getRrr().equalsIgnoreCase("null")) {

			} else {
				rrrGenDetail.setRrr(rrrdetRes.getResponseData().get(0).getRrr());
			}
			RRRGenDetail rrrGenDetailresp=rrrGenDetailRepository.save(rrrGenDetail);
		}
		
		
		
		return rrrdetRes;
	}

	@Override
	public BillPaymtRes billpaymentnotification(BillPaymtReq billPaymtReq) {
		// TODO Auto-generated method stub
		BillPaymtRes billPaymtRes=new BillPaymtRes();
		logger.info("inside billpaymentnotification  " );
		billPaymtReq.setFundingSource(fdsource);
		billPaymtReq.setBranchCode(bcode);
		billPaymtReq.setIncomeAccount(inacct);
		Random random = new Random();
		Math.round(random.nextFloat() * Math.pow(10,12));
		billPaymtReq.setPaymentAuthCode("546789096543545678990");
		
		
		long requestId = new Date().getTime();
		String rid = String.valueOf(requestId);
		HttpHeaders httpHeaders = new HttpHeaders();
		//httpHeaders.set("publicKey", pkeybilsvr);
		httpHeaders.set("Content-Type", "application/json");
		//httpHeaders.set("transactionId", rid);
		httpHeaders.set("cache-control", "no-cache");
		String pattern = String.format("%s%s%s%s%s%s", billPaymtReq.getRrr(), billPaymtReq.getAmountDebitted(),
				billPaymtReq.getFundingSource(), billPaymtReq.getDebittedAccount(), billPaymtReq.getPaymentAuthCode(),
				secret);
		String calculatedhash = generateHashedStringSHA512(pattern);
		httpHeaders.set("TXN_HASH", calculatedhash);
		String billerUrlx = paynotifyUrl;
		logger.info("payment notify url : " + billerUrlx);

		Gson gsonReq = new Gson();
		String gsonReqx = gsonReq.toJson(billPaymtReq);
		JSONObject data=null;
		try {
			data = new JSONObject(gsonReqx);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("payment notification Request Json message is : " + data.toString());
		HttpEntity<String> httpEntity = new HttpEntity<String>(data.toString(), httpHeaders);

		// HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<BillPaymtRes> response = restTemplate.exchange(billerUrlx, HttpMethod.POST, httpEntity,
				BillPaymtRes.class);

		billPaymtRes = (BillPaymtRes) (response.getBody());
		
		return billPaymtRes;
	}

	@Override
	public PaymtstatusRes paymentstatus(PaymtstatusReq paymtstatusReq) {
		// TODO Auto-generated method stub
		PaymtstatusRes paymtstatusRes= new PaymtstatusRes();
		
		HttpHeaders httpHeaders = new HttpHeaders();
		//httpHeaders.set("publicKey", pkeybilsvr);
		httpHeaders.set("Content-Type", "application/json");
		httpHeaders.set("cache-control", "no-cache");
		String billerUrlx = paymentstatusUrl + paymtstatusReq.getTransactionid() ;
		logger.info("Get payment status url : " + billerUrlx);
		//HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
		
		Gson gsonReq = new Gson();
		String gsonReqx = gsonReq.toJson(paymtstatusReq);
		JSONObject data=null;
		try {
			data = new JSONObject(gsonReqx);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("payment notification Request Json message is : " + data.toString());
		HttpEntity<String> httpEntity = new HttpEntity<String>(data.toString(), httpHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<PaymtstatusRes> response = restTemplate.exchange(billerUrlx, HttpMethod.POST, httpEntity,
				PaymtstatusRes.class);

		paymtstatusRes = (PaymtstatusRes) (response.getBody());
		
		return paymtstatusRes;
	}

	@Override
	public Biller printreceipt() {
		// TODO Auto-generated method stub
		return null;
	}
	
	   public String generateHashedStringSHA512(String hashString) {
	        String out="";
	        try {
	            logger.info("About to compute the message hash in SHA 512");
	            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
	            messageDigest.update(hashString.getBytes());
	            byte[] echoData = messageDigest.digest();

	            StringBuilder sb = new StringBuilder();
	            for (byte element : echoData) {
	                sb.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
	            }
	            out = sb.toString();
	            logger.info("Finished computing the message hash");

	        }
	        catch(Exception e){
	            logger.info("Error encountered during hashing process in SHA 512");
	        }
	        return out;
	    }

	

	@Override
	public List<RRRGenDetail> getAllInitiatedRRR() {
		// TODO Auto-generated method stub
		//return  rrrGenDetailRepository.findAll(new Sort(Sort.Direction.DESC, "requestTime"));
		return  rrrGenDetailRepository.findAll(Sort.by(Sort.Direction.DESC, "requestTime"));
	}

	@Override
	public RRRGenDetail getInitiatedRRRById(Long id) {
		// TODO Auto-generated method stub
		RRRGenDetail d = new RRRGenDetail();
		Optional<RRRGenDetail> dOpt = rrrGenDetailRepository.findById(id);
		if(dOpt.isPresent())
			d = dOpt.get();
		return  d;
	}

	@Override
	public BillPaymtRes billpaymentnotify(String rrr, String debitacct, String debitamt,String tellername,String token,String approval,String comment) {
		// TODO Auto-generated method stub
		BillPaymtReq billPaymtReq = new BillPaymtReq();
		BillPaymtRes billPaymtRes = new BillPaymtRes();
		logger.info("inside billpaymentnotify service implementation ");
		try {
		billPaymtReq.setRrr(rrr);
		billPaymtReq.setDebittedAccount(debitacct);
		billPaymtReq.setAmountDebitted(debitamt);
		billPaymtReq.setTellerName(tellername);
		billPaymtReq.setPaymentChannel("INTERNETBANKING");
		billPaymtReq.setApproval(approval);
		billPaymtReq.setComment(comment);
		//billPaymtReq.setBranchCode(branchCode);
		AuthDetail authDetail= authDetailRepository.findByRrr(rrr);
		RRRGenDetail rrrGenDetail=rrrGenDetailRepository.findByRrr(rrr);
		
		if(authDetail.getAuthlevelCount()>=1) {
			billPaymtRes.setResponseCode("55");
			billPaymtRes.setResponseMsg("Transaction Approval Already Completed");
			return billPaymtRes;
		}
		if(authDetail.getApprovalStatus()!=null &&  authDetail.getApprovalStatus().equalsIgnoreCase("REJECTED")) {
			billPaymtRes.setResponseCode("51");
			billPaymtRes.setResponseMsg("Transaction has been Rejected Already");
			return billPaymtRes;
		}
		if(authDetail.getResponseCode()!=null && authDetail.getResponseCode().equalsIgnoreCase("00")) {
			billPaymtRes.setResponseCode("52");
			billPaymtRes.setResponseMsg("Transaction already Processed Successfully");
			return billPaymtRes;
		}
		logger.info("about to check token");
		EntrustMultiFactorAuthImplService srv = new EntrustMultiFactorAuthImplService();
		TokenAuthDTO arg0 = new TokenAuthDTO();
		arg0.setAppCode(entrustAppCode);
		arg0.setAppDesc(entrustAppDesc);
		arg0.setGroup(staffEntrustGroup);
		arg0.setTokenPin(token);
		arg0.setUserName(tellername);
		AuthResponseDTO res = srv.getEntrustMultiFactorAuthImplPort().performTokenAuth(arg0);
		logger.info("token res.getRespMessage() ==" + res.getRespMessage());
		logger.info("token res.getRespCode() ==" + res.getRespCode());
		
			if (res != null) {
				if (res.getRespCode() == 1) {
					int cnt=authDetail.getAuthlevelCount();
					++cnt;
					if(cnt<1) {
						authDetail.setAuthlevelCount(cnt);
						authDetail.setAuthuser(tellername);
						if(approval.equalsIgnoreCase("APPROVE")) {
							authDetail.setApprovalStatus("PARTLY_APPROVED");
							billPaymtRes.setResponseCode("00");
							billPaymtRes.setResponseMsg("PARTLY_APPROVED");
							rrrGenDetail.setApprovalStatus("PARTLY_APPROVED");
						}else if(approval.equalsIgnoreCase("REJECT")) {
							authDetail.setApprovalStatus("REJECTED");							
							billPaymtRes.setResponseCode("09");
							billPaymtRes.setResponseMsg("REJECTED");
							rrrGenDetail.setApprovalStatus("REJECTED");
							
						}
						authDetail.setComment(comment);
						
					}else if(cnt==1) {
						authDetail.setAuthlevelCount(cnt);
						authDetail.setAuthuser(tellername);
						if(approval.equalsIgnoreCase("APPROVE")) {
							authDetail.setApprovalStatus("FULLY_APPROVED");
							rrrGenDetail.setApprovalStatus("FULLY_APPROVED");
							billPaymtRes = this.billpaymentnotification(billPaymtReq);
							authDetail.setResponseCode(billPaymtRes.getResponseCode());							
							authDetail.setPaymentStatus(billPaymtRes.getResponseCode().equalsIgnoreCase("00")?"SUCCESSFUL":"FAILED");
							rrrGenDetail.setPaymentStatus(billPaymtRes.getResponseCode().equalsIgnoreCase("00")?"SUCCESSFUL":"FAILED");
							
						}else if(approval.equalsIgnoreCase("REJECT")) {
							authDetail.setApprovalStatus("REJECTED");
							billPaymtRes.setResponseCode("09");
							billPaymtRes.setResponseMsg("REJECTED");
							rrrGenDetail.setApprovalStatus("REJECTED");
						}
						authDetail.setComment(comment);
						
					}
				} else {
					billPaymtRes.setIResponseCode("88");
					billPaymtRes.setIResponseMessage("Token Authentication failed");
					billPaymtRes.setResponseCode("88");
				}
				authDetailRepository.save(authDetail);
			}
			
		}catch(Exception e) {
			logger.info("Remita Payment Notify Exception :"+e.getMessage());
		}
		return billPaymtRes;
	}

	@Override
	public RRRGenDetail getInitiatedRRR(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BillPaymtRes billpaymentinitiate(String rrr, String debitacct, String debitamt, String tellername,
			String token) {
		// TODO Auto-generated method stub
		
		BillPaymtReq billPaymtReq = new BillPaymtReq();
		BillPaymtRes billPaymtRes = new BillPaymtRes();
		AuthDetail authDetail= new AuthDetail();
		try {
		billPaymtReq.setRrr(rrr);
		billPaymtReq.setDebittedAccount(debitacct);
		billPaymtReq.setAmountDebitted(debitamt);
		billPaymtReq.setTellerName(tellername);
		billPaymtReq.setPaymentChannel("INTERNETBANKING");
		//billPaymtReq.setBranchCode(branchCode);
		
		EntrustMultiFactorAuthImplService srv = new EntrustMultiFactorAuthImplService();
		TokenAuthDTO arg0 = new TokenAuthDTO();
		arg0.setAppCode(entrustAppCode);
		arg0.setAppDesc(entrustAppDesc);
		arg0.setGroup(staffEntrustGroup);
		arg0.setTokenPin(token);
		arg0.setUserName(tellername);
		AuthResponseDTO res = srv.getEntrustMultiFactorAuthImplPort().performTokenAuth(arg0);
		
		logger.info("token res.getRespMessage() ==" + res.getRespMessage());
		logger.info("token res.getRespCode() ==" + res.getRespCode());
		
			if (res != null) {
				if (res.getRespCode() == 1) {
					RRRGenDetail rrrGenDetailresp = rrrGenDetailRepository.findByRrr(rrr);
					rrrGenDetailresp.setInitiatedUser(tellername);
					rrrGenDetailresp.setDebittedAccount(debitacct);
					rrrGenDetailresp.setApprovalStatus("PENDING_AUTHORIZATION");
					RRRGenDetail newRRRGenDetail=rrrGenDetailRepository.save(rrrGenDetailresp);
					authDetail.setBillidName(newRRRGenDetail.getBillId());
					
					authDetail.setServicetypeName(newRRRGenDetail.getServiceType());
					authDetail.setServiceType(newRRRGenDetail.getServiceType());
					authDetail.setMerchantid(newRRRGenDetail.getBillId());
					authDetail.setRrr(newRRRGenDetail.getRrr());
					authDetail.setPayersName(newRRRGenDetail.getPayerName());
					authDetail.setPayersEmail(newRRRGenDetail.getPayerEmail());
					authDetail.setPayersPhone(newRRRGenDetail.getPayerPhone());
					authDetail.setAmountDue(debitamt);
					authDetail.setAccounttodebit(newRRRGenDetail.getDebittedAccount());
					authDetail.setAmount(newRRRGenDetail.getAmount().toString());
					authDetail.setTranDate(new Date());
					authDetail.setApprovalStatus("PENDING_AUTHORIZATION");
					
					authDetailRepository.save(authDetail);
					billPaymtRes.setIResponseCode("00");
					billPaymtRes.setIResponseMessage("Transaction saved successfully");
					billPaymtRes.setResponseCode("00");
				} else {
					billPaymtRes.setIResponseCode("88");
					billPaymtRes.setIResponseMessage("Token Authentication failed");
					billPaymtRes.setResponseCode("88");
				}
			}
		//billPaymtRes = this.billpaymentnotification(billPaymtReq);
		}catch(Exception e) {
			logger.info("Remita Payment Notify Exception :"+e.getMessage());
			billPaymtRes.setIResponseCode("99");
			billPaymtRes.setIResponseMessage("Transaction saving failed");
			billPaymtRes.setResponseCode("99");
		}
		return billPaymtRes;
		
	}

	@Override
	public List<AuthDetail> getAllAwaitAuth() {
		// TODO Auto-generated method stub
		return  authDetailRepository.findAll(Sort.by(Sort.Direction.DESC, "tranDate"));
		//return null;
	}

	@Override
	public AuthDetail getAuthRRRById(Long id) {
		// TODO Auto-generated method stub
		AuthDetail d = new AuthDetail();
		Optional<AuthDetail> dOpt = authDetailRepository.findById(id);
		if(dOpt.isPresent())
			d = dOpt.get();
		return d;
	}
    @Override
    public String printremitareceipt(String rrr) {
                // TODO Auto-generated method stub
                long requestId = new Date().getTime();
                String rid = String.valueOf(requestId);
                String filename= "c:\\download-files\\"+rrr +"_op"+rid +".pdf";
                String resp="download failed";
                try {
                RemitaReceiptReq receipt= new RemitaReceiptReq();
                receipt.setAppid("op");
                receipt.setHash("op");
                receipt.setRrr(rrr);                   
        Gson gsonReq = new Gson();
         String gsonReqx = gsonReq.toJson(receipt);
                            JSONObject data=null;
                            try {
                                        data = new JSONObject(gsonReqx);
                            } catch (JSONException e) {                          
                                        logger.info("Error parsing generate RRR JSON request : "+e.getMessage());                                    
                            }
         logger.info("generate remita receipt Request Json message is : " + data.toString());
                            HttpHeaders httpHeaders = new HttpHeaders();       
                            
                            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
                            httpHeaders.set("cache-control", "no-cache");
                            RestTemplate restTemplate = new RestTemplate();
                            HttpEntity<String> httpEntity = new HttpEntity<String>(data.toString(),httpHeaders);
                            ResponseEntity<byte[]> response = restTemplate.exchange(remitareceipturl, HttpMethod.POST, httpEntity,
                                                    byte[].class);
                            Files.write(Paths.get(filename), response.getBody());
                            resp="File download successful ;"+filename;
                }catch (Exception e){
                            logger.info("Error downloading remita receipt : "+e.getMessage());
   e.printStackTrace();
                   resp="File download failed : "+e.getMessage();
}
                
                
                return resp;
    }
	

	   /*
	@Override
	public BillerRes getBiller() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BillerServicetypeRes getBillerServicetype(String billerid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customfield customFields(String billerid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VdRes validateRequest(VdReq vdReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenrrrRes generaterrr(GenrrrReq genrrrReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RRRdetRes rrrdetails(String rrr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BillPaymtRes billpaymentnotification(BillPaymtReq billPaymtReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymtstatusRes paymentstatus(PaymtstatusReq paymtstatusReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Biller printreceipt() {
		// TODO Auto-generated method stub
		return null;
	}
	*/

}
