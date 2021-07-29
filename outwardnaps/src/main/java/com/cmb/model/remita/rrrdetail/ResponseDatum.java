package com.cmb.model.remita.rrrdetail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"rrr",
"amountDue",
"payerEmail",
"payerName",
"payerPhone",
"description",
"currency",
"type",
"acceptPartPayment",
"frequency",
"payerAccountNumber",
"maxNoOfDebits",
"startDate",
"endDate"
})
public class ResponseDatum {

@JsonProperty("rrr")
private String rrr;
@JsonProperty("amountDue")
private double amountDue;
@JsonProperty("payerEmail")
private String payerEmail;
@JsonProperty("payerName")
private String payerName;
@JsonProperty("payerPhone")
private String payerPhone;
@JsonProperty("description")
private String description;
@JsonProperty("currency")
private String currency;
@JsonProperty("type")
private String type;
@JsonProperty("acceptPartPayment")
private Boolean acceptPartPayment;
@JsonProperty("frequency")
private String frequency;
@JsonProperty("payerAccountNumber")
private String payerAccountNumber;
@JsonProperty("maxNoOfDebits")
private Object maxNoOfDebits;
@JsonProperty("startDate")
private String startDate;
@JsonProperty("endDate")
private String endDate;

@JsonProperty("rrr")
public String getRrr() {
return rrr;
}

@JsonProperty("rrr")
public void setRrr(String rrr) {
this.rrr = rrr;
}

@JsonProperty("amountDue")
public double getAmountDue() {
return amountDue;
}

@JsonProperty("amountDue")
public void setAmountDue(double amountDue) {
this.amountDue = amountDue;
}

@JsonProperty("payerEmail")
public String getPayerEmail() {
return payerEmail;
}

@JsonProperty("payerEmail")
public void setPayerEmail(String payerEmail) {
this.payerEmail = payerEmail;
}

@JsonProperty("payerName")
public String getPayerName() {
return payerName;
}

@JsonProperty("payerName")
public void setPayerName(String payerName) {
this.payerName = payerName;
}

@JsonProperty("payerPhone")
public String getPayerPhone() {
return payerPhone;
}

@JsonProperty("payerPhone")
public void setPayerPhone(String payerPhone) {
this.payerPhone = payerPhone;
}

@JsonProperty("description")
public String getDescription() {
return description;
}

@JsonProperty("description")
public void setDescription(String description) {
this.description = description;
}

@JsonProperty("currency")
public String getCurrency() {
return currency;
}

@JsonProperty("currency")
public void setCurrency(String currency) {
this.currency = currency;
}

@JsonProperty("type")
public String getType() {
return type;
}

@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

@JsonProperty("acceptPartPayment")
public Boolean getAcceptPartPayment() {
return acceptPartPayment;
}

@JsonProperty("acceptPartPayment")
public void setAcceptPartPayment(Boolean acceptPartPayment) {
this.acceptPartPayment = acceptPartPayment;
}

@JsonProperty("frequency")
public String getFrequency() {
return frequency;
}

@JsonProperty("frequency")
public void setFrequency(String frequency) {
this.frequency = frequency;
}

@JsonProperty("payerAccountNumber")
public String getPayerAccountNumber() {
return payerAccountNumber;
}

@JsonProperty("payerAccountNumber")
public void setPayerAccountNumber(String payerAccountNumber) {
this.payerAccountNumber = payerAccountNumber;
}

@JsonProperty("maxNoOfDebits")
public Object getMaxNoOfDebits() {
return maxNoOfDebits;
}

@JsonProperty("maxNoOfDebits")
public void setMaxNoOfDebits(Object maxNoOfDebits) {
this.maxNoOfDebits = maxNoOfDebits;
}

@JsonProperty("startDate")
public String getStartDate() {
return startDate;
}

@JsonProperty("startDate")
public void setStartDate(String startDate) {
this.startDate = startDate;
}

@JsonProperty("endDate")
public String getEndDate() {
return endDate;
}

@JsonProperty("endDate")
public void setEndDate(String endDate) {
this.endDate = endDate;
}

@Override
public String toString() {
	return "ResponseDatum [rrr=" + rrr + ", amountDue=" + amountDue + ", payerEmail=" + payerEmail + ", payerName="
			+ payerName + ", payerPhone=" + payerPhone + ", description=" + description + ", currency=" + currency
			+ ", type=" + type + ", acceptPartPayment=" + acceptPartPayment + ", frequency=" + frequency
			+ ", payerAccountNumber=" + payerAccountNumber + ", maxNoOfDebits=" + maxNoOfDebits + ", startDate="
			+ startDate + ", endDate=" + endDate + "]";
}


}