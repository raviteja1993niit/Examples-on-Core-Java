package com.psx.prime360ClientService.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PSX_NSP_BLKUPLOAD_INITIAL")
public class BulkUploadDefn implements Serializable {
	private static final long serialVersionUID = -3182300L;
	@Id
	@Column(name = "PSX_BATCH_ID")
	private String psxbatchId;
	@Column(name = "FILE_NAME")
	private String fileName;
	@Column(name = "SUBMIT_DATE")
	private Date submitDate;
	@Column(name = "PROCESSED_DATE")
	private Date processedDate;
	@Column(name = "OFFLINE_RESULT_COUNT")
	private Integer offlineResultCount;
	@Column(name = "ONLINE_RESULT_COUNT")
	private Integer onlineResultCount;
	@Column(name = "PROCESSED_TIME_IN_MILLIS")
	private Long processedTime;
	@Column(name = "SERVER_FILE_NAME")
	private String serverFileName;
	@Column(name = "MAPPING_INFO")
	private String mappingInfo;
	@Column(name = "STATUS")
	private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BulkUploadDefn [psxbatchId=" + psxbatchId + ", fileName=" + fileName + ", submitDate=" + submitDate
				+ ", processedDate=" + processedDate + ", offlineResultCount=" + offlineResultCount
				+ ", onlineResultCount=" + onlineResultCount + ", processedTime=" + processedTime + ", serverFileName="
				+ serverFileName + ", mappingInfo=" + mappingInfo + ", status="
				+ status + "]";
	}

}
