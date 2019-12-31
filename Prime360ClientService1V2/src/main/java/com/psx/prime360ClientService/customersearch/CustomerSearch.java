package com.psx.prime360ClientService.customersearch;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.psx.prime360ClientService.dto.ProfilesDTO;
import com.psx.prime360ClientService.entity.NSPRequestEntity;
import com.psx.prime360ClientService.serviceImpl.RequestPostingServiceImpl;

import org.apache.log4j.Logger;

/**
 *
 * @author jayantronald
 *
 */
@Transactional
@Service
@Component
public class CustomerSearch {
	private static Logger logger = Logger.getLogger(CustomerSearch.class.getName());
	CustomerSearchBO customerSearchBO;
	CustomerSearchDTO customerSearchDTO;
	Connection con=null;
	static Properties prop = new Properties();
	
	HashMap<String,Object> row1=new HashMap<>();

	public CustomerSearchBO getCustomerSearchBO() {
		return customerSearchBO;
	}

	public void setCustomerSearchBO(CustomerSearchBO customerSearchBO) {
		this.customerSearchBO = customerSearchBO;
	}
	
	public void setCustomerSearchDTO(CustomerSearchDTO customerSearchDTO) {
		this.customerSearchDTO = customerSearchDTO;
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public static CustomerSearch fromJSON(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, CustomerSearch.class);
	}
	
	public static CustomerSearch fromProps(Map<String,String> properties) {
		String keyToFind = "customerSearchTabs";
		String tagNames=properties.get(keyToFind);
		List<String> tableTagName = Arrays.asList(tagNames.split(","));
		CustomerSearchBO customerSearchBO=new CustomerSearchBO();
		List<CustomerSearchTableBO> tables=new ArrayList<>();
		for (int index = 0; index < tableTagName.size(); index++) {
			CustomerSearchTableBO c=new CustomerSearchTableBO();
			c.setTableName(properties.get(keyToFind+"."+tableTagName.get(index)+".tableName"));
			c.setTableTagName(tableTagName.get(index));
			int cntr=0;
			while(properties.get(keyToFind+"."+tableTagName.get(index)+".customerSearchParameters."+cntr+".engineName")!=null) {
				CustomerSearchParameter csp=new CustomerSearchParameter();
				csp.setEngineName(properties.get(keyToFind+"."+tableTagName.get(index)+".customerSearchParameters."+cntr+".engineName"));
				csp.setParameterDisplayName(properties.get(keyToFind+"."+tableTagName.get(index)+".customerSearchParameters."+cntr+".parameterDisplayValue"));
				csp.setParameterValue(properties.get(keyToFind+"."+tableTagName.get(index)+".customerSearchParameters."+cntr+".parameterValue"));
				c.getCustomerSearchParameters().add(csp);
				cntr++;
			}
			int iqIndex=0;
			List<String> insertQueries=new ArrayList<String>();
			List<String> bindValues=new ArrayList<String>();
			while(properties.get(keyToFind+"."+tableTagName.get(index)+".insertQuery."+iqIndex)!=null) {
				insertQueries.add(properties.get(keyToFind+"."+tableTagName.get(index)+".insertQuery."+iqIndex));
				bindValues.add(properties.get(keyToFind+"."+tableTagName.get(index)+".bindValues."+iqIndex));
				iqIndex++;
				
			}
			c.setInsertQuery(insertQueries.toArray(new String[0]));
			c.setBindValues(bindValues.toArray(new String[0]));
			tables.add(c);
		}
		customerSearchBO.setTables(tables);
		customerSearchBO.setJmsMessage(properties.get(keyToFind+".jmsMessage"));
		customerSearchBO.setQueueName(properties.get(keyToFind+".queueName"));
		customerSearchBO.setResultsQuery(properties.get(keyToFind+".resultsQuery"));
		customerSearchBO.setResultsTableHeader(Arrays.asList(properties.get(keyToFind+".resultsTableHeader").split(",")));
		CustomerSearch customerSearch = new CustomerSearch();
		customerSearch.setCustomerSearchBO(customerSearchBO);
		return customerSearch;
	}
	

	public CustomerSearchDTO getCustomerSearchDTO() {
		int tabCount=customerSearchBO.getTables().size();
		List<String> resultsTableHeaderList = customerSearchBO.getResultsTableHeader();
		List<String[]> resultsList = customerSearchBO.getResults();
		
		CustomerSearchDTO customerSearchDTO = new CustomerSearchDTO();
		List<CustomerSearchTableDTO> tables = new ArrayList<CustomerSearchTableDTO>();
		
		for (int i=0;i<tabCount;i++) {
			CustomerSearchTableDTO customerSearchTableDTO = new CustomerSearchTableDTO();
			String tabName = customerSearchBO.getTables().get(i).getTableName();
			List<CustomerSearchParameter> customerSearchParameters  = customerSearchBO.getTables().get(i).getCustomerSearchParameters();

			customerSearchTableDTO.setTableName(tabName);
			customerSearchTableDTO.setCustomerSearchParameters(customerSearchParameters);
			
			tables.add(customerSearchTableDTO);
		}
		
		customerSearchDTO.setTables(tables);
		customerSearchDTO.setResultsTableHeader(resultsTableHeaderList);
		customerSearchDTO.setResults(resultsList);
		return customerSearchDTO;
	}

	public String persist(RequestPostingServiceImpl requestPostingServiceImpl,CustomerSearchDTO customerSearchDTO, ProfilesDTO profilesDTO, DataSource dataSource, String operation) throws Exception {
		int dtoSize = customerSearchDTO.getTables().size();
		HashMap<String,ArrayList<HashMap<String, Object>>> request=new HashMap<>();
		HashMap<String,Object> dataMap = new HashMap<String,Object>();
		//String requestID=System.nanoTime()+"";
		String requestID = appNextValProc();
		logger.info("1st generated RequestID "+requestID);
		
		for (int i = 0; i < dtoSize; i++) {
			String tableName = customerSearchDTO.getTables().get(i).getTableName();
			tableName = tableName+".";
			ArrayList<HashMap<String,Object>> tableData=new ArrayList<>();
			HashMap<String,Object> row=new HashMap<>();
			
			List<CustomerSearchParameter> customerSearchParameters  = customerSearchDTO.getTables().get(i).getCustomerSearchParameters();
			for (CustomerSearchParameter customerSearchParameter : customerSearchParameters) {
				//String key = customerSearchParameter.getParameterDisplayName();
				String key = customerSearchParameter.getEngineName();
				dataMap.put(tableName.concat(key), customerSearchParameter.getParameterValue());
				row.put(customerSearchParameter.getEngineName(),customerSearchParameter.getParameterValue());
			}
			
			row.put("CUST_UNQ_ID",  requestID);
			row.put("PSX_ID",  requestID);
			tableData.add(row);
			row1=row;
		//	System.out.println("Row data....;;;"+row1);
			request.put("CDAP", tableData);
		}
	
			dataMap.put("CUST_UNQ_ID", requestID);
			dataMap.put("PSX_ID", requestID);
			dataMap.put("PSX_BATCH_ID", new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime()));
			dataMap.put("CURRENT_TIME", new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime()));
			dataMap.put("CURRENT_USER", "PSX_USER");
			dataMap.put("REQUEST_STATUS", "P");
			dataMap.put("INSERT_TIME", new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(Calendar.getInstance().getTime()));

			if (dataMap != null && dataSource != null) {
			//System.out.println("datamap==========="+dataMap + " : " + dataSource);
			// System.out.println("customerSearchBO : " + customerSearchBO);
			
			
			NSPRequestEntity nspRequestEntity = new NSPRequestEntity();
			System.out.println("second time ::"+nspRequestEntity);			


			nspRequestEntity.setAadhar((String) row1.get("AADHAAR"));
			nspRequestEntity.setAccount_no((String) row1.get("ACCOUNT_NUMBER"));
			nspRequestEntity.setApplication_no((String) row1.get("APP_NO"));
			nspRequestEntity.setCa_no((String) row1.get("CA_NUMBER"));
			nspRequestEntity.setCin((String) row1.get("CIN"));
			nspRequestEntity.setCredit_card((String) row1.get("CREDIT_CARD_NUMBER"));
			nspRequestEntity.setCust_unq_id((String) row1.get("CUST_UNQ_ID"));
			logger.info("before setting value  the requestid  "+requestID);
			nspRequestEntity.setPsx_Id(Long.parseLong((String) row1.get("PSX_ID")));
			logger.info("After setting the value to bean "+nspRequestEntity.getPsx_Id());
			nspRequestEntity.setCustomer_no((String) row1.get("CUSTOMER_NO"));
			nspRequestEntity.setRequest_id((String) row1.get("CUST_UNQ_ID"));
			nspRequestEntity.setDin((String) row1.get("DIN"));
			
			
			if(row1.get("DOB1")!=null && row1.get("DOB1")!="") {
			String s=(String)row1.get("DOB1");
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy"); 
			java.util.Date date = sdf1.parse(s); 
			java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());  
			nspRequestEntity.setDob1(sqlStartDate);
			}
			
			
			
			if(row1.get("DOI")!=null && row1.get("DOI")!="") {
				String q=(String)row1.get("DOI");
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy"); 
				java.util.Date date1 = sdf1.parse(q); 
				java.sql.Date sqlStartDate = new java.sql.Date(date1.getTime()); 
				nspRequestEntity.setDoi(sqlStartDate);
				}
			
		//	nspRequestEntity.setDoi((String) row1.get("DOI"));
			nspRequestEntity.setDriving_lic((String) row1.get("DRIVINGLIC"));
			nspRequestEntity.setEmployer_name((String) row1.get("EMPLOYER_NAME"));
			nspRequestEntity.setFather_Name((String) row1.get("FATHER_NAME"));
			nspRequestEntity.setName((String) row1.get("NAME"));
			nspRequestEntity.setOff_area((String) row1.get("OFF_AREA"));
			nspRequestEntity.setOffice_City((String) row1.get("OFFICE_CITY"));
			nspRequestEntity.setOffice_Email((String) row1.get("OFFICE_EMAIL"));
			nspRequestEntity.setOffice_Pin((String) row1.get("OFFICE_PIN"));
			nspRequestEntity.setPan((String) row1.get("PAN"));
			nspRequestEntity.setPassport((String) row1.get("PASSPORT"));
			nspRequestEntity.setPermanent_Address((String) row1.get("PERMANENT_ADDRESS"));
			nspRequestEntity.setPermanent_Pin((String) row1.get("PERMANENT_PIN"));
			nspRequestEntity.setRegistration_no((String) row1.get("REGISTRATION_NO"));
			nspRequestEntity.setSegment((String) row1.get("SEGMENT"));
			nspRequestEntity.setTan_no((String) row1.get("TAN_NO"));
			nspRequestEntity.setVoterid((String) row1.get("VOTERID"));
			nspRequestEntity.setPermanent_City((String) row1.get("PERMANENT_CITY"));
			nspRequestEntity.setOffice_Address((String) row1.get("OFFICE_ADDRESS"));
			nspRequestEntity.setPermanent_Email((String) row1.get("PERMANENT_EMAIL"));
			nspRequestEntity.setPermanent_Phone((String) row1.get("PERMANENT_PHONE"));
			nspRequestEntity.setOffice_Phone((String) row1.get("OFFICE_PHONE"));
			
			
			

			//System.out.println("nspRequestEntity=======" + nspRequestEntity);

			RequestPostingServiceImpl serviceImpl = new RequestPostingServiceImpl();
			
		//	System.out.println("before sending to save method >> ::"+nspRequestEntity);
			try {
				//logger.info("calling the saveCustomerSearch()");
			serviceImpl.saveCustomerSearch(nspRequestEntity);
			}catch(Exception e) {
				logger.info("exception occured while calling saveCustomerSearch()");
				e.printStackTrace();
			}
			Object[] retValue = customerSearchBO.persist(requestPostingServiceImpl,profilesDTO, dataMap, dataSource, request, operation);
			if(retValue.length>0) {
				customerSearchDTO.setResultsTableHeader((List<String>)retValue[0]);
				customerSearchDTO.setResults((List<String[]>)retValue[1]);	
			}
			customerSearchDTO.setRequest_id(requestID);
			
			
		}
			
		return requestID;
	}
	 private String appNextValProc() throws SQLException, IOException, ClassNotFoundException {
	        prop.load(RequestPostingServiceImpl.class.getClassLoader().getResourceAsStream("database.properties"));

	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        con = DriverManager.getConnection(prop.getProperty("spring.datasource.url"),
	                prop.getProperty("spring.datasource.username"), prop.getProperty("spring.datasource.password"));
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        String sql = "select appnextval('request_id') from dual";
	        String reqPsxid = null;
	        try {
	            pstmt = con.prepareStatement(sql);
	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	                reqPsxid = rs.getString(1);
	            }

	            rs.close();
	            pstmt.close();
	        } catch (SQLException e) {
	            throw e;
	        } catch (NullPointerException npe) {
	            throw npe;
	        }
	        return reqPsxid;
	    }
}