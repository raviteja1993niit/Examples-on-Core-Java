package com.psx.prime360ClientService.exception;


import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class Prime360CustomException extends RuntimeException{

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	
//	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ResourceNotFoundException.class);
	private static Logger logger = Logger.getLogger(Prime360CustomException.class.getName());
	public Prime360CustomException(String message) {
		super(message);
		logger.info("Prime360CustomException Started :: ");
	}

	
	

}

