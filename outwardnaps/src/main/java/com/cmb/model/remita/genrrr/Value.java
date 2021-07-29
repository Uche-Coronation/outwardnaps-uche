package com.cmb.model.remita.genrrr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"value",
"quntity",
"amount"
})
public class Value {

@JsonProperty("value")
private String value;
@JsonProperty("quntity")
private Integer quntity;
@JsonProperty("amount")
private Integer amount;

@JsonProperty("value")
public String getValue() {
return value;
}

@JsonProperty("value")
public void setValue(String value) {
this.value = value;
}

@JsonProperty("quntity")
public Integer getQuntity() {
return quntity;
}

@JsonProperty("quntity")
public void setQuntity(Integer quntity) {
this.quntity = quntity;
}

@JsonProperty("amount")
public Integer getAmount() {
return amount;
}

@JsonProperty("amount")
public void setAmount(Integer amount) {
this.amount = amount;
}

public Value() {
	super();
	// TODO Auto-generated constructor stub
}

@Override
public String toString() {
	return "Value [value=" + value + ", quntity=" + quntity + ", amount=" + amount + "]";
}

}
