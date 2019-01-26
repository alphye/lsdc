package com.lishengzn.exception;

public class SimpleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SimpleException() {
		super();
	}

	public SimpleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SimpleException(String message, Throwable cause) {
		super(message, cause);
	}

	public SimpleException(String message) {
		super(message);
	}

	public SimpleException(Throwable cause) {
		super(cause);
	}
	

}
