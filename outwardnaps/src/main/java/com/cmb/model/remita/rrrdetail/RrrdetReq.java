package com.cmb.model.remita.rrrdetail;

public class RrrdetReq {
	private String appid;
	private String hash;
	private String rrr;
	public RrrdetReq() {
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
	public String getRrr() {
		return rrr;
	}
	public void setRrr(String rrr) {
		this.rrr = rrr;
	}
	@Override
	public String toString() {
		return "RrrdetReq [appid=" + appid + ", hash=" + hash + ", rrr=" + rrr + "]";
	}
	

}
