/**
 * 
 */
package com.cmb.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cmb.interfaces.AuthorizerLevelRepository;
import com.cmb.interfaces.BatchDetailRepository;
import com.cmb.interfaces.RRRTranDetailRepository;
import com.cmb.interfaces.RemitaTransactionDetailRepository;
import com.cmb.interfaces.TransactionStatusRepository;
import com.cmb.interfaces.UploadRepository;
import com.cmb.interfaces.UserStatusRepository;
import com.cmb.model.AcctId;
import com.cmb.model.AuthorizerLevel;
import com.cmb.model.InitiatorDetail;
import com.cmb.model.Notification;
import com.cmb.model.NotificationResponse;
import com.cmb.model.NotificationStatusResponse;
import com.cmb.model.PaymentInfo;
import com.cmb.model.Rec;
import com.cmb.model.Recs;
import com.cmb.model.RecsResponse;
import com.cmb.model.SevenupPayment;
import com.cmb.model.TrnAmt;
import com.cmb.model.User;
import com.cmb.model.UserRepository;
import com.cmb.model.UserRole;
import com.cmb.model.UserRoleRepository;
import com.cmb.model.UserStatus;
import com.cmb.model.remita.AuthDetail;
import com.cmb.model.remita.RRRGenDetail;
import com.cmb.model.remita.billpaymentnotification.BillPaymtRes;
import com.cmb.model.remita.customfield.CfValue;
import com.cmb.model.remita.genrrr.CustomField;
import com.cmb.model.remita.genrrr.GenrrrReq;
import com.cmb.model.remita.genrrr.GenrrrRes;
import com.cmb.model.remita.genrrr.ResponseDatum;
import com.cmb.model.remita.rrrdetail.RRRdetRes;
import com.cmb.model.remita.rrrdetail.RrrdetReq;
import com.cmb.services.BaseInterface;
import com.cmb.services.ProcessServiceInterface;
import com.cmb.services.RemitaService;
import com.cmb.services.RemitaServiceImpl;
import com.cmb.services.SampleAESCrypter;
import com.cmb.services.SevenupService;
import com.cmb.services.WebUtils;
import com.expertedge.entrustplugin.ws.AuthResponseDTO;
//import com.expertedge.entrustplugin.ws.EntrustMultiFactorAuthImplService;
import com.expertedge.entrustplugin.ws.TokenAuthDTO;

/**
 * @author waliu.faleye
 *
 */
@Controller
// @SessionAttributes(value = {"userName","userRole"}, types = {String.class,
// String.class})
@SessionAttributes(value = { "username", "userrole", "role", "useripaddress", "loginuser" })
public class HomeController implements ErrorController {
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final String PATH = "/error";
	private UserRepository userRepository;
	private UserRoleRepository userRoleRepository;
	// private RemitaTransactionDetailRepository remitaTransactionDetailRepository;
	// private RRRTranDetailRepository rrrTranDetailRepository;
	
	
	@Autowired
	RemitaService remitaService;
	
	@Autowired
	ProcessServiceInterface psi;

	@Autowired
	BaseInterface baseInterface;

	@Value("${spring.ldap.ip}")
	String ldapIp;
	@Value("${spring.ldap.port}")
	String ldapPort;
	@Value("${spring.ldap.base}")
	String ldapBase;
	@Value("${spring.ldap.secauth}")
	String ldapSecAuth;
	@Value("${entrust.app.code}")
	String entrustAppCode;

	@Value("${entrust.app.desc}")
	String entrustAppDesc;

	@Value("${staff.entrust.group}")
	String staffEntrustGroup;

	@Value("${entrust.token.authentication.failed}")
	String entrustTokenAuthFailed;
	private UserStatusRepository userStatusRepo;
	private AuthorizerLevelRepository alRepo;
	// private RemitaService remserv;

	@Autowired
	public HomeController(UserRepository userRepository, UserRoleRepository userRoleRepository,
			BatchDetailRepository batchDetailRepo, UploadRepository uploadRepository,
			TransactionStatusRepository transactionStatusRepo, UserStatusRepository userStatusRepo,
			AuthorizerLevelRepository alRepo, RemitaTransactionDetailRepository remitaTransactionDetailRepository,
			RRRTranDetailRepository rrrTranDetailRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.userStatusRepo = userStatusRepo;
		this.alRepo = alRepo;
		// this.remitaTransactionDetailRepository = remitaTransactionDetailRepository;
		// this.rrrTranDetailRepository=rrrTranDetailRepository;
		// this.remserv=remserv;
	}

	@GetMapping(value = "/detailLogin")
	public String index(Model model) {

		User user = new User();

		model.addAttribute("user", user);

		return "login";
	}

	@GetMapping(value = "/")
	public String outwardNapsLoginPage(User user) {

		return "login";
	}

	@GetMapping(value = "/loginHome")
	public String getHome(@Valid User user, BindingResult result, ModelMap model) {
		String userName = user.getUserName(); // get logged in username

		UserRole role = new UserRole();
		String roleDesc = "";
		List<User> loginUser = userRepository.findByUserName(userName);

		if (!loginUser.isEmpty()) {
			role = userRoleRepository.getOne(loginUser.get(0).getUserRole().getId());// .findById(loginUser.get(0).getUserRole());
			roleDesc = role.getRoleDescription();
			model.addAttribute("userrole", roleDesc);
			model.addAttribute("user", user);
			model.addAttribute("username", user.getUserName());
			model.addAttribute("role", roleDesc);
			model.addAttribute("useripaddress", WebUtils.getClientIp());
			System.out.println("roleDesc >>> " + roleDesc);
			System.out.println("user.getUserName() >>> " + user.getUserName());

			if (loginUser.get(0).getStatus().equals("A")) {
				// return "home-naps";
				return "landingone";

			} else {
				return "changepassword";
			}
		} else {
			return "index";
		}
	}

	@GetMapping(value = "/loginPage")
	public String index123(ModelMap model) {
		System.out.println("1234");
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}

	@GetMapping(value = "/loginTest")
	public String index1234(ModelMap model) {
		System.out.println("1234");
		// model.addAttribute("user", user);
		return "01authsuccess";
	}

	@GetMapping(value = "/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("errorMessage", "Invalid Credentials");
		return "login";
	}

	@GetMapping(value = "/login-access-denied")
	public String loginAccessDenied(ModelMap model) {
		model.addAttribute("errorMessage", "Login Access Denied");
		return "login";
	}

	@GetMapping(value = "/login")
	public String login(ModelMap model) {
		model.addAttribute("errorMessage", "Login Access Denied");
		return "login";
	}

	@GetMapping(value = "/detailLoginRedirect")
	public String index(@Valid User user, BindingResult result, ModelMap model) {
		System.out.println("detailLoginRedirect");
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		User loginUser = null;
		if (authentication != null) {
			System.out.println("authentication.getPrincipal()===" + authentication.getPrincipal());
			loginUser = (User) authentication.getPrincipal();

		}
		UserRole role = new UserRole();
		String roleDesc = "";
		role = loginUser.getUserRole();
		roleDesc = role.getRoleDescription();
		// user.setStatus(loginUser.getStatus());
		loginUser.setStatusId(loginUser.getStatus().getStatus());
		model.addAttribute("userrole", roleDesc);
		model.addAttribute("user", loginUser);
		model.addAttribute("loginuser", loginUser);
		model.addAttribute("username", loginUser.getUserName());
		model.addAttribute("role", roleDesc);
		System.out.println("roleDesc >>> " + roleDesc);
		System.out.println("user.getUserName() >>> " + loginUser.getUserName());

		
		return "login";
		
	}

	@PostMapping(value = "/loginDetail")
	public String loginDetail(@Valid User user, BindingResult result, ModelMap model) {
		String userName = user.getUserName(); // get logged in username

		User loginUser = (User) this.getSession().getAttribute("loginuser");
		if (loginUser != null && userName != null && userName.equalsIgnoreCase(loginUser.getUserName())) {
			UserRole role = loginUser.getUserRole();
			String roleDesc = role.getRoleDescription();
				// EntrustMultiFactorAuthImplService srv = new
				// EntrustMultiFactorAuthImplService();
				TokenAuthDTO arg0 = new TokenAuthDTO();
				arg0.setAppCode(entrustAppCode);
				arg0.setAppDesc(entrustAppDesc);
				arg0.setGroup(staffEntrustGroup);
				arg0.setTokenPin(user.getToken());
				arg0.setUserName(loginUser.getUserName());
				// AuthResponseDTO res =
				// srv.getEntrustMultiFactorAuthImplPort().performTokenAuth(arg0);
				AuthResponseDTO res = baseInterface.entrustValidation(arg0);
				System.out.println("token res.getRespMessage() ==" + res.getRespMessage());
				System.out.println("token res.getRespCode() ==" + res.getRespCode());

				if (res.getRespCode() != 1) {
					System.out.println("token res.getRespMessage() ==" + res.getRespMessage());
					System.out.println("token res.getRespCode() ==" + res.getRespCode());
					System.out.println("token res.getRespMessageCode() ==" + res.getRespMessageCode());
					user.setTokenRespMessage(entrustTokenAuthFailed);
					user.setStatusId(loginUser.getStatus().getStatus());

					// AuthorizerLevel al = new AuthorizerLevel();
					AuthorizerLevel existAl = alRepo.findByUser(loginUser);
					if (existAl != null && "AUTHORIZER".equalsIgnoreCase(roleDesc)) {
						// user.setLevel(existAl.getLevel());
						model.addAttribute("userrole", roleDesc.concat(" ").concat(existAl.getLevel().toString()));
					}
					model.addAttribute("user", user);
					/*
					 * ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
					 * transactionStatusRepo); InitiatorDetail initiatorDetail =
					 * prs.getInitiatorDetails(); System.out.println("initiatorDetail===" +
					 * initiatorDetail); model.addAttribute("initiatorDetail", initiatorDetail);
					 */
					return "login";

					// return role.getUrl();
				} else {

					// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
					// transactionStatusRepo);
					//System.out.println("before==="+LocalDateTime.now());
					//InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
					//System.out.println("after==="+LocalDateTime.now());
					//System.out.println("initiatorDetail===" + initiatorDetail);
					//model.addAttribute("initiatorDetail", initiatorDetail);
					AuthorizerLevel existAl = alRepo.findByUser(loginUser);
					if (existAl != null && "AUTHORIZER".equalsIgnoreCase(role.getRoleDescription())) {
						// user.setLevel(existAl.getLevel());
						model.addAttribute("userrole", roleDesc);
					}
					// return "landing";
					return "landingone";
					// return role.getUrl();

				}
			
		} else {
			return "login";
		}

	}

	@GetMapping(value = "/getChannelLanding")
	public String getChannelLanding(@Valid User user1, BindingResult result, ModelMap model) {
		String channelLandingPage = "";
		String userName = user1.getUserName();
		UserRole role = new UserRole();
		List<User> loginUser = userRepository.findByUserName(userName);
		User dbUser = null;
		String roleDesc = "";
		try {
			if (!loginUser.isEmpty()) {
				dbUser = loginUser.get(0);
				role = userRoleRepository.getOne(dbUser.getUserRole().getId());
				roleDesc = role.getRoleDescription();

			}

			// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
			// transactionStatusRepo);
			InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
			System.out.println("initiatorDetail===" + initiatorDetail);
			model.addAttribute("initiatorDetail", initiatorDetail);
			AuthorizerLevel existAl = alRepo.findByUser(dbUser);
			if (existAl != null && "AUTHORIZER".equalsIgnoreCase(role.getRoleDescription())) {
				// user.setLevel(existAl.getLevel());
				model.addAttribute("userrole", roleDesc.concat(" ").concat(existAl.getLevel().toString()));
			}
			channelLandingPage = role.getUrl();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return channelLandingPage;
	}

	@GetMapping(value = "/home")
	public String home(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		model.addAttribute("initiatorDetail", initiatorDetail);

		// return "landingone";
		return "landing";
	}

	@ExceptionHandler(HttpSessionRequiredException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "The session has expired")
	public String handleSessionExpired() {
		return "login";
	}
	
    @GetMapping(value = "/generateRemitaReceipt")
    public void downloadRemitaReceipt( HttpServletRequest request, 
                                     HttpServletResponse response,@RequestParam String rrr) 
    {
        String resp = remitaService.printremitareceipt(rrr);
                        if(resp.contains("File download successful")){
                                    logger.info(" receipt File download successful ");
                                    String[] respss=resp.split(";");              
                                    
        String dataDirectory = respss[1].trim();        //request.getServletContext().getRealPath("/WEB-INF/downloads/pdf/");
        logger.info(" getting the downloaded file path : "+dataDirectory);
        Path file = Paths.get(dataDirectory); //, fileName);
        if (Files.exists(file)) 
        {
            logger.info(" receipt File downloaded found ");
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename="+rrr);
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
              }// end if resp contains
    }

	@PostMapping(value = "/remitaWithRRR")
	public String remitaWithRRR(@RequestParam("rrr") String rrr, @Valid User user1, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		// InitiatorDetail initiatorDetail = psi.getInitiatorDetails();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		logger.info("Inside getRRRDetail request controller ");
		RrrdetReq rrrdetReq = new RrrdetReq();
		rrrdetReq.setAppid("cmb01");
		rrrdetReq.setHash("x");
		rrrdetReq.setRrr(rrr);
		RRRdetRes resp = new RRRdetRes();

		logger.info("Request : " + rrrdetReq.toString());

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		System.out.println("userrole==" + session.getAttribute("role"));
		String userrole = (String) session.getAttribute("role");
		User useraSess = (User) session.getAttribute("loginuser");
		String usera = null;
		if (useraSess != null)
			usera = useraSess.getUserName();
		logger.info("User Role : " + userrole);

		try {
			logger.info("user : " + usera);
			if (!(usera != null)) {
				model.addAttribute("errorMessage", "Session timed out, please login again to continue");
				return "login";

			}
			resp = remitaService.rrrdetails(rrrdetReq.getRrr(), user1.getUserName());
			logger.info("Response : " + resp.toString());
		} catch (Exception ex) {
			logger.info("Error in retrieving RRR Detail : " + ex.getMessage());
			ex.printStackTrace();
		}
		model.addAttribute("modal", "Y");
		model.addAttribute("rrrdetail", resp);
		model.addAttribute("rescode", resp.getResponseCode());
		model.addAttribute("resmsg", resp.getResponseMsg());

		// return "landingone";
		return "01withoutrrr";
	}

	@PostMapping(value = "/remitaPayInitiate")
	public String remitaPayInitiate(@RequestParam("rr") String rrr,
			@RequestParam("debittedAccount") String debittedAccount,
			@RequestParam("accttDebitted") String debittedAccountb,
			@RequestParam("amountDebitted") String amountDebitted, @RequestParam("userx") String iniuser,
			@RequestParam("token") String token, ModelMap model) {
		BillPaymtRes resp = new BillPaymtRes();
		try {
			// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
			// transactionStatusRepo);
			// InitiatorDetail initiatorDetail = prs.getInitiatorDetails();
			// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
			// Successfully");

			logger.info("Inside remitaPayInitiate request controller "); // debittedAccount
			logger.info("rrr " + rrr);
			logger.info("amountDebitted" + amountDebitted);
			RrrdetReq rrrdetReq = new RrrdetReq();
			rrrdetReq.setAppid("cmb01");
			rrrdetReq.setHash("x");
			rrrdetReq.setRrr(rrr);

			logger.info("Request : " + rrrdetReq.toString());
			logger.info("debittedAccount :  " + debittedAccount != null ? debittedAccount : debittedAccountb);
			logger.info("Request : " + rrrdetReq.toString());

			logger.info("user : " + iniuser);
			if (!(iniuser != null)) {
				model.addAttribute("errorMessage", "Session timed out, please login again to continue");
				return "login";

			}

			logger.info("token : " + token);
			resp = remitaService.billpaymentinitiate(rrr, debittedAccount != null ? debittedAccount : debittedAccountb,
					amountDebitted, iniuser, token);
			logger.info("Response : " + resp.toString());
			model.addAttribute("modal", "Y");
			model.addAttribute("rrrdetail", resp);
			if (resp.getResponseCode().equalsIgnoreCase("00")) {
				model.addAttribute("pmesg",
						"Your Payment (RRR:" + rrr + ") has been initiated Successfully and gone for verification");
			} else {
				model.addAttribute("pmesg", "Your Payment (RRR:" + rrr + ") initiation failed, please try again");
			}

			return "01success";
		} catch (Exception ex) {
			logger.info("Error in notifying payment : " + ex.getMessage());
			ex.printStackTrace();
			model.addAttribute("modal", "Y");
			model.addAttribute("rrrdetail", resp);
			model.addAttribute("pmesg", "Your Payment (RRR:" + rrr + ") initiation failed, please try again");

			return "01success";
		}

	}

	@PostMapping(value = "/remitaPayNotify")
	public String remitaPayNotify(@RequestParam("rr") String rrr,
			@RequestParam("debittedAccount") String debittedAccount,
			@RequestParam("amountDebitted") String amountDebitted, @RequestParam("userx") String iniuser,
			@RequestParam("token") String token, @RequestParam("approval") String approval,
			@RequestParam("comment") String comment, @Valid User user1, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		// InitiatorDetail initiatorDetail = psi.getInitiatorDetails();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		logger.info("Inside remitaPayNotify request controller ");
		logger.info("rrr " + rrr);
		logger.info("amountDebitted" + amountDebitted);
		RrrdetReq rrrdetReq = new RrrdetReq();
		rrrdetReq.setAppid("cmb01");
		rrrdetReq.setHash("x");
		rrrdetReq.setRrr(rrr);
		BillPaymtRes resp = new BillPaymtRes();

		logger.info("Request : " + rrrdetReq.toString());
		logger.info("debittedAccount : " + debittedAccount);
		logger.info("Request : " + rrrdetReq.toString());

		try {

			logger.info("user : " + iniuser);
			if (!(iniuser != null)) {
				model.addAttribute("errorMessage", "Session timed out, please login again to continue");
				return "login";

			}
			resp = remitaService.billpaymentnotify(rrr, debittedAccount, amountDebitted, iniuser, token, approval,
					comment);
			logger.info("Response : " + resp.toString());
			model.addAttribute("modal", "Y");
			model.addAttribute("rrrdetail", resp);
			// resp.getResponseData().get(0).getAmountDebitted();
			// resp.getResponseData().get(0).getBalanceDue()
			if (resp.getResponseCode().equalsIgnoreCase("00")) {
				if (resp.getResponseMsg().equalsIgnoreCase("PARTLY_APPROVED")) {
					model.addAttribute("pmesg",
							"Your Payment (RRR:" + rrr + ") has been Partly Approved and gone for Final Approval");
				} else {
					model.addAttribute("rrrdetail2", resp.getResponseData().get(0));
					model.addAttribute("pmesg", "Your Payment (RRR:" + rrr + ") has been Processed Successfully");
				}
			} else if (resp.getResponseCode().equalsIgnoreCase("09")) {
				model.addAttribute("pmesg", "Your Payment (RRR:" + rrr + ") has been Rejected");

			} else if (resp.getResponseCode().equalsIgnoreCase("51")) {
				model.addAttribute("pmesg", "Your Payment (RRR:" + rrr + ") has been Previously Rejected");

			} else if (resp.getResponseCode().equalsIgnoreCase("52")) {
				model.addAttribute("pmesg",
						"Your Payment (RRR:" + rrr + ") is already processed successfully, duplicate processing");

			} else if (resp.getResponseCode().equalsIgnoreCase("55")) {
				model.addAttribute("pmesg", "Your Payment (RRR:" + rrr + ")  Approval is Already Completed");

			} else {
				model.addAttribute("pmesg", "Your Payment (RRR:" + rrr + ") was not Processed Successfully");
			}

			return "01authsuccess";
		} catch (Exception ex) {
			logger.info("Error in notifying payment : " + ex.getMessage());
			ex.printStackTrace();
			model.addAttribute("modal", "Y");
			model.addAttribute("rrrdetail", resp);
			model.addAttribute("pmesg", "Your Payment (RRR:" + rrr + ") was not Processed Successfully");

			return "01authsuccess";
		}

	}

	@PostMapping(value = "/remitaGenRRR")
	public String remitaGenRRR(@RequestParam("billId") String billId, @RequestParam("serviceType") String serviceType,
			@RequestParam("amount") String amount, @RequestParam("payerPhone") String payerPhone,
			@RequestParam("currency") String currency, @RequestParam("payerName") String payerName,
			@RequestParam("payerEmail") String payerEmail,
			// @RequestParam("cotyp") String coltype,
			// @RequestParam("cfpid") String cfpid,
			// @RequestParam("cfcid") String cfcid,
			// @RequestParam("cfcamt") String cfcamt,
			@RequestParam("debittedAccount") String debittedAccount, @RequestParam("users") String iniuser,
			@RequestParam("cfccount") String cfccount, @RequestParam("cfcsubmit") String cfcsubmit,
			@RequestParam("servicetypeName") String servicetypeName, @RequestParam("billidName") String billidName,
			@Valid User user1, ModelMap model) {

		logger.info("Inside remitaGenRRR request controller ");
		try {
			GenrrrReq genrrrReq = new GenrrrReq();
			genrrrReq.setBillidName(billidName);
			genrrrReq.setBillId(billId);
			genrrrReq.setServicetypeName(servicetypeName);
			genrrrReq.setServiceType(serviceType);
			genrrrReq.setAmount(new BigDecimal(amount));
			genrrrReq.setPayerPhone(payerPhone);
			genrrrReq.setCurrency(currency);
			genrrrReq.setPayerName(payerName);
			genrrrReq.setPayerEmail(payerEmail);
			genrrrReq.setAppid("cmbrem01");
			genrrrReq.setHash("a");
			genrrrReq.setInitiatedUser(iniuser);
			/// genrrrReq.s
			// genrrrReq.s
			GenrrrRes resp = new GenrrrRes();
			logger.info("Inside generate RRR request controller ");
			logger.info("Request : " + genrrrReq.toString());
			// ----------------------------------------------------------------

			List<CustomField> cfgCustomList = new ArrayList<CustomField>();
			List<CustomField> cfgCustomLista = new ArrayList<CustomField>();
//			CustomField cfgCustom = new CustomField();
//			CustomField cfgCustoma = new CustomField();
//			List<CfValue> vallist2 = new ArrayList<CfValue>();
//			List<CfValue> vallist2a = new ArrayList<CfValue>();
//			List<CfValue> vallist2ab = new ArrayList<CfValue>();
			logger.info("cfccount : " + cfccount); // cfcsubmit
			logger.info("cfcsubmit : " + cfcsubmit);
			String ccntt = cfccount.equalsIgnoreCase("") ? "0" : cfccount;
			logger.info("ccntt : " + ccntt);
			int count = Integer.parseInt(ccntt != null ? ccntt : "0");
			logger.info("count : " + count);
			if (count > 0) {
				logger.info("All concatenated string : " + cfcsubmit);
				String[] allsubmitted = cfcsubmit.split(";");
				logger.info(" splitted string ; " + Arrays.toString(allsubmitted));
				String[] realsubmitted = null;

				CfValue[] vallist = new CfValue[allsubmitted.length];

				String[] pcfid = new String[allsubmitted.length];
				CustomField[] cfgcus = new CustomField[allsubmitted.length];
				for (int a = 0; a < allsubmitted.length; a++) {
					realsubmitted = allsubmitted[a].split(":");
					CfValue val = new CfValue();
					if ((realsubmitted[0]).trim().equalsIgnoreCase("DD")) {
						logger.info("inside DD Custom field");
						val.setValue(realsubmitted[2]);
						val.setQuantity(1);
						val.setAmount(0);

					} else if ((realsubmitted[0]).trim().equalsIgnoreCase("SL")) {
						logger.info("inside SL Custom field");
						val.setValue(realsubmitted[2]);
						val.setQuantity(1);
						val.setAmount(0);

					} else if ((realsubmitted[0]).trim().equalsIgnoreCase("SP")) {
						logger.info("inside SP Custom field");
						val.setValue(realsubmitted[2]);
						val.setQuantity(Integer.parseInt(realsubmitted[3]));
						val.setAmount(Double.parseDouble(realsubmitted[4]));

					} else if ((realsubmitted[0]).trim().equalsIgnoreCase("D")) {
						logger.info("inside D Custom field");
						val.setValue(realsubmitted[2]);
						val.setQuantity(0);
						val.setAmount(0);

					} else {
						logger.info("inside ALL Custom field");
						val.setValue(realsubmitted[2]);
						val.setQuantity(0);
						val.setAmount(Double.parseDouble(realsubmitted[4]));

					}
					vallist[a] = val;
					logger.info("val field:" + Arrays.toString(vallist));
					pcfid[a] = (realsubmitted[1]); // cfgCustomList
					// cfgcus[a].setValues(Arrays.asList(vallist));
					logger.info("parent cf id:" + Arrays.toString(pcfid));

				} // end for loop
				StringBuffer k = new StringBuffer();
				String ss;
				for (int b = 0; b < pcfid.length; b++) {
					// vallist2

					if (b == 0) {

						k.append(b);

					}
					if ((b > 0)) {
						if (pcfid[b].equalsIgnoreCase(pcfid[b - 1])) {

							k.append(":").append(b);
						} else {
							k.append(";").append(b);
						}
					}
				}
				logger.info("k :" + k);
				ss = k.toString();
				logger.info("ss :" + ss);

				String[] sss = ss.split(";");
				for (int g = 0; g < count; g++) {
					// List<CustomField> cfgCustomLista = new ArrayList<CustomField>();
					CustomField cfgCustom = new CustomField();
					CustomField cfgCustoma = new CustomField();
					List<CfValue> vallist2 = new ArrayList<CfValue>();
					List<CfValue> vallist2a = new ArrayList<CfValue>();
					List<CfValue> vallist2ab = new ArrayList<CfValue>();
					String[] ssss = sss[g].split(":");
					if (ssss.length == 1) {
						logger.info("length:" + ssss.length);
						logger.info("sss[g]:" + Integer.parseInt(sss[g]));
						vallist2a.add(vallist[Integer.parseInt(sss[g])]);
						logger.info("vallist2 :" + vallist2a.toString());
						cfgCustoma.setId(pcfid[g]);
						cfgCustoma.setValues(vallist2a);
						logger.info("cfgCustom :" + cfgCustoma.toString());
						cfgCustomLista.add(cfgCustoma);

						logger.info("cfgCustomList : " + Arrays.toString(cfgCustomLista.toArray()));
					} else {
						logger.info("length:" + ssss.length);

						for (String v : ssss) {
							logger.info("sss[g]:" + v);
							vallist2a.add(vallist[Integer.parseInt(v)]);
							logger.info("vallist2 :" + vallist2a.toString());
						}
						cfgCustoma.setId(pcfid[g]);
						cfgCustoma.setValues(vallist2a);
						logger.info("cfgCustom :" + cfgCustoma.toString());
						cfgCustomLista.add(cfgCustoma);
						logger.info("cfgCustomList : " + Arrays.toString(cfgCustomLista.toArray()));

					}
				}

				// val.setValue(customFieldDropDown.getId());
				// val.setQuantity(1);
				// val.setAmount(customFieldDropDown.getUnitprice());
				// logger.info("cfgCustomList : " + Arrays.toString(cfgCustomList.toArray()));
				genrrrReq.setCustomFields(cfgCustomLista);
			} else {
				CustomField cfgCustoma = new CustomField();
				List<CfValue> vallist2 = new ArrayList<CfValue>();
				cfgCustoma.setId("");
				cfgCustoma.setValues(vallist2);
				logger.info("cfgCustom :" + cfgCustoma.toString());
				cfgCustomLista.add(cfgCustoma);
				logger.info("cfgCustomList : " + Arrays.toString(cfgCustomLista.toArray()));
				genrrrReq.setCustomFields(cfgCustomLista);
			}
			// ----------------------------------------------------------------
			logger.info("About calling generate RRR method  ");
			try {
				resp = remitaService.generaterrr(genrrrReq, debittedAccount, iniuser);
				// resp=remitaServiceImpl.generaterrr(genrrrReq,rrrGenDetailRepo);
				logger.info("Response : " + resp.toString());
				model.addAttribute("modal", "Y");
				model.addAttribute("genrrr", resp);
				List<ResponseDatum> rdt = resp.getResponseData();
				String rrrr;
				String amtdue;
				if (rdt != null) {
					amtdue = rdt.get(0).getAmountDue() != null ? rdt.get(0).getAmountDue() : "0"; // note for empty
					rrrr = rdt.get(0).getRrr() != null ? rdt.get(0).getRrr() : "";
				} else {
					rrrr = null;
					amtdue = genrrrReq.getAmount().toString();
				}
				model.addAttribute("rrno", rrrr);
				model.addAttribute("amtdue", amtdue);
				model.addAttribute("requestx", genrrrReq);
				model.addAttribute("debittedAccount", debittedAccount);
				model.addAttribute("rescode", resp.getResponseCode());
				model.addAttribute("resmsg", resp.getResponseMsg());
			} catch (Exception ex) {
				logger.info("Error in generating RRR : " + ex.getMessage());
				ex.printStackTrace();
				com.cmb.model.remita.BillerRes bres = new com.cmb.model.remita.BillerRes();
				bres = remitaService.getBiller();
				logger.info(" Biller returned from service ");
				List<com.cmb.model.remita.ResponseDatum> dt = bres.getResponseData();
				model.addAttribute("billers", dt);
				model.addAttribute("modal", "N");
				model.addAttribute("genrrr", resp);
				model.addAttribute("requestx", genrrrReq);
				model.addAttribute("debittedAccount", debittedAccount);
				model.addAttribute("rescode", "98");
				model.addAttribute("resmsg", "failed, try again");
			}

		} catch (Exception s) {
			logger.info("Exception generating RRR : " + s.getMessage());
			logger.info("Exception generating RRR : " + s.getStackTrace());
			s.printStackTrace();
			com.cmb.model.remita.BillerRes bres = new com.cmb.model.remita.BillerRes();
			bres = remitaService.getBiller();
			logger.info(" Biller returned from service ");
			List<com.cmb.model.remita.ResponseDatum> dt = bres.getResponseData();
			model.addAttribute("billers", dt);
			model.addAttribute("modal", "N");
			// model.addAttribute("genrrr", resp);
			// model.addAttribute("requestx",genrrrReq);
			model.addAttribute("debittedAccount", debittedAccount);
			model.addAttribute("rescode", "98");
			model.addAttribute("resmsg", "failed, try again");
		}
		return "01remitapayment";

	}

	@GetMapping(value = "/initDetailRRR")
	public String initDetailRRR(Model model, @RequestParam Long id) {

		logger.info("Inside Initiator Detail RRR request controller with id " + id);

		RRRGenDetail resp = new RRRGenDetail();
		// logger.info("Inside generate RRR request controller " );
		// logger.info("Request : "+ genrrrReq.toString());

		try {
			// .findOne(id)
			resp = remitaService.getInitiatedRRRById(id);
			// resp=remitaServiceImpl.generaterrr(genrrrReq,rrrGenDetailRepo);
			logger.info("Response : " + resp.toString());
			model.addAttribute("modal", "Y");
			model.addAttribute("initrrr", resp);
			model.addAttribute("rescode", resp.getResponseCode());
			model.addAttribute("resmsg", resp.getResponseMsg());

			return "01remitainitdetail";
		} catch (Exception ex) {
			logger.info("Error in geting RRR Detail for Initiator : " + ex.getMessage());
			ex.printStackTrace();
			model.addAttribute("errorMessage", "Error retrieving transaction details");
			return "01remitainitdetail";
		}

	}

	@GetMapping(value = "/authDetailRRR")
	public String authDetailRRR(Model model, @RequestParam Long id) {

		logger.info("Inside authDetailRRR request controller with id " + id);

		// RRRGenDetail resp = new RRRGenDetail();
		AuthDetail resp = new AuthDetail();
		// logger.info("Inside generate RRR request controller " );
		// logger.info("Request : "+ genrrrReq.toString());

		try {
			// .findOne(id)
			// resp=remitaService.getInitiatedRRRById(id);
			resp = remitaService.getAuthRRRById(id);
			// resp.getDebittedAccount()
			// resp=remitaServiceImpl.generaterrr(genrrrReq,rrrGenDetailRepo);
			logger.info("Response : " + resp.toString());
			model.addAttribute("modal", "Y");
			model.addAttribute("authrrr", resp);
			// model.addAttribute("rescode", resp.getResponseCode());
			// model.addAttribute("resmsg", resp.getResponseMsg());

			return "01remitaauthdetail";
		} catch (Exception ex) {
			logger.info("Error in geting RRR Detail for Authorizer : " + ex.getMessage());
			ex.printStackTrace();
			model.addAttribute("errorMessage", "Error retrieving transaction details");
			return "01remitaauthdetail";
		}

	}

	// ************************
	@GetMapping(value = "/homeNAPS")
	public String homeNAPS(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
		//InitiatorDetail initiatorDetail = new InitiatorDetail();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		model.addAttribute("initiatorDetail", initiatorDetail);

		// return "landingone";
		return "landing";
	}

	@GetMapping(value = "/homeNAPSAUTH")
	public String homeNAPSAUTH(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		model.addAttribute("initiatorDetail", initiatorDetail);

		// return "landingone";
		return "landingauth";
	}

	@GetMapping(value = "/homeNAPSADM")
	public String homeNAPSADM(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		model.addAttribute("initiatorDetail", initiatorDetail);

		// return "landingone";
		return "rolemanagement";
	}

	@GetMapping(value = "/homeRemita")
	public String homeRemita(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		// InitiatorDetail initiatorDetail = prs.getInitiatorDetails();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		List<RRRGenDetail> rrrgen = remitaService.getAllInitiatedRRR();
		// remitaService.ge
		model.addAttribute("remitaInitiatorDetaillist", rrrgen);

		// return "landingone";
		return "01remitadashboard";
	}

	@GetMapping(value = "/remitaAuth")
	public String remitaAuth(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		// InitiatorDetail initiatorDetail = prs.getInitiatorDetails();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		// List<RRRGenDetail> rrrgen=remitaService.getAllInitiatedRRR();

		List<AuthDetail> rrrgen = remitaService.getAllAwaitAuth();

		// List<RRRGenDetail> rrrgena=remitaService.getAllInitiatedRRR();
		// remitaService.ge
		model.addAttribute("remitaInitiatorDetaillist", rrrgen);

		// return "landingone";
		return "01authremita";
	}

	@GetMapping(value = "/homeDashboard")
	public String homeDashboard(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		//InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		//model.addAttribute("initiatorDetail", initiatorDetail);

		return "landingone";
		// return "01remitadashboard";
	}

	@GetMapping(value = "/homeRemitaRRR")
	public String homeRemitaRRR(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		// InitiatorDetail initiatorDetail = psi.getInitiatorDetails();
		logger.info("Inside get Biller controller ");
		RemitaServiceImpl remitaServiceImpl = new RemitaServiceImpl();
		com.cmb.model.remita.BillerRes bres = new com.cmb.model.remita.BillerRes();
		bres = remitaService.getBiller();
		logger.info(" Biller returned from service ");
		List<com.cmb.model.remita.ResponseDatum> dt = bres.getResponseData();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		model.addAttribute("billers", dt); // ResponseDatum

		// return "landingone";
		return "01remitapayment";
	}

	@GetMapping(value = "/getBiller")
	public com.cmb.model.remita.BillerRes getBiller() {
		logger.info("Inside get Biller controller ");
		RemitaServiceImpl remitaServiceImpl = new RemitaServiceImpl();
		com.cmb.model.remita.BillerRes bil = new com.cmb.model.remita.BillerRes();
		bil = remitaService.getBiller();
		logger.info(" Biller returned from service ");
		return bil;
	}

	@GetMapping(value = "/getBillerServiceType")
	public com.cmb.model.remita.BillerRes getBillerServiceType() {
		logger.info("Inside get Biller controller ");
		// RemitaServiceImpl remitaServiceImpl = new RemitaServiceImpl();
		com.cmb.model.remita.BillerRes bil = new com.cmb.model.remita.BillerRes();
		bil = remitaService.getBiller();
		logger.info(" Biller returned from service ");
		return bil;
	}

	@GetMapping(value = "/homeRemitaNORRR")
	public String homeRemitaNORRR(@Valid User user1, BindingResult result, ModelMap model) {
		// ProcessServices prs = new ProcessServices(batchDetailRepo, uploadRepository,
		// transactionStatusRepo);
		InitiatorDetail initiatorDetail = baseInterface.getInitiatorDetails();
		// initiatorDetail.setResponseMessage("Bulk Transfer Request Submitted
		// Successfully");
		model.addAttribute("initiatorDetail", initiatorDetail);

		// return "landingone";
		return "01withoutrrr";
	}
	// ************************

	@Override
	public String getErrorPath() {
		return PATH;
	}

	@ModelAttribute("roles")
	public List<UserRole> getRoles() {
		List<UserRole> roles = new ArrayList<UserRole>();

		roles = userRoleRepository.findAll();
		return roles;
	}

	@ModelAttribute("statuses")
	public Map<String, String> getStatuses() {
		Map<String, String> statuses = new HashMap<String, String>();

		statuses.put("A", "Active");
		statuses.put("I", "Inactive");

		return statuses;
	}

	@GetMapping(value = "/user")
	public String userForm(@Valid @ModelAttribute(value = "user") User user, BindingResult result, ModelMap model) {
		List<UserRole> roles = new ArrayList<UserRole>();
		UserRole emptyRole = new UserRole();
		emptyRole.setRoleDescription("Please Select Role");
		roles.add(emptyRole);
		roles.addAll(userRoleRepository.findAll());
		model.addAttribute("roles", roles);
		return "user";

	}

	@PostMapping(value = "/saveUser")
	public String saveUser(@Valid User user, BindingResult result, ModelMap model) {
		SampleAESCrypter crypter = new SampleAESCrypter();
		Optional<UserStatus> statusOpt = userStatusRepo.findById("A");
		if (statusOpt.isPresent()) {
			user.setStatus(statusOpt.get());
			try {
				user.setPassword(crypter.encrypt(user.getPassword()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userRepository.save(user);
		}
		model.addAttribute("username", user);

		return "hello";
	}
	
	
	@GetMapping(value = "/UserRole")
	public String userRoleForm(@Valid UserRole userRole, BindingResult result, ModelMap model) {

		model.addAttribute("userrole", userRole);

		return "userrole";

	}

	@PostMapping(value = "/saveUserRole")
	public String createUserRole(@Valid UserRole userRole, BindingResult result, ModelMap model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		System.out.println("userrole==" + session.getAttribute("role"));
		String userrole = (String) session.getAttribute("role");

		UserRole role = userRoleRepository.save(userRole);
		model.addAttribute("userrole", userrole);
		if (role == null) {
			return "userrole";
		} else
			return "home-naps";
	}

	@PostMapping(value = "/changePassword")
	public String changePassword(@Valid User user, BindingResult result, ModelMap model) {

		System.out.println("user.getUserName() >>> " + user.getUserName());
		User registerdUser = userRepository.findByUserName(user.getUserName()).get(0);
		SampleAESCrypter crypter = new SampleAESCrypter();
		String changedPassword = "";
		try {
			if (!crypter.decrypt(registerdUser.getPassword()).equals(user.getCurrentPassword())) {
				return "changepassword";
			}
			if (!user.getNewPassword().equals(user.getConfirmNewPassword())) {
				return "changepassword";
			}

			changedPassword = crypter.encrypt(user.getNewPassword());
			registerdUser.setPassword(changedPassword);
			Optional<UserStatus> statusOpt = userStatusRepo.findById("A");
			if (statusOpt.isPresent()) {
				registerdUser.setStatus(statusOpt.get());

				userRepository.save(registerdUser);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "index";
	}

	public HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		return session;
	}
}
