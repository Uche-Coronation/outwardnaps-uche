package com.cmb.model.remita;

import com.cmb.model.remita.genrrr.CustomField;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"customFields",
"id",
"billidName",
"billId",
"merchantId",
"amount",
"servicetypeName",
"serviceType",
"payerPhone",
"currency",
"payerName",
"payerEmail",
"amountDue",
"initiatedUser",
"authorizedUser",
"authorizerCount",
"lastAuthorizerLevel",
"rrr",
"debittedAccount",
"requestTime",
"responseTime",
"responseCode",
"approvalStatus",
"paymentStatus",
"responseMsg"
})
@Entity
@Table(name="RRRGeneration_Detail")
public class RRRGenDetail {
	
@Column(name = "customFields", nullable = true )
@JsonProperty("customFields")
private ArrayList<String> customFields = null;
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("id")
private Long id;

@Column(name = "billidName", nullable = true )
@JsonProperty("billidName")
private String billidName;

@Column(name = "billId", nullable = true )
@JsonProperty("billId")
private String billId;

//merchantId
@Column(name = "merchantId", nullable = true )
@JsonProperty("merchantId")
private String merchantId;
@Column(name = "amount", nullable = false )
@JsonProperty("amount")
private BigDecimal amount;

@Column(name = "servicetypeName", nullable = true )
@JsonProperty("servicetypeName")
private String servicetypeName;

@Column(name = "serviceType", nullable = true )
@JsonProperty("serviceType")
private String serviceType;
@Column(name = "payerPhone", nullable = true )
@JsonProperty("payerPhone")
private String payerPhone;

@Column(name = "debittedAccount", nullable = true )
@JsonProperty("debittedAccount")
private String debittedAccount;

@Column(name = "currency", nullable = false )
@JsonProperty("currency")
private String currency;
@Column(name = "payerName", nullable = true )
@JsonProperty("payerName")
private String payerName;
@Column(name = "payerEmail", nullable = true )
@JsonProperty("payerEmail")
private String payerEmail;
@Column(name = "amountDue", nullable = true )
@JsonProperty("amountDue")
private String amountDue;
@Column(name = "initiatedUser", nullable = true )
@JsonProperty("initiatedUser")
private String initiatedUser;
@Column(name = "authorizedUser", nullable = true )
@JsonProperty("authorizedUser")
private String authorizedUser;

@Column(name = "authorizerCount", columnDefinition="integer default 0" )
@JsonProperty("authorizerCount")
private int authorizerCount;

@Column(name = "lastAuthorizerLevel", nullable = true )
@JsonProperty("lastAuthorizerLevel")
private Long lastAuthorizerLevel;
@Column(name = "rrr", nullable = true )
@JsonProperty("rrr")
private String rrr;
@Column(name = "requestTime", nullable = true )
@JsonProperty("requestTime")
private Date requestTime;
@Column(name = "responseTime", nullable = true )
@JsonProperty("responseTime")
private Date responseTime;
@Column(name = "responseCode", nullable = true )
@JsonProperty("responseCode")
private String responseCode;

//approvalStatus
@Column(name = "approvalStatus", nullable = true )
@JsonProperty("approvalStatus")
private String approvalStatus;

@Column(name = "paymentStatus", nullable = true )
@JsonProperty("paymentStatus")
private String paymentStatus;

@Column(name = "responseMsg", nullable = true )
@JsonProperty("responseMsg")
private String responseMsg;

@JsonProperty("customFields")
public ArrayList<String> getCustomFields() {
return customFields;
}

@JsonProperty("customFields")
public void setCustomFields(ArrayList<String> customFields) {
this.customFields = customFields;
}

@JsonProperty("id")
public Long getId() {
return id;
}

@JsonProperty("id")
public void setId(Long id) {
this.id = id;
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

@JsonProperty("billId")
public String getBillId() {
return billId;
}

@JsonProperty("billId")
public void setBillId(String billId) {
this.billId = billId;
}


public String getMerchantId() {
	return merchantId;
}

public void setMerchantId(String merchantId) {
	this.merchantId = merchantId;
}

@JsonProperty("amount")
public BigDecimal getAmount() {
return amount;
}

@JsonProperty("amount")
public void setAmount(BigDecimal amount) {
this.amount = amount;
}

@JsonProperty("payerPhone")
public String getPayerPhone() {
return payerPhone;
}

@JsonProperty("payerPhone")
public void setPayerPhone(String payerPhone) {
this.payerPhone = payerPhone;
}

@JsonProperty("currency")
public String getCurrency() {
return currency;
}

@JsonProperty("currency")
public void setCurrency(String currency) {
this.currency = currency;
}

@JsonProperty("payerName")
public String getPayerName() {
return payerName;
}

@JsonProperty("payerName")
public void setPayerName(String payerName) {
this.payerName = payerName;
}

@JsonProperty("payerEmail")
public String getPayerEmail() {
return payerEmail;
}

@JsonProperty("payerEmail")
public void setPayerEmail(String payerEmail) {
this.payerEmail = payerEmail;
}

@JsonProperty("amountDue")
public String getAmountDue() {
return amountDue;
}

@JsonProperty("amountDue")
public void setAmountDue(String amountDue) {
this.amountDue = amountDue;
}

@JsonProperty("initiatedUser")
public String getInitiatedUser() {
return initiatedUser;
}

@JsonProperty("initiatedUser")
public void setInitiatedUser(String initiatedUser) {
this.initiatedUser = initiatedUser;
}

@JsonProperty("authorizedUser")
public String getAuthorizedUser() {
return authorizedUser;
}

@JsonProperty("authorizedUser")
public void setAuthorizedUser(String authorizedUser) {
this.authorizedUser = authorizedUser;
}

@JsonProperty("rrr")
public String getRrr() {
return rrr;
}

@JsonProperty("rrr")
public void setRrr(String rrr) {
this.rrr = rrr;
}

@JsonProperty("requestTime")
public Date getRequestTime() {
return requestTime;
}

@JsonProperty("requestTime")
public void setRequestTime(Date requestTime) {
this.requestTime = requestTime;
}

@JsonProperty("responseTime")
public Date getResponseTime() {
return responseTime;
}

@JsonProperty("responseTime")
public void setResponseTime(Date responseTime) {
this.responseTime = responseTime;
}

@JsonProperty("responseCode")
public String getResponseCode() {
return responseCode;
}

@JsonProperty("responseCode")
public void setResponseCode(String responseCode) {
this.responseCode = responseCode;
}

@JsonProperty("responseMsg")
public String getResponseMsg() {
return responseMsg;
}

@JsonProperty("responseMsg")
public void setResponseMsg(String responseMsg) {
this.responseMsg = responseMsg;
}

public String getServiceType() {
	return serviceType;
}

public void setServiceType(String serviceType) {
	this.serviceType = serviceType;
}

public int getAuthorizerCount() {
	return authorizerCount;
}

public void setAuthorizerCount(int authorizerCount) {
	this.authorizerCount = authorizerCount;
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

public Long getLastAuthorizerLevel() {
	return lastAuthorizerLevel;
}

public void setLastAuthorizerLevel(Long lastAuthorizerLevel) {
	this.lastAuthorizerLevel = lastAuthorizerLevel;
}

public String getDebittedAccount() {
	return debittedAccount;
}

public void setDebittedAccount(String debittedAccount) {
	this.debittedAccount = debittedAccount;
}

@Override
public String toString() {
	return "RRRGenDetail [customFields=" + customFields + ", id=" + id + ", billidName=" + billidName + ", billId="
			+ billId + ", merchantId=" + merchantId + ", amount=" + amount + ", servicetypeName=" + servicetypeName
			+ ", serviceType=" + serviceType + ", payerPhone=" + payerPhone + ", debittedAccount=" + debittedAccount
			+ ", currency=" + currency + ", payerName=" + payerName + ", payerEmail=" + payerEmail + ", amountDue="
			+ amountDue + ", initiatedUser=" + initiatedUser + ", authorizedUser=" + authorizedUser
			+ ", authorizerCount=" + authorizerCount + ", lastAuthorizerLevel=" + lastAuthorizerLevel + ", rrr=" + rrr
			+ ", requestTime=" + requestTime + ", responseTime=" + responseTime + ", responseCode=" + responseCode
			+ ", approvalStatus=" + approvalStatus + ", paymentStatus=" + paymentStatus + ", responseMsg=" + responseMsg
			+ "]";
}













}