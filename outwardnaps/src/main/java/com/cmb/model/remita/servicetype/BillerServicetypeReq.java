package com.cmb.model.remita.servicetype;

public class BillerServicetypeReq {
	private String appid;
	private String hash;
	private String billerid;
	public BillerServicetypeReq() {
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
	public String getBillerid() {
		return billerid;
	}
	public void setBillerid(String billerid) {
		this.billerid = billerid;
	}
	@Override
	public String toString() {
		return "BillerServicetypeReq [appid=" + appid + ", hash=" + hash + ", billerid=" + billerid + "]";
	}
	

}
