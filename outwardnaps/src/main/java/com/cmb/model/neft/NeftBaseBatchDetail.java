/**
 * 
 */
package com.cmb.model.neft;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author waliu.faleye
 *
 */
@MappedSuperclass
public class NeftBaseBatchDetail {
	
	/**
	 * @param appid
	 * @param bankCode
	 * @param totalValue
	 * @param msgId
	 * @param date
	 * @param dateDisplay
	 * @param itemCount
	 */
	public NeftBaseBatchDetail(NeftBaseBatchDetail a) {
		super();
		this.appid = a.appid;
		this.bankCode = a.bankCode;
		this.totalValue = a.totalValue;
		this.msgId = Long.valueOf(a.msgIdStr);
		this.msgIdStr = a.msgIdStr;
		this.date = a.date;
		this.dateDisplay = a.dateDisplay;
		this.itemCount = a.itemCount;
	}
	public NeftBaseBatchDetail() {
		super();
	}

	@Column(name="app_id")
	private String appid;
	
	@Column(name="bank_code")
	private String bankCode;
	
	@Column(name="total_value")
	private BigDecimal totalValue;
	
	@JsonIgnore
	@Column(name="msg_id",unique=true)
	private String msgIdStr;
	
	@Transient
	private Long msgId;	

	@JsonIgnore
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime date;

	@Transient
	private String dateDisplay;
	
	@Column(name="item_count")
	private Long itemCount;
	
	@Transient
	private String settlementTimeDisplay;

	/**
	 * @return the appid
	 */
	@JsonProperty(value="appid")
	public String getAppid() {
		return appid;
	}

	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @return the bankCode
	 */
	@JsonProperty(value="BankCode")
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the totalValue
	 */
	@JsonProperty(value="TotalValue")
	public BigDecimal getTotalValue() {
		return totalValue;
	}

	/**
	 * @param totalValue the totalValue to set
	 */
	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	/**
	 * @return the msgId
	 */
	@JsonProperty(value="MsgID")
	public Long getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * @return the itemCount
	 */
	@JsonProperty(value="ItemCount")
	public Long getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * @return the dateDisplay
	 */
	@JsonProperty(value="Date")
	public String getDateDisplay() {
		return dateDisplay;
	}

	/**
	 * @param dateDisplay the dateDisplay to set
	 */
	public void setDateDisplay(String dateDisplay) {
		this.dateDisplay = dateDisplay;
	}
	/**
	 * @return the msgIdStr
	 */
	public String getMsgIdStr() {
		return msgIdStr;
	}
	/**
	 * @param msgIdStr the msgIdStr to set
	 */
	public void setMsgIdStr(String msgIdStr) {
		this.msgIdStr = msgIdStr;
	}/**
	 * @return the settlementTimeDisplay
	 */
	@JsonProperty(value="SettlementTime")
	public String getSettlementTimeDisplay() {
		return settlementTimeDisplay;
	}

	/**
	 * @param settlementTimeDisplay the settlementTimeDisplay to set
	 */
	public void setSettlementTimeDisplay(String settlementTimeDisplay) {
		this.settlementTimeDisplay = settlementTimeDisplay;
	}

}
