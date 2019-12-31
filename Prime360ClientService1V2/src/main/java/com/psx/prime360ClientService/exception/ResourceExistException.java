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
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceExistException extends RuntimeException{
//	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Prime360Exception.class);
	private static Logger logger = Logger.getLogger(ResourceExistException.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resourceId;

	public ResourceExistException(String resourceId, String message) {
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
