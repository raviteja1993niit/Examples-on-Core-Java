package com.psx.prime360ClientService.utils;



import java.util.HashMap;
import java.util.List;

public class MyResults {
	String sourceSystemName;
	List<MyResult> offline;
	List<MyResult> online;
	public List<MyResult> getOnline() {
		return online;
	}
	public void setOnline(List<MyResult> online) {
		this.online = online;
	}
	public String getSourceSystemName() {
		return sourceSystemName;
	}
	public void setSourceSystemName(String sourceSystemName) {
		this.sourceSystemName = sourceSystemName;
	}
	public List<MyResult> getOffline() {
		return offline;
	}
	public void setOffline(List<MyResult> offline) {
		this.offline = offline;
	}
	
}
