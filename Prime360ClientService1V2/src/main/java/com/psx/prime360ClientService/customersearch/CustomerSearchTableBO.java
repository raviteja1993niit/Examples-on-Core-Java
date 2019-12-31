package com.psx.prime360ClientService.customersearch;

/**
 *
 * @author jayantronald
 *
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import posidex.core.prime360.dmlutils.PlainJDBCDML;
import posidex.core.prime360.dmlutils.PlainJDBCUtils;

public class CustomerSearchTableBO {
	String tableTagName;
	String tableName;
	List<CustomerSearchParameter> customerSearchParameters = new ArrayList<CustomerSearchParameter>();
	String[] insertQuery;
	String[] bindValues;
	//String statusQuery;
	//String statusBindValues;

	void persist(HashMap<String, Object> values, Connection con) throws Exception {
		// call insert query
		// System.out.println("CustomerSearchTableBO insertQuery : " + insertQuery);
		HashMap<String, String> additionalProps = new HashMap<String, String>();
		HashMap<String, String> props = new HashMap<String, String>();
		for(int i=0;i<insertQuery.length;i++) {
			props.put(tableName + ".insert."+i, insertQuery[i]);
			props.put(tableName + ".insert."+i+".bindValues", bindValues[i]);
			
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

	public List<CustomerSearchParameter> getCustomerSearchParameters() {
		return customerSearchParameters;
	}

	public void setCustomerSearchParameters(List<CustomerSearchParameter> customerSearchParameters) {
		this.customerSearchParameters = customerSearchParameters;
	}

	public String[] getInsertQuery() {
		return insertQuery;
	}

	public void setInsertQuery(String[] insertQuery) {
		this.insertQuery = insertQuery;
	}

	public String[] getBindValues() {
		return bindValues;
	}

	public void setBindValues(String[] bindValues) {
		this.bindValues = bindValues;
	}

	
}