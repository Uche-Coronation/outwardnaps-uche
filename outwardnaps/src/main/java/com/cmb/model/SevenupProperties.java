package com.cmb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SevenupProperties {

	@JsonProperty
	private String bankCode;
	@JsonProperty
	private String bankAccountCode;
	@JsonProperty
	private String providerCode;
	@JsonProperty
	private String validateUrl;
	
}
