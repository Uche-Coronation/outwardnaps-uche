package com.cmb.model.remita.customfield;

public class CustomFieldReq {
	private String appid;
	private String hash;
	private String billid;
	
	public CustomFieldReq() {
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
	public String getBillid() {
		return billid;
	}
	public void setBillid(String billid) {
		this.billid = billid;
	}
	@Override
	public String toString() {
		return "CustomFieldReq [appid=" + appid + ", hash=" + hash + ", billid=" + billid + "]";
	}
	

}
