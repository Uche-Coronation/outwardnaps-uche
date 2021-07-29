package com.cmb.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "sevenup")
public class SevenupConfig {

	private String performPayment;
	private String notify;
	private String saveInfo;
	private String notStatus;
	private String CustomerFee;
	private String SBCFee;
	private String ChannelName;
	private String SourceBankCode;
	private String CollectionBankCode;
	private String CollectionBankAccountCode;
	private String ProviderCode;
	private String validateUrl;
	
}
