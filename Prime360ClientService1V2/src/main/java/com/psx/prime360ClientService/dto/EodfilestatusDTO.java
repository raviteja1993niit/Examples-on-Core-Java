package com.psx.prime360ClientService.dto;

import java.util.Date;

public class EodfilestatusDTO {
	
	private Integer sno;
	private String batchId;
	private String fileName;
	private String source;
	private Integer offlineResultCount;
	private Integer onlineResultCount;
	private Long insetts;
	private String status;
	public Integer getSno() {
		return sno;
	}
	public String getBatchId() {
		return batchId;
	}
	public String getFileName() {
		return fileName;
	}
	public String getSource() {
		return source;
	}
	public Integer getOfflineResultCount() {
		return offlineResultCount;
	}
	public Integer getOnlineResultCount() {
		return onlineResultCount;
	}
	public Long getInsetts() {
		return insetts;
	}
	public String getStatus() {
		return status;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public void setOfflineResultCount(Integer offlineResultCount) {
		this.offlineResultCount = offlineResultCount;
	}
	public void setOnlineResultCount(Integer onlineResultCount) {
		this.onlineResultCount = onlineResultCount;
	}
	public void setInsetts(Long insetts) {
		this.insetts = insetts;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "EodfilestatusDTO [sno=" + sno + ", batchId=" + batchId + ", fileName=" + fileName + ", source=" + source
				+ ", offlineResultCount=" + offlineResultCount + ", onlineResultCount=" + onlineResultCount
				+ ", insetts=" + insetts + ", status=" + status + ", getSno()=" + getSno() + ", getBatchId()="
				+ getBatchId() + ", getFileName()=" + getFileName() + ", getSource()=" + getSource()
				+ ", getOfflineResultCount()=" + getOfflineResultCount() + ", getOnlineResultCount()="
				+ getOnlineResultCount() + ", getInsetts()=" + getInsetts() + ", getStatus()=" + getStatus()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	


}
