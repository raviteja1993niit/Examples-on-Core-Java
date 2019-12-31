package com.psx.prime360ClientService.customersearch;

/**
 *
 * @author jayantronald
 *
 */

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchTableDTO {
	String tableName;
	List<CustomerSearchParameter> customerSearchParameters = new ArrayList<CustomerSearchParameter>();

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

	@Override
	public String toString() {
		return "CustomerSearchTableDTO [tableName=" + tableName + ", customerSearchParameters="
				+ customerSearchParameters + "]";
	}

}