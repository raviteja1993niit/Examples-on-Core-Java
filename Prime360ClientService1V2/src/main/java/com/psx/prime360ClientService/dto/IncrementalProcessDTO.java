package com.psx.prime360ClientService.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author sunny
 *
 * 23-Jun-2018
 */

public class IncrementalProcessDTO {
	private String psxBatchID;
	private String processType;
	private String sourceSystemName;
	private String EODTimeStamp;

	public String getPsxBatchID() {
		return psxBatchID;
	}

	public void setPsxBatchID(String psxBatchID) {
		this.psxBatchID = psxBatchID;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getSourceSystemName() {
		return sourceSystemName;
	}

	public void setSourceSystemName(String sourceSystemName) {
		this.sourceSystemName = sourceSystemName;
	}

	public String getEODTimeStamp() {
		return EODTimeStamp;
	}

	public void setEODTimeStamp(String eODTimeStamp) {
		EODTimeStamp = eODTimeStamp;
	}

	@Override
	public String toString() {
		return "IncrementalProcessBO [psxBatchID=" + psxBatchID + ", processType=" + processType + ", sourceSystemName="
				+ sourceSystemName + ", EODTimeStamp=" + EODTimeStamp + "]";
	}

	public String toJson() {
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
		Gson gson = gb.create();
		return gson.toJson(this);
	}

}
