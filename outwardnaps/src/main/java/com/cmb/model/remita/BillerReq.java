package com.cmb.model.remita;

public class BillerReq {
	private String appid;
	private String hash;
	public BillerReq() {
		super();
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
	@Override
	public String toString() {
		return "BillerReq [appid=" + appid + ", hash=" + hash + "]";
	}
	

}
