package com.cmb.model.remita.customfield;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"columnName",
"columnType",
"customFieldDropDown",
"columnLength",
"required"
})
public class ResponseDatum {

@JsonProperty("id")
private String id;
@JsonProperty("columnName")
private String columnName;
@JsonProperty("columnType")
private String columnType;
@JsonProperty("customFieldDropDown")
private List<CustomFieldDropDown> customFieldDropDown = null;
@JsonProperty("columnLength")
private String columnLength;
@JsonProperty("required")
private Boolean required;

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("columnName")
public String getColumnName() {
return columnName;
}

@JsonProperty("columnName")
public void setColumnName(String columnName) {
this.columnName = columnName;
}

@JsonProperty("columnType")
public String getColumnType() {
return columnType;
}

@JsonProperty("columnType")
public void setColumnType(String columnType) {
this.columnType = columnType;
}

@JsonProperty("customFieldDropDown")
public List<CustomFieldDropDown> getCustomFieldDropDown() {
return customFieldDropDown;
}

@JsonProperty("customFieldDropDown")
public void setCustomFieldDropDown(List<CustomFieldDropDown> customFieldDropDown) {
this.customFieldDropDown = customFieldDropDown;
}

@JsonProperty("columnLength")
public String getColumnLength() {
return columnLength;
}

@JsonProperty("columnLength")
public void setColumnLength(String columnLength) {
this.columnLength = columnLength;
}

@JsonProperty("required")
public Boolean getRequired() {
return required;
}

@JsonProperty("required")
public void setRequired(Boolean required) {
this.required = required;
}

@Override
public String toString() {
	return "ResponseDatum [id=" + id + ", columnName=" + columnName + ", columnType=" + columnType
			+ ", customFieldDropDown=" + customFieldDropDown + ", columnLength=" + columnLength + ", required="
			+ required + "]";
}

}