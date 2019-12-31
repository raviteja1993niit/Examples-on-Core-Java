package com.psx.prime360ClientService.relationalsearch;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import posidex.core.prime360.dmlutils.PlainJDBCDML;
import posidex.core.prime360.dmlutils.PlainJDBCUtils;

/**
 *
 * @author jayantronald
 *
 */

public class RelationalSearchTableBO {

	String tableTagName;
	String tableName;
	List<RelationalSearchParameter> relationalSearchParameters = new ArrayList<RelationalSearchParameter>();
	String insertQuery;
	String bindValues;
	String statusQuery;
	String statusBindValues;

	void persist(HashMap<String, Object> values, Connection con) throws Exception {
		// call insert query
		// System.out.println("RelationalSearchTableBO insertQuery : " + insertQuery);
		HashMap<String, String> additionalProps = new HashMap<String, String>();
		HashMap<String, String> props = new HashMap<String, String>();
		props.put(tableName + ".insert.0", insertQuery);
		props.put(tableName + ".insert.0.bindValues", bindValues);
		if (statusQuery != null) {
			props.put(tableName + ".insert.1", statusQuery);
			props.put(tableName + ".insert.1.bindValues", statusBindValues);
		}

		String commonKey = tableName + ".";
		HashMap<String, PlainJDBCDML> myDMLs = PlainJDBCUtils.getMyPreparedStatements(con, props, commonKey);
		PlainJDBCUtils.setBindValuesExecuteAndCloseDMLs(myDMLs, values, additionalProps);
	}

	public String getTableTagName() {
		return tableTagName;
	}

	public void setTableTagName(String tableTagName) {
		this.tableTagName = tableTagName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<RelationalSearchParameter> getRelationalSearchParameters() {
		return relationalSearchParameters;
	}

	public void setRelationalSearchParameters(List<RelationalSearchParameter> relationalSearchParameters) {
		this.relationalSearchParameters = relationalSearchParameters;
	}

	public String getInsertQuery() {
		return insertQuery;
	}

	public void setInsertQuery(String insertQuery) {
		this.insertQuery = insertQuery;
	}

	public String getBindValues() {
		return bindValues;
	}

	public void setBindValues(String bindValues) {
		this.bindValues = bindValues;
	}

	public String getStatusQuery() {
		return statusQuery;
	}

	public void setStatusQuery(String statusQuery) {
		this.statusQuery = statusQuery;
	}

	public String getStatusBindValues() {
		return statusBindValues;
	}

	public void setStatusBindValues(String statusBindValues) {
		this.statusBindValues = statusBindValues;
	}

	@Override
	public String toString() {
		return "RelationalSearchTableBO [tableTagName=" + tableTagName + ", tableName=" + tableName
				+ ", relationalSearchParameters=" + relationalSearchParameters + ", insertQuery=" + insertQuery
				+ ", bindValues=" + bindValues + ", statusQuery=" + statusQuery + ", statusBindValues="
				+ statusBindValues + "]";
	}

}
