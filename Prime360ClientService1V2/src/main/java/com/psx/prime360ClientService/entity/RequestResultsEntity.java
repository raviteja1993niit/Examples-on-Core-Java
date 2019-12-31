package com.psx.prime360ClientService.entity;

import java.util.List;

public class RequestResultsEntity {
	
	
	NSPRequestEntity nspRequestEntity;
	List<NSPRequestResultsEntity> nspRequestResultsEntity;
	public NSPRequestEntity getNspRequestEntity() {
		return nspRequestEntity;
	}
	public List<NSPRequestResultsEntity> getNspRequestResultsEntity() {
		return nspRequestResultsEntity;
	}
	public void setNspRequestEntity(NSPRequestEntity nspRequestEntity) {
		this.nspRequestEntity = nspRequestEntity;
	}
	public void setNspRequestResultsEntity(List<NSPRequestResultsEntity> nspRequestResultsEntity) {
		this.nspRequestResultsEntity = nspRequestResultsEntity;
	}
	@Override
	public String toString() {
		return "RequestResultsEntity [nspRequestEntity=" + nspRequestEntity + ", nspRequestResultsEntity="
				+ nspRequestResultsEntity + "]";
	}
	
	
	

}
