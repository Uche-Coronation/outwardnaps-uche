package com.cmb.model.remita.paymentstatus;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"responseCode",
"responseMsg",
"iResponseCode",
"iResponseMessage",
"appVersionCode",
"responseData"
})
public class PaymtstatusRes {

@JsonProperty("responseCode")
private String responseCode;
@JsonProperty("responseMsg")
private String responseMsg;
@JsonProperty("iResponseCode")
private Object iResponseCode;
@JsonProperty("iResponseMessage")
private Object iResponseMessage;
@JsonProperty("appVersionCode")
private Object appVersionCode;
@JsonProperty("responseData")
private List<ResponseDatum> responseData = null;

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

@JsonProperty("iResponseCode")
public Object getIResponseCode() {
return iResponseCode;
}

@JsonProperty("iResponseCode")
public void setIResponseCode(Object iResponseCode) {
this.iResponseCode = iResponseCode;
}

@JsonProperty("iResponseMessage")
public Object getIResponseMessage() {
return iResponseMessage;
}

@JsonProperty("iResponseMessage")
public void setIResponseMessage(Object iResponseMessage) {
this.iResponseMessage = iResponseMessage;
}

@JsonProperty("appVersionCode")
public Object getAppVersionCode() {
return appVersionCode;
}

@JsonProperty("appVersionCode")
public void setAppVersionCode(Object appVersionCode) {
this.appVersionCode = appVersionCode;
}

@JsonProperty("responseData")
public List<ResponseDatum> getResponseData() {
return responseData;
}

@JsonProperty("responseData")
public void setResponseData(List<ResponseDatum> responseData) {
this.responseData = responseData;
}

@Override
public String toString() {
	return "PaymtstatusRes [responseCode=" + responseCode + ", responseMsg=" + responseMsg + ", iResponseCode="
			+ iResponseCode + ", iResponseMessage=" + iResponseMessage + ", appVersionCode=" + appVersionCode
			+ ", responseData=" + responseData.toString() + "]";
}

}