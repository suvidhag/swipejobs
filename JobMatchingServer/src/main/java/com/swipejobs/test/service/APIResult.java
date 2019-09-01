package com.swipejobs.test.service;

import org.springframework.http.HttpStatus;

public class APIResult<E> {

	public static final String OK = "Ok";

	/**
	 * Http Response status code
	 */
	private HttpStatus httpStatus;
	
	/**
	 * Http response error message
	 */
	private String errorMessage;
	

	/**
	 * expected result
	 */
	E value;

	public APIResult(E value) {
		this.value = value;
		this.errorMessage = OK;
	}

	public APIResult(HttpStatus status, String message) {
		this.httpStatus = status;
		this.errorMessage = message;
	}

	/**
	 * @return the httpStatus
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return the value
	 */
	public E getValue() {
		return value;
	}

}
