package com.psx.prime360ClientService.dto;

import java.util.List;

/**
 *
 * @author jayantronald
 *
 */

public class ResultsDTO {
	List<String> resultTableHeader;
	List<String[]> resultTableData;

	public List<String> getResultTableHeader() {
		return resultTableHeader;
	}

	public void setResultTableHeader(List<String> resultTableHeader) {
		this.resultTableHeader = resultTableHeader;
	}

	public List<String[]> getResultTableData() {
		return resultTableData;
	}

	public void setResultTableData(List<String[]> resultTableData) {
		this.resultTableData = resultTableData;
	}

	@Override
	public String toString() {
		return "ResultsBO [resultTableHeader=" + resultTableHeader + ", resultTableData=" + resultTableData + "]";
	}

}
