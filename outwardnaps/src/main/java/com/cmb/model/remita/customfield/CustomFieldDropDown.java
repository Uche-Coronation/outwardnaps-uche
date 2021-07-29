package com.cmb.model.remita.customfield;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"unitprice",
"fixedprice",
"accountid",
"description",
"code",
"id"
})
public class CustomFieldDropDown {

@JsonProperty("unitprice")
private String unitprice;
@JsonProperty("fixedprice")
private String fixedprice;
@JsonProperty("accountid")
private String accountid;
@JsonProperty("description")
private String description;
@JsonProperty("code")
private String code;
@JsonProperty("id")
private String id;

@JsonProperty("unitprice")
public String getUnitprice() {
return unitprice;
}

@JsonProperty("unitprice")
public void setUnitprice(String unitprice) {
this.unitprice = unitprice;
}

@JsonProperty("fixedprice")
public String getFixedprice() {
return fixedprice;
}

@JsonProperty("fixedprice")
public void setFixedprice(String fixedprice) {
this.fixedprice = fixedprice;
}

@JsonProperty("accountid")
public String getAccountid() {
return accountid;
}

@JsonProperty("accountid")
public void setAccountid(String accountid) {
this.accountid = accountid;
}

@JsonProperty("description")
public String getDescription() {
return description;
}

@JsonProperty("description")
public void setDescription(String description) {
this.description = description;
}

@JsonProperty("code")
public String getCode() {
return code;
}

@JsonProperty("code")
public void setCode(String code) {
this.code = code;
}

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@Override
public String toString() {
	return "CustomFieldDropDown [unitprice=" + unitprice + ", fixedprice=" + fixedprice + ", accountid=" + accountid
			+ ", description=" + description + ", code=" + code + ", id=" + id + "]";
}

}