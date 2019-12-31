package com.psx.prime360ClientService.customersearch;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/**
 *
 * @author jayantronald
 *
 */

public class CustomerSearchDTO {
	List<CustomerSearchTableDTO> tables = new ArrayList<>();
	List<String> resultsTableHeader = new ArrayList<>();
	List<String[]> results = new ArrayList<>();
	String request_id; 
	
	
	
	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public List<CustomerSearchTableDTO> getTables() {
		return tables;
	}

	public void setTables(List<CustomerSearchTableDTO> tables) {
		this.tables = tables;
	}

	public List<String> getResultsTableHeader() {
		return resultsTableHeader;
	}

	public void setResultsTableHeader(List<String> resultsTableHeader) {
		this.resultsTableHeader = resultsTableHeader;
	}

	public List<String[]>getResults() {
		return results;
	}

	public void setResults(List<String[]> results) {
		this.results = results;
	}



	@Override
	public String toString() {
		return "CustomerSearchDTO [tables=" + tables + ", resultsTableHeader=" + resultsTableHeader + ", results="
				+ results + ", request_id=" + request_id + ", getRequest_id()=" + getRequest_id() + ", getTables()="
				+ getTables() + ", getResultsTableHeader()=" + getResultsTableHeader() + ", getResults()="
				+ getResults() + ", toJSON()=" + toJSON() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
		
	}

}