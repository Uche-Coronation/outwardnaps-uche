package com.cmb.model.remita.paymentstatus;

public class PaymtstatusReq {
	private String appid;
	private String hash;
	private String transactionid;
	public PaymtstatusReq() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	@Override
	public String toString() {
		return "PaymtstatusReq [appid=" + appid + ", hash=" + hash + ", transactionid=" + transactionid + "]";
	}
	

}
