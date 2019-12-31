package com.psx.prime360ClientService.exception;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.psx.prime360ClientService.resourcehandler.NegativeEodFileUploadHander;


/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
@RestControllerAdvice
@RestController
public class Prime360Exception extends ResponseEntityExceptionHandler{
	
	//private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Prime360Exception.class);
	private static Logger logger = Logger.getLogger(Prime360Exception.class.getName());
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Response> handleAllExceptions(Exception ex, WebRequest request) {
		Response response = new Response(new Date(), ex.getMessage(), false);
		logger.info("Prime 360 Exception started :: "+ response);
		logger.error(ex.toString(),ex);
		ex.printStackTrace();
		return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<Response> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		Response response = new Response(new Date(), ex.getMessage(), false);
		logger.info("Prime 360 ResourceNotFoundException started :: " + response);
		logger.error(ex.getResourceId()+" : "+ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceExistException.class)
	public final ResponseEntity<Response> resourceExistException(ResourceExistException ex, WebRequest request) {
		Response response = new Response(new Date(), ex.getMessage(), false);
		logger.info("Prime 360  ResourceExistException started :: " + response);
		logger.error(ex.getResourceId()+" : "+ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(Prime360CustomException.class)
	public final ResponseEntity<Response> prime360CustomException(Prime360CustomException ex, WebRequest request) {
		Response response = new Response(new Date(), ex.getMessage(), false);
		logger.info("Prime 360  Prime360CustomException started :: " + response);
		logger.error(ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<Response>(response, HttpStatus.EXPECTATION_FAILED);
	}

}
