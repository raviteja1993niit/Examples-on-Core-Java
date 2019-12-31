
package com.psx.prime360ClientService.exception;

public class PosidexException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5657188683326586153L;

	/**
	 * Instantiates a new posidex exception.
	 */
	public PosidexException() {
		super();
	}

	/**
	 * Instantiates a new posidex exception.
	 * 
	 * @param errorMsg
	 *            the error msg
	 */
	public PosidexException(String errorMsg) {
		super(errorMsg);
		
	}

	/**
	 * Instantiates a new posidex exception.
	 * 
	 * @param t
	 *            the t
	 */
	public PosidexException(Throwable t) {
		super(t);
	}
	
}
