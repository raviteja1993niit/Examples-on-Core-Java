package com.psx.prime360ClientService.entity;

import java.util.List;

public class HeaderColumnWrapper {

	private List<BulkUploadColumnMapping> bulkColmappingList;

	public List<BulkUploadColumnMapping> getBulkColmappingList() {
		return bulkColmappingList;
	}

	public void setBulkColmappingList(List<BulkUploadColumnMapping> bulkColmappingList) {
		this.bulkColmappingList = bulkColmappingList;
	}

	@Override
	public String toString() {
		return "HeaderColumnWrapper [bulkColmappingList=" + bulkColmappingList + "]";
	}

}
