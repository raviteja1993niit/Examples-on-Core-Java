package com.psx.prime360ClientService.relationalsearch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jayantronald
 *
 */

public class RelationalSearchTableDTO {
	String tableName;
	List<RelationalSearchParameter> relationalSearchParameter = new ArrayList<RelationalSearchParameter>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<RelationalSearchParameter> getRelationalSearchParameter() {
		return relationalSearchParameter;
	}

	public void setRelationalSearchParameter(List<RelationalSearchParameter> relationalSearchParameter) {
		this.relationalSearchParameter = relationalSearchParameter;
	}

	@Override
	public String toString() {
		return "RelationalSearchTableDTO [tableName=" + tableName + ", relationalSearchParameter="
				+ relationalSearchParameter + "]";
	}

}
