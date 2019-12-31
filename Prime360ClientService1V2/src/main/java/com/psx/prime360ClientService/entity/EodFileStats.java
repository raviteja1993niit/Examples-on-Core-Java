package com.psx.prime360ClientService.entity;

import java.sql.Timestamp;

public class EodFileStats {

	private String batchId;
	private String process_type;
	private String fileName;
	private int totalCount;
	private int importCount;
	private int errorCount;
	private String insertTs;
	private String sourceSystem;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getProcess_type() {
		return process_type;
	}

	public void setProcess_type(String process_type) {
		this.process_type = process_type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getImportCount() {
		return importCount;
	}

	public void setImportCount(int importCount) {
		this.importCount = importCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public String getInsertTs() {
		return insertTs;
	}

	public void setInsertTs(String insertTs) {
		this.insertTs = insertTs;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	@Override
	public String toString() {
		return "EodFileStats [batchId=" + batchId + ", process_type="
				+ process_type + ", fileName=" + fileName + ", totalCount="
				+ totalCount + ", importCount=" + importCount + ", errorCount="
				+ errorCount + ", insertTs=" + insertTs + ", sourceSystem="
				+ sourceSystem + "]";
	}

}
