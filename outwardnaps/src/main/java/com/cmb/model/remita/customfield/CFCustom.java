package com.cmb.model.remita.customfield;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CFCustom {
	private String id;
	private String columnName;
	private String columnType;
	private boolean required;
	private List<CfValue> values;
	public CFCustom() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<CfValue> getValues() {
		return values;
	}
	public void setValues(List<CfValue> values) {
		this.values = values;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public boolean getRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	@Override
	public String toString() {
		return "CFCustom [id=" + id + ", columnName=" + columnName + ", columnType=" + columnType + ", required="
				+ required + ", values=" + values + "]";
	}
	

	
	

}
