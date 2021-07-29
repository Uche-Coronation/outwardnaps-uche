package com.cmb.model.remita.validaterequest.res;

import java.util.List;
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
private List<Value> values = null;

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("values")
public List<Value> getValues() {
return values;
}

@JsonProperty("values")
public void setValues(List<Value> values) {
this.values = values;
}

@Override
public String toString() {
	return "CustomField [id=" + id + ", values=" + values.toString() + "]";
}

}