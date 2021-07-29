/**
 * 
 */
package com.cmb.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.cmb.model.neft.OutwardNeftItem;

/**
 * @author waliu.faleye
 *
 */
@Entity
@Table(name="USER",uniqueConstraints=@UniqueConstraint(columnNames = { "USER_NAME", "USER_ROLE_ID" }))
public class User {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "USER_NAME", length = 100, unique = true, nullable = false)
	private String userName;

	@Column(name = "PASSWORD", nullable = true)
	private String password;

	@Transient
	private String statusId;

	@Transient
	private Long userRoleId;

	@Transient
	private Long level;	
	
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(nullable=false)
	private UserStatus status;

	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "user_role_id",nullable=false)
	private UserRole userRole;
	
	@Transient
	@ManyToOne
	private UserRole userRoleData;
	
	@Transient
	private String currentPassword;
	
	@Transient
	private String newPassword;
	
	@Transient
	private String confirmNewPassword;

    @Transient
	private String token;

    @Transient
	private String tokenRespMessage;

    @Transient
	private String errorMessage;
	
	@Column(nullable = false,name="created_by")
	private String createdBy;
	
	@Column(nullable = false,name="created_date")
	private Date createdDate;

    @Column(name="delete_flg", length=1)
	private String deleteFlg = "N";
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private AuthorizerLevel authorizerLevel;
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenRespMessage() {
		return tokenRespMessage;
	}

	public void setTokenRespMessage(String tokenRespMessage) {
		this.tokenRespMessage = tokenRespMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}

	/**
	 * @param currentPassword the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmNewPassword
	 */
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	/**
	 * @param confirmNewPassword the confirmNewPassword to set
	 */
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	/**
	 * @return the userRoleData
	 */
	public UserRole getUserRoleData() {
		return userRoleData;
	}

	/**
	 * @param userRoleData the userRoleData to set
	 */
	public void setUserRoleData(UserRole userRoleData) {
		this.userRoleData = userRoleData;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	/**
	 * @return the authorizerLevel
	 */
	public AuthorizerLevel getAuthorizerLevel() {
		return authorizerLevel;
	}

	/**
	 * @param authorizerLevel the authorizerLevel to set
	 */
	public void setAuthorizerLevel(AuthorizerLevel authorizerLevel) {
		this.authorizerLevel = authorizerLevel;
	}

}
