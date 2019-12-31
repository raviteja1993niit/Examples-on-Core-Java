package com.psx.prime360ClientService.relationalsearch;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/**
 *
 * @author jayantronald
 *
 */

public class RelationalSearchDTO {
	List<RelationalSearchTableDTO> tables = new ArrayList<>();
	List<String> resultsTableHeader = new ArrayList<>();
	List<String[]> results = new ArrayList<>();

	public List<RelationalSearchTableDTO> getTables() {
		return tables;
	}

	public void setTables(List<RelationalSearchTableDTO> tables) {
		this.tables = tables;
	}

	public List<String> getResultsTableHeader() {
		return resultsTableHeader;
	}

	public void setResultsTableHeader(List<String> resultsTableHeader) {
		this.resultsTableHeader = resultsTableHeader;
	}

	public List<String[]> getResults() {
		return results;
	}

	public void setResults(List<String[]> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "RelationalSearchDTO [tables=" + tables + ", resultsTableHeader=" + resultsTableHeader + ", results="
				+ results + "]";
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
