package com.cmb.model.remita;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="AuthDetail")
public class AuthDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long	id;
	@Column(name = "RRR", nullable = false )
	private String	rrr;
	@Column(name = "billidName", nullable = true )
	private String	billidName;
	@Column(name = "Merchantid", nullable = false )
	private String	merchantid;
	@Column(name = "servicetypeName", nullable = false )
	private String	servicetypeName;
	@Column(name = "ServiceType", nullable = false )
	private String	serviceType;
	@Column(name = "Amount", nullable = false )
	private String	amount;
	@Column(name = "TranDate", nullable = false )
	private java.util.Date	tranDate;
	@Column(name = "ApprovalStatus", nullable = true )
	private String	approvalStatus;
	@Column(name = "PaymentStatus", nullable = true )
	private String	paymentStatus;		
	@Column(name = "PayersName", nullable = true )
	private String	payersName;
	@Column(name = "PayersEmail", nullable = true )
	private String	payersEmail;
	@Column(name = "PayersPhone", nullable = true )
	private String	payersPhone;
	@Column(name = "AmountDue", nullable = true )
	private String	amountDue;
	@Column(name = "Accounttodebit", nullable = true )
	private String	accounttodebit;
	@Column(name = "ResponseCode", nullable = true )
	private String	responseCode;
	@Column(name = "Iuser", nullable = true )
	private String	iuser;
	@Column(name = "Authuser", nullable = true )
	private String	authuser;
	@Column(name = "Authlevel", nullable = true )
	private String	authlevel;
	@Column(name = "Comment", nullable = true )
	private String	comment;
	@Column(name = "AuthlevelCount", nullable = true )
	private int	authlevelCount;
	@Column(name = "Iresponsetime", nullable = true )
	private java.util.Date	iresponsetime;
	@Column(name = "Authresponsetime", nullable = true )
	private java.util.Date	authresponsetime;
	public AuthDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRrr() {
		return rrr;
	}
	public void setRrr(String rrr) {
		this.rrr = rrr;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getServiceType() {
		return serviceType;
	}
	
	public String getBillidName() {
		return billidName;
	}
	public void setBillidName(String billidName) {
		this.billidName = billidName;
	}
	public String getServicetypeName() {
		return servicetypeName;
	}
	public void setServicetypeName(String servicetypeName) {
		this.servicetypeName = servicetypeName;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public java.util.Date getTranDate() {
		return tranDate;
	}
	public void setTranDate(java.util.Date tranDate) {
		this.tranDate = tranDate;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPayersName() {
		return payersName;
	}
	public void setPayersName(String payersName) {
		this.payersName = payersName;
	}
	public String getPayersEmail() {
		return payersEmail;
	}
	public void setPayersEmail(String payersEmail) {
		this.payersEmail = payersEmail;
	}
	public String getPayersPhone() {
		return payersPhone;
	}
	public void setPayersPhone(String payersPhone) {
		this.payersPhone = payersPhone;
	}
	public String getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(String amountDue) {
		this.amountDue = amountDue;
	}
	public String getAccounttodebit() {
		return accounttodebit;
	}
	public void setAccounttodebit(String accounttodebit) {
		this.accounttodebit = accounttodebit;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getIuser() {
		return iuser;
	}
	public void setIuser(String iuser) {
		this.iuser = iuser;
	}
	public String getAuthuser() {
		return authuser;
	}
	public void setAuthuser(String authuser) {
		this.authuser = authuser;
	}
	public String getAuthlevel() {
		return authlevel;
	}
	public void setAuthlevel(String authlevel) {
		this.authlevel = authlevel;
	}
	public int getAuthlevelCount() {
		return authlevelCount;
	}
	public void setAuthlevelCount(int authlevelCount) {
		this.authlevelCount = authlevelCount;
	}
	public java.util.Date getIresponsetime() {
		return iresponsetime;
	}
	public void setIresponsetime(java.util.Date iresponsetime) {
		this.iresponsetime = iresponsetime;
	}
	public java.util.Date getAuthresponsetime() {
		return authresponsetime;
	}
	public void setAuthresponsetime(java.util.Date authresponsetime) {
		this.authresponsetime = authresponsetime;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "AuthDetail [id=" + id + ", rrr=" + rrr + ", billidName=" + billidName + ", merchantid=" + merchantid
				+ ", servicetypeName=" + servicetypeName + ", serviceType=" + serviceType + ", amount=" + amount
				+ ", tranDate=" + tranDate + ", approvalStatus=" + approvalStatus + ", paymentStatus=" + paymentStatus
				+ ", payersName=" + payersName + ", payersEmail=" + payersEmail + ", payersPhone=" + payersPhone
				+ ", amountDue=" + amountDue + ", accounttodebit=" + accounttodebit + ", responseCode=" + responseCode
				+ ", iuser=" + iuser + ", authuser=" + authuser + ", authlevel=" + authlevel + ", comment=" + comment
				+ ", authlevelCount=" + authlevelCount + ", iresponsetime=" + iresponsetime + ", authresponsetime="
				+ authresponsetime + "]";
	}





	
	

}
