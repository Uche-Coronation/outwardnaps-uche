/**
 * 
 */
package com.cmb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.cmb.model.neft.ReturnReason;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author waliu.faleye
 *
 */
@Table(name="transactionauthorizerdetail",uniqueConstraints = @UniqueConstraint(columnNames = { "UPLOAD_SESSION_ID", "level" }))
@Entity
public class TransactionAuthorizerDetail {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tran_level_generator")
	@SequenceGenerator(name = "tran_level_generator", allocationSize = 1, sequenceName = "tran_level_gen")
	@Id
	private Long id;

	@Column(name = "UPLOAD_SESSION_ID", nullable = false, length = 30)
	private String uploadSessionId;

	@Column(name = "AUTHORIZE_DATE", nullable = true, length = 50)
	private Date authorizeDate;

	@Transient
	private String displayAuthorizeDate;

	@ManyToOne
	@JoinColumn(nullable = true,name="transaction_status")
	private TransactionStatus transactionStatus;

	@Column(name = "comment", nullable = true)
	private String comment;

	@ManyToOne
	@JoinColumn(nullable = true)
	private User user;

	//@Column(name = "attribute", nullable = true)
	//private String attribute;

	@Column(name = "level", nullable = false)
	private Long level;

	@Transient
	private String roleDisplay;

	@Transient
	private String displayUser;

	@Transient
	private String displayStatus;

	@Transient
	private String token;

	@Transient
	private String tokenRespMessage;

	@Transient
	private Long batchId;

	@Transient
	private String mode;

	private String channel;
	
	@ManyToOne
	@JoinColumn(nullable=true,name="return_reason")
	private ReturnReason returnReason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUploadSessionId() {
		return uploadSessionId;
	}

	public void setUploadSessionId(String uploadSessionId) {
		this.uploadSessionId = uploadSessionId;
	}

	public Date getAuthorizeDate() {
		return authorizeDate;
	}

	public void setAuthorizeDate(Date authorizeDate) {
		this.authorizeDate = authorizeDate;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}*/

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getRoleDisplay() {
		return roleDisplay;
	}

	public void setRoleDisplay(String roleDisplay) {
		this.roleDisplay = roleDisplay;
	}

	public String getDisplayUser() {
		return displayUser;
	}

	public void setDisplayUser(String displayUser) {
		this.displayUser = displayUser;
	}

	public String getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	public String getDisplayAuthorizeDate() {
		return displayAuthorizeDate;
	}

	public void setDisplayAuthorizeDate(String displayAuthorizeDate) {
		this.displayAuthorizeDate = displayAuthorizeDate;
	}

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

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the returnReason
	 */
	public ReturnReason getReturnReason() {
		return returnReason;
	}

	/**
	 * @param returnReason the returnReason to set
	 */
	public void setReturnReason(ReturnReason returnReason) {
		this.returnReason = returnReason;
	}
}
