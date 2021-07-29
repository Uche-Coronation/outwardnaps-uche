/**
 * 
 */
package com.cmb.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmb.interfaces.AuthorizerLevelRepository;
import com.cmb.interfaces.BatchDetailRepository;
import com.cmb.interfaces.FinancialInstitutionRepository;
import com.cmb.interfaces.MaximumAuthorizationLevelsRepository;
import com.cmb.interfaces.TransactionAuthorizerDetailRepository;
import com.cmb.interfaces.TransactionStatusRepository;
import com.cmb.interfaces.UploadRepository;
import com.cmb.model.AuthorizerLevel;
import com.cmb.model.BatchDetail;
import com.cmb.model.FinancialInstitution;
import com.cmb.model.InitiatorDetail;
import com.cmb.model.MaximumAuthorizationLevels;
import com.cmb.model.NameEnquiryResponse;
import com.cmb.model.NapsResponseData;
import com.cmb.model.NipNameEnquiryRequest;
import com.cmb.model.NipNameEnquiryResponse;
import com.cmb.model.PaymentDetail;
import com.cmb.model.Record;
import com.cmb.model.RequestData;
import com.cmb.model.ResponseData;
import com.cmb.model.TransactionAuthorizerDetail;
import com.cmb.model.TransactionStatus;
import com.cmb.model.UploadData;
import com.cmb.model.UploadRequestData;
import com.cmb.model.User;
import com.cmb.model.UserRepository;
import com.cmb.services.BaseInterface;
import com.cmb.services.ExcelFileGenerator;
import com.cmb.services.ProcessServiceInterface;
import com.expertedge.entrustplugin.ws.AuthResponseDTO;
import com.expertedge.entrustplugin.ws.TokenAuthDTO;

/**
 * @author waliu.faleye
 *
 */
@Transactional
@Controller
public class UploadController implements ErrorController {
	@Value("${entrust.app.code}")
	String entrustAppCode;

	@Value("${entrust.app.desc}")
	String entrustAppDesc;

	@Value("${staff.entrust.group}")
	String staffEntrustGroup;

	@Value("${entrust.token.authentication.failed}")
	String entrustTokenAuthFailed;

	@Value("${outwardnaps.url}")
	String outwardNapsUrl;

	@Value("${transaction.channel}")
	String transactionChannel;

	@Value("${ignore.limit.check}")
	String ignoreLimitCheck;

	private static final String PATH = "/error";

	private UploadRepository uploadRepository;
	private String uploadSessionId;
	private String fileName;
	private BatchDetailRepository batchDetailRepo;
	// private TransactionStatusRepository transactionStatusRepo;
	private TransactionAuthorizerDetailRepository tadRepo;
	// private UserRepository userRepo;
	// private AuthorizerLevelRepository alRepo;

	@Autowired
	List<MaximumAuthorizationLevels> malList;

	/**
	 * @param uploadRepository
	 */
	@Autowired
	public UploadController(UploadRepository uploadRepository, FinancialInstitutionRepository financialInstitutionRepo,
			BatchDetailRepository batchDetailRepo, TransactionStatusRepository transactionStatusRepo,
			MaximumAuthorizationLevelsRepository malRepo, TransactionAuthorizerDetailRepository tadRepo,
			UserRepository userRepo, AuthorizerLevelRepository alRepo) {
		super();
		this.uploadRepository = uploadRepository;
		// this.financialInstitutionRepo = financialInstitutionRepo;
		this.batchDetailRepo = batchDetailRepo;
		// this.transactionStatusRepo = transactionStatusRepo;
		// this.malRepo = malRepo;
		this.tadRepo = tadRepo;
		// this.userRepo = userRepo;
		// this.alRepo = alRepo;
	}

	// @Autowired
	// JobLauncher jobLauncher;

//	@Autowired
//	@Qualifier("processUploadJob")
//	Job job;
//
//	@Autowired
//	@Qualifier("naps")
//	Job jobAuthorize;

	@Value("${outwardnaps.uploadedfile.path}")
	String filePath;

	@Value("${outwardnaps.file.template}")
	String fileTemplate;

	// FinancialInstitutionRepository financialInstitutionRepo;
	@Autowired
	List<FinancialInstitution> finInsts;

	@Autowired
	ProcessServiceInterface psi;

	@Autowired
	BaseInterface baseInterface;

	@GetMapping(value = "/bulkUpload")
	public String bulkUpload(UploadData uploadData, Model model) {

		model.addAttribute("uploadData", uploadData);
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser == null) {
			return "redirect:/";
		}
		return "upload";
	}

	@GetMapping(value = "/bankCode")
	public String bankCode(Model model) {
		model.addAttribute("bankList", finInsts);
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser == null) {
			return "redirect:/";
		}

		return "bankcode";
	}

	@GetMapping(value = "/uploadFile")
	public String uploadFilePage(UploadData uploadData) {

		return "uploadfile";
	}

	@RequestMapping(value = PATH)
	public String error() {
		return "error";
	}

	@GetMapping(value = "/downloadFileTemplate")
	public String downloadFileTemplate(HttpServletResponse response) {

		try {
			if (fileTemplate != null) {
				Path pdfPath = Paths.get(fileTemplate);
				byte[] dataObj = Files.readAllBytes(pdfPath);
				File file = new File(fileTemplate);
				streamReport(response, dataObj, file.getName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	protected void streamReport(HttpServletResponse response, byte[] data, String name) throws IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=" + name);
		response.setContentLength(data.length);

		response.getOutputStream().write(data);
		response.getOutputStream().flush();
	}

	@GetMapping(value = "/authorizeUploadFile")
	public String authorizeUploadFilePage(UploadRequestData uploadRequestData, ModelMap model) {
		model.addAttribute("batchIds", uploadRepository.findDistinctBatchId());
		return "authorizeUploadFile";
	}

	@PostMapping(value = "/submitFile")
	public String submitFilePage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			UploadData uploadData, ModelMap model) {
		ExcelFileGenerator excelGen = new ExcelFileGenerator();
		NipNameEnquiryResponse enqResp = null;
		String responseMessage = "Kindly ensure that your file name has never been used ";
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			Set<UploadRequestData> reqDataList = new HashSet<UploadRequestData>();
			BatchDetail batchDetail = new BatchDetail();
			String myFileName = "";
			try {
				if (file.isEmpty()) {
					redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
					return "redirect:uploadStatus";
				}

				try {

					// Get the file and save it somewhere
					byte[] bytes = file.getBytes();
					Path path = Paths.get(filePath + file.getOriginalFilename());
					Files.write(path, bytes);

					redirectAttributes.addFlashAttribute("message",
							"You successfully uploaded '" + file.getOriginalFilename() + "'");

				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("file.getOriginalFilename()>>>>" + file.getOriginalFilename());
				myFileName = file.getOriginalFilename().split("\\.")[0]
						.concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"))).concat(".")
						.concat(file.getOriginalFilename().split("\\.")[1]);
				System.out.println("myFileName>>>>" + myFileName);
				this.setUploadSessionId(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + generateRandom());
				excelGen.setFileName(filePath + file.getOriginalFilename());

				List<List<String>> list = excelGen.readExcel();
				System.out.println("upload list.size()===" + list.size());
				uploadData.setUploadSessionId(getUploadSessionId());
				int i = 1;
				for (List<String> list2 : list) {
					if (!list2.isEmpty()) {
						UploadRequestData reqData = new UploadRequestData();
						reqData.setAccountNumber(list2.get(0).trim());
						reqData.setAccountSortCode(list2.get(4).trim());
						reqData.setItemAmount(new BigDecimal(list2.get(2)));
						reqData.setItemAmountStr(this.formatAmount(reqData.getItemAmount()));

						reqData.setItemSerialNumber(Long.valueOf(i));
						reqData.setOrderingPartyAccountNumber(uploadData.getOrderingPartyAccountNumber());
						reqData.setRecieverName(list2.get(1));
						reqData.setReferenceNumber(String.valueOf(generateRandom()));
						reqData.setSenderName(uploadData.getOrderingPartyAccountName());
						reqData.setTransactionParticulars(list2.get(3));
						reqData.setUploadedBy((String) getSession().getAttribute("username"));
						reqData.setUploadedDate(new Date());
						reqData.setUploadedFileName(myFileName);
						reqData.setUploadSessionId(getUploadSessionId());
						List<FinancialInstitution> finOpt = finInsts.parallelStream()
								.filter(a -> a.getSortCode().equals(reqData.getAccountSortCode()))
								.collect(Collectors.toList());
						if (!finOpt.isEmpty()) {
							FinancialInstitution fi = finOpt.get(0);
							NipNameEnquiryRequest enqRequest = new NipNameEnquiryRequest();
							enqRequest.setAccountNumber(list2.get(0).trim());
							enqRequest.setDestinationInstitutionCode(fi.getNipBankCode());
							try {
								enqResp = psi.nipNameEnquiry(enqRequest);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							String verifiedName = enqResp == null ? ""
									: Strings.isNullOrEmpty(enqResp.getResponseCode()) ? ""
											: enqResp.getResponseCode().equals("00") ? enqResp.getAccountName()
													: enqResp.getResponseDescription();
							reqData.setVerifiedName(verifiedName);
							reqData.setBankName(fi.getInstitutionName());
							reqDataList.add(reqData);
							i++;
						} else {
							responseMessage = "Invalid Bank Code for account number: " + list2.get(0);
						}
					}
				}
				batchDetail.setTransactionStatus(baseInterface.getTransactionStatus("I"));
				batchDetail.setOrderingPartyAccountNumber(uploadData.getOrderingPartyAccountNumber());
				batchDetail.setUploadedBy((String) getSession().getAttribute("username"));
				batchDetail.setUploadedDate(new Date());
				batchDetail.setUploadedFileName(myFileName);
				batchDetail.setUploadSessionId(getUploadSessionId());
				batchDetail.setUploadRequestData(reqDataList);
				batchDetail.getUploadRequestData().forEach(a -> a.setNapsBatchDetail(batchDetail));
				batchDetailRepo.save(batchDetail);

				BigDecimal totalTransactionAmount = reqDataList.stream().map(a -> a.getItemAmount())
						.reduce(BigDecimal::add).get();
				model.addAttribute("record", reqDataList);
				model.addAttribute("uploadSessionId", this.getUploadSessionId());
				uploadData.setUploadSessionId(this.getUploadSessionId());
				uploadData.setReqDataList(new ArrayList<UploadRequestData>(reqDataList));
				uploadData.setTotalTransactionAmount(this.formatAmount(totalTransactionAmount));
				model.addAttribute("totalTransactionAmount", this.formatAmount(totalTransactionAmount));

			} catch (Exception ex) {
				System.out.println("my exception");
				ex.printStackTrace();
				uploadData.setResponseMessage(responseMessage);
				model.addAttribute("uploadData", uploadData);
				return "upload";
			}
		} else {
			return "redirect:/";
		}

		return "bulkuploadsuccess";
	}

	@PostMapping(value = "/submitVerifiedFile")
	public String submitVerifiedFile(UploadData uploadData, ModelMap model) {
		boolean customerWaiveCharge;
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			TokenAuthDTO arg0 = new TokenAuthDTO();
			arg0.setAppCode(entrustAppCode);
			arg0.setAppDesc(entrustAppDesc);
			arg0.setGroup(staffEntrustGroup);
			System.out.println("uploadData.getToken() ==" + uploadData.getToken());
			arg0.setTokenPin(uploadData.getToken());
			arg0.setUserName((String) getSession().getAttribute("username"));
			AuthResponseDTO res = baseInterface.entrustValidation(arg0);

			if (res != null) {
				if (res.getRespCode() != 1) {
					System.out.println("token res.getRespMessage() ==" + res.getRespMessage());
					System.out.println("token res.getRespCode() ==" + res.getRespCode());
					System.out.println("token res.getRespMessageCode() ==" + res.getRespMessageCode());
					uploadData.setTokenRespMessage(entrustTokenAuthFailed);
					model.addAttribute("uploadData", uploadData);
					return "bulkuploadsuccess";
				} else {
					// return "bulktransfer";

				}
			} else {
				return "bulkuploadsuccess";
			}
			customerWaiveCharge = Strings.isNullOrEmpty(uploadData.getCustomerWaiveCharge()) ? false : true;

			BatchDetail batchDetail = batchDetailRepo.findByUploadSessionId(uploadData.getUploadSessionId());

			Set<UploadRequestData> reqList = batchDetail.getUploadRequestData();
			RequestData reqData = new RequestData();
			reqData.setAccountNumber(uploadData.getOrderingPartyAccountNumber().trim());
			reqData.setTransactionChannel(transactionChannel);

			NameEnquiryResponse senderAccountNameResp = psi
					.getLocalAccountDetail(uploadData.getOrderingPartyAccountNumber().trim());
			System.out.println("senderAccountNameResp.getAccountName()>>>>>" + senderAccountNameResp.getAccountName());
			reqList.forEach(a -> {
				a.setOrderingPartyAccountNumber(uploadData.getOrderingPartyAccountNumber().trim());
				a.setSenderName(senderAccountNameResp.getAccountName());
				a.setCustomerWaiveCharge(customerWaiveCharge);
			});

			uploadRepository.saveAll(reqList);
			// Optional<TransactionStatus> transOpt = transactionStatusRepo.findById("E");
			batchDetail.setTransactionStatus(baseInterface.getTransactionStatus("E"));
			batchDetail.setOrderingPartyAccountNumber(uploadData.getOrderingPartyAccountNumber().trim());
			batchDetail.setOrderingPartyAccountName(senderAccountNameResp.getAccountName());
			batchDetail.setCustomerWaiveCharge(customerWaiveCharge);
			batchDetailRepo.save(batchDetail);
			InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
			initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted Successfully");
			model.addAttribute("initiatorDetail", initiatorDetail);
			Long maxAuthLevel = 0l;
			if (!malList.isEmpty())
				maxAuthLevel = malList.get(0).getMaxLevel();
			List<TransactionAuthorizerDetail> tadList = new ArrayList<TransactionAuthorizerDetail>();
			System.out.println("maxAuthLevel====" + maxAuthLevel);
			for (Long i = 1L; i <= maxAuthLevel; i++) {
				TransactionAuthorizerDetail tad = new TransactionAuthorizerDetail();
				tad.setLevel(i);
				tad.setUploadSessionId(uploadData.getUploadSessionId());
				tad.setChannel("NAPS");
				TransactionAuthorizerDetail myTad = tadRepo
						.findByUploadSessionIdAndLevel(uploadData.getUploadSessionId(), i);
				if (myTad == null) {
					tadList.add(tad);
				}
			}
			if (!tadList.isEmpty()) {
				tadRepo.saveAll(tadList);
			}
		} else {
			return "redirect:/";
		}
		return "landing";
	}

	/*
	 * public JobParameters getJobParameters() { JobParametersBuilder
	 * jobParametersBuilder = new JobParametersBuilder();
	 * jobParametersBuilder.addString("fileName", this.getFileName());
	 * jobParametersBuilder.addString("filePath", filePath);
	 * jobParametersBuilder.addString("orderingPartyAccountNumber", "1017820991");
	 * jobParametersBuilder.addString("processorSol", "999");
	 * jobParametersBuilder.addString("senderName", "waliu faleye");
	 * jobParametersBuilder.addString("uploadSessionId", this.getUploadSessionId());
	 * jobParametersBuilder.addString("userName", "SYSTEM"); return
	 * jobParametersBuilder.toJobParameters(); }
	 */

	@GetMapping("/authorizePage")
	public String authorizePage(@RequestParam Long id, ModelMap model) {
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
			TransactionAuthorizerDetail tad = new TransactionAuthorizerDetail();
			model.addAttribute("initiatorDetail", initiatorDetail);
			Optional<BatchDetail> batchOpt = batchDetailRepo.findById(id);
			if (batchOpt.isPresent()) {
				BatchDetail batchDetail = batchOpt.get();
				String sessionId = batchDetail.getUploadSessionId();
				List<TransactionAuthorizerDetail> tadList = tadRepo.findByUploadSessionId(sessionId);

				TransactionStatus tranStatus = baseInterface.getTransactionStatus("R");
				List<TransactionAuthorizerDetail> rejectedTransaction = tadList.parallelStream()
						.filter(a -> tranStatus.equals(a.getTransactionStatus())).collect(Collectors.toList());

				if (!rejectedTransaction.isEmpty() || (!Strings.isNullOrEmpty(batchDetail.getResponseCode())
						&& "000".equals(batchDetail.getResponseCode()))) {
					System.out.println("It's rejected or completed already");
					tad.setMode("VIEW");
				} else {
					System.out.println("sessionId>>>>" + sessionId);
					AuthorizerLevel al = loginUser.getAuthorizerLevel();
					System.out.println("al.getLevel()>>>>" + al.getLevel() == null ? 0L : al.getLevel());
					List<TransactionAuthorizerDetail> mytadList = tadList;// .parallelStream()
					// .filter(a -> a.getLevel() == al.getLevel()).collect(Collectors.toList());
					for (TransactionAuthorizerDetail myTad : mytadList) {
						// TransactionAuthorizerDetail myTad = mytadList.get(0);
						System.out.println("myTad>>>>" + myTad);
						// Long maxLevel = malList.isEmpty() ? 0L : malList.get(0).getMaxLevel();
						// if (al.getLevel().equals(maxLevel)) {
						// final authorizer can retry when transaction is not successful
						if (myTad != null && myTad.getTransactionStatus() != null
								&& "P".equalsIgnoreCase(myTad.getTransactionStatus().getStatus())) {
							tad.setMode("VIEW");
							break;
						}
						/*
						 * } else { if (myTad != null && myTad.getTransactionStatus() != null) {
						 * tad.setMode("VIEW"); } }
						 */
					}

				}

				Set<UploadRequestData> uploadDataList = batchDetail.getUploadRequestData();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				SimpleDateFormat sdfUploadedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				tadList.forEach(a -> {
					a.setRoleDisplay("Authorizer ".concat(a.getLevel().toString()));
					a.setDisplayUser(a.getUser() == null ? "" : a.getUser().getUserName());
					a.setDisplayStatus(
							a.getTransactionStatus() == null ? "" : a.getTransactionStatus().getDescription());
					a.setDisplayAuthorizeDate(a.getAuthorizeDate() == null ? "" : sdf.format(a.getAuthorizeDate()));
				});
				batchDetail.setDisplayUploadedDate(sdfUploadedDate.format(batchDetail.getUploadedDate()));

				uploadDataList.forEach(a -> {
					a.setDisplayUploadedDate(sdf.format(a.getUploadedDate()));
					a.setItemAmountStr(formatAmount(a.getItemAmount()));
				});
				model.addAttribute("tadList", tadList);
				model.addAttribute("uploadDataList", uploadDataList);
				model.addAttribute("tad", tad);
				model.addAttribute("batchDetail", batchDetail);
			}
		} else {
			return "redirect:/";
		}

		return "authorization";
	}

	@PostMapping(value = "/submitFileDetails")
	public String submitFileDetailsPage(@ModelAttribute Record uploadData, ModelMap model) {
		ArrayList<UploadRequestData> reqDataList = uploadData.getReqDataList();
		System.out.println("Size == " + reqDataList.size());

		try {

			// update acceptItem on the item list
			reqDataList.forEach(data -> data.setAcceptItem(data.getAcceptItem().length() > 0 ? "Y" : "N"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		uploadRepository.saveAll(reqDataList);

		return "uploadfiledetail";
	}

	@PostMapping(value = "/rejectBulkRequest")
	public String rejectBulkRequest(TransactionAuthorizerDetail authorizerDetail, ModelMap model) {
		String responseMessage = "";
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			TokenAuthDTO arg0 = new TokenAuthDTO();
			arg0.setAppCode(entrustAppCode);
			arg0.setAppDesc(entrustAppDesc);
			arg0.setGroup(staffEntrustGroup);
			arg0.setTokenPin(authorizerDetail.getToken());
			arg0.setUserName((String) getSession().getAttribute("username"));
			AuthResponseDTO res = baseInterface.entrustValidation(arg0);

			if (res != null) {
				if (res.getRespCode() != 1) {
					System.out.println("token res.getRespMessage() ==" + res.getRespMessage());
					System.out.println("token res.getRespCode() ==" + res.getRespCode());
					System.out.println("token res.getRespMessageCode() ==" + res.getRespMessageCode());
					authorizerDetail.setTokenRespMessage(entrustTokenAuthFailed);
					this.authorizePage(authorizerDetail.getBatchId(), model);
				} else {
					// return "bulktransfer";

				}
			} else {
				this.authorizePage(authorizerDetail.getBatchId(), model);
			}

			TransactionStatus ts = baseInterface.getTransactionStatus("R");

			AuthorizerLevel al = loginUser.getAuthorizerLevel();

			if (al != null) {
				TransactionAuthorizerDetail tad = tadRepo.findByUploadSessionId(authorizerDetail.getUploadSessionId())
						.get(0);
				// .findByUploadSessionIdAndLevel(authorizerDetail.getUploadSessionId(),
				// al.getLevel());
				tad.setAuthorizeDate(new Date());
				tad.setComment(authorizerDetail.getComment());
				tad.setTransactionStatus(ts);
				tad.setUser(loginUser);

				tadRepo.save(tad);
			}
			BatchDetail batchDetail = batchDetailRepo.findByUploadSessionId(authorizerDetail.getUploadSessionId());
			batchDetail.setComment(authorizerDetail.getComment());
			batchDetail.setTransactionStatus(ts);

			batchDetailRepo.save(batchDetail);
		} else {
			return "redirect:/";
		}
		InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
		System.out.println("initiatorDetail===" + initiatorDetail);
		model.addAttribute("initiatorDetail", initiatorDetail);
		responseMessage = "Request was Rejected Successfully";
		model.addAttribute("responseMessage", responseMessage);

		return "landingauth";
	}

	@PostMapping(value = "/authorizeBulkRequest")
	public String authorizeBulkRequest(TransactionAuthorizerDetail authorizerDetail, ModelMap model) {
		String responseMessage = "Request Authorization was Successful.";
		String updateData = "";
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		List<NapsResponseData> responseList = new ArrayList<NapsResponseData>();

		if (loginUser != null) {
			TokenAuthDTO arg0 = new TokenAuthDTO();
			arg0.setAppCode(entrustAppCode);
			arg0.setAppDesc(entrustAppDesc);
			arg0.setGroup(staffEntrustGroup);
			arg0.setTokenPin(authorizerDetail.getToken());
			arg0.setUserName((String) getSession().getAttribute("username"));
			AuthResponseDTO res = baseInterface.entrustValidation(arg0);
			BatchDetail batchDetail = batchDetailRepo.findByUploadSessionId(authorizerDetail.getUploadSessionId());

			if (res != null) {
				if (res.getRespCode() != 1) {
					System.out.println("token res.getRespMessage() ==" + res.getRespMessage());
					System.out.println("token res.getRespCode() ==" + res.getRespCode());
					System.out.println("token res.getRespMessageCode() ==" + res.getRespMessageCode());
					authorizerDetail.setTokenRespMessage(entrustTokenAuthFailed);
					this.authorizePage(authorizerDetail.getBatchId(), model);
				} else {
					// return "bulktransfer";

				}
			} else {
				this.authorizePage(authorizerDetail.getBatchId(), model);
			}
			System.out.println("2nd factor passed");
			ResponseData rs = null;
			boolean ignoreLimitCheckBool = true;
			if (!"Y".equals(ignoreLimitCheck)) {
				ignoreLimitCheckBool = false;
			}
			final TransactionStatus tsApprove = baseInterface.getTransactionStatus("A");
			TransactionStatus ts = tsApprove;
			List<TransactionAuthorizerDetail> batchTadList = tadRepo
					.findByUploadSessionId(batchDetail.getUploadSessionId());

			AuthorizerLevel al = loginUser.getAuthorizerLevel();

			if (al != null) {
				// List<MaximumAuthorizationLevels> malList = malList;
				// Long maxLevel = malList.isEmpty() ? 0L : malList.get(0).getMaxLevel();
				// if (al.getLevel().equals(maxLevel)) {
				// This is final authorizer need to call webservice
				/*
				 * List<TransactionAuthorizerDetail> lowerLevelList =
				 * batchTadList.parallelStream() .filter(a ->
				 * a.getLevel().compareTo(al.getLevel()) != 0 &&
				 * tsApprove.getStatus().equals(a.getTransactionStatus().getStatus()))
				 * .collect(Collectors.toList()); List<TransactionAuthorizerDetail> lowerLevel =
				 * batchTadList.parallelStream() .filter(a ->
				 * a.getLevel().compareTo(al.getLevel()) != 0).collect(Collectors.toList());
				 * System.out.println("lowerLevelList.size()====" + lowerLevelList.size());
				 * System.out.println("lowerLevel.size()====" + lowerLevel.size());
				 */
				// if (maxLevel.compareTo(1L) > 0 && !lowerLevelList.isEmpty()) {
				responseList = psi.napsuploadStatusCheck(batchDetail.getUploadSessionId());
				System.out.println("napsuploadStatusCheck responseList.size()====" + responseList.size());
				if (!"000".equals(batchDetail.getResponseCode())) {
					if (responseList.isEmpty()) {
						rs = psi.outwardnapsTransfer(batchDetail, ignoreLimitCheckBool);
					} else {
						rs = new ResponseData();
						rs.setResponseCode("000");
						rs.setResponseDescription("Successfully submitted for processing");
					}
				} else {
					rs = new ResponseData();
					rs.setResponseCode(batchDetail.getResponseCode());
					rs.setResponseDescription(batchDetail.getResponseDescription());
				}
				/*
				 * } else { if (lowerLevel.isEmpty()) { rs =
				 * psi.outwardnapsTransfer(batchDetail, ignoreLimitCheckBool); } else {
				 * updateData = "N"; responseMessage =
				 * "Request cannot be authorized until other authorizer(s) level approve"; } }
				 */
				if (rs != null) {
					if ("000".equals(rs.getResponseCode())) {
						ts = baseInterface.getTransactionStatus("P");
					} else {
						// updateData = "N";
					}
					responseMessage = rs.getResponseDescription();
					batchDetail.setResponseCode(rs.getResponseCode());
					batchDetail.setResponseDescription(rs.getResponseDescription());
				} else {
					if (!"N".equals(updateData)) {
						updateData = "N";
						responseMessage = "Request authorization Failed, contact Admin.";
					}
				}
				// }
				System.out.println("updateData===" + updateData);
				if (!"N".equals(updateData)) {
					System.out.println("batchTadList.size()====" + batchTadList.size());
					System.out.println("al.getLevel()====" + al.getLevel());
					List<TransactionAuthorizerDetail> tadList = batchTadList.parallelStream()
							.filter(a -> a.getTransactionStatus() == null).collect(Collectors.toList());// .parallelStream()
					// .filter(a -> a.getLevel().compareTo(al.getLevel()) ==
					// 0).collect(Collectors.toList());
					System.out.println("tadList.size()====" + tadList.size());
					if (!tadList.isEmpty()) {
						TransactionAuthorizerDetail tad = tadList.get(0);
						tad.setAuthorizeDate(new Date());
						tad.setComment(authorizerDetail.getComment());
						tad.setTransactionStatus(ts);
						tad.setUser(loginUser);
						System.out.println("Entrying tad updateData ===" + updateData);
						tadRepo.save(tad);
					}
				}
			}
			if (!"N".equals(updateData)) {
				batchDetail.setComment(authorizerDetail.getComment());

				batchDetail.setTransactionStatus(ts);
				if ("000".equals(batchDetail.getResponseCode()))
					batchDetail.setBatchStatus("I");

				System.out.println("Entrying batchDetail updateData ===" + updateData);
				if (responseList.isEmpty())
					batchDetail
							.setCounter(Long.sum(batchDetail.getCounter() == null ? 0l : batchDetail.getCounter(), 1l));
				batchDetailRepo.save(batchDetail);
			}
		} else {
			return "redirect:/";
		}

		InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
		System.out.println("initiatorDetail===" + initiatorDetail);
		model.addAttribute("initiatorDetail", initiatorDetail);
		model.addAttribute("responseMessage", responseMessage);
		return "landingauth";
	}

	@GetMapping(value = "/authorizerHome")
	public String authorizerHome(Model model) {
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			String responseMessage = "";
			InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
			System.out.println("initiatorDetail===" + initiatorDetail);
			model.addAttribute("initiatorDetail", initiatorDetail);
			model.addAttribute("responseMessage", responseMessage);
		} else {
			return "redirect:/";
		}
		return "landingauth";
	}

	@GetMapping(value = "/viewDetail")
	public String viewDetail(Model model, @RequestParam Long id) {
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			Optional<BatchDetail> batchOpt = batchDetailRepo.findById(id);
			BatchDetail batchDetail = batchOpt.isPresent() ? batchOpt.get() : new BatchDetail();
			List<TransactionAuthorizerDetail> batchTadList = tadRepo
					.findByUploadSessionId(batchDetail.getUploadSessionId());

			List<UploadRequestData> uploadDataList = new ArrayList<UploadRequestData>(
					batchDetail.getUploadRequestData());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat sdfUploadedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");

			batchDetail.setDisplayUploadedDate(sdfUploadedDate.format(batchDetail.getUploadedDate()));

			uploadDataList.forEach(a -> {
				a.setDisplayUploadedDate(sdf.format(a.getUploadedDate()));
				a.setItemAmountStr(formatAmount(a.getItemAmount()));
			});
			batchTadList.forEach(a -> {
				a.setRoleDisplay("Authorizer ".concat(a.getLevel().toString()));
				a.setDisplayUser(a.getUser() == null ? "" : a.getUser().getUserName());
				a.setDisplayStatus(a.getTransactionStatus() == null ? "" : a.getTransactionStatus().getDescription());
				a.setDisplayAuthorizeDate(a.getAuthorizeDate() == null ? "" : sdf.format(a.getAuthorizeDate()));
			});
			model.addAttribute("uploadDataList", uploadDataList);
			model.addAttribute("batchDetail", batchDetail);
			model.addAttribute("tadList", batchTadList);
		} else {
			return "redirect:/";
		}

		return "batchdetails";
	}

	/**
	 * @return the uploadSessionId
	 */
	public String getUploadSessionId() {
		return uploadSessionId;
	}

	/**
	 * @param uploadSessionId the uploadSessionId to set
	 */
	public void setUploadSessionId(String uploadSessionId) {
		this.uploadSessionId = uploadSessionId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static Long generateRandom() {
		Random random = new Random();
		char[] digits = new char[8];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < 8; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}

	public HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		return session;
	}

	public String formatAmount(BigDecimal value) {
		// System.out.println("formatting value == "+value);
		String format1 = "###,###,##0.00";

		DecimalFormat fm1 = new DecimalFormat(format1);

		String formattedValue = fm1.format(Double.valueOf(value.toString()));

		return formattedValue;
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}

	@GetMapping(value = "/generatePaymentEvidence")
	public String generatePaymentEvidence(@RequestParam Long id, HttpServletResponse response) {

		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser == null) {
			return "redirect:/";
		}
		if (id != null) {
			UploadRequestData item = uploadRepository.findByIdAndPaymentStatus(id, "PAID");
			System.out.println("item === " + item);
			if (item != null) {
				try {

					String fileName = baseInterface.genericPaymentEvidence(getPaymentDetail(item));
					if (fileName != null) {
						Path pdfPath = Paths.get(fileName);
						byte[] dataObj = Files.readAllBytes(pdfPath);
						File file = new File(fileName);
						streamReport(response, dataObj, file.getName());
					}
				} catch (IOException ex) {
					System.out.printf("generatePaymentEvidence error class is %s and message is %s", ex.getClass(),
							ex.getMessage());
					ex.printStackTrace();
				}
			}
		}

		return null;
	}

	private PaymentDetail getPaymentDetail(UploadRequestData item) {
		PaymentDetail pd = new PaymentDetail();
		pd.setAmount(this.formatAmount(item.getItemAmount()));
		pd.setBeneficiaryAccountName(item.getRecieverName());
		pd.setBeneficiaryAccountNumber(item.getAccountNumber());
		pd.setBeneficiaryBankName(item.getBankName());
		int end = item.getOrderingPartyAccountNumber().length();
		int start = end - 4;
		pd.setMaskedOriginatorAccountNumber(
				"******".concat(item.getOrderingPartyAccountNumber().substring(start, end)));
		pd.setNarration(item.getTransactionParticulars());
		pd.setOriginatorAccountName(item.getSenderName());
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMMM uuuu");
		SimpleDateFormat fmt1 = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
		pd.setPrintingDate(LocalDate.now().format(fmt));
		pd.setRequestTime(fmt1.format(item.getUploadedDate()));
		pd.setSessionID(item.getUploadSessionId() + "-" + item.getReferenceNumber());
		return pd;
	}
}
