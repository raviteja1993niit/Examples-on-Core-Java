package com.psx.prime360ClientService.customersearch;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import com.psx.prime360ClientService.beans.RequestBean;
import com.psx.prime360ClientService.dto.ProfilesDTO;
import com.psx.prime360ClientService.resourcehandler.NegativeEodFileUploadHander;
import com.psx.prime360ClientService.serviceImpl.RequestPostingServiceImpl;

/**
 *
 * @author jayantronald
 *
 */

public class CustomerSearchBO {
//	private static final Logger logger = LoggerFactory.getLogger(CustomerSearchBO.class);
	
    private static Logger logger = Logger.getLogger(CustomerSearchBO.class.getName());

	
	List<CustomerSearchTableBO> tables;
	private String jmsMessage;// RequestBean in JMSFormat;
	private String queueName;
	private String resultsQuery;
	List<String> resultsTableHeader = null;
	List<String[]> results = null;

	Object[] persist(RequestPostingServiceImpl requestPostingServiceImpl,ProfilesDTO profilesDTO, HashMap<String, Object> dataMap, DataSource dataSource, HashMap<String,ArrayList<HashMap<String, Object>>> request, String operation) throws Exception {
		Connection con = dataSource.getConnection();
		con.setAutoCommit(false);
		for (int i = 0; i < tables.size(); i++) {
			tables.get(i).persist(dataMap, con);
		}
		con.commit();
		con.close();
		
		// Call Engine Rest API
		
		try {con.close();} catch (Exception e) {}
		
		HashMap<String,String> paramMap = new HashMap<>();
		RequestBean bean = new RequestBean();
		bean.setRequestID(dataMap.get("CUST_UNQ_ID").toString());
		bean.setRequestInformation(request);
		bean.setProcessType("matchingProcess");
		bean.setParamMap(paramMap);
		bean.setRequestStatus("P");
		bean.setSourceSystemName("PRIME360");
		bean.setMatchingRule(profilesDTO.getMatchingRuleCSV());
		bean.setResidualParams(profilesDTO.getResidualsCSV());
		bean.setWeightages(profilesDTO.getWeightagesCSV());
		bean.setScaleEquations(profilesDTO.getScaleStringentCSV());
		bean.setRankingOrders(profilesDTO.getRankingCSV());
		logger.info("CustomerSearchBO Bean : " + bean);
		
		String response = requestPostingServiceImpl.sendSynchronousRequest(bean);
		logger.info("CustomerSearchBO response : " + response);
		
		if(response != "false" && operation != "blkUpload") {
			if(bean.getRequestID() != null) {
				requestPostingServiceImpl.getCustomerSearchResultsByRequestId(this, bean.getRequestID());
			}
			return new Object[] { resultsTableHeader, results };
		} else {
			return new Object[] {};
		}
		//System.out.println("CustomerSearchBO getResultsTableHeader() : " + getResultsTableHeader());
		//System.out.println("CustomerSearchBO getResults() : " + getResults());
		
	}

	public List<CustomerSearchTableBO> getTables() {
		return tables;
	}

	public void setTables(List<CustomerSearchTableBO> tables) {
		this.tables = tables;
	}

	public String getJmsMessage() {
		return jmsMessage;
	}

	public void setJmsMessage(String jmsMessage) {
		this.jmsMessage = jmsMessage;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
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

	public String getResultsQuery() {
		return resultsQuery;
	}

	public void setResultsQuery(String resultsQuery) {
		this.resultsQuery = resultsQuery;
	}

	@Override
	public String toString() {
		return "CustomerSearchBO [tables=" + tables + ", jmsMessage=" + jmsMessage + ", queueName=" + queueName
				+ ", resultsTableHeader=" + resultsTableHeader + ", results=" + results + ", resultsQuery="
				+ resultsQuery + "]";
	}

}