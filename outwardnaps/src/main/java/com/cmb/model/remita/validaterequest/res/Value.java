package com.cmb.model.remita.validaterequest.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"value",
"amount",
"quantity"
})
public class Value {

@JsonProperty("value")
private String value;
@JsonProperty("amount")
private Integer amount;
@JsonProperty("quantity")
private Integer quantity;

@JsonProperty("value")
public String getValue() {
return value;
}

@JsonProperty("value")
public void setValue(String value) {
this.value = value;
}

@JsonProperty("amount")
public Integer getAmount() {
return amount;
}

@JsonProperty("amount")
public void setAmount(Integer amount) {
this.amount = amount;
}

@JsonProperty("quantity")
public Integer getQuantity() {
return quantity;
}

@JsonProperty("quantity")
public void setQuantity(Integer quantity) {
this.quantity = quantity;
}

@Override
public String toString() {
	return "Value [value=" + value + ", amount=" + amount + ", quantity=" + quantity + "]";
}

}