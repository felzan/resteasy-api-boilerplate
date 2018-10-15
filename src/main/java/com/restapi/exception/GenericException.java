package com.restapi.exception;

public class GenericException extends Exception {

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private int httpStatusCode = 500;

	public GenericException(final int errorCode, final String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(final int errorCode) {
		this.errorCode = errorCode;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(final int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

}