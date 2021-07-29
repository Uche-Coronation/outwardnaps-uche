package com.cmb.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Notification {

	@JsonProperty
	private String CustomerCode;
	
	@JsonProperty
	private String CustomerName;
	
	@JsonProperty
	private String InvoiceNumber;
	
	@JsonProperty
	private String SecurityCode;
	
	@JsonProperty
	private String SourceBankCode;
	
	@JsonProperty
	private String ExecutionId;
	
	@JsonProperty
	private double SBCFee;
	
	@JsonProperty
	private double CustomerFee;
	
	@JsonProperty
	private double Amount;
	
	@JsonProperty
	private double TotalAmount;
	
	@JsonProperty
	private String Narration;
	
	@JsonProperty
	private String ChannelName;
	
	@JsonProperty
	private String CustomerAccountNumber;
	
	@JsonProperty
	private String PaymentReference;
	
	@JsonProperty
	private String TransactionApprovalDate;
	
	@JsonProperty
	private String ProviderCode;
	
	@JsonProperty
	private String CollectionBankCode;
	
	@JsonProperty
	private String CollectionBankAccountCode;
	
	@JsonProperty
	private String PaymentStatus;
	
	@JsonProperty
	private String Status;
	
	@JsonProperty
	private String UpdatedDate;
	
	@JsonProperty
	private String UpdatedBy;

	public String getCustomerCode() {
		return CustomerCode;
	}

	public void setCustomerCode(String customerCode) {
		CustomerCode = customerCode;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getInvoiceNumber() {
		return InvoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}

	public String getSecurityCode() {
		return SecurityCode;
	}

	public void setSecurityCode(String securityCode) {
		SecurityCode = securityCode;
	}

	public String getSourceBankCode() {
		return SourceBankCode;
	}

	public void setSourceBankCode(String sourceBankCode) {
		SourceBankCode = sourceBankCode;
	}

	public String getExecutionId() {
		return ExecutionId;
	}

	public void setExecutionId(String executionId) {
		ExecutionId = executionId;
	}

	public double getSBCFee() {
		return SBCFee;
	}

	public void setSBCFee(double sBCFee) {
		SBCFee = sBCFee;
	}

	public double getCustomerFee() {
		return CustomerFee;
	}

	public void setCustomerFee(double customerFee) {
		CustomerFee = customerFee;
	}

	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}

	public double getTotalAmount() {
		return TotalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		TotalAmount = totalAmount;
	}

	public String getNarration() {
		return Narration;
	}

	public void setNarration(String narration) {
		Narration = narration;
	}

	public String getChannelName() {
		return ChannelName;
	}

	public void setChannelName(String channelName) {
		ChannelName = channelName;
	}

	public String getCustomerAccountNumber() {
		return CustomerAccountNumber;
	}

	public void setCustomerAccountNumber(String customerAccountNumber) {
		CustomerAccountNumber = customerAccountNumber;
	}

	public String getPaymentReference() {
		return PaymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		PaymentReference = paymentReference;
	}

	public String getTransactionApprovalDate() {
		return TransactionApprovalDate;
	}

	public void setTransactionApprovalDate(String transactionApprovalDate) {
		TransactionApprovalDate = transactionApprovalDate;
	}

	public String getProviderCode() {
		return ProviderCode;
	}

	public void setProviderCode(String providerCode) {
		ProviderCode = providerCode;
	}

	public String getCollectionBankCode() {
		return CollectionBankCode;
	}

	public void setCollectionBankCode(String collectionBankCode) {
		CollectionBankCode = collectionBankCode;
	}

	public String getCollectionBankAccountCode() {
		return CollectionBankAccountCode;
	}

	public void setCollectionBankAccountCode(String collectionBankAccountCode) {
		CollectionBankAccountCode = collectionBankAccountCode;
	}

	public String getPaymentStatus() {
		return PaymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		PaymentStatus = paymentStatus;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		UpdatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}

	
}
