package com.psx.prime360ClientService.dto;

import java.util.Date;

public class BulkFileDetailsDTO {

	private Integer bulkId;
	private String psxbatchId;
	private String fileName;
	private Date submitDate;
	private Date processedDate;
	private Integer offlineResultCount;
	private Integer onlineResultCount;
	private Long processedTime;
	private String serverFileName;
	private String mappingInfo;
	private Integer profileId;

	public Integer getBulkId() {
		return bulkId;
	}

	public void setBulkId(Integer bulkId) {
		this.bulkId = bulkId;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public String getPsxbatchId() {
		return psxbatchId;
	}

	public void setPsxbatchId(String psxbatchId) {
		this.psxbatchId = psxbatchId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	public Integer getOfflineResultCount() {
		return offlineResultCount;
	}

	public void setOfflineResultCount(Integer offlineResultCount) {
		this.offlineResultCount = offlineResultCount;
	}

	public Integer getOnlineResultCount() {
		return onlineResultCount;
	}

	public void setOnlineResultCount(Integer onlineResultCount) {
		this.onlineResultCount = onlineResultCount;
	}

	public Long getProcessedTime() {
		return processedTime;
	}

	public void setProcessedTime(Long processedTime) {
		this.processedTime = processedTime;
	}

	public String getServerFileName() {
		return serverFileName;
	}

	public void setServerFileName(String serverFileName) {
		this.serverFileName = serverFileName;
	}

	public String getMappingInfo() {
		return mappingInfo;
	}

	public void setMappingInfo(String mappingInfo) {
		this.mappingInfo = mappingInfo;
	}

	@Override
	public String toString() {
		return "BulkFileDetailsDTO [bulkId=" + bulkId + ", psxbatchId=" + psxbatchId + ", fileName=" + fileName
				+ ", submitDate=" + submitDate + ", processedDate=" + processedDate + ", offlineResultCount="
				+ offlineResultCount + ", onlineResultCount=" + onlineResultCount + ", processedTime=" + processedTime
				+ ", serverFileName=" + serverFileName + ", mappingInfo=" + mappingInfo + ", profileId=" + profileId
				+ "]";
	}

}
