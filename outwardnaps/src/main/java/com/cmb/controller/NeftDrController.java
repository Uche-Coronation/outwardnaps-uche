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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmb.interfaces.TransactionAuthorizerDetailRepository;
import com.cmb.model.AuthorizerLevel;
import com.cmb.model.InitiatorDetail;
import com.cmb.model.MaximumAuthorizationLevels;
import com.cmb.model.NameEnquiryResponse;
import com.cmb.model.NipNameEnquiryRequest;
import com.cmb.model.NipNameEnquiryResponse;
import com.cmb.model.PaymentDetail;
import com.cmb.model.TransactionAuthorizerDetail;
import com.cmb.model.TransactionStatus;
import com.cmb.model.User;
import com.cmb.model.neft.NEFTBank;
import com.cmb.model.neft.NeftBatchDetail;
import com.cmb.model.neft.NeftOutwardResponse;
import com.cmb.model.neft.OutwardNeftItem;
import com.cmb.neft.interfaces.NeftProcessServiceInterface;
import com.cmb.services.BaseInterface;
import com.cmb.services.ExcelFileGenerator;
import com.cmb.services.ProcessServiceInterface;
import com.expertedge.entrustplugin.ws.AuthResponseDTO;
import com.expertedge.entrustplugin.ws.TokenAuthDTO;

/**
 * @author waliu.faleye
 *
 */
@Controller
@RequestMapping(value = "/neft/dr")
public class NeftDrController implements ErrorController {
	@Autowired
	ProcessServiceInterface psi;

	@Autowired
	NeftProcessServiceInterface neftInterface;

	@Autowired
	BaseInterface baseInterface;

	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public String error() {
		return "neft/error";
	}

	// @Autowired
	// List<TransactionStatus> tranStatusList;
	// TransactionStatusRepository transactionStatusRepo;

	@Value("${outwardneft.dr.file.template}")
	String fileTemplate;

	@Value("${outwardnaps.uploadedfile.path}")
	String filePath;

	@Value("${neft.appid}")
	String appid;

	@Value("${cmb.bankcode}")
	String bankCode;

	@Value("${cmb.neft.sortcode}")
	String sortCode;

	@Value("${cmb.neft.db.colectiontype}")
	String collectionType;

	@Value("${cmb.neft.cycleno}")
	String cycleNo;

	@Value("${cmb.neft.dr.instrumenttype}")
	String instrumentType;

	@Value("${cmb.neft.micrrepairind}")
	String mICRRepairInd;

	@Value("${cmb.neft.trancode}")
	String tranCode;

	@Value("${job.reportgenerated.path}")
	String reportGeneratedFilePath;

	@Value("${entrust.app.code}")
	String entrustAppCode;

	@Value("${entrust.app.desc}")
	String entrustAppDesc;

	@Value("${staff.entrust.group}")
	String staffEntrustGroup;

	@Value("${entrust.token.authentication.failed}")
	String entrustTokenAuthFailed;

	// @Autowired
	// MaximumAuthorizationLevelsRepository malRepo;

	@Autowired
	List<MaximumAuthorizationLevels> malList;

	@Autowired
	TransactionAuthorizerDetailRepository tadRepo;

	@Value("${ignore.limit.check}")
	String ignoreLimitCheck;

	@Value("${neft.paid.status}")
	String paymentStatus;

	@GetMapping(value = "/home")
	public String home(@Valid User user1, BindingResult result, ModelMap model) {
		InitiatorDetail initiatorDetail = baseInterface.getNeftInitiatorDetails(instrumentType);
		initiatorDetail.getNeftBatchDetails().stream().filter(a-> instrumentType.equalsIgnoreCase(a.getInstrumentType()));
		model.addAttribute("initiatorDetail", initiatorDetail);

		return "neft/landingneftdr";
	}

	@GetMapping(value = "/homeAUTH")
	public String homeAUTH(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		InitiatorDetail initiatorDetail = baseInterface.getNeftInitiatorDetails(instrumentType);
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		model.addAttribute("initiatorDetail", initiatorDetail);

		// return "landingone";
		return "neft/landingauthdr";
	}

	@GetMapping(value = "/viewDetail")
	public String viewNeftDetail(Model model, @RequestParam Long id) {
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Optional<NeftBatchDetail> batchOpt = neftInterface.findNeftBatchDetailById(id);
			NeftBatchDetail batchDetail = batchOpt.isPresent() ? batchOpt.get() : new NeftBatchDetail();
			List<TransactionAuthorizerDetail> batchTadList = tadRepo.findByUploadSessionId(batchDetail.getMsgIdStr());

			DateTimeFormatter sdfUploadedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

			batchDetail.setDisplayDate(batchDetail.getDate().format(sdfUploadedDate));
			Set<OutwardNeftItem> uploadDataList = batchDetail.getOutwardNeftItems();
			uploadDataList.forEach(a -> {
				// a.setDisplayUploadedDate(sdf.format(a.getUploadedDate()));
				// a.setBankName(!Strings.isNullOrEmpty(a.getBankName()) ? a.getBankName() :
				// financialInstitutionRepo.findOne(a.getAccountSortCode()).getInstitutionName());
				a.setAmountStr(neftInterface.formatAmount(a.getAmount()));
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

		return "neft/neftbatchdetailsdr";
	}

	@GetMapping(value = "/bankCode")
	public String neftBankCode(Model model) {
		model.addAttribute("bankList", neftInterface.findNeftBanks());
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser == null) {
			return "redirect:/";
		}

		return "neft/neftbankcodedr";
	}

	@GetMapping(value = "/bulkUpload")
	public String neftBulkUpload(Model model) {
		model.addAttribute("batchDetail", new NeftBatchDetail());
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser == null) {
			return "redirect:/";
		}
		return "neft/neftuploaddr";
	}

	@PostMapping(value = "/submitFile")
	public String submitFilePage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			ModelMap model, HttpServletResponse response) {
		ExcelFileGenerator excelGen = new ExcelFileGenerator();
		NipNameEnquiryResponse enqResp = null;
		String responseMessage = "Kindly ensure that your file name has never been used ";
		Set<OutwardNeftItem> reqDataList = new HashSet<OutwardNeftItem>();
		NeftBatchDetail batchDetail = new NeftBatchDetail();
		String myFileName = "";
		LocalDateTime now = LocalDateTime.now();
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
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
				myFileName = file.getOriginalFilename().split("\\.")[0].concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"))).concat(".").concat(file.getOriginalFilename().split("\\.")[1]);
				excelGen.setFileName(filePath + file.getOriginalFilename());
				// LocalDateTime time = LocalDateTime.now();
				List<List<String>> list = excelGen.readExcel();
				System.out.println("upload list.size()===" + list.size());
				batchDetail.setMsgIdStr(now.format(DateTimeFormatter.ofPattern("yyMMddHHmmss")));
				batchDetail.setMsgId(Long.valueOf(batchDetail.getMsgIdStr()));
				batchDetail.setAppid(appid);
				batchDetail.setBankCode(bankCode);
				batchDetail.setDate(now);
				batchDetail.setSettlementTimeF(now.toString());
				batchDetail.setInstrumentType(instrumentType);
				int i = 0;
				BigDecimal totalValue = BigDecimal.ZERO;
				List<String[]> errorList = new ArrayList<String[]>();
				for (List<String> list2 : list) {
					if (!list2.isEmpty()) {
						i++;
						OutwardNeftItem reqData = new OutwardNeftItem();
						reqData.setAccountNo(list2.get(1));
						reqData.setAmount(new BigDecimal(list2.get(3)));
						reqData.setAmountStr(neftInterface.formatAmount(reqData.getAmount()));
						totalValue = totalValue.add(reqData.getAmount());

						reqData.setBankOfFirstDepositDate(now);
						reqData.setBankOfFirstDepositSortCode(sortCode);
						reqData.setBeneficiary(list2.get(2));
						reqData.setBeneficiaryAccountNo(list2.get(1));
						reqData.setCollectionType(collectionType);
						reqData.setCycleNo(cycleNo);
						reqData.setInstrumentDate(now);
						reqData.setInstrumentType(instrumentType);
						reqData.setItemSequenceNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS")));
						reqData.setMicrRepairInd(mICRRepairInd);
						reqData.setNarration(list2.get(4).trim());
						reqData.setPayerAccountNo(list2.get(0).trim());
						reqData.setPresentingBankSortCode(sortCode);
						reqData.setPresentmentDate(now);
						reqData.setSerialNo(String.valueOf(i));
						reqData.setSettlementTime(now);
						reqData.setSortCode(list2.get(5).trim());
						reqData.setSpecialClearing(Boolean.FALSE);
						reqData.setTranCode(tranCode);
						NameEnquiryResponse localNEResp = psi.getLocalAccountDetail(reqData.getPayerAccountNo());
						if (localNEResp != null && "00".equals(localNEResp.getResponseCode())) {
							reqData.setBvnPayer(localNEResp.getBvn());
							reqData.setPayerName(localNEResp.getAccountName());
							reqData.setCurrency(localNEResp.getAccountCurrency());
						} else {
							String[] arr = new String[2];
							arr[0] = String.valueOf(i);
							arr[1] = "Invalid CMB Account : " + reqData.getAccountNo();
							errorList.add(arr);
						}
						List<NEFTBank> banks = neftInterface
								.findNeftBankBySortCode(reqData.getSortCode());
						if (!banks.isEmpty()) {
							NEFTBank neftBank = banks.get(0);
							reqData.setBankName(neftBank.getBankName());
							NipNameEnquiryRequest enqRequest = new NipNameEnquiryRequest();
							enqRequest.setAccountNumber(reqData.getBeneficiaryAccountNo());
							enqRequest.setDestinationInstitutionCode(neftBank.getNipBankCode());
							try {
								enqResp = psi.nipNameEnquiry(enqRequest);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							String verifiedName = "";
							if (enqResp != null && "00".equals(enqResp.getResponseCode())) {
								verifiedName = enqResp.getAccountName();
								reqData.setBvnBeneficiary(enqResp.getCustomerBVN());
							} else {
								if (enqResp != null)
									verifiedName = enqResp.getResponseDescription();
							}
							reqData.setVerifiedName(verifiedName);
						} else {
							String[] arr = new String[2];
							arr[0] = String.valueOf(i);
							arr[1] = "Invalid Bank Code : " + reqData.getSortCode();
							errorList.add(arr);
						}
						
						reqData.setCounter(0l);
						reqDataList.add(reqData);
					}
				}

				batchDetail.setItemCount(Long.valueOf(i));
				batchDetail.setTotalValue(totalValue);

				if (!errorList.isEmpty()) {
					batchDetail.setResponseMessage(
							"File Upload Failed! Please check the downloaded file for Error details");
					model.addAttribute("batchDetail", batchDetail);
					// write to excel and download
					List<String> headerList = new ArrayList<String>();
					headerList.add("Row Number");
					headerList.add("Error Message");
					ExcelFileGenerator excelGen1 = new ExcelFileGenerator();
					excelGen1.setData(errorList);
					excelGen1.setHeaderList(headerList);
					excelGen1.setPageHeader("NEFT Upload Error");
					excelGen1.setReportGeneratedFilePath(reportGeneratedFilePath);
					excelGen1.setSheetTitle("Error");
					excelGen1.setFileName("neft-upload-error-");
					String returnFile = excelGen1.generateExcel();

					if (returnFile != null) {
						Path pdfPath = Paths.get(returnFile);
						byte[] dataObj = Files.readAllBytes(pdfPath);
						File file1 = new File(returnFile);
						streamReport(response, dataObj, file1.getName());
					}
					return "neft/neftuploaddr";
				}
				TransactionStatus ts = baseInterface.getTransactionStatus("I");
				System.out.println("ts.getStatus()===" + ts.getStatus());
				batchDetail.setTransactionStatus(ts);

				batchDetail.setIntiatorId(loginUser);

				batchDetail.setUploadedFileName(myFileName);
				batchDetail.setOutwardNeftItems(reqDataList);
				System.out.println("batchDetail.getStatus()===" + batchDetail.getTransactionStatus().getStatus());
				neftInterface.saveNeftBatchDetail(batchDetail);

				model.addAttribute("batchDetail", batchDetail);
			} catch (Exception ex) {
				System.out.println("my exception");
				ex.printStackTrace();
				batchDetail.setResponseMessage(responseMessage);
				model.addAttribute("batchDetail", batchDetail);
				return "neft/neftuploaddr";
			}
		} else {
			return "redirect:/";
		}

		return "neft/bulkuploadsuccessdr";
	}

	@PostMapping(value = "/submitVerifiedFile")
	public String submitVerifiedFile(NeftBatchDetail batchDetail, ModelMap model) {
		// EntrustMultiFactorAuthImplService srv = new
		// EntrustMultiFactorAuthImplService();
		TokenAuthDTO arg0 = new TokenAuthDTO();
		arg0.setAppCode(entrustAppCode);
		arg0.setAppDesc(entrustAppDesc);
		arg0.setGroup(staffEntrustGroup);
		System.out.println("batchDetail.getToken() ==" + batchDetail.getToken());
		arg0.setTokenPin(batchDetail.getToken());
		arg0.setUserName((String) getSession().getAttribute("username"));
		// AuthResponseDTO res =
		// srv.getEntrustMultiFactorAuthImplPort().performTokenAuth(arg0);
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			AuthResponseDTO res = baseInterface.entrustValidation(arg0);

			if (res != null) {
				if (res.getRespCode() != 1) {
					System.out.println("token res.getRespMessage() ==" + res.getRespMessage());
					System.out.println("token res.getRespCode() ==" + res.getRespCode());
					System.out.println("token res.getRespMessageCode() ==" + res.getRespMessageCode());
					batchDetail.setTokenRespMessage(entrustTokenAuthFailed);
					model.addAttribute("batchDetail", batchDetail);
					return "neft/bulkuploadsuccessdr";
				} else {

				}
			} else {
				return "neft/bulkuploadsuccessdr";
			}

			Optional<NeftBatchDetail> batchDetailOpt = neftInterface.findNeftBatchDetailById(batchDetail.getId());
			if (batchDetailOpt.isPresent()) {
				batchDetail = batchDetailOpt.get();
				batchDetail.setTransactionStatus(baseInterface.getTransactionStatus("E"));

				Long maxAuthLevel = 0l;
				if (!malList.isEmpty())
					maxAuthLevel = malList.get(0).getMaxLevel();
				List<TransactionAuthorizerDetail> tadList = new ArrayList<TransactionAuthorizerDetail>();

				for (Long i = 1L; i <= maxAuthLevel; i++) {
					TransactionAuthorizerDetail tad = new TransactionAuthorizerDetail();
					tad.setLevel(i);
					tad.setChannel("NEFT");
					tad.setUploadSessionId(batchDetail.getMsgIdStr());
					TransactionAuthorizerDetail myTad = tadRepo.findByUploadSessionIdAndLevel(batchDetail.getMsgIdStr(),
							i);
					if (myTad == null) {
						tadList.add(tad);
					}
				}
				if (!tadList.isEmpty()) {
					tadRepo.saveAll(tadList);
					neftInterface.saveNeftBatchDetail(batchDetail);
				}

				InitiatorDetail initiatorDetail = baseInterface.getNeftInitiatorDetails(instrumentType);
				initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted Successfully");
				model.addAttribute("initiatorDetail", initiatorDetail);
			}
		} else {
			return "redirect:/";
		}
		return "neft/landingneftdr";
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

	public HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		return session;
	}

	@GetMapping("/authorizePage")
	public String authorizePage(@RequestParam Long id, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		TransactionAuthorizerDetail tad = new TransactionAuthorizerDetail();
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
			// Successfully");
			Optional<NeftBatchDetail> batchOpt = neftInterface.findNeftBatchDetailById(id);
			if (batchOpt.isPresent()) {
				NeftBatchDetail batchDetail = batchOpt.get();
				List<TransactionAuthorizerDetail> tadList = tadRepo.findByUploadSessionId(batchDetail.getMsgIdStr());

				TransactionStatus tranStatus = baseInterface.getTransactionStatus("R");
				List<TransactionAuthorizerDetail> rejectedTransaction = tadList.parallelStream()
						.filter(a -> tranStatus.getStatus()
								.equals(a.getTransactionStatus() != null ? a.getTransactionStatus().getStatus() : ""))
						.collect(Collectors.toList());
				// tadRepo.findByUploadSessionIdAndTransactionStatus(batchDetail.getMsgIdStr(),
				// tranStatus);
				if (!rejectedTransaction.isEmpty()) {
					System.out.println("It's rejected already");
					tad.setMode("VIEW");
				} else {
					AuthorizerLevel al = loginUser.getAuthorizerLevel();
					// AuthorizerLevel al = alRepo.findByUser(loginUser);
					System.out.println("al.getLevel()>>>>" + al.getLevel() == null ? 0L : al.getLevel());
					List<TransactionAuthorizerDetail> mytadList = tadList;//.parallelStream()
							//.filter(a -> a.getLevel().compareTo(al.getLevel()) == 0).collect(Collectors.toList());
					if (!mytadList.isEmpty()) {
						TransactionAuthorizerDetail myTad = mytadList.get(0);
						// tadRepo.findByUploadSessionIdAndLevel(batchDetail.getMsgIdStr(),al.getLevel());
						System.out.println("myTad>>>>" + myTad);
						if (myTad != null && myTad.getTransactionStatus() != null) {
							tad.setMode("VIEW");
						}
					}

				}

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				DateTimeFormatter sdfUploadedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

				tadList.forEach(a -> {
					a.setRoleDisplay("Authorizer ".concat(a.getLevel().toString()));
					a.setDisplayUser(a.getUser() == null ? "" : a.getUser().getUserName());
					a.setDisplayStatus(
							a.getTransactionStatus() == null ? "" : a.getTransactionStatus().getDescription());
					a.setDisplayAuthorizeDate(a.getAuthorizeDate() == null ? "" : sdf.format(a.getAuthorizeDate()));
				});
				batchDetail.setDisplayDate(batchDetail.getDate().format(sdfUploadedDate));

				batchDetail.getOutwardNeftItems().forEach(a -> {
					// a.setDisplayDate(a.getDate().fo);
					a.setAmountStr(neftInterface.formatAmount(a.getAmount()));
				});
				model.addAttribute("tadList", tadList);
				// model.addAttribute("uploadDataList", uploadDataList);
				model.addAttribute("tad", tad);
				model.addAttribute("batchDetail", batchDetail);

				InitiatorDetail initiatorDetail = baseInterface.getNeftInitiatorDetails(instrumentType);
				model.addAttribute("initiatorDetail", initiatorDetail);
			}
		} else {
			return "redirect:/";
		}

		return "neft/authorizationdr";
	}

	@PostMapping(value = "/rejectBulkRequest")
	public String rejectBulkRequest(TransactionAuthorizerDetail authorizerDetail, ModelMap model) {
		String responseMessage = "";
		// EntrustMultiFactorAuthImplService srv = new
		// EntrustMultiFactorAuthImplService();
		TokenAuthDTO arg0 = new TokenAuthDTO();
		arg0.setAppCode(entrustAppCode);
		arg0.setAppDesc(entrustAppDesc);
		arg0.setGroup(staffEntrustGroup);
		arg0.setTokenPin(authorizerDetail.getToken());
		arg0.setUserName((String) getSession().getAttribute("username"));
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {

			Optional<NeftBatchDetail> batchDetailOpt = neftInterface
					.findNeftBatchDetailById(authorizerDetail.getBatchId());
			NeftBatchDetail batchDetail = batchDetailOpt.isPresent() ? batchDetailOpt.get() : null;
			List<TransactionAuthorizerDetail> batchTadList = tadRepo.findByUploadSessionId(batchDetail.getMsgIdStr());
			// AuthResponseDTO res =
			// srv.getEntrustMultiFactorAuthImplPort().performTokenAuth(arg0);
			AuthResponseDTO res = baseInterface.entrustValidation(arg0);

			if (res != null) {
				if (res.getRespCode() != 1) {
					System.out.println("token res.getRespMessage() ==" + res.getRespMessage());
					System.out.println("token res.getRespCode() ==" + res.getRespCode());
					System.out.println("token res.getRespMessageCode() ==" + res.getRespMessageCode());
					authorizerDetail.setTokenRespMessage(entrustTokenAuthFailed);
					// model.addAttribute("uploadData", authorizerDetail);
					// return "bulkuploadsuccess";
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
				System.out.println("batchTadList.size()====" + batchTadList.size());
				System.out.println("al.getLevel()====" + al.getLevel());
				List<TransactionAuthorizerDetail> tadList = batchTadList;//.parallelStream()
				//		.filter(a -> a.getLevel().compareTo(al.getLevel()) == 0).collect(Collectors.toList());
				// tadRepo.findByUploadSessionIdAndLevel(batchDetail.getMsgIdStr(),
				// al.getLevel());
				System.out.println("tadList.size()====" + tadList.size());
				if (!tadList.isEmpty()) {
					TransactionAuthorizerDetail tad = tadList.get(0);
					tad.setAuthorizeDate(new Date());
					tad.setComment(authorizerDetail.getComment());
					tad.setTransactionStatus(ts);
					tad.setUser(loginUser);

					tadRepo.save(tad);
				}
			}

			if (batchDetail != null) {
				// NeftBatchDetail batchDetail = batchDetailOpt.get();
				batchDetail.setComment(authorizerDetail.getComment());
				batchDetail.setTransactionStatus(ts);

				neftInterface.saveNeftBatchDetail(batchDetail);
			}
		} else {
			return "redirect:/";
		}
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		InitiatorDetail initiatorDetail = baseInterface.getNeftInitiatorDetails(instrumentType);
		System.out.println("initiatorDetail===" + initiatorDetail);
		model.addAttribute("initiatorDetail", initiatorDetail);
		responseMessage = "Request was Rejected Successfully";
		model.addAttribute("responseMessage", responseMessage);

		return "neft/landingauthdr";
	}

	@PostMapping(value = "/authorizeBulkRequest")
	public String authorizeBulkRequest(TransactionAuthorizerDetail authorizerDetail, ModelMap model) {
		String responseMessage = "Request Authorization was Successful.";
		String updateData = "";
		// EntrustMultiFactorAuthImplService srv = new
		// EntrustMultiFactorAuthImplService();
		TokenAuthDTO arg0 = new TokenAuthDTO();
		arg0.setAppCode(entrustAppCode);
		arg0.setAppDesc(entrustAppDesc);
		arg0.setGroup(staffEntrustGroup);
		arg0.setTokenPin(authorizerDetail.getToken());
		arg0.setUserName((String) getSession().getAttribute("username"));
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			// AuthResponseDTO res =
			// srv.getEntrustMultiFactorAuthImplPort().performTokenAuth(arg0);
			AuthResponseDTO res = baseInterface.entrustValidation(arg0);

			Optional<NeftBatchDetail> batchDetailOpt = neftInterface
					.findNeftBatchDetailById(authorizerDetail.getBatchId());
			NeftBatchDetail batchDetail = batchDetailOpt.isPresent() ? batchDetailOpt.get() : null;

			if (res != null) {
				if (res.getRespCode() != 1) {
					System.out.println("token res.getRespMessage() ==" + res.getRespMessage());
					System.out.println("token res.getRespCode() ==" + res.getRespCode());
					System.out.println("token res.getRespMessageCode() ==" + res.getRespMessageCode());
					authorizerDetail.setTokenRespMessage(entrustTokenAuthFailed);
					// model.addAttribute("uploadData", authorizerDetail);
					// return "bulkuploadsuccess";
					this.authorizePage(authorizerDetail.getBatchId(), model);
				} else {
					// return "bulktransfer";

				}
			} else {
				this.authorizePage(authorizerDetail.getBatchId(), model);
			}
			System.out.println("2nd factor passed");
			NeftOutwardResponse rs = null;
			final TransactionStatus tsApprove = baseInterface.getTransactionStatus("A");
			TransactionStatus ts = tsApprove;
			// List<User> userList = userRepo.findByUserName((String)
			// this.getSession().getAttribute("username"));
			List<TransactionAuthorizerDetail> batchTadList = tadRepo.findByUploadSessionId(batchDetail.getMsgIdStr());
			// loginUser = userList.get(0);

			AuthorizerLevel al = loginUser.getAuthorizerLevel();

			if (al != null) {
				// List<MaximumAuthorizationLevels> malList = malRepo.findAll();
				Long maxLevel = malList.isEmpty() ? 0L : malList.get(0).getMaxLevel();
				//if (al.getLevel().equals(maxLevel)) {
					// This is final authorizer need to call webservice
					List<TransactionAuthorizerDetail> lowerLevelList = batchTadList.parallelStream()
							.filter(a -> a.getLevel().compareTo(al.getLevel()) != 0 && tsApprove.getStatus().equals(
									a.getTransactionStatus() != null ? a.getTransactionStatus().getStatus() : ""))
							.collect(Collectors.toList());
					// tadRepo.findByUploadSessionIdAndLevelNotAndTransactionStatus(batchDetail.getMsgIdStr(),al.getLevel(),
					// ts);
					List<TransactionAuthorizerDetail> lowerLevel = batchTadList.parallelStream()
							.filter(a -> a.getLevel().compareTo(al.getLevel()) != 0).collect(Collectors.toList());
					// tadRepo.findByUploadSessionIdAndLevelNot(batchDetail.getMsgIdStr(),
					// al.getLevel());
					//if (maxLevel.compareTo(1L) > 0 && !lowerLevelList.isEmpty()) {
						rs = neftInterface.processNeftBatchTransactions(batchDetail);
					/*} else {
						if (lowerLevel.isEmpty()) {
							rs = neftInterface.processNeftDebitBatchTransactions(batchDetail);
						} else {
							updateData = "N";
							responseMessage = "Request cannot be authorized until other authorizer(s) level approve";
						}
					}*/
					if (rs != null) {
						if ("00".equals(rs.getResponseCode())) {
							ts = baseInterface.getTransactionStatus("P");
							batchDetail.setBatchStatus("I");
						} else {
							updateData = "N";
						}
						batchDetail.setResponseCode(rs.getResponseCode());
						batchDetail.setResponseDescription(rs.getResponseMessage());
						responseMessage = rs.getResponseMessage();
					} else {
						if (!"N".equals(updateData)) {
							updateData = "N";
							responseMessage = "Request authorization Failed, Kindly Retry.";
						}
					}
				//}
				System.out.println("updateData===" + updateData);
				if (!"N".equals(updateData)) {
					System.out.println("batchTadList.size()====" + batchTadList.size());
					System.out.println("al.getLevel()====" + al.getLevel());
					List<TransactionAuthorizerDetail> tadList = batchTadList;//.parallelStream()
					//		.filter(a -> a.getLevel().compareTo(al.getLevel()) == 0).collect(Collectors.toList());
					// tadRepo.findByUploadSessionIdAndLevel(batchDetail.getMsgIdStr(),
					// al.getLevel());
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

				System.out.println("Entrying batchDetail updateData ===" + updateData);
				neftInterface.saveNeftBatchDetail(batchDetail);
			}
		} else {
			return "redirect:/";
		}

		// responseMessage = "Request was Rejected Successfully";
		InitiatorDetail initiatorDetail = baseInterface.getNeftInitiatorDetails(instrumentType);
		System.out.println("initiatorDetail===" + initiatorDetail);
		model.addAttribute("initiatorDetail", initiatorDetail);
		model.addAttribute("responseMessage", responseMessage);
		return "neft/landingauthdr";
	}

	@GetMapping(value = "/authorizerHome")
	public String authorizerHome(Model model) {
		String responseMessage = "";
		InitiatorDetail initiatorDetail = baseInterface.getNeftInitiatorDetails(instrumentType);
		System.out.println("initiatorDetail===" + initiatorDetail);
		model.addAttribute("initiatorDetail", initiatorDetail);
		model.addAttribute("responseMessage", responseMessage);
		return "neft/landingauthdr";
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "neft/error";
	}

	@GetMapping(value = "/generatePaymentEvidence")
	public String generatePaymentEvidence(@RequestParam Long id, HttpServletResponse response) {

		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser == null){
			return "redirect:/";
		}
		if (id != null) {
			OutwardNeftItem item = neftInterface.findOutwardNeftItemByIdAndPaymentStatus(id, paymentStatus);
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

	private PaymentDetail getPaymentDetail(OutwardNeftItem item) {
		PaymentDetail pd = new PaymentDetail();
		pd.setAmount(neftInterface.formatAmount(item.getAmount()));
		pd.setBeneficiaryAccountName(item.getBeneficiary());
		pd.setBeneficiaryAccountNumber(item.getBeneficiaryAccountNo());
		pd.setBeneficiaryBankName(item.getBankName());
		int end = item.getAccountNo().length();
		int start = end - 4;
		pd.setMaskedOriginatorAccountNumber("******".concat(item.getAccountNo().substring(start, end)));
		pd.setNarration(item.getNarration());
		pd.setOriginatorAccountName(item.getPayerName());
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMMM uuuu");
		DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss");
		pd.setPrintingDate(LocalDate.now().format(fmt));
		pd.setRequestTime(item.getInstrumentDate().format(fmt1));
		pd.setSessionID(item.getNeftBatchDetail().getMsgIdStr()+"-"+item.getItemSequenceNo());
		return pd;
	}
}
