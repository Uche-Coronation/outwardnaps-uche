package com.cmb.model.remita.customfield;

import java.util.Arrays;
import java.util.List;

public class CFGCustom {
	private String id;
	private List<CfValue> values;
	public CFGCustom() {
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
	@Override
	public String toString() {
		return "CFGCustom [id=" + id + ", values=" + Arrays.toString(values.toArray()) + "]";
	}
	

}
