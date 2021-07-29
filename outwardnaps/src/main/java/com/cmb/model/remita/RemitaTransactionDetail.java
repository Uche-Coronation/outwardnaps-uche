package com.cmb.model.remita;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"rrr",
"incomeAccount",
"debittedAccount",
"paymentAuthCode",
"paymentChannel",
"tellerName",
"branchCode",
"amountDebitted",
"fundingSource",
"hash",
"transactionId",
"totalAmount",
"balanceDue",
"paymentRef",
"responseCode",
"responseMsg",
"requestTime",
"responseTime"
})
@Entity
@Table(name="Remita_Transaction_Detail")
public class RemitaTransactionDetail {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@JsonProperty("id")
private Long id;
@JsonProperty("rrr")
private String rrr;
@JsonProperty("incomeAccount")
private String incomeAccount;
@JsonProperty("debittedAccount")
private String debittedAccount;
@JsonProperty("paymentAuthCode")
private String paymentAuthCode;
@JsonProperty("paymentChannel")
private String paymentChannel;
@JsonProperty("tellerName")
private String tellerName;
@JsonProperty("branchCode")
private String branchCode;
@JsonProperty("amountDebitted")
private Integer amountDebitted;
@JsonProperty("fundingSource")
private String fundingSource;
@JsonProperty("hash")
private String hash;
@JsonProperty("transactionId")
private String transactionId;
@JsonProperty("totalAmount")
private Integer totalAmount;
@JsonProperty("balanceDue")
private Integer balanceDue;
@JsonProperty("paymentRef")
private String paymentRef;
@JsonProperty("responseCode")
private String responseCode;
@JsonProperty("responseMsg")
private String responseMsg;
@JsonProperty("requestTime")
private Date requestTime;
@JsonProperty("responseTime")
private Date responseTime;

public Date getRequestTime() {
	return requestTime;
}

public void setRequestTime(Date requestTime) {
	this.requestTime = requestTime;
}

public Date getResponseTime() {
	return responseTime;
}

public void setResponseTime(Date responseTime) {
	this.responseTime = responseTime;
}

@JsonProperty("id")
public Long getId() {
return id;
}

@JsonProperty("id")
public void setId(Long id) {
this.id = id;
}

@JsonProperty("rrr")
public String getRrr() {
return rrr;
}

@JsonProperty("rrr")
public void setRrr(String rrr) {
this.rrr = rrr;
}

@JsonProperty("incomeAccount")
public String getIncomeAccount() {
return incomeAccount;
}

@JsonProperty("incomeAccount")
public void setIncomeAccount(String incomeAccount) {
this.incomeAccount = incomeAccount;
}

@JsonProperty("debittedAccount")
public String getDebittedAccount() {
return debittedAccount;
}

@JsonProperty("debittedAccount")
public void setDebittedAccount(String debittedAccount) {
this.debittedAccount = debittedAccount;
}

@JsonProperty("paymentAuthCode")
public String getPaymentAuthCode() {
return paymentAuthCode;
}

@JsonProperty("paymentAuthCode")
public void setPaymentAuthCode(String paymentAuthCode) {
this.paymentAuthCode = paymentAuthCode;
}

@JsonProperty("paymentChannel")
public String getPaymentChannel() {
return paymentChannel;
}

@JsonProperty("paymentChannel")
public void setPaymentChannel(String paymentChannel) {
this.paymentChannel = paymentChannel;
}

@JsonProperty("tellerName")
public String getTellerName() {
return tellerName;
}

@JsonProperty("tellerName")
public void setTellerName(String tellerName) {
this.tellerName = tellerName;
}

@JsonProperty("branchCode")
public String getBranchCode() {
return branchCode;
}

@JsonProperty("branchCode")
public void setBranchCode(String branchCode) {
this.branchCode = branchCode;
}

@JsonProperty("amountDebitted")
public Integer getAmountDebitted() {
return amountDebitted;
}

@JsonProperty("amountDebitted")
public void setAmountDebitted(Integer amountDebitted) {
this.amountDebitted = amountDebitted;
}

@JsonProperty("fundingSource")
public String getFundingSource() {
return fundingSource;
}

@JsonProperty("fundingSource")
public void setFundingSource(String fundingSource) {
this.fundingSource = fundingSource;
}

@JsonProperty("hash")
public String getHash() {
return hash;
}

@JsonProperty("hash")
public void setHash(String hash) {
this.hash = hash;
}

@JsonProperty("transactionId")
public String getTransactionId() {
return transactionId;
}

@JsonProperty("transactionId")
public void setTransactionId(String transactionId) {
this.transactionId = transactionId;
}

@JsonProperty("totalAmount")
public Integer getTotalAmount() {
return totalAmount;
}

@JsonProperty("totalAmount")
public void setTotalAmount(Integer totalAmount) {
this.totalAmount = totalAmount;
}

@JsonProperty("balanceDue")
public Integer getBalanceDue() {
return balanceDue;
}

@JsonProperty("balanceDue")
public void setBalanceDue(Integer balanceDue) {
this.balanceDue = balanceDue;
}

@JsonProperty("paymentRef")
public String getPaymentRef() {
return paymentRef;
}

@JsonProperty("paymentRef")
public void setPaymentRef(String paymentRef) {
this.paymentRef = paymentRef;
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

@Override
public String toString() {
return new ToStringBuilder(this).append("id", id).append("rrr", rrr).append("incomeAccount", incomeAccount).append("debittedAccount", debittedAccount).append("paymentAuthCode", paymentAuthCode).append("paymentChannel", paymentChannel).append("tellerName", tellerName).append("branchCode", branchCode).append("amountDebitted", amountDebitted).append("fundingSource", fundingSource).append("hash", hash).append("transactionId", transactionId).append("totalAmount", totalAmount).append("balanceDue", balanceDue).append("paymentRef", paymentRef).append("responseCode", responseCode).append("responseMsg", responseMsg).append("requestTime", requestTime).append("responseTime", responseTime).toString();
}

}