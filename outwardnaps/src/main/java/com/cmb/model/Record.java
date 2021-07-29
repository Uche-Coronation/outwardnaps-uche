/**
 * 
 */
package com.cmb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author waliu.faleye
 *
 */
public class Record {
	ArrayList<UploadRequestData> reqDataList;

	/**
	 * @return the reqDataList
	 */
	public ArrayList<UploadRequestData> getReqDataList() {
		return reqDataList;
	}

	/**
	 * @param reqDataList
	 *            the reqDataList to set
	 */
	public void setReqDataList(ArrayList<UploadRequestData> reqDataList) {
		this.reqDataList = reqDataList;
	}

}
