package com.psx.prime360ClientService.resourcehandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psx.prime360ClientService.customersearch.CustomerSearchDTO;
import com.psx.prime360ClientService.dto.IdentitySearchDTO;
import com.psx.prime360ClientService.entity.NSPRequestEntity;
import com.psx.prime360ClientService.entity.NSPRequestResultsEntity;
import com.psx.prime360ClientService.entity.Profile;
import com.psx.prime360ClientService.entity.RequestResultsEntity;
import com.psx.prime360ClientService.relationalsearch.RelationalSearchDTO;
import com.psx.prime360ClientService.serviceI.RequestPostingServiceI;

/**
 *
 * @author jayantronald
 *
 */

@RequestMapping("/requestPosting")
@RestController
public class RequestPostingResourceHandler {
	//private static final Logger logger = LoggerFactory.getLogger(RequestPostingResourceHandler.class);

	 private static Logger logger = Logger.getLogger(RequestPostingResourceHandler.class.getName());
	
	@Autowired
	RequestPostingServiceI service;

	@Autowired
	DataSource dataSource;

	@GetMapping("/getAllRequestId")
	public List<String> getAllRequestId() {
		logger.info("RequestPostingResourceHandler getAllRequestId()");
		List<String> retValue = new ArrayList<>();
		retValue = service.getAllRequestId();
		logger.info("RequestPostingResourceHandler getAllRequestId() : " + retValue);
		return retValue;
	}

	@GetMapping("/getRecordsByRequestId/{requestId}")
	public RequestResultsEntity getRecordsBasedOnId(@PathVariable String requestId) {
		
		logger.info("RequestPostingResourceHandler getRecordsBasedOnId(-) requestId : " + requestId);
		
		NSPRequestEntity nspRequestEntity = service.getRecordsBasedOnId(requestId);
		logger.info("RequestPostingResourceHandler Object getRecordsBasedOnId(-) : " + nspRequestEntity);
		
		List<NSPRequestResultsEntity> nspRequestResultsEntity = service.getResultsBasedOnId(requestId);
		logger.info("RequestPostingResourceHandler List getRecordsBasedOnId(-) : " + nspRequestResultsEntity);
		
		RequestResultsEntity requestResults = new RequestResultsEntity();
		
		requestResults.setNspRequestEntity(nspRequestEntity);
		requestResults.setNspRequestResultsEntity(nspRequestResultsEntity);
		logger.info("RequestPostingResourceHandler getRecordsBasedOnId(-) :::::: " + requestResults);

		return requestResults;
	}

	
	@GetMapping("/getAllEquality")
	public List<String> getAllEquality() throws IOException {
		List<String> retValue = service.getEquality();
		logger.info("RequestPostingResourceHandler getAllEquality() :: " + retValue);
		return retValue;
	}

	@PostMapping("/submitIdentitySearchRequest/{operation}")
	public String submitIdentitySearchRequest(@RequestBody List<String> equalityData, @PathVariable String operation)
			throws Exception {
		logger.info("RequestPostingResourceHandler submitIdentitySearchRequest(-) : " + equalityData + " ; operation : "
				+ operation);
		String retValue = service.submitIdentitySearchRequest(equalityData, null, operation);
		logger.info("RequestPostingResourceHandler submitIdentitySearchRequest(-) retValue :: " + retValue);
		return retValue;
	}

	@GetMapping("/getIdentitySearchResultsByRequestId/{requestId}")
	public IdentitySearchDTO getIdentitySearchResultsByRequestId(@PathVariable String requestId) {
		logger.info("RequestPostingResourceHandler getIdentitySearchResultsByRequestId(-) requestId : " + requestId);
		IdentitySearchDTO retValue = service.getIdentitySearchResultsByRequestId(requestId);
		logger.info("RequestPostingResourceHandler getIdentitySearchResultsByRequestId(-) retValue : " + retValue);
		return retValue;
	}

	@GetMapping("/getAllActiveProfiles")
	public List<Profile> getAllActiveProfiles() {
		logger.info("RequestPostingResourceHandler getAllActiveProfiles()");
		return service.getAllActiveProfiles();
	}

	@GetMapping("/getCustomerSearchTable")
	public CustomerSearchDTO getCustomerSearchTable() {
		logger.info("RequestPostingResourceHandler getCustomerSearchTable()");
		return service.getCustomerSearchTable();
	}

	@PostMapping("/submitCustomerSearchRequest/{profileId}/{operation}")
	public String submitCustomerSearchRequest(@RequestBody CustomerSearchDTO customerSearchDTO,
			@PathVariable String profileId, @PathVariable String operation) throws Exception {
		logger.info("RequestPostingResourceHandler submitCustomerSearchRequest(-,-,-) customerSearchDTO : "
				+ customerSearchDTO + " , profileId : " + profileId + " , operation : " + operation);
		String dto = service.submitCustomerSearchRequest(customerSearchDTO, profileId, operation);
		logger.info("RequestPostingResourceHandler submitCustomerSearchRequest() : " + dto);
	
		
		return dto;
	}

	@GetMapping("/getAllRelationalProfiles")
	public List<Profile> getAllRelationalProfiles() {
		logger.info("RequestPostingResourceHandler getAllRelationalProfiles()");
		List<Profile> retValue = null;
		try {
			retValue = service.getAllRelationalProfiles();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return retValue;
	}

	@GetMapping("/getRelationalSearchTable")
	public RelationalSearchDTO getRelationalSearchTable() {
		logger.info("RequestPostingResourceHandler getRelationalSearchTable()");
		return service.getRelationalSearchTable();
	}

	@PostMapping("/submitRelationalSearchRequest/{profileId}/{operation}")
	public RelationalSearchDTO submitRelationalSearchRequest(@RequestBody RelationalSearchDTO relationalSearchDTO,
			@PathVariable String profileId, @PathVariable String operation) throws Exception {
		logger.info("RequestPostingResourceHandler submitRelationalSearchRequest(-,-,-) relationalSearchDTO : "
				+ relationalSearchDTO + " , profileId : " + profileId + " , operation : " + operation);
		RelationalSearchDTO dto = service.submitRelationalSearchRequest(relationalSearchDTO, profileId, operation);
		logger.info("RequestPostingResourceHandler submitCustomerSearchRequest() : " + dto);
		return dto;
	}

	// @PostMapping("/uploadeodfile")
	// public RelationalSearchDTO uploadeodfile(){
	// logger.info("RequestPostingResourceHandler getAllRelationalProfiles()");
	// return null;
	// }

}
