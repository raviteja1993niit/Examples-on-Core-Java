package com.psx.prime360ClientService.entity;

import java.sql.Timestamp;
import java.util.List;

public class EodProcessingStages {

	private String stage;
	private String eodbatchid;
	private String description;
	private String inserttime;
	private String psxbatchid;
	private String stagedescription;
	private String status;
	private List<String> batchIdsList;
	private int sno;
	private String node1status;
	private String node2status;

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getEodbatchid() {
		return eodbatchid;
	}

	public void setEodbatchid(String eodbatchid) {
		this.eodbatchid = eodbatchid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInserttime() {
		return inserttime;
	}

	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}

	public String getPsxbatchid() {
		return psxbatchid;
	}

	public void setPsxbatchid(String psxbatchid) {
		this.psxbatchid = psxbatchid;
	}

	public String getStagedescription() {
		return stagedescription;
	}

	public void setStagedescription(String stagedescription) {
		this.stagedescription = stagedescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getBatchIdsList() {
		return batchIdsList;
	}

	public void setBatchIdsList(List<String> batchIdsList) {
		this.batchIdsList = batchIdsList;
	}

	public String getNode1status() {
		return node1status;
	}

	public void setNode1status(String node1status) {
		this.node1status = node1status;
	}

	public String getNode2status() {
		return node2status;
	}

	public void setNode2status(String node2status) {
		this.node2status = node2status;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	@Override
	public String toString() {
		return "EodProcessingStages [stage=" + stage + ", eodbatchid="
				+ eodbatchid + ", description=" + description + ", inserttime="
				+ inserttime + ", psxbatchid=" + psxbatchid
				+ ", stagedescription=" + stagedescription + ", status="
				+ status + ", batchIdsList=" + batchIdsList + ", sno=" + sno
				+ ", node1status=" + node1status + ", node2status="
				+ node2status + "]";
	}

}