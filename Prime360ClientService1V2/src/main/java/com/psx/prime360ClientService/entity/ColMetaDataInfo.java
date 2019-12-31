package com.psx.prime360ClientService.entity;

import java.util.Arrays;




public class ColMetaDataInfo {
	private String slNo;
	private String columnName;
	private String dataType;
	private String nullable;
	private String isPrimarykey;
	private String isDedupe;
	private String dedupeType;
	private String cleanProfile;
	private String displayName;
	private String[] associationParameters;

	public ColMetaDataInfo() {
	}

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getIsDedupe() {
		return isDedupe;
	}

	public void setIsDedupe(String isDedupe) {
		this.isDedupe = isDedupe;
	}

	public String getDedupeType() {
		return dedupeType;
	}

	public void setDedupeType(String dedupeType) {
		this.dedupeType = dedupeType;
	}

	public String getCleanProfile() {
		return cleanProfile;
	}

	public void setCleanProfile(String cleanProfile) {
		this.cleanProfile = cleanProfile;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIsPrimarykey() {
		return isPrimarykey;
	}

	public void setIsPrimarykey(String isPrimarykey) {
		this.isPrimarykey = isPrimarykey;
	}

	public String[] getAssociationParameters() {
		return associationParameters;
	}

	public void setAssociationParameters(String[] associationParameters) {
		this.associationParameters = associationParameters;
	}

	

	@Override
	public String toString() {
		return "ColMetaDataInfo [slNo=" + slNo + ", columnName=" + columnName + ", dataType=" + dataType + ", nullable="
				+ nullable + ", isPrimarykey=" + isPrimarykey + ", isDedupe=" + isDedupe + ", dedupeType=" + dedupeType
				+ ", cleanProfile=" + cleanProfile + ", displayName=" + displayName + 
				 ", associationParameters=" + Arrays.toString(associationParameters) + "]";
	}

}