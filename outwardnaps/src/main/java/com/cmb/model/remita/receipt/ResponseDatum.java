package com.cmb.model.remita.receipt;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"description",
"label"
})
public class ResponseDatum implements Serializable {
	@JsonProperty("id")
	private String id;
	@JsonProperty("description")
	private String description;
	@JsonProperty("label")
	private String label;
	private final static long serialVersionUID = 2449590696584367397L;

	@JsonProperty("id")
	public String getId() {
	return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
	this.id = id;
	}

	@JsonProperty("description")
	public String getDescription() {
	return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
	this.description = description;
	}

	@JsonProperty("label")
	public String getLabel() {
	return label;
	}

	@JsonProperty("label")
	public void setLabel(String label) {
	this.label = label;
	}
}
