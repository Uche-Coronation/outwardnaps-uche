package com.cmb.model.remita.genrrr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"amountDue",
"rrr"
})
public class ResponseDatum {

@JsonProperty("amountDue")
private String amountDue;
@JsonProperty("rrr")
private String rrr;

@JsonProperty("amountDue")
public String getAmountDue() {
return amountDue;
}

@JsonProperty("amountDue")
public void setAmountDue(String amountDue) {
this.amountDue = amountDue;
}

@JsonProperty("rrr")
public String getRrr() {
return rrr;
}

@JsonProperty("rrr")
public void setRrr(String rrr) {
this.rrr = rrr;
}

@Override
public String toString() {
	return "ResponseDatum [amountDue=" + amountDue + ", rrr=" + rrr + "]";
}

}