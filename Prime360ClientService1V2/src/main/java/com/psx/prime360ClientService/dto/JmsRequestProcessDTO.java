package com.psx.prime360ClientService.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author sunny
 *
 * 23-Jun-2018
 */
public class JmsRequestProcessDTO {
	private String psxBatchID;
	private String processType;
	private String sourceSystemName;

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

	@Override
	public String toString() {
		return "JmsRequestProcessBO [psxBatchID=" + psxBatchID + ", processType=" + processType + ", sourceSystemName="
				+ sourceSystemName + "]";
	}

	public String toJson() {
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
		Gson gson = gb.create();
		return gson.toJson(this);
	}

}
