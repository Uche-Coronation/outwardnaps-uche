package com.cmb.model;

import org.springframework.stereotype.Component;

import com.cmb.model.AcctId;
import com.cmb.model.TrnAmt;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Rec {

	@JsonProperty
	AcctId acctId;
	@JsonProperty
	String creditDebitFlg;
	@JsonProperty
	int serialNum;
	@JsonProperty
	TrnAmt trnAmt;
	@JsonProperty
	String trnParticulars;
	
	public String getTrnParticulars() {
		return trnParticulars;
	}
	public void setTrnParticulars(String trnParticulars) {
		this.trnParticulars = trnParticulars;
	}
	
	public AcctId getAcctId() {
		return acctId;
	}
	public void setAcctId(AcctId acctId) {
		this.acctId = acctId;
	}
	public String getCreditDebitFlg() {
		return creditDebitFlg;
	}
	public void setCreditDebitFlg(String creditDebitFlg) {
		this.creditDebitFlg = creditDebitFlg;
	}
	public int getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(int serialNum) {
		this.serialNum = serialNum;
	}
	public TrnAmt getTrnAmt() {
		return trnAmt;
	}
	public void setTrnAmt(TrnAmt trnAmt) {
		this.trnAmt = trnAmt;
	}
	
	@Override
	public String toString() {
		return "Rec [acctId=" + acctId + ", creditDebitFlg=" + creditDebitFlg + ", serialNum=" + serialNum + ", trnAmt="
				+ trnAmt + ", trnParticulars=" + trnParticulars + "]";
	}
	
}

