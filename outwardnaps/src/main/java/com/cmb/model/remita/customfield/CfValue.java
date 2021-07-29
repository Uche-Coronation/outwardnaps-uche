package com.cmb.model.remita.customfield;

public class CfValue {
	private String value;
	private int quantity;
	private double amount;
	public CfValue() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "CfValue [value=" + value + ", quantity=" + quantity + ", amount=" + amount + "]";
	}
	

}
