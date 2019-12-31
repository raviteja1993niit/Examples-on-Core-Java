package com.psx.prime360ClientService.exception;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
public class Response {
	
	private Date timestamp;
	private String message;
	private String details;
	private boolean success;
	private Collection<?> data;
	private List<?> errors;
	private Object object;

	public Response() {
		super();
	}

	public Response(String message, boolean success, Collection<?> data) {
		super();
		this.message = message;
		this.success = success;
		this.data = data;
	}

	public Response(Date timestamp, String message, String details, boolean success, Collection<?> data, List<?> errors,
			Object object) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.success = success;
		this.data = data;
		this.errors = errors;
		this.object = object;
	}

	public Response(Date timestamp, String message, boolean success) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.success = success;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Collection<?> getData() {
		return data;
	}

	public void setData(Collection<?> data) {
		this.data = data;
	}

	public List<?> getErrors() {
		return errors;
	}

	public void setErrors(List<?> errors) {
		this.errors = errors;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [timestamp=" + timestamp + ", message=" + message + ", details=" + details
				+ ", success=" + success + ", data=" + data + ", errors=" + errors + ", object=" + object + "]";
	}

}
