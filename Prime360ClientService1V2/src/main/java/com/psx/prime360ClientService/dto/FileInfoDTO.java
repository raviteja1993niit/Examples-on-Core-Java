package com.psx.prime360ClientService.dto;

import java.sql.Timestamp;

public class FileInfoDTO {
	private String fileName;
	private Integer batchId;
	private Integer totalRecords;
	private Timestamp insertTs;
	private String status;
	public String getFileName() {
		return fileName;
	}
	public Integer getBatchId() {
		return batchId;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public Timestamp getInsertTs() {
		return insertTs;
	}
	public String getStatus() {
		return status;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public void setInsertTs(Timestamp insertTs) {
		this.insertTs = insertTs;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "FileInfoDTO [fileName=" + fileName + ", batchId=" + batchId + ", totalRecords=" + totalRecords
				+ ", insertTs=" + insertTs + ", status=" + status + ", getFileName()=" + getFileName()
				+ ", getBatchId()=" + getBatchId() + ", getTotalRecords()=" + getTotalRecords() + ", getInsertTs()="
				+ getInsertTs() + ", getStatus()=" + getStatus() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
