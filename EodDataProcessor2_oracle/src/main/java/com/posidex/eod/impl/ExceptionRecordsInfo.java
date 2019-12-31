package com.posidex.eod.impl;

public class ExceptionRecordsInfo {

	private String eodBatchId;
	private String record;
	private String errorInfo;

	public String getEodBatchId() {
		return eodBatchId;
	}

	public void setEodBatchId(String eodBatchId) {
		this.eodBatchId = eodBatchId;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "ExceptionRecordsInfo [eodBatchId=" + eodBatchId + ", record="
				+ record + ", errorInfo=" + errorInfo + "]";
	}

}
