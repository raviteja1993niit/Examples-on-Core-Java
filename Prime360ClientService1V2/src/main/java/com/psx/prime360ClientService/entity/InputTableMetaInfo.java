package com.psx.prime360ClientService.entity;

import java.util.List;

public class InputTableMetaInfo {
	String tableName;
	String tableAliasName;
	List<String> colNames;

	public InputTableMetaInfo() {

	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableAliasName() {
		return tableAliasName;
	}

	public void setTableAliasName(String tableAliasName) {
		this.tableAliasName = tableAliasName;
	}

	public List<String> getColNames() {
		return colNames;
	}

	public void setColNames(List<String> colNames) {
		this.colNames = colNames;
	}

	@Override
	public String toString() {
		return "InputTableMetaInfo [tableName=" + tableName + ", tableAliasName=" + tableAliasName + ", colNames="
				+ colNames + "]";
	}

	
}
