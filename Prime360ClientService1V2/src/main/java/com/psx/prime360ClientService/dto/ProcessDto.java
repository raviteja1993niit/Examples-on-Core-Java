package com.psx.prime360ClientService.dto;

public class ProcessDto {
	
	private String file;
	private String headercolumnWrapper;
	private String serverFile;
	private String tableName;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getHeadercolumnWrapper() {
		return headercolumnWrapper;
	}
	public void setHeadercolumnWrapper(String headercolumnWrapper) {
		this.headercolumnWrapper = headercolumnWrapper;
	}
	
	public String getServerFile() {
		return serverFile;
	}
	public void setServerFile(String serverFile) {
		this.serverFile = serverFile;
	}
	@Override
	public String toString() {
		return "ProcessDto [file=" + file + ", headercolumnWrapper=" + headercolumnWrapper + ", serverFile="
				+ serverFile + ", tableName=" + tableName + "]";
	}
	
	
	
	

}
