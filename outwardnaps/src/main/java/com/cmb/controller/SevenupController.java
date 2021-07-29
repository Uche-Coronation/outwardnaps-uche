package com.cmb.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cmb.model.SevenupConfig;
import com.cmb.model.SevenupPayment;
import com.cmb.model.SevenupProperties;
import com.cmb.model.User;
import com.cmb.model.UserRepository;
import com.cmb.model.UserRoleRepository;
import com.cmb.services.SevenupService;

@Controller
public class SevenupController {
	
	Logger logger = LoggerFactory.getLogger(SevenupController.class);
	
	@Autowired
	SevenupService sevenupService;
	
	@Autowired
	SevenupConfig sevenupConfig;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;

	@GetMapping(value="/homeSevenup")
	public String homeSevenup(ModelMap model) {
		model.addAttribute("sevenupPayment", new SevenupPayment());
		model.addAttribute("sevenupProperties", sevenupConfig);
		return "sevenup";
	}
	
	/*
	 * @GetMapping(value="/deleteUser") public String deleteUser(@RequestParam
	 * String id, HttpServletRequest request, ModelMap model) { User user =
	 * userRepository.findById(Long.parseLong(id)).get(); user.setDeleteFlg("Y");
	 * user.setStatusId("D"); userRepository.save(user); return admSevenup(request,
	 * model); }
	 */
	
	public HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		return session;
	}
	
	/*
	 * @PostMapping(value="/addUser") public String addUser(ModelMap
	 * model, @RequestParam String username,
	 * 
	 * @RequestParam String password, @RequestParam String role, HttpServletRequest
	 * request) { User loginUser = (User) getSession().getAttribute("loginuser");
	 * User newuser = new User(); newuser.setCreatedBy(loginUser.getUserName());
	 * newuser.setUserName(username); newuser.setPassword(password);
	 * newuser.setUserRoleId(Long.parseLong(role));
	 * newuser.setUserRole(userRoleRepository.findById(Long.parseLong(role)).get());
	 * UserStatus userStatus = new UserStatus();
	 * userStatus.setDescription("Active"); userStatus.setStatus("A");
	 * newuser.setStatus(userStatus); newuser.setStatusId(userStatus.getStatus());
	 * newuser.setCreatedDate(Calendar.getInstance().getTime());
	 * userRepository.save(newuser); return admSevenup(request, model); }
	 */
	
	/*
	 * @GetMapping(value="/admSevenup") public String admSevenup( HttpServletRequest
	 * request, ModelMap model) { int page = 0; int size = 10;
	 * 
	 * if (request.getParameter("page") != null &&
	 * !request.getParameter("page").isEmpty()) { page =
	 * Integer.parseInt(request.getParameter("page")) - 1; }
	 * 
	 * if (request.getParameter("size") != null &&
	 * !request.getParameter("size").isEmpty()) { size =
	 * Integer.parseInt(request.getParameter("size")); }
	 * 
	 * model.addAttribute("users", userRepository.findAll(PageRequest.of(page,
	 * size))); return "svnupadm"; }
	 */
	
	@GetMapping(value="/authSevenup")
	public String authSevenup(ModelMap model) {
		model.addAttribute("payments", sevenupService.fetchAllInitiatedPayments());
		return "sevenupauth";
	} 
	
	@GetMapping(value="/sevenupLanding")
	public String sevenupLanding(HttpServletRequest request, ModelMap model) {
		int page = 0;
        int size = 10;
        
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
		Page<SevenupPayment> allPayments = sevenupService.fetchAllPayments(page,size);
		model.addAttribute("payments", allPayments);
		model.addAttribute("totalPayments", calculateTotalPayments(allPayments.getContent()));
		model.addAttribute("pendingPayments", calculatePendingPayments(allPayments.getContent()));
		return "svnuplanding";
	} 
	
	private BigDecimal calculatePendingPayments(List<SevenupPayment> allPayments) {
		BigDecimal pendingPayments = new BigDecimal(0.0);
		for(SevenupPayment payment : allPayments) {
			if(payment.getStatus().equalsIgnoreCase("initiated")) {
				pendingPayments = pendingPayments.add(new BigDecimal(payment.getAmount()));
			}
		}
		return pendingPayments;
	}

	private BigDecimal calculateTotalPayments(List<SevenupPayment> allPayments) {
		BigDecimal totalPayments = new BigDecimal(0.0);
		for(SevenupPayment payment : allPayments) {
			totalPayments = totalPayments.add(new BigDecimal(payment.getAmount()));
		}
		return totalPayments;
	}

	@PostMapping(value="/initiatePayment")
	public String initiatePayment(ModelMap model, HttpServletRequest request, @ModelAttribute("sevenupPayment") SevenupPayment sevenupPayment) {
		try {
			if((!sevenupPayment.getCustomerRegionCode().isEmpty() || sevenupPayment.getCustomerRegionCode() != null) && 
			(!sevenupPayment.getExecutionID().isEmpty() || sevenupPayment.getExecutionID() != null) &&
			(!sevenupPayment.getCustomerName().isEmpty() || sevenupPayment.getCustomerName() != null)) {
			User loginUser = (User) this.getSession().getAttribute("loginuser");
			sevenupPayment.setInitiator(loginUser);
			sevenupPayment.setStatus("initiated");
			sevenupPayment.setInitiationDate(java.sql.Date.valueOf(LocalDate.now()));
		    sevenupService.savePayment(sevenupPayment);
		    return sevenupLanding(request, model);
			}else {
				model.addAttribute("message", "Failed to validate Order");
				return "sevenupinfo";
			}
		}catch(Exception e) {
			model.addAttribute("message", "Error initiating payment, check logs");
			logger.info("Error registering payment: " + e.getMessage());
			return "sevenupinfo";
		}
		
	}
	
	@GetMapping(value="/declinePayment")
	public String declinePayment(ModelMap model, @RequestParam String id) {
		try {
		SevenupPayment sevenup = sevenupService.findPayment(Long.parseLong(id));
		User loginUser = (User) this.getSession().getAttribute("loginuser");
		sevenup.setAuthorizer(loginUser);
		sevenup.setStatus("declined");
		sevenup.setAuthorizationDate(java.sql.Date.valueOf(LocalDate.now()));
		sevenupService.savePayment(sevenup);
		return authSevenup(model);
		}catch(Exception e) {
			logger.info("Declining sevenup payment error: " + e.getMessage());
			model.addAttribute("message", "Error declining payment, please check logs");
			return "svnuperr";
		}
	}
	
	@GetMapping(value="/authorizePayment")
	public String authorizePayment(ModelMap model, @RequestParam String id) {
		try {
			User loginUser = (User) this.getSession().getAttribute("loginuser");
		 sevenupService.authPayment(id,loginUser);
		return authSevenup(model);
		}catch(Exception e) {
			logger.info("Authorizing sevenup payment error: " + e.getMessage());
			model.addAttribute("message", "Error authorizing payment, please check logs");
			return "svnuperr";
		}
	}
	

}
