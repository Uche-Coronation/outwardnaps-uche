package com.cmb.model.remita.customfield;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"responseCode",
"responseData",
"responseMsg",
"appVersionCode",
"acceptPartPayment",
"fixedPrice",
"fixedAmount",
"currency"
})
public class Customfield {

@JsonProperty("responseCode")
private String responseCode;
@JsonProperty("responseData")
private List<ResponseDatum> responseData = null;
@JsonProperty("responseMsg")
private String responseMsg;
@JsonProperty("appVersionCode")
private Object appVersionCode;
@JsonProperty("acceptPartPayment")
private Boolean acceptPartPayment;
@JsonProperty("fixedPrice")
private Boolean fixedPrice;
@JsonProperty("fixedAmount")
private Integer fixedAmount;
@JsonProperty("currency")
private String currency;

@JsonProperty("responseCode")
public String getResponseCode() {
return responseCode;
}

@JsonProperty("responseCode")
public void setResponseCode(String responseCode) {
this.responseCode = responseCode;
}

@JsonProperty("responseData")
public List<ResponseDatum> getResponseData() {
return responseData;
}

@JsonProperty("responseData")
public void setResponseData(List<ResponseDatum> responseData) {
this.responseData = responseData;
}

@JsonProperty("responseMsg")
public String getResponseMsg() {
return responseMsg;
}

@JsonProperty("responseMsg")
public void setResponseMsg(String responseMsg) {
this.responseMsg = responseMsg;
}

@JsonProperty("appVersionCode")
public Object getAppVersionCode() {
return appVersionCode;
}

@JsonProperty("appVersionCode")
public void setAppVersionCode(Object appVersionCode) {
this.appVersionCode = appVersionCode;
}

@JsonProperty("acceptPartPayment")
public Boolean getAcceptPartPayment() {
return acceptPartPayment;
}

@JsonProperty("acceptPartPayment")
public void setAcceptPartPayment(Boolean acceptPartPayment) {
this.acceptPartPayment = acceptPartPayment;
}

@JsonProperty("fixedPrice")
public Boolean getFixedPrice() {
return fixedPrice;
}

@JsonProperty("fixedPrice")
public void setFixedPrice(Boolean fixedPrice) {
this.fixedPrice = fixedPrice;
}

@JsonProperty("fixedAmount")
public Integer getFixedAmount() {
return fixedAmount;
}

@JsonProperty("fixedAmount")
public void setFixedAmount(Integer fixedAmount) {
this.fixedAmount = fixedAmount;
}

@JsonProperty("currency")
public String getCurrency() {
return currency;
}

@JsonProperty("currency")
public void setCurrency(String currency) {
this.currency = currency;
}

@Override
public String toString() {
	return "Customfield [responseCode=" + responseCode + ", responseData=" + Arrays.toString(responseData.toArray()) + ", responseMsg="
			+ responseMsg + ", appVersionCode=" + appVersionCode + ", acceptPartPayment=" + acceptPartPayment
			+ ", fixedPrice=" + fixedPrice + ", fixedAmount=" + fixedAmount + ", currency=" + currency + "]";
}

}
