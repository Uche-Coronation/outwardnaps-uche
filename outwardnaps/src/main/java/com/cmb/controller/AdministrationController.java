/**
 * 
 */
package com.cmb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cmb.interfaces.AuthorizerLevelRepository;
import com.cmb.interfaces.BatchDetailRepository;
import com.cmb.interfaces.MaximumAuthorizationLevelsRepository;
import com.cmb.interfaces.TransactionStatusRepository;
import com.cmb.interfaces.UploadRepository;
import com.cmb.interfaces.UserStatusRepository;
import com.cmb.model.AuthorizerLevel;
import com.cmb.model.MaximumAuthorizationLevels;
import com.cmb.model.User;
import com.cmb.model.UserRepository;
import com.cmb.model.UserRole;
import com.cmb.model.UserRoleRepository;
import com.cmb.model.UserStatus;
import com.cmb.services.ProcessServiceInterface;
import com.expertedge.entrustplugin.ws.AdminResponseDTO;

/**
 * @author waliu.faleye
 *
 */
@ControllerAdvice
@Controller
public class AdministrationController {

	private UserRepository userRepo;
	private UserRoleRepository userRoleRepo;
	private UserStatusRepository userStatusRepo;
	///private UploadRepository uploadRepository;
	//private BatchDetailRepository batchDetailRepo;
	//private TransactionStatusRepository transactionStatusRepo;
	private MaximumAuthorizationLevelsRepository maximumAuthorizationLevelsRepo;
	private AuthorizerLevelRepository alRepo;

	@Value("${entrust.user.creation.failed}")
	String entrustUserCreationFailed;
	
	@Autowired
	ProcessServiceInterface psi;

	public AdministrationController(UserRepository userRepo, UserRoleRepository userRoleRepo,
			UserStatusRepository userStatusRepo, BatchDetailRepository batchDetailRepo,
			UploadRepository uploadRepository, TransactionStatusRepository transactionStatusRepo,
			MaximumAuthorizationLevelsRepository maximumAuthorizationLevelsRepo,AuthorizerLevelRepository alRepo) {

		this.userRepo = userRepo;
		this.userRoleRepo = userRoleRepo;
		this.userStatusRepo = userStatusRepo;
		//this.batchDetailRepo = batchDetailRepo;
		//this.transactionStatusRepo = transactionStatusRepo;
		//this.uploadRepository = uploadRepository;
		this.maximumAuthorizationLevelsRepo = maximumAuthorizationLevelsRepo;
		this.alRepo = alRepo;
	}

	@PostMapping(value = "/updateUser")
	public String updateUser(Model model, User user) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		List<User> internalUsers = null;
		try {
			HttpSession session = attr.getRequest().getSession(true);
			User loginUser = (User) session.getAttribute("loginuser");
			//ProcessServices srv = new ProcessServices(batchDetailRepo, uploadRepository, transactionStatusRepo);
			user.setCreatedBy(loginUser.getUserName());
			user.setCreatedDate(new Date());
			Optional<UserRole> roleOpt = userRoleRepo.findById(user.getUserRoleId());
			user.setUserRole(roleOpt.isPresent()?roleOpt.get():new UserRole());
			
			if (user.getId() == null) {
				Optional<UserStatus> statusOpt = userStatusRepo.findById("A");
				user.setStatus(statusOpt.isPresent()?statusOpt.get():new UserStatus());
				AdminResponseDTO res = psi.createEntrustUser(user);
				if (res != null) {
					if (res.getRespCode() == 1 || res.getRespCode() == -1) {
						userRepo.save(user);
					} else {
						System.out.println("token res.getRespMessage() ==" + res.getRespMessage());
						System.out.println("token res.getRespCode() ==" + res.getRespCode());
						System.out.println("token res.getRespMessageCode() ==" + res.getRespMessageCode());
						// user.setErrorMessage(res.getRespMessage());
						String entrustErrMsg = entrustUserCreationFailed.replace("var1", user.getUserName());
						user.setErrorMessage(entrustErrMsg);
					}
				} else {
					user.setErrorMessage("There is an issue connecting to entrust. Contact Administrator.");
				}
			} else {
				Optional<UserStatus> statusOpt = userStatusRepo.findById(user.getStatusId());
				user.setStatus(statusOpt.isPresent()?statusOpt.get():new UserStatus());
				userRepo.save(user);
			}			
			//authorizer
			if(user.getUserRoleId() == 2) {
				AuthorizerLevel al = new AuthorizerLevel();
				AuthorizerLevel existAl = alRepo.findByUser(user);
				if(existAl == null) {
				al.setLevel(user.getLevel());
				al.setUser(user);
				alRepo.save(al);}else {existAl.setLevel(user.getLevel());alRepo.save(existAl);}
			}
			internalUsers = userRepo.findAll();
			internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));
			model.addAttribute("internalusers", internalUsers);
			model.addAttribute("user", user);
		} catch (Exception ex) {
			ex.printStackTrace();
			user.setErrorMessage("There is an issue please Contact Administrator.");
			model.addAttribute("user", user);
			internalUsers = userRepo.findAll();
			internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));
			model.addAttribute("internalusers", internalUsers);
		}
		return "rolemanagement";
	}

	@GetMapping(value = "/editUser")
	public String editUser(Model model, @RequestParam Long id) {
		List<User> internalUsers = null;
		User editUser = null;
		try {
			Optional<User> userOpt = userRepo.findById(id);
			editUser = userOpt.isPresent()?userOpt.get():new User();
			internalUsers = userRepo.findAll();
			// editUser.setm
			editUser.setUserRoleId(editUser.getUserRole().getId());
			editUser.setStatusId(editUser.getStatus().getStatus());
			AuthorizerLevel al = alRepo.findByUser(editUser);
			if(al != null) {
				editUser.setLevel(al.getLevel());
			}
			model.addAttribute("userRoleId", editUser.getUserRole().getId());
			model.addAttribute("statusId", editUser.getStatus().getStatus());
			model.addAttribute("mode", "edit");
			model.addAttribute("user", editUser);
			internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));
			model.addAttribute("internalusers", internalUsers);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (editUser == null)
				editUser = new User();
			internalUsers = userRepo.findAll();
			editUser.setErrorMessage("There is an issue please Contact Administrator.");
			model.addAttribute("user", editUser);
			internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));
			model.addAttribute("internalusers", internalUsers);
		}

		return "rolemanagement";
	}

	@GetMapping(value = "/deleteUser")
	public String deleteUser(Model model, @RequestParam Long id) {
		// UserRole userRole = null;
		List<User> internalUsers = null;
		User editUser = null;
		try {
			Optional<User> userOpt = userRepo.findById(id);
			editUser = userOpt.isPresent()?userOpt.get():new User();
			Optional<UserStatus> statusOpt = userStatusRepo.findById("D");
			editUser.setStatus(statusOpt.isPresent()?statusOpt.get():new UserStatus());
			editUser.setDeleteFlg("Y");
			userRepo.save(editUser);
			// userRole = customerImpl.findByRoleName("CUSTOMER");
			internalUsers = userRepo.findAll();
			model.addAttribute("user", editUser);
			internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));
			model.addAttribute("internalusers", internalUsers);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (editUser == null)
				editUser = new User();
			editUser.setErrorMessage("There is an issue please Contact Administrator.");
			model.addAttribute("user", editUser);
			internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));
			model.addAttribute("internalusers", internalUsers);
		}

		return "rolemanagement";
	}

	@GetMapping(value = "/editAdminUser")
	public String editAdminUser(Model model, @RequestParam Long id) {
		List<User> internalUsers = null;
		User editUser = null;
		try {
			Optional<User> userOpt = userRepo.findById(id);
			editUser = userOpt.isPresent()?userOpt.get():new User();
			// userRole = customerImpl.findByRoleName("CUSTOMER");
			// internalUsers = customerImpl.findByUserRoleNot(userRole);
			// userRole = customerImpl.findByRoleName("ADMIN");
			List<User> adminUsers = userRepo.findAll();
			// editUser.setm
			editUser.setUserRoleId(editUser.getUserRole().getId());
			editUser.setStatusId(editUser.getStatus().getStatus());
			model.addAttribute("userRoleId", editUser.getUserRole().getId());
			model.addAttribute("statusId", editUser.getStatus().getStatus());
			model.addAttribute("mode", "edit");
			model.addAttribute("user", editUser);
			model.addAttribute("adminusers", adminUsers);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (editUser == null)
				editUser = new User();
			internalUsers = userRepo.findAll();
			editUser.setErrorMessage("There is an issue please Contact Administrator.");
			model.addAttribute("user", editUser);
			internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));
			model.addAttribute("adminusers", internalUsers);
		}

		return "surolemanagement";
	}

	@GetMapping(value = "/deleteAdminUser")
	public String deleteAdminUser(Model model, @RequestParam Long id) {
		List<User> internalUsers = null;
		User editUser = null;
		try {
			Optional<User> userOpt = userRepo.findById(id);
			editUser = userOpt.isPresent()?userOpt.get():new User();
			userRepo.deleteById(id);
			// userRole = customerImpl.findByRoleName("CUSTOMER");
			// internalUsers = customerImpl.findByUserRoleNot(userRole);
			// userRole = customerImpl.findByRoleName("ADMIN");
			List<User> adminUsers = userRepo.findAll();
			model.addAttribute("user", editUser);
			model.addAttribute("adminusers", adminUsers);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (editUser == null)
				editUser = new User();
			editUser.setErrorMessage("There is an issue please Contact Administrator.");
			model.addAttribute("user", editUser);
			internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));
			model.addAttribute("adminusers", internalUsers);
		}

		return "surolemanagement";
	}

	@PostMapping(value = "/updateAdminUser")
	public String updateAdminUser(Model model, User user) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		List<User> internalUsers = null;
		try {
			HttpSession session = attr.getRequest().getSession(true);
			User loginUser = (User) session.getAttribute("loginuser");

			user.setCreatedBy(loginUser.getUserName());
			user.setCreatedDate(new Date());
			Optional<UserRole> roleOpt = userRoleRepo.findById(user.getUserRoleId());
			user.setUserRole(roleOpt.isPresent()?roleOpt.get():new UserRole());
			user.setPassword("00000000");
			if (user.getId() == null) {
				Optional<UserStatus> statusOpt = userStatusRepo.findById("A");
				user.setStatus(statusOpt.isPresent()?statusOpt.get():new UserStatus());
				//ProcessServices srv = new ProcessServices(batchDetailRepo, uploadRepository, transactionStatusRepo);
				AdminResponseDTO res = psi.createEntrustUser(user);
				if (res != null) {
					if (res.getRespCode() == 1 || res.getRespCode() == -1) {
						userRepo.save(user);
					} else {
						System.out.println("token res.getRespMessage() ==" + res.getRespMessage());
						System.out.println("token res.getRespCode() ==" + res.getRespCode());
						System.out.println("token res.getRespMessageCode() ==" + res.getRespMessageCode());
						// user.setErrorMessage(res.getRespMessage());
						String entrustErrMsg = entrustUserCreationFailed.replace("var1", user.getUserName());
						user.setErrorMessage(entrustErrMsg);
					}
				} else {
					user.setErrorMessage("There is an issue connecting to entrust. Contact Administrator.");
				}
			} else {
				Optional<UserStatus> statusOpt = userStatusRepo.findById(user.getStatusId());
				if(statusOpt.isPresent()) {
				user.setStatus(statusOpt.get());
				userRepo.save(user);
				}
			}
			internalUsers = userRepo.findAll();
			internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));
			model.addAttribute("adminusers", internalUsers);
			model.addAttribute("user", user);
		} catch (Exception ex) {
			ex.printStackTrace();
			user.setErrorMessage("There is an issue please Contact Administrator.");
			model.addAttribute("user", user);
			internalUsers = userRepo.findAll();
			internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));
			model.addAttribute("adminusers", internalUsers);
		}
		return "surolemanagement";
	}

	@ModelAttribute(value = "internalusers")
	public List<User> getInternalUsers() {
		// UserRole userRole = userRepo.findAll();
		List<User> internalUsers = userRepo.findAll();
		internalUsers.forEach(a -> a.setLevel(alRepo.findByUser(a)==null?0L:alRepo.findByUser(a).getLevel()));

		return internalUsers;
	}

	@ModelAttribute(value = "internaluserroles")
	public List<UserRole> getInternalUserRoles() {
		// UserRole userRole = customerImpl.findByRoleName("CUSTOMER");
		List<UserRole> internalUserRoles = userRoleRepo.findAll();

		return internalUserRoles;
	}

	@ModelAttribute(value = "adminusers")
	public List<User> getAdminUsers() {
		// UserRole userRole = customerImpl.findByRoleName("ADMIN");
		List<User> adminUsers = userRepo.findAll();

		return adminUsers;
	}

	@ModelAttribute(value = "adminuserroles")
	public List<UserRole> getAdminUserRoles() {
		// UserRole userRole = customerImpl.findByRoleName("ADMIN");
		// List<UserRole> internalUserRoles =
		// customerImpl.findByRoleNameNot("CUSTOMER");
		List<UserRole> adminUserRoles = new ArrayList<UserRole>();
		// adminUserRoles.add(userRole);

		return adminUserRoles;
	}

	@ModelAttribute(value = "status")
	public List<UserStatus> getStatus() {
		// UserRole userRole = customerImpl.findByRoleName("CUSTOMER");
		List<UserStatus> internalStatus = userStatusRepo.findAll();

		return internalStatus;
	}

	@ModelAttribute(value = "authorizerLevels")
	public List<Long> authorizerLevels() {
		MaximumAuthorizationLevels level = maximumAuthorizationLevelsRepo.findAll().get(0);
		List<Long> values = new ArrayList<Long>();
		for (Long i = 1L; i <= level.getMaxLevel(); i++) {
			values.add(i);
		}

		return values;
	}
}
