package com.psx.prime360ClientService.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author sunny
 *
 * 23-Jun-2018
 */
public class ProbFcbRequestProcessDTO {
	private String processType;
	private String sourceSystemName;
	private String probingComponent;
	private String probingIDs;

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

	public String getProbingComponent() {
		return probingComponent;
	}

	public void setProbingComponent(String probingComponent) {
		this.probingComponent = probingComponent;
	}

	public String getProbingIDs() {
		return probingIDs;
	}

	public void setProbingIDs(String probingIDs) {
		this.probingIDs = probingIDs;
	}

	@Override
	public String toString() {
		return "ProbFcbRequestProcessBO [processType=" + processType + ", sourceSystemName=" + sourceSystemName
				+ ", probingComponent=" + probingComponent + ", probingIDs=" + probingIDs + "]";
	}

	public String toJson() {
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
		Gson gson = gb.create();
		return gson.toJson(this);
	}
}
