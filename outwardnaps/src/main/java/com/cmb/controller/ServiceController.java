/**
 * 
 */
package com.cmb.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmb.interfaces.BatchDetailRepository;
import com.cmb.interfaces.RRRGenDetailRepository;
import com.cmb.interfaces.RRRTranDetailRepository;
import com.cmb.interfaces.RemitaTransactionDetailRepository;
import com.cmb.interfaces.TransactionStatusRepository;
import com.cmb.interfaces.UploadRepository;
import com.cmb.model.InitiatorDetail;
import com.cmb.model.NameEnquiryRequest;
import com.cmb.model.NameEnquiryResponse;
import com.cmb.model.NapsResponseData;
import com.cmb.model.PaymentDetail;
import com.cmb.model.RequestData;
import com.cmb.model.ResponseData;
import com.cmb.model.UploadRequestData;
import com.cmb.model.neft.NEFTBank;
import com.cmb.model.neft.NeftBatchDetail;
import com.cmb.model.remita.customfield.CFCustom;
import com.cmb.model.remita.customfield.CfValue;
import com.cmb.model.remita.customfield.CustomFieldDropDown;
import com.cmb.model.remita.customfield.CustomFieldReq;
import com.cmb.model.remita.customfield.Customfield;
import com.cmb.model.remita.customfield.ResponseDatum;
import com.cmb.model.remita.genrrr.GenrrrReq;
import com.cmb.model.remita.genrrr.GenrrrRes;
import com.cmb.model.remita.rrrdetail.RRRdetRes;
import com.cmb.model.remita.rrrdetail.RrrdetReq;
import com.cmb.model.remita.servicetype.BillerServicetypeReq;
import com.cmb.model.remita.servicetype.BillerServicetypeRes;
import com.cmb.model.remita.validaterequest.VdReq;
import com.cmb.model.remita.validaterequest.res.VdRes;
import com.cmb.neft.interfaces.NEFTBankRepository;
import com.cmb.neft.interfaces.NeftProcessServiceInterface;
import com.cmb.services.BaseInterface;
import com.cmb.services.ProcessServiceInterface;
import com.cmb.services.RemitaService;
import com.cmb.services.RemitaServiceImpl;

/**
 * @author waliu.faleye
 *
 */
@RestController
public class ServiceController {
	Logger logger = LoggerFactory.getLogger(ServiceController.class);
	@Value("${outwardnaps.url}")
	String outwardNapsUrl;
	private UploadRepository uploadRepository;
	// private String uploadSessionId;
	// private String fileName;
	@Autowired
	RemitaService remitaService;

	@Autowired
	NeftProcessServiceInterface neftInterface;

	private BatchDetailRepository batchDetailRepo;

	@Autowired
	ProcessServiceInterface psi;

	@Autowired
	NEFTBankRepository neftBankRepo;

	@Autowired
	BaseInterface baseInterface;

	private RRRGenDetailRepository rrrGenDetailRepo;
	private TransactionStatusRepository transactionStatusRepo;
	private RemitaTransactionDetailRepository remitaTransactionDetailRepository;
	private RRRTranDetailRepository rrrTranDetailRepository;

	public ServiceController(UploadRepository uploadRepository, BatchDetailRepository batchDetailRepo,
			TransactionStatusRepository transactionStatusRepo,
			RemitaTransactionDetailRepository remitaTransactionDetailRepository,
			RRRTranDetailRepository rrrTranDetailRepository, RRRGenDetailRepository rrrGenDetailRepo) {
		this.uploadRepository = uploadRepository;
		this.batchDetailRepo = batchDetailRepo;
		this.transactionStatusRepo = transactionStatusRepo;
		this.rrrTranDetailRepository = rrrTranDetailRepository;
		this.remitaTransactionDetailRepository = remitaTransactionDetailRepository;
		this.rrrGenDetailRepo = rrrGenDetailRepo;
	}

	@PostMapping(value = "/accountDetail")
	public NameEnquiryResponse localNameEnquiry(@RequestBody NameEnquiryRequest reqData) {
		NameEnquiryResponse resp = new NameEnquiryResponse();
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		try {
			// prs.setOutwardNapsUrl(outwardNapsUrl);
			System.out.println("reqData.getAccountNumber()====" + reqData.getAccountNumber());
			resp = psi.getLocalAccountDetail(reqData.getAccountNumber());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resp;
	}

	@PostMapping(value = "/accountDailyBalance")
	public ResponseData getAccountDailyBalance(@RequestBody RequestData reqData) {
		ResponseData resp = new ResponseData();
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		try {
			// prs.setOutwardNapsUrl(outwardNapsUrl);
			System.out.println("reqData.getAccountNumber()====" + reqData.getAccountNumber());
			resp = psi.getAccountDailyBalance(reqData);
			resp.setDailyBalanceStr(formatAmount(resp.getDailyBalance()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resp;
	}

	@PostMapping(value = "/accountDailyTranLimit")
	public ResponseData getAccountDailyTranLimit(@RequestBody RequestData reqData) {
		ResponseData resp = new ResponseData();
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		try {
			// prs.setOutwardNapsUrl(outwardNapsUrl);
			System.out.println("reqData.getAccountNumber()====" + reqData.getAccountNumber());
			resp = psi.getAccountDailyTranLimit(reqData);
			resp.setTotalDailyLimitStr(formatAmount(resp.getTotalDailyLimit()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resp;
	}

	@GetMapping(value = "/getBillerx")
	public com.cmb.model.remita.BillerRes getBiller() {
		logger.info("Inside get Biller controller ");
		// RemitaServiceImpl remitaServiceImpl = new RemitaServiceImpl();
		com.cmb.model.remita.BillerRes bil = new com.cmb.model.remita.BillerRes();
		bil = remitaService.getBiller();
		logger.info(" Biller returned from service ");
		return bil;
	}

	// @CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value = "/billerServiceType")
	public List<com.cmb.model.remita.servicetype.ResponseDatum> billerServiceType(
			@RequestBody BillerServicetypeReq billerServicetypeReq) {
		logger.info("Inside get Biller controller ");
		RemitaServiceImpl remitaServiceImpl = new RemitaServiceImpl();
		List<com.cmb.model.remita.servicetype.ResponseDatum> resp = new ArrayList<com.cmb.model.remita.servicetype.ResponseDatum>();
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		try {
			BillerServicetypeRes respa = remitaService.getBillerServicetype(billerServicetypeReq.getBillerid());
			resp = respa.getResponseData().get(0);
		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return resp;
	}

	// @CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value = "/getCustomField")
	public Customfield getCustomField(@RequestBody CustomFieldReq customFieldReq) {
		logger.info("Inside get custom fields controller ");
		logger.info("custom fields : " + customFieldReq.toString());
		Customfield customfield = new Customfield();
		CFCustom cfcustom = new CFCustom();
		List<CFCustom> cfcustomarr = new ArrayList<CFCustom>();
		List<ResponseDatum> responseData;
		RemitaServiceImpl remitaServiceImpl = new RemitaServiceImpl();
		List<com.cmb.model.remita.servicetype.ResponseDatum> resp = new ArrayList<com.cmb.model.remita.servicetype.ResponseDatum>();
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		try {
			customfield = remitaService.customFields(customFieldReq);
			// resp =respa.getResponseData().get(0);
			// customfield.
			logger.info("custom fields returned: " + customfield.toString());
			List<CfValue> vallist = new ArrayList<CfValue>();
			if (customfield.getResponseCode().equalsIgnoreCase("00")) {
				responseData = customfield.getResponseData();
				for (ResponseDatum responseDatax : responseData) {
					cfcustom.setId(responseDatax.getId());
					cfcustom.setColumnName(responseDatax.getColumnName());
					cfcustom.setColumnType(responseDatax.getColumnType());
					cfcustom.setRequired(responseDatax.getRequired());
					for (CustomFieldDropDown customFieldDropDown : responseDatax.getCustomFieldDropDown()) {
						CfValue val = new CfValue();
						val.setValue(customFieldDropDown.getId());
						val.setQuantity(1);
						val.setAmount(Double.parseDouble(customFieldDropDown.getUnitprice()));
						vallist.add(val);

					}
					cfcustomarr.add(cfcustom);
				}

				responseData.size();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("custom fieldx returned: " + Arrays.deepToString(cfcustomarr.toArray()));
		return customfield;
	}

	@PostMapping(value = "/validateRemita")
	public VdRes validateRemita(@RequestBody VdReq vdReq) {
		VdRes resp = new VdRes();
		logger.info("Inside validate Remita request controller ");
		// logger.info("Request : "+ vdReq.toString());
		RemitaServiceImpl remitaServiceImpl = new RemitaServiceImpl();
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		try {
			resp = remitaService.validateRequest(vdReq);
			logger.info("Response : " + resp.toString());
		} catch (Exception ex) {
			logger.info("Error in Validating Remita request : " + ex.getMessage());
			ex.printStackTrace();
		}

		return resp;
	}

	@PostMapping(value = "/generateRRR")
	public GenrrrRes generateRRR(@RequestBody GenrrrReq genrrrReq) {
		GenrrrRes resp = new GenrrrRes();
		logger.info("Inside generate RRR request controller ");
		logger.info("Request : " + genrrrReq.toString());
		RemitaServiceImpl remitaServiceImpl = new RemitaServiceImpl();
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		try {
			// resp=remitaService.generaterrr(genrrrReq);
			//
			// resp=remitaServiceImpl.generaterrr(genrrrReq,rrrGenDetailRepo);
			logger.info("Response : " + resp.toString());
		} catch (Exception ex) {
			logger.info("Error in generating RRR : " + ex.getMessage());
			ex.printStackTrace();
		}

		return resp;
	}

	@PostMapping(value = "/getRRRDetail")
	public RRRdetRes getRRRDetail(@RequestBody RrrdetReq rrrdetReq) {
		RRRdetRes resp = new RRRdetRes();
		logger.info("Inside getRRRDetail request controller ");
		logger.info("Request : " + rrrdetReq.toString());
		RemitaServiceImpl remitaServiceImpl = new RemitaServiceImpl();
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		try {
			resp = remitaService.rrrdetails(rrrdetReq.getRrr(), "");
			logger.info("Response : " + resp.toString());
		} catch (Exception ex) {
			logger.info("Error in retrieving RRR Detail : " + ex.getMessage());
			ex.printStackTrace();
		}

		return resp;
	}

	@PostMapping(value = "/napsuploadStatusCheck")
	public List<NapsResponseData> napsuploadStatusCheck(@RequestBody UploadRequestData reqData) {
		List<NapsResponseData> resp = new ArrayList<NapsResponseData>();
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		try {
			// prs.setOutwardNapsUrl(outwardNapsUrl);
			System.out.println("reqData.getUploadSessionId()====" + reqData.getUploadSessionId());
			resp = psi.napsuploadStatusCheck(reqData.getUploadSessionId());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resp;
	}

	public String formatAmount(BigDecimal value) {
		// System.out.println("formatting value == "+value);
		String format1 = "###,###,##0.00";

		DecimalFormat fm1 = new DecimalFormat(format1);

		String formattedValue = fm1.format(Double.valueOf(value.toString()));

		return formattedValue;
	}

	@PostMapping(value = "/saveNeftBatchDetail")
	public ResponseData saveNeftBatchDetail(@RequestBody NeftBatchDetail detail) {
		ResponseData resp = new ResponseData();
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		NeftBatchDetail saveData = neftInterface.saveNeftBatchDetail(detail);
		if (saveData != null) {
			resp.setResponseCode("success");
			resp.setResponseDescription("success");
		}

		return resp;
	}

	@PostMapping(value = "/saveNeftBank")
	public ResponseData saveNeftBank(@RequestBody NEFTBank detail) {
		ResponseData resp = new ResponseData();

		NEFTBank savedBank = neftBankRepo.save(detail);
		if (savedBank != null) {
			resp.setResponseCode("success");
			resp.setResponseDescription("success");
		}

		return resp;
	}

	@PostMapping(value = "/processNeftBatchTransactions")
	public void processNeftBatchTransactions(@RequestBody Long batchId) {
		Optional<NeftBatchDetail> opt = neftInterface.findNeftBatchDetailById(batchId);
		if (opt.isPresent())
			neftInterface.processNeftBatchTransactions(opt.get());
	}

	@PostMapping(value = "/processNeftBatchReturn")
	public void processNeftBatchReturn(@RequestBody Long batchId) {
		Optional<NeftBatchDetail> opt = neftInterface.findNeftBatchDetailById(batchId);
		if (opt.isPresent())
			neftInterface.processNeftBatchReturn(opt.get());
	}

	@PostMapping(value = "/genericPaymentEvidence")
	public void genericPaymentEvidence(@RequestBody PaymentDetail paymentDetail) throws IOException {
		baseInterface.genericPaymentEvidence(paymentDetail);
	}

	@GetMapping(value = "/getNAPSBatchDetails")
	public InitiatorDetail getNAPSBatchDetails() throws IOException {
		return baseInterface.getInitiatorDetails();
	}

}
