package com.psx.prime360ClientService.entity;

public class BulkUploadColumnMapping {

	private String headerColName;//csv header				//LHS Name
	private String displayColName;//database column name	//RHS display Name
	private String engineColName;
	private String csvHeaderData;//matching data			//LHS Name
	
	//private String fileColumnName;//LHS Name
	//private String displayColumnName;//RHS display Name
	//private String engineColName;
	//private String engineColumnName;//RHS back end name

	public String getCsvHeaderData() {
		return csvHeaderData;
	}

	public void setCsvHeaderData(String csvHeaderData) {
		this.csvHeaderData = csvHeaderData;
	}

	public String getDisplayColName() {
		return displayColName;
	}

	public void setDisplayColName(String displayColName) {
		this.displayColName = displayColName;
	}

	public String getEngineColName() {
		return engineColName;
	}

	public void setEngineColName(String engineColName) {
		this.engineColName = engineColName;
	}

	public String getHeaderColName() {
		return headerColName;
	}

	public void setHeaderColName(String headerColName) {
		this.headerColName = headerColName;
	}

	@Override
	public String toString() {
		return "BulkUploadColumnMapping [headerColName=" + headerColName + ", displayColName=" + displayColName + "]";
	}

}
