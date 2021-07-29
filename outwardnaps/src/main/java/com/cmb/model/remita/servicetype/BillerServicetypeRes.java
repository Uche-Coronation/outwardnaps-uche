package com.cmb.model.remita.servicetype;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"responseCode",
"responseData",
"responseMsg",
"appVersionCode"
})
public class BillerServicetypeRes {

@JsonProperty("responseCode")
private String responseCode;
@JsonProperty("responseData")
private List<List<ResponseDatum>> responseData = null;
@JsonProperty("responseMsg")
private String responseMsg;
@JsonProperty("appVersionCode")
private Object appVersionCode;

@JsonProperty("responseCode")
public String getResponseCode() {
return responseCode;
}

@JsonProperty("responseCode")
public void setResponseCode(String responseCode) {
this.responseCode = responseCode;
}

@JsonProperty("responseData")
public List<List<ResponseDatum>> getResponseData() {
return responseData;
}

@JsonProperty("responseData")
public void setResponseData(List<List<ResponseDatum>> responseData) {
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

@Override
public String toString() {
	return "Servicetype [responseCode=" + responseCode + ", responseData=" + responseData + ", responseMsg="
			+ responseMsg + ", appVersionCode=" + appVersionCode + "]";
}


}