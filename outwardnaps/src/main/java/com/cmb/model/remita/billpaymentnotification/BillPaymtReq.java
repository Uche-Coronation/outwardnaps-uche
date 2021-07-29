package com.cmb.model.remita.billpaymentnotification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(value={ "appid", "hashing" }, allowGetters= true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"rrr",
"incomeAccount",
"debittedAccount",
"paymentAuthCode",
"paymentChannel",
"tellerName",
"approval",
"comment",
"branchCode",
"amountDebitted",
"fundingSource",
"hash"
})
public class BillPaymtReq {
	@JsonProperty("appid")
	private String appid;
	@JsonProperty("hashing")
	private String hashing;
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
@JsonProperty("approval")
private String approval;
@JsonProperty("comment")
private String comment;
@JsonProperty("tellerName")
private String tellerName;
@JsonProperty("branchCode")
private String branchCode;
@JsonProperty("amountDebitted")
private String amountDebitted;
@JsonProperty("fundingSource")
private String fundingSource;
@JsonProperty("hash")
private String hash;


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
public String getAmountDebitted() {
return amountDebitted;
}

@JsonProperty("amountDebitted")
public void setAmountDebitted(String amountDebitted) {
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

public String getAppid() {
	return appid;
}

public String getHashing() {
	return hashing;
}

public String getApproval() {
	return approval;
}

public void setApproval(String approval) {
	this.approval = approval;
}

public String getComment() {
	return comment;
}

public void setComment(String comment) {
	this.comment = comment;
}

public void setAppid(String appid) {
	this.appid = appid;
}

public void setHashing(String hashing) {
	this.hashing = hashing;
}

@Override
public String toString() {
	return "BillPaymtReq [appid=" + appid + ", hashing=" + hashing + ", rrr=" + rrr + ", incomeAccount=" + incomeAccount
			+ ", debittedAccount=" + debittedAccount + ", paymentAuthCode=" + paymentAuthCode + ", paymentChannel="
			+ paymentChannel + ", approval=" + approval + ", comment=" + comment + ", tellerName=" + tellerName
			+ ", branchCode=" + branchCode + ", amountDebitted=" + amountDebitted + ", fundingSource=" + fundingSource
			+ ", hash=" + hash + "]";
}



}