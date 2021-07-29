package com.cmb.model.remita.validaterequest;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(value={ "appid", "hash" }, allowGetters= true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"customFields",
"billId",
"amount",
"payerPhone",
"currency",
"payerName",
"payerEmail",
"cotyp",
"cfpid",
"cfcid",
"cfcamt",
"cfccount",
"cfcsubmit"
})

public class VdReq {
	@JsonProperty("appid")
	private String appid;
	@JsonProperty("hash")
	private String hash;
@JsonProperty("customFields")
private List<CustomField> customFields = null;
@JsonProperty("billId")
private String billId;
@JsonProperty("amount")
private Integer amount;
@JsonProperty("payerPhone")
private String payerPhone;
@JsonProperty("currency")
private String currency;
@JsonProperty("payerName")
private String payerName;
@JsonProperty("payerEmail")
private String payerEmail;
@JsonProperty("cotyp")
private String cotyp;
@JsonProperty("cfpid")
private String cfpid;
@JsonProperty("cfcid")
private String cfcid;
@JsonProperty("cfcamt")
private String cfcamt;
@JsonProperty("cfccount")
private String cfccount;
@JsonProperty("cfcsubmit")
private String cfcsubmit;



@JsonProperty("customFields")
public List<CustomField> getCustomFields() {
return customFields;
}

@JsonProperty("customFields")
public void setCustomFields(List<CustomField> customFields) {
this.customFields = customFields;
}

@JsonProperty("billId")
public String getBillId() {
return billId;
}

@JsonProperty("billId")
public void setBillId(String billId) {
this.billId = billId;
}

@JsonProperty("amount")
public Integer getAmount() {
return amount;
}

@JsonProperty("amount")
public void setAmount(Integer amount) {
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

public String getAppid() {
	return appid;
}

public String getHash() {
	return hash;
}

public VdReq() {
	super();
	// TODO Auto-generated constructor stub
}

public String getCotyp() {
	return cotyp;
}

public void setCotyp(String cotyp) {
	this.cotyp = cotyp;
}

public String getCfpid() {
	return cfpid;
}

public void setCfpid(String cfpid) {
	this.cfpid = cfpid;
}

public String getCfcid() {
	return cfcid;
}

public void setCfcid(String cfcid) {
	this.cfcid = cfcid;
}

public String getCfcamt() {
	return cfcamt;
}

public void setCfcamt(String cfcamt) {
	this.cfcamt = cfcamt;
}

public void setAppid(String appid) {
	this.appid = appid;
}

public void setHash(String hash) {
	this.hash = hash;
}

public String getCfccount() {
	return cfccount;
}

public void setCfccount(String cfccount) {
	this.cfccount = cfccount;
}

public String getCfcsubmit() {
	return cfcsubmit;
}

public void setCfcsubmit(String cfcsubmit) {
	this.cfcsubmit = cfcsubmit;  //  + Arrays.toString(customFields.toArray()) +
}

@Override
public String toString() {
	return "VdReq [appid=" + appid + ", hash=" + hash + ", customFields=" + customFields + ", billId=" + billId
			+ ", amount=" + amount + ", payerPhone=" + payerPhone + ", currency=" + currency + ", payerName="
			+ payerName + ", payerEmail=" + payerEmail + ", cotyp=" + cotyp + ", cfpid=" + cfpid + ", cfcid=" + cfcid
			+ ", cfcamt=" + cfcamt + ", cfccount=" + cfccount + ", cfcsubmit=" + cfcsubmit + "]";
}





}
