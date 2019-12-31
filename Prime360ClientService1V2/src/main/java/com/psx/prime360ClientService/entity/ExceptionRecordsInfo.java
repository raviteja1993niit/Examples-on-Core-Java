package com.psx.prime360ClientService.entity;

public class ExceptionRecordsInfo {

	private String eodBatchId;
	private String customerObj;
	private String errorInfo;
	public String getEodBatchId() {
		return eodBatchId;
	}
	public void setEodBatchId(String eodBatchId) {
		this.eodBatchId = eodBatchId;
	}
	public String getCustomerObj() {
		return customerObj;
	}
	public void setCustomerObj(String customerObj) {
		this.customerObj = customerObj;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	@Override
	public String toString() {
		return "ExceptionRecordsInfo [eodBatchId=" + eodBatchId
				+ ", customerObj=" + customerObj + ", errorInfo=" + errorInfo
				+ "]";
	}
	
	
}
