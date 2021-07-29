/**
 * 
 */
package com.cmb.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.cmb.interfaces.TransactionAuthorizerDetailRepository;
import com.cmb.model.AuthorizerLevel;
import com.cmb.model.InitiatorDetail;
import com.cmb.model.MaximumAuthorizationLevels;
import com.cmb.model.TransactionAuthorizerDetail;
import com.cmb.model.TransactionStatus;
import com.cmb.model.User;
import com.cmb.model.neft.CollectionType;
import com.cmb.model.neft.InflowApprovalData;
import com.cmb.model.neft.NEFTBank;
import com.cmb.model.neft.NeftBatchReturnDetail;
import com.cmb.model.neft.NeftOutwardResponse;
import com.cmb.model.neft.OutwardNeftReturn;
import com.cmb.model.neft.PendingNeftData;
import com.cmb.model.neft.ReturnReason;
import com.cmb.neft.interfaces.NeftProcessServiceInterface;
import com.cmb.services.BaseInterface;
import com.cmb.services.ProcessServiceInterface;
import com.expertedge.entrustplugin.ws.AuthResponseDTO;
import com.expertedge.entrustplugin.ws.TokenAuthDTO;
import com.google.common.base.Strings;

import lombok.extern.slf4j.Slf4j;

/**
 * @author waliu.faleye
 *
 */
@Controller
@RequestMapping(value = "/neft/in")
@Slf4j
public class NeftDrInflowController implements ErrorController {
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

	@Value("${outwardneft.file.template}")
	String fileTemplate;

	@Value("${outwardnaps.uploadedfile.path}")
	String filePath;

	@Value("${neft.appid}")
	String appid;

	@Value("${cmb.bankcode}")
	String bankCode;

	@Value("${cmb.neft.sortcode}")
	String sortCode;

	@Value("${cmb.neft.colectiontype}")
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

	@Autowired
	List<CollectionType> getCollectionTypes;

	@Autowired
	List<ReturnReason> getReturnReasons;

	@GetMapping(value = "/home")
	public String home(@Valid User user1, BindingResult result, ModelMap model) {
		InitiatorDetail initiatorDetail = baseInterface.getNeftInflowInitiatorDetails();
		model.addAttribute("initiatorDetail", initiatorDetail);

		return "neft/landingneftdrinflow";
	}

	@GetMapping(value = "/homeAUTH")
	public String homeAUTH(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		InitiatorDetail initiatorDetail = baseInterface.getNeftInflowInitiatorDetails();
		InitiatorDetail initiatorDetail2 = new InitiatorDetail();
		List<PendingNeftData> data = initiatorDetail.getInflowDetails().parallelStream().filter(a -> !Strings.isNullOrEmpty(a.getTransactionStatus().getStatus())).collect(Collectors.toList());
		log.info("data.size()===" + data.size());
		initiatorDetail2.setInflowDetails(data);
		initiatorDetail2.setTotalPendingTransactionsCount(initiatorDetail.getTotalPendingTransactionsCount());
		initiatorDetail2.setTotalTransactionsCount(initiatorDetail.getTotalTransactionsCount());
		log.info("initiatorDetail2===" + initiatorDetail2);
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		model.addAttribute("initiatorDetail", initiatorDetail2);

		// return "landingone";
		return "neft/landingauthdrinflow";
	}

	@GetMapping(value = "/viewDetail")
	public String viewNeftDetail(Model model, @RequestParam Long id) {
		//User loginUser = (User) this.getSession().getAttribute("loginuser");
		//if (loginUser != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Optional<PendingNeftData> batchOpt = neftInterface.findPendingNeftDataById(id);
			PendingNeftData batchDetail = batchOpt.isPresent() ? batchOpt.get() : new PendingNeftData();
			log.info("/viewDetail batchDetail.getMsgId == ",batchDetail.getMsgId());
			batchDetail.setAmountStr(neftInterface.formatAmount(batchDetail.getAmount()));
			List<NEFTBank> banks = neftInterface
					.findNeftBankBySortCode(batchDetail.getPresentingBankSortCode().trim());
			if(!banks.isEmpty()) {
				batchDetail.setBankName(banks.get(0).getBankName());
			}
			List<TransactionAuthorizerDetail> batchTadList = tadRepo.findByUploadSessionId(batchDetail.getMsgId().toString().concat("-").concat(batchDetail.getItemSequenceNo()));

			// DateTimeFormatter sdfUploadedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy
			// HH:mm");

			batchTadList.forEach(a -> {
				a.setRoleDisplay("Authorizer ".concat(a.getLevel().toString()));
				a.setDisplayUser(a.getUser() == null ? "" : a.getUser().getUserName());
				a.setDisplayStatus(a.getTransactionStatus() == null ? "" : a.getTransactionStatus().getDescription());
				a.setDisplayAuthorizeDate(a.getAuthorizeDate() == null ? "" : sdf.format(a.getAuthorizeDate()));
			});
			TransactionAuthorizerDetail tad = new TransactionAuthorizerDetail();
			// model.addAttribute("uploadDataList", uploadDataList);
			TransactionStatus tranStatus = baseInterface.getTransactionStatus("R");
			List<TransactionAuthorizerDetail> rejectedTransaction = batchTadList.parallelStream()
					.filter(a -> tranStatus.getStatus()
							.equals(a.getTransactionStatus() != null ? a.getTransactionStatus().getStatus() : ""))
					.collect(Collectors.toList());
			// tadRepo.findByUploadSessionIdAndTransactionStatus(batchDetail.getMsgIdStr(),
			// tranStatus);
			if (!rejectedTransaction.isEmpty()) {
				log.info("It's rejected already");
				tad.setMode("VIEW");
			} else {
				/*AuthorizerLevel al = loginUser.getAuthorizerLevel();
				// AuthorizerLevel al = alRepo.findByUser(loginUser);
				log.info("","al.getLevel()>>>>" + al.getLevel() == null ? 0L : al.getLevel());
				List<TransactionAuthorizerDetail> mytadList = batchTadList;//.parallelStream()
				//		.filter(a -> a.getLevel().compareTo(al.getLevel()) == 0).collect(Collectors.toList());
				if (!mytadList.isEmpty()) {
					TransactionAuthorizerDetail myTad = mytadList.get(0);
					// tadRepo.findByUploadSessionIdAndLevel(batchDetail.getMsgIdStr(),al.getLevel());
					log.info("myTad>>>>" + myTad);
					if (myTad != null && myTad.getTransactionStatus() != null) {
						tad.setMode("VIEW");
					}
				}*/

			}

			model.addAttribute("batchDetail", batchDetail);
			model.addAttribute("tadList", batchTadList);
			model.addAttribute("tad", tad);
		//} else {
		//	return "redirect:/";
		//}

		return "neft/neftbatchdetailsdrinflow";
	}

	@PostMapping(value = "/submitInflowRequestForApproval")
	public String submitInflowRequestForApproval(TransactionAuthorizerDetail authorizerDetail, ModelMap model) {
		// EntrustMultiFactorAuthImplService srv = new
		// EntrustMultiFactorAuthImplService();
		TokenAuthDTO arg0 = new TokenAuthDTO();
		arg0.setAppCode(entrustAppCode);
		arg0.setAppDesc(entrustAppDesc);
		arg0.setGroup(staffEntrustGroup);
		log.info("batchDetail.getToken() ==" + authorizerDetail.getToken());
		arg0.setTokenPin(authorizerDetail.getToken());
		arg0.setUserName((String) getSession().getAttribute("username"));
		// AuthResponseDTO res =
		// srv.getEntrustMultiFactorAuthImplPort().performTokenAuth(arg0);
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null) {
			AuthResponseDTO res = baseInterface.entrustValidation(arg0);

			if (res != null) {
				if (res.getRespCode() != 1) {
					log.info("token res.getRespMessage() ==" + res.getRespMessage());
					log.info("token res.getRespCode() ==" + res.getRespCode());
					log.info("token res.getRespMessageCode() ==" + res.getRespMessageCode());
					authorizerDetail.setTokenRespMessage(entrustTokenAuthFailed);
					InitiatorDetail initiatorDetail = baseInterface.getNeftInflowInitiatorDetails();
					model.addAttribute("initiatorDetail", initiatorDetail);
					return "neft/landingneftdrinflow";
				} else {

				}
			} else {
				InitiatorDetail initiatorDetail = baseInterface.getNeftInflowInitiatorDetails();
				model.addAttribute("initiatorDetail", initiatorDetail);
				return "neft/landingneftdrinflow";
			}

			Optional<PendingNeftData> batchDetailOpt = neftInterface.findPendingNeftDataById(authorizerDetail.getBatchId());
			if (batchDetailOpt.isPresent()) {
				PendingNeftData batchDetail = batchDetailOpt.get();
				batchDetail.setTransactionStatus(baseInterface.getTransactionStatus("E"));
				batchDetail.setInitiatorCommment(authorizerDetail.getComment());

				Long maxAuthLevel = 0l;
				if (!malList.isEmpty())
					maxAuthLevel = malList.get(0).getMaxLevel();
				List<TransactionAuthorizerDetail> tadList = new ArrayList<TransactionAuthorizerDetail>();

				for (Long i = 1L; i <= maxAuthLevel; i++) {
					TransactionAuthorizerDetail tad = new TransactionAuthorizerDetail();
					tad.setLevel(i);
					tad.setChannel("NEFT");
					tad.setUploadSessionId(batchDetail.getMsgId().toString().concat("-").concat(batchDetail.getItemSequenceNo()));
					TransactionAuthorizerDetail myTad = tadRepo.findByUploadSessionIdAndLevel(batchDetail.getMsgId().toString().concat("-").concat(batchDetail.getItemSequenceNo()),
							i);
					if (myTad == null) {
						tadList.add(tad);
					}
				}
				if (!tadList.isEmpty()) {
					tadRepo.saveAll(tadList);
					batchDetail.setInitiator(loginUser);
					neftInterface.savePendingNeftData(batchDetail);
				}

				InitiatorDetail initiatorDetail = baseInterface.getNeftInflowInitiatorDetails();
				initiatorDetail.setResponseMessage("Inflow Request Submitted Successfully for Approval");
				model.addAttribute("initiatorDetail", initiatorDetail);
			}
		} else {
			return "redirect:/";
		}
		return "neft/landingneftdrinflow";
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
		//if (loginUser != null) {
			// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
			// Successfully");
			Optional<PendingNeftData> batchOpt = neftInterface.findPendingNeftDataById(id);
			if (batchOpt.isPresent()) {
				PendingNeftData batchDetail = batchOpt.get();
				List<NEFTBank> banks = neftInterface
						.findNeftBankBySortCode(batchDetail.getPresentingBankSortCode().trim());
				if(!banks.isEmpty()) {
					batchDetail.setBankName(banks.get(0).getBankName());
				}
				batchDetail.setAmountStr(neftInterface.formatAmount(batchDetail.getAmount()));
				List<TransactionAuthorizerDetail> tadList = tadRepo.findByUploadSessionId(batchDetail.getMsgId().toString().concat("-").concat(batchDetail.getItemSequenceNo()));

				TransactionStatus tranStatus = baseInterface.getTransactionStatus("R");
				List<TransactionAuthorizerDetail> rejectedTransaction = tadList.parallelStream()
						.filter(a -> tranStatus.getStatus()
								.equals(a.getTransactionStatus() != null ? a.getTransactionStatus().getStatus() : ""))
						.collect(Collectors.toList());
				// tadRepo.findByUploadSessionIdAndTransactionStatus(batchDetail.getMsgIdStr(),
				// tranStatus);
				if (!rejectedTransaction.isEmpty()) {
					log.info("It's rejected already");
					tad.setMode("VIEW");
				} else {
					AuthorizerLevel al = loginUser.getAuthorizerLevel();
					// AuthorizerLevel al = alRepo.findByUser(loginUser);
					log.info("","al.getLevel()>>>>" + al.getLevel() == null ? 0L : al.getLevel());
					List<TransactionAuthorizerDetail> mytadList = tadList;//.parallelStream()
					//		.filter(a -> a.getLevel().compareTo(al.getLevel()) == 0).collect(Collectors.toList());
					if (!mytadList.isEmpty()) {
						TransactionAuthorizerDetail myTad = mytadList.get(0);
						// tadRepo.findByUploadSessionIdAndLevel(batchDetail.getMsgIdStr(),al.getLevel());
						log.info("myTad>>>>" + myTad);
						if (myTad != null && myTad.getTransactionStatus() != null) {
							tad.setMode("VIEW");
						}
					}

				}

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				// DateTimeFormatter sdfUploadedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy
				// HH:mm");
				List<ReturnReason> returnReasons = new ArrayList<ReturnReason>();
				ReturnReason defReason = new ReturnReason();
				defReason.setCode("");
				defReason.setDescription("Select Return Reason");
				returnReasons.add(defReason);
				returnReasons.addAll(getReturnReasons);

				tadList.forEach(a -> {
					a.setRoleDisplay("Authorizer ".concat(a.getLevel().toString()));
					a.setDisplayUser(a.getUser() == null ? "" : a.getUser().getUserName());
					a.setDisplayStatus(
							a.getTransactionStatus() == null ? "" : a.getTransactionStatus().getDescription());
					a.setDisplayAuthorizeDate(a.getAuthorizeDate() == null ? "" : sdf.format(a.getAuthorizeDate()));
				});
				model.addAttribute("tadList", tadList);
				// model.addAttribute("uploadDataList", uploadDataList);
				model.addAttribute("tad", tad);
				model.addAttribute("batchDetail", batchDetail);
				model.addAttribute("returnReasons", returnReasons);

				//InitiatorDetail initiatorDetail = baseInterface.getNeftInflowInitiatorDetails();
				//model.addAttribute("initiatorDetail", initiatorDetail);
			}
		//} else {
		//	return "redirect:/";
		//}

		return "neft/authorizationdrinflow";
	}

	@PostMapping(value = "/returnInflowRequest")
	public String returnInflowRequest(TransactionAuthorizerDetail authorizerDetail, ModelMap model) {
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

			Optional<PendingNeftData> batchDetailOpt = neftInterface
					.findPendingNeftDataById(authorizerDetail.getBatchId());
			PendingNeftData batchDetail = batchDetailOpt.isPresent() ? batchDetailOpt.get() : null;
			List<TransactionAuthorizerDetail> batchTadList = tadRepo.findByUploadSessionId(batchDetail.getMsgId().toString().concat("-").concat(batchDetail.getItemSequenceNo()));
			// AuthResponseDTO res =
			// srv.getEntrustMultiFactorAuthImplPort().performTokenAuth(arg0);
			AuthResponseDTO res = baseInterface.entrustValidation(arg0);
			NeftOutwardResponse rs = null;
			if (res != null) {
				if (res.getRespCode() != 1) {
					log.info("token res.getRespMessage() ==" + res.getRespMessage());
					log.info("token res.getRespCode() ==" + res.getRespCode());
					log.info("token res.getRespMessageCode() ==" + res.getRespMessageCode());
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
				log.info("batchTadList.size()====" + batchTadList.size());
				log.info("al.getLevel()====" + al.getLevel());
				List<TransactionAuthorizerDetail> tadList = batchTadList;//.parallelStream()
						//.filter(a -> a.getLevel().compareTo(al.getLevel()) == 0).collect(Collectors.toList());
				// tadRepo.findByUploadSessionIdAndLevel(batchDetail.getMsgIdStr(),
				// al.getLevel());
				NeftBatchReturnDetail returnDetail = this.getNeftBatchReturnDetail(batchDetail,authorizerDetail);
				rs = baseInterface.neftOutwardSubmitReturn(returnDetail);
				responseMessage = rs.getResponseMessage();
				log.info("tadList.size()====" + tadList.size());
				if (!tadList.isEmpty() && rs != null && "00".equals(rs.getResponseCode())) {
					TransactionAuthorizerDetail tad = tadList.get(0);
					tad.setAuthorizeDate(new Date());
					tad.setComment(authorizerDetail.getComment());
					tad.setTransactionStatus(ts);
					tad.setUser(loginUser);
					tad.setReturnReason(authorizerDetail.getReturnReason());

					tadRepo.save(tad);
				}
			}

			if (batchDetail != null && rs != null && "00".equals(rs.getResponseCode())) {
				// NeftBatchDetail batchDetail = batchDetailOpt.get();
				// batchDetail.setComment(authorizerDetail.getComment());
				batchDetail.setTransactionStatus(ts);
				batchDetail.setReturnReason(authorizerDetail.getReturnReason());
				batchDetail.setResponseCode(rs.getResponseCode());
				batchDetail.setResponseDescription(rs.getResponseMessage());

				neftInterface.savePendingNeftData(batchDetail);
			}
		} else {
			return "redirect:/";
		}
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		InitiatorDetail initiatorDetail = baseInterface.getNeftInflowInitiatorDetails();
		InitiatorDetail initiatorDetail2 = new InitiatorDetail();
		List<PendingNeftData> data = initiatorDetail.getInflowDetails().parallelStream().filter(a -> !Strings.isNullOrEmpty(a.getTransactionStatus().getStatus())).collect(Collectors.toList());
		initiatorDetail2.setInflowDetails(data);
		initiatorDetail2.setTotalPendingTransactionsCount(initiatorDetail.getTotalPendingTransactionsCount());
		initiatorDetail2.setTotalTransactionsCount(initiatorDetail.getTotalTransactionsCount());
		log.info("initiatorDetail2===" + initiatorDetail2);
		log.info("initiatorDetail===" + initiatorDetail);
		model.addAttribute("initiatorDetail", initiatorDetail2);
		//responseMessage = "Request was Rejected Successfully";
		model.addAttribute("responseMessage", responseMessage);

		return "neft/landingauthdrinflow";
	}

	private NeftBatchReturnDetail getNeftBatchReturnDetail(PendingNeftData data,TransactionAuthorizerDetail authDetail) {
		NeftBatchReturnDetail returnDetail = new NeftBatchReturnDetail();

		returnDetail.setAppid(appid);
		returnDetail.setBankCode(data.getBankCode());
		returnDetail.setDateDisplay(LocalDateTime.now().toString());
		returnDetail.setItemCount(data.getItemCount());
		returnDetail.setMsgId(data.getMsgId());
		returnDetail.setTotalValue(data.getTotalValue());
		returnDetail.setSettlementTimeDisplay(data.getSettlementTimeDisplay());
		OutwardNeftReturn returnItem = new OutwardNeftReturn();
		returnItem.setAccountNo(data.getAccountNo());
		returnItem.setAmount(data.getAmount());
		returnItem.setBankName("");
		returnItem.setCheckDigit("");
		List<CollectionType> colTypes = getCollectionTypes.stream().filter(a-> a.getCollectionType().equals(data.getCollectionType())).collect(Collectors.toList());
		log.info("colTypes.size()===="+colTypes.size());
		returnItem.setCollectionType(!colTypes.isEmpty()?colTypes.get(0).getReturnType() : data.getCollectionType());
		returnItem.setCurrency(data.getCurrency());
		returnItem.setExpiryDate("");
		returnItem.setInstrumentType(data.getInstrumentType());
		returnItem.setItemSequenceNo(data.getItemSequenceNo());
		returnItem.setPaymentStatus(data.getStatus());
		returnItem.setPresentmentDateDisplay(data.getPresentmentDateDisplay());
		returnItem.setPresentingBankSortCode(data.getPresentingBankSortCode());
		returnItem.setReturnReason(authDetail.getReturnReason().getCode());
		returnItem.setSerialNo(data.getSerialNo());
		returnItem.setSessionId("");
		returnItem.setSettlementTimeDisplay(data.getSettlementTimeDisplay());
		returnItem.setSortCode(data.getSortCode());
		returnItem.setTranCode(data.getTranCode());
		Set<OutwardNeftReturn> returnItems = new HashSet<OutwardNeftReturn>();
		returnItems.add(returnItem);
		returnDetail.setOutwardNeftReturns(returnItems);

		return returnDetail;
	}

	@PostMapping(value = "/authorizeInflowRequest")
	public String authorizeInflowRequest(TransactionAuthorizerDetail authorizerDetail, ModelMap model) {
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

			Optional<PendingNeftData> batchDetailOpt = neftInterface
					.findPendingNeftDataById(authorizerDetail.getBatchId());
			PendingNeftData batchDetail = batchDetailOpt.isPresent() ? batchDetailOpt.get() : null;

			if (res != null) {
				if (res.getRespCode() != 1) {
					log.info("token res.getRespMessage() ==" + res.getRespMessage());
					log.info("token res.getRespCode() ==" + res.getRespCode());
					log.info("token res.getRespMessageCode() ==" + res.getRespMessageCode());
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
			log.info("2nd factor passed");
			NeftOutwardResponse rs = null;
			final TransactionStatus tsApprove = baseInterface.getTransactionStatus("A");
			TransactionStatus ts = tsApprove;
			// List<User> userList = userRepo.findByUserName((String)
			// this.getSession().getAttribute("username"));
			List<TransactionAuthorizerDetail> batchTadList = tadRepo.findByUploadSessionId(batchDetail.getMsgId().toString().concat("-").concat(batchDetail.getItemSequenceNo()));
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
					InflowApprovalData transactions = new InflowApprovalData();
					transactions.setAppId(appid);
					List<PendingNeftData> transactionList = new ArrayList<PendingNeftData>();
					transactionList.add(batchDetail);
					transactions.setTransactionList(transactionList);
					//if (maxLevel.compareTo(1L) > 0 && !lowerLevelList.isEmpty()) {
						rs = baseInterface.submitNeftDebitApproval(transactions);
					/*} else {
						if (lowerLevel.isEmpty()) {
							rs = baseInterface.submitNeftDebitApproval(transactions);
						} else {
							updateData = "N";
							responseMessage = "Request cannot be authorized until other authorizer(s) level approve";
						}
					}*/
					if (rs != null) {
						if ("00".equals(rs.getResponseCode())) {
							ts = baseInterface.getTransactionStatus("P");
							// batchDetail.setBatchStatus("I");
						} else {
							updateData = "N";
						}
						// batchDetail.setResponseCode(rs.getResponseCode());
						// batchDetail.setResponseDescription(rs.getResponseMessage());
						responseMessage = rs.getResponseMessage();
					} else {
						if (!"N".equals(updateData)) {
							updateData = "N";
							responseMessage = "Request authorization Failed, Kindly Retry.";
						}
					}
				//}
				log.info("updateData===" + updateData);
				if (!"N".equals(updateData)) {
					log.info("batchTadList.size()====" + batchTadList.size());
					log.info("al.getLevel()====" + al.getLevel());
					List<TransactionAuthorizerDetail> tadList = batchTadList;//.parallelStream()
							//.filter(a -> a.getLevel().compareTo(al.getLevel()) == 0).collect(Collectors.toList());
					// tadRepo.findByUploadSessionIdAndLevel(batchDetail.getMsgIdStr(),
					// al.getLevel());
					log.info("tadList.size()====" + tadList.size());
					if (!tadList.isEmpty()) {
						TransactionAuthorizerDetail tad = tadList.get(0);
						tad.setAuthorizeDate(new Date());
						tad.setComment(authorizerDetail.getComment());
						tad.setTransactionStatus(ts);
						tad.setUser(loginUser);
						log.info("Entrying tad updateData ===" + updateData);
						tadRepo.save(tad);
					}
				}
			}
			if (!"N".equals(updateData) && rs != null) {
				// batchDetail.setComment(authorizerDetail.getComment());

				batchDetail.setTransactionStatus(ts);
				batchDetail.setResponseCode(rs.getResponseCode());
				batchDetail.setResponseDescription(rs.getResponseMessage());

				log.info("Entrying batchDetail updateData ===" + updateData);
				neftInterface.savePendingNeftData(batchDetail);
			}
		} else {
			return "redirect:/";
		}

		// responseMessage = "Request was Rejected Successfully";
		InitiatorDetail initiatorDetail = baseInterface.getNeftInflowInitiatorDetails();
		InitiatorDetail initiatorDetail2 = new InitiatorDetail();
		List<PendingNeftData> data = initiatorDetail.getInflowDetails().parallelStream().filter(a -> !Strings.isNullOrEmpty(a.getTransactionStatus().getStatus())).collect(Collectors.toList());
		initiatorDetail2.setInflowDetails(data);
		initiatorDetail2.setTotalPendingTransactionsCount(initiatorDetail.getTotalPendingTransactionsCount());
		initiatorDetail2.setTotalTransactionsCount(initiatorDetail.getTotalTransactionsCount());
		log.info("initiatorDetail2===" + initiatorDetail2);
		log.info("initiatorDetail===" + initiatorDetail);
		model.addAttribute("initiatorDetail", initiatorDetail2);
		model.addAttribute("responseMessage", responseMessage);
		return "neft/landingauthdrinflow";
	}

	@GetMapping(value = "/authorizerHome")
	public String authorizerHome(Model model) {
		String responseMessage = "";
		InitiatorDetail initiatorDetail = baseInterface.getNeftInflowInitiatorDetails();
		InitiatorDetail initiatorDetail2 = new InitiatorDetail();
		List<PendingNeftData> data = initiatorDetail.getInflowDetails().parallelStream().filter(a -> !Strings.isNullOrEmpty(a.getTransactionStatus().getStatus())).collect(Collectors.toList());
		initiatorDetail2.setInflowDetails(data);
		initiatorDetail2.setTotalPendingTransactionsCount(initiatorDetail.getTotalPendingTransactionsCount());
		initiatorDetail2.setTotalTransactionsCount(initiatorDetail.getTotalTransactionsCount());
		log.info("initiatorDetail2===" + initiatorDetail2);
		model.addAttribute("initiatorDetail", initiatorDetail2);
		model.addAttribute("responseMessage", responseMessage);
		return "neft/landingauthdrinflow";
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "neft/error";
	}

}
