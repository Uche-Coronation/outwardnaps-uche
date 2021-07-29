package com.cmb.model.remita.billpaymentnotification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"rrr",
"totalAmount",
"balanceDue",
"paymentRef",
"paymentDate",
"debittedAccount",
"amountDebitted"
})
public class ResponseDatum {

@JsonProperty("rrr")
private String rrr;
@JsonProperty("totalAmount")
private double totalAmount;
@JsonProperty("balanceDue")
private double balanceDue;
@JsonProperty("paymentRef")
private String paymentRef;
@JsonProperty("paymentDate")
private String paymentDate;
@JsonProperty("debittedAccount")
private String debittedAccount;
@JsonProperty("amountDebitted")
private double amountDebitted;

@JsonProperty("rrr")
public String getRrr() {
return rrr;
}

@JsonProperty("rrr")
public void setRrr(String rrr) {
this.rrr = rrr;
}

@JsonProperty("totalAmount")
public double getTotalAmount() {
return totalAmount;
}

@JsonProperty("totalAmount")
public void setTotalAmount(double totalAmount) {
this.totalAmount = totalAmount;
}

@JsonProperty("balanceDue")
public double getBalanceDue() {
return balanceDue;
}

@JsonProperty("balanceDue")
public void setBalanceDue(double balanceDue) {
this.balanceDue = balanceDue;
}

@JsonProperty("paymentRef")
public String getPaymentRef() {
return paymentRef;
}

@JsonProperty("paymentRef")
public void setPaymentRef(String paymentRef) {
this.paymentRef = paymentRef;
}

@JsonProperty("paymentDate")
public String getPaymentDate() {
return paymentDate;
}

@JsonProperty("paymentDate")
public void setPaymentDate(String paymentDate) {
this.paymentDate = paymentDate;
}

@JsonProperty("debittedAccount")
public String getDebittedAccount() {
return debittedAccount;
}

@JsonProperty("debittedAccount")
public void setDebittedAccount(String debittedAccount) {
this.debittedAccount = debittedAccount;
}

@JsonProperty("amountDebitted")
public double getAmountDebitted() {
return amountDebitted;
}

@JsonProperty("amountDebitted")
public void setAmountDebitted(double amountDebitted) {
this.amountDebitted = amountDebitted;
}

@Override
public String toString() {
	return "ResponseDatum [rrr=" + rrr + ", totalAmount=" + totalAmount + ", balanceDue=" + balanceDue + ", paymentRef="
			+ paymentRef + ", paymentDate=" + paymentDate + ", debittedAccount=" + debittedAccount + ", amountDebitted="
			+ amountDebitted + "]";
}

}