package com.psx.prime360ClientService.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author sunny
 *
 * 23-Jun-2018
 */
public class JMSEnqueueDTO {
	private String psxBatchID;
	private String processType;
	private String sourceSystemName;
	private String EODTimeStamp;
	private String probingComponent;
	private String probingIDs;
	private String requestInformation;
	private String queueName;

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

	public String getRequestInformation() {
		return requestInformation;
	}

	public void setRequestInformation(String requestInformation) {
		this.requestInformation = requestInformation;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@Override
	public String toString() {
		return "JMSEnqueueDTO [psxBatchID=" + psxBatchID + ", processType=" + processType + ", sourceSystemName="
				+ sourceSystemName + ", EODTimeStamp=" + EODTimeStamp + ", probingComponent=" + probingComponent
				+ ", probingIDs=" + probingIDs + ", requestInformation=" + requestInformation + ", queueName="
				+ queueName + "]";
	}

	public String toJson() {
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
		Gson gson = gb.create();
		return gson.toJson(this);
	}

}
