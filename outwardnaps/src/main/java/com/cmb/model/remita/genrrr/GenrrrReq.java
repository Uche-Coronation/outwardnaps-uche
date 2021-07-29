package com.cmb.model.remita.genrrr;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonIgnoreProperties(value={ "appid", "hash" }, allowGetters= true)
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
"customFields",
"billidName",
"billId",
"servicetypeName",
"serviceType",
"amount",
"payerPhone",
"currency",
"payerName",
"payerEmail",
"initiatedUser",
"authorizedUser"
})
public class GenrrrReq {

	@JsonProperty("appid")
	private String appid;
	@JsonProperty("hash")
	private String hash;
@JsonProperty("customFields")
private List<CustomField> customFields = null;
@JsonProperty("billidName")
private String billidName;
@JsonProperty("billId")
private String billId;
@JsonProperty("servicetypeName")
private String servicetypeName;
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

@JsonProperty("serviceType")
private String serviceType;
@JsonProperty("amount")
private BigDecimal amount;
@JsonProperty("payerPhone")
private String payerPhone;
@JsonProperty("currency")
private String currency;
@JsonProperty("payerName")
private String payerName;
@JsonProperty("payerEmail")
private String payerEmail;
@JsonProperty("initiatedUser")
private String initiatedUser;
@JsonProperty("authorizedUser")
private String authorizedUser;
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

public GenrrrReq() {
	super();
	// TODO Auto-generated constructor stub
}

public String getAppid() {
	return appid;
}

public String getHash() {
	return hash;
}

public String getInitiatedUser() {
	return initiatedUser;
}

public void setInitiatedUser(String initiatedUser) {
	this.initiatedUser = initiatedUser;
}

public String getAuthorizedUser() {
	return authorizedUser;
}

public void setAuthorizedUser(String authorizedUser) {
	this.authorizedUser = authorizedUser;
}

public void setAppid(String appid) {
	this.appid = appid;
}

public void setHash(String hash) {
	this.hash = hash;
}  // Arrays.toString(customFields.toArray())!= null?Arrays.toString(customFields.toArray()):"" 

public String getServiceType() {
	return serviceType;
}

public void setServiceType(String serviceType) {
	this.serviceType = serviceType;
}

@Override
public String toString() {
	return "GenrrrReq [appid=" + appid + ", hash=" + hash + ", customFields=" + customFields + ", billidName="
			+ billidName + ", billId=" + billId + ", servicetypeName=" + servicetypeName + ", serviceType="
			+ serviceType + ", amount=" + amount + ", payerPhone=" + payerPhone + ", currency=" + currency
			+ ", payerName=" + payerName + ", payerEmail=" + payerEmail + ", initiatedUser=" + initiatedUser
			+ ", authorizedUser=" + authorizedUser + "]";
}










}
