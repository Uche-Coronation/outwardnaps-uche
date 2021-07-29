package com.cmb.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sevenup_payment")
public class SevenupPayment {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long paymentID;
	@Column
	private String customerName;
	@Column
	private String accountNumber;
	@Column
	private String customerCode;
	@Column
	private String amount;
	@Column
	private String invoiceNumber;
	@Column
	private String securityCode;
	@Column
	private String transactionParticulars;
	@Column
	private String customerRegionCode;
	@Column
	private String customerRegionName;
	@Column
	private String executionID;
	@Column
	private String status;
	@Column
	private Date initiationDate;
	@ManyToOne
	@JoinColumn(name = "initiatorID")
	private User initiator;
	@Column
	private Date authorizationDate;
	@ManyToOne
	@JoinColumn(name = "authorizerID")
	private User authorizer;
	
	public long getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(long paymentID) {
		this.paymentID = paymentID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getTransactionParticulars() {
		return transactionParticulars;
	}
	public void setTransactionParticulars(String transactionParticulars) {
		this.transactionParticulars = transactionParticulars;
	}
	public String getCustomerRegionCode() {
		return customerRegionCode;
	}
	public void setCustomerRegionCode(String customerRegionCode) {
		this.customerRegionCode = customerRegionCode;
	}
	public String getCustomerRegionName() {
		return customerRegionName;
	}
	public void setCustomerRegionName(String customerRegionName) {
		this.customerRegionName = customerRegionName;
	}
	public String getExecutionID() {
		return executionID;
	}
	public void setExecutionID(String executionID) {
		this.executionID = executionID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getInitiationDate() {
		return initiationDate;
	}
	public void setInitiationDate(Date initiationDate) {
		this.initiationDate = initiationDate;
	}
	public Date getAuthorizationDate() {
		return authorizationDate;
	}
	public void setAuthorizationDate(Date authorizationDate) {
		this.authorizationDate = authorizationDate;
	}
	public User getInitiator() {
		return initiator;
	}
	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}
	public User getAuthorizer() {
		return authorizer;
	}
	public void setAuthorizer(User authorizer) {
		this.authorizer = authorizer;
	}
	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;
	}
	
	
}
