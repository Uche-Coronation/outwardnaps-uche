package com.cmb.model.remita.validaterequest.res;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"customFields",
"billId",
"amount",
"payerPhone",
"currency",
"payerName",
"payerEmail",
"status"
})
public class ResponseDatum {

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
@JsonProperty("status")
private String status;

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

@JsonProperty("status")
public String getStatus() {
return status;
}

@JsonProperty("status")
public void setStatus(String status) {
this.status = status;
}

@Override
public String toString() {
	return "ResponseDatum [customFields=" + customFields.toString() + ", billId=" + billId + ", amount=" + amount + ", payerPhone="
			+ payerPhone + ", currency=" + currency + ", payerName=" + payerName + ", payerEmail=" + payerEmail
			+ ", status=" + status + "]";
}

}
