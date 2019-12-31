package com.psx.prime360ClientService.exception;


import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.psx.prime360ClientService.resourcehandler.NegativeEodFileUploadHander;


/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
//	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ResourceNotFoundException.class);
	private static Logger logger = Logger.getLogger(ResourceNotFoundException.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 6913387373853077665L;
	
	private String resourceId ;
//	private Boolean sccusse;

	public ResourceNotFoundException(String resourceId, String message) {
		super(message);
		this.resourceId = resourceId;
		logger.info("ResourceNotFoundException started :: ");
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}
