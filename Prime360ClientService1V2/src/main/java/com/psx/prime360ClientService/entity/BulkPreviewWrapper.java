package com.psx.prime360ClientService.entity;

import java.util.List;

public class BulkPreviewWrapper {
	private String fileName;
	private List<List<String>> previewList;
	private String serverFile;
	public String getServerFile() {
		return serverFile;
	}
	public void setServerFile(String serverFile) {
		this.serverFile = serverFile;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<List<String>> getPreviewList() {
		return previewList;
	}
	public void setPreviewList(List<List<String>> previewList) {
		this.previewList = previewList;
	}
	
	
}
