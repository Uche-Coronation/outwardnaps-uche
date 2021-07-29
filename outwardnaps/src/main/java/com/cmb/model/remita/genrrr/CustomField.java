package com.cmb.model.remita.genrrr;

import java.util.List;

import com.cmb.model.remita.customfield.CfValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"values"
})
public class CustomField {

@JsonProperty("id")
private String id;
@JsonProperty("values")
private List<CfValue> values = null;

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("values")
public List<CfValue> getValues() {
return values;
}

@JsonProperty("values")
public void setValues(List<CfValue> values) {
this.values = values;
}

public CustomField() {
	super();
	// TODO Auto-generated constructor stub
}

@Override
public String toString() {
	return "CustomField [id=" + id + ", values=" + values + "]";
}

}
