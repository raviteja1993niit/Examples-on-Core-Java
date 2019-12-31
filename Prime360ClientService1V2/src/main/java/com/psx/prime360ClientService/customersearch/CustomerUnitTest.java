package com.psx.prime360ClientService.customersearch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jayantronald
 *
 */

public class CustomerUnitTest {

//	public static void main(String[] args) {
//		CustomerSearchParameter customerSearchParameter1 = new CustomerSearchParameter();
//		customerSearchParameter1.setParameterDisplayName("Name");
//		customerSearchParameter1.setParameterValue("Jayant");
//
//		CustomerSearchParameter customerSearchParameter2 = new CustomerSearchParameter();
//		customerSearchParameter2.setParameterDisplayName("Father Name");
//		customerSearchParameter2.setParameterValue("Sanjay");
//
//		CustomerSearchParameter customerSearchParameter3 = new CustomerSearchParameter();
//		customerSearchParameter3.setParameterDisplayName("Mother Name");
//		customerSearchParameter3.setParameterValue("Punita");
//
//		List<CustomerSearchParameter> customerSearchParameters = new ArrayList<CustomerSearchParameter>();
//		customerSearchParameters.add(customerSearchParameter1);
//		customerSearchParameters.add(customerSearchParameter2);
//		customerSearchParameters.add(customerSearchParameter3);
//
//		CustomerSearchTableBO customerSearchTableBO1 = new CustomerSearchTableBO();
//		customerSearchTableBO1.setTableName("Demographics");
//		customerSearchTableBO1.setCustomerSearchParameters(customerSearchParameters);
//		customerSearchTableBO1.setInsertQuery("INSERT QUERY");
//		
//		
//		
//		CustomerSearchParameter customerSearchParameter4 = new CustomerSearchParameter();
//		customerSearchParameter4.setParameterDisplayName("Office Phone");
//		customerSearchParameter4.setParameterValue("123456");
//
//		CustomerSearchParameter customerSearchParameter5 = new CustomerSearchParameter();
//		customerSearchParameter5.setParameterDisplayName("Permanent Phone");
//		customerSearchParameter5.setParameterValue("789456");
//
//		CustomerSearchParameter customerSearchParameter6 = new CustomerSearchParameter();
//		customerSearchParameter6.setParameterDisplayName("Temporary Phone");
//		customerSearchParameter6.setParameterValue("456789");
//
//		List<CustomerSearchParameter> customerSearchParameters1 = new ArrayList<CustomerSearchParameter>();
//		customerSearchParameters1.add(customerSearchParameter4);
//		customerSearchParameters1.add(customerSearchParameter5);
//		customerSearchParameters1.add(customerSearchParameter6);
//
//		CustomerSearchTableBO customerSearchTableBO2 = new CustomerSearchTableBO();
//		customerSearchTableBO2.setTableName("Contact");
//		customerSearchTableBO2.setCustomerSearchParameters(customerSearchParameters1);
//		customerSearchTableBO2.setInsertQuery("INSERT QUERY");
//		
//		
//
//		List<CustomerSearchTableBO> list = new ArrayList<>();
//		list.add(customerSearchTableBO1);
//		list.add(customerSearchTableBO2);
//		
//		List<String> resultsTableHeader = new ArrayList<>();
//		resultsTableHeader.add("PSX_ID");
//		resultsTableHeader.add("NAME");
//		resultsTableHeader.add("DOB1");
//		resultsTableHeader.add("RECORD_TYPE");
//		resultsTableHeader.add("MATCH_TYPE");
//		resultsTableHeader.add("SCALE_TYPE");
//		List<String[]> results = new ArrayList<>();
//
//		CustomerSearchBO customerSearchBO = new CustomerSearchBO();
//		customerSearchBO.setTables(list);
//		customerSearchBO.setJmsMessage("JMS MESSAGE");
//		customerSearchBO.setQueueName("PSX QUEUE");
//		customerSearchBO.setResultsTableHeader(resultsTableHeader);
//		customerSearchBO.setResults(results);
//		customerSearchBO.setResultsQuery("ResultsQuery");
//
//		CustomerSearch customerSearch = new CustomerSearch();
//		customerSearch.setCustomerSearchBO(customerSearchBO);
//		customerSearch = CustomerSearch.fromJSON(customerSearch.toJSON());
//
//		System.out.println(customerSearch.toJSON());
//		System.out.println(customerSearch.getCustomerSearchDTO().toJSON());
//	}
}