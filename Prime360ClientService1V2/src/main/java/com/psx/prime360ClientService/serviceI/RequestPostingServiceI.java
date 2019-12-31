package com.psx.prime360ClientService.serviceI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.psx.prime360ClientService.customersearch.CustomerSearchBO;
import com.psx.prime360ClientService.customersearch.CustomerSearchDTO;
import com.psx.prime360ClientService.dto.IdentitySearchDTO;
import com.psx.prime360ClientService.entity.NSPRequestEntity;
import com.psx.prime360ClientService.entity.NSPRequestResultsEntity;
import com.psx.prime360ClientService.entity.Profile;
import com.psx.prime360ClientService.relationalsearch.RelationalSearchBO;
import com.psx.prime360ClientService.relationalsearch.RelationalSearchDTO;

/**
 *
 * @author jayantronald
 *
 */

public interface RequestPostingServiceI {
	
	public List<String> getAllRequestId();
	public NSPRequestEntity getRecordsBasedOnId(String requestId);
	public List<NSPRequestResultsEntity> getResultsBasedOnId(String requestId);
	public List<String> getEquality() throws IOException;
	public String submitIdentitySearchRequest(List<String> equalityData, String profileId, String operation) throws Exception;
	public String getStatus(String serviceName);
	public IdentitySearchDTO getIdentitySearchResultsByRequestId(String requestId) ;
	public List<Profile> getAllActiveProfiles();
	public CustomerSearchDTO getCustomerSearchTable();	
	String submitCustomerSearchRequest(CustomerSearchDTO customerSearchDTO,String profileId,  String operation) throws SQLException, Exception;
	public void getCustomerSearchResultsByRequestId(CustomerSearchBO customerSearchBO, String requestId) ;
	public List<Profile> getAllRelationalProfiles();
	public RelationalSearchDTO getRelationalSearchTable();	
	RelationalSearchDTO submitRelationalSearchRequest(RelationalSearchDTO relationalSearchDTO,String profileId,  String operation) throws SQLException, Exception;
	public void getRelationalSearchResultsByRequestId(RelationalSearchBO relationalSearchBO, String requestId) ;
	void submitMultipleCustomerSearchRequest(String[] fileName, String[] tableNames, String profileId, String operation,Map<String,String> headerColVsDBColName) throws Exception;
	public boolean saveCustomerSearch(NSPRequestEntity nspRequestEntity) throws ClassNotFoundException, SQLException, IOException;
	//public void getStages() throws IOException, ClassNotFoundException, SQLException;
	
}